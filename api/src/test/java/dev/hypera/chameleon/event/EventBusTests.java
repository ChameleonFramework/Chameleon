/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 The Chameleon Framework Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.hypera.chameleon.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.hypera.chameleon.event.EventBus.ExceptionHandler;
import dev.hypera.chameleon.exception.ChameleonException;
import dev.hypera.chameleon.logger.DummyChameleonLogger;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

final class EventBusTests {

    private static final @NotNull ExceptionHandler NOP_EXCEPTION_HANDLER = (eventBus, subscriber, event, throwable) -> {};

    @Test
    void subscribeUnsubscribe() {
        EventBus eventBus = new EventBusImpl(NOP_EXCEPTION_HANDLER);
        assertFalse(eventBus.subscribed(TestEvent.class));

        EventSubscription subscription = eventBus.subscribe(TestEvent.class, TestEvent::touch);
        assertTrue(eventBus.subscribed(TestEvent.class));

        TestEvent event = new TestEvent(false);

        eventBus.dispatch(event);
        assertEquals(1, event.getTouches());

        subscription.unsubscribe();
        assertFalse(eventBus.subscribed(TestEvent.class));

        eventBus.dispatch(event);
        assertEquals(1, event.getTouches());
    }

    @Test
    void priority() throws Throwable {
        DummyChameleonLogger logger = new DummyChameleonLogger();
        EventBus eventBus = new EventBusImpl(logger);

        eventBus.subscribe(
            EventSubscriber.builder(TestEvent.class)
                .priority(EventSubscriptionPriority.MEDIUM)
                .handler(event -> {
                    assertEquals(1, event.getTouches());
                    event.touch();
                })
                .build()
        );

        eventBus.subscribe(
            EventSubscriber.builder(TestEvent.class)
                .priority(EventSubscriptionPriority.LOW)
                .handler(event -> {
                    assertEquals(2, event.getTouches());
                    event.touch();
                })
                .build()
        );

        eventBus.subscribe(
            EventSubscriber.builder(TestEvent.class)
                .priority(EventSubscriptionPriority.HIGH)
                .handler(event -> {
                    assertEquals(0, event.getTouches());
                    event.touch();
                })
                .build()
        );

        eventBus.dispatch(new TestEvent(false));

        for (Throwable throwable : logger.getExceptions()) {
            throw throwable;
        }
    }

    @Test
    void cancellable() {
        EventBus eventBus = new EventBusImpl(NOP_EXCEPTION_HANDLER);
        eventBus.subscribe(TestEvent.class, TestEvent::touch);

        TestEvent event = new TestEvent(false);
        eventBus.dispatch(event);
        assertEquals(1, event.getTouches());

        event.cancel();
        eventBus.dispatch(event);
        assertEquals(1, event.getTouches());

        TestEvent cancelledEvent = new TestEvent(true);
        eventBus.dispatch(cancelledEvent);
        assertEquals(0, cancelledEvent.getTouches());

        eventBus.subscribe(
            EventSubscriber.builder(TestEvent.class)
                .acceptCancelled()
                .handler(TestEvent::touch)
                .build()
        );
        eventBus.dispatch(cancelledEvent);
        assertEquals(1, cancelledEvent.getTouches());

        cancelledEvent.uncancel();
        eventBus.dispatch(cancelledEvent);
        assertEquals(3, cancelledEvent.getTouches());
    }

    @Test
    void receivesChildren() {
        EventBus eventBus = new EventBusImpl(NOP_EXCEPTION_HANDLER);
        eventBus.subscribe(ChameleonEvent.class, event -> {
            if (event instanceof TestEvent) {
                ((TestEvent) event).touch();
            }
        });

        TestEvent event = new TestEvent(false);
        eventBus.dispatch(event);
        assertEquals(1, event.getTouches());
    }

    @Test
    void expiresAfter() {
        EventBus eventBus = new EventBusImpl(NOP_EXCEPTION_HANDLER);
        eventBus.subscribe(
            EventSubscriber.builder(TestEvent.class)
                .expireAfter(1)
                .handler(TestEvent::touch)
                .build()
        );

        TestEvent event = new TestEvent(false);

        eventBus.dispatch(event);
        assertEquals(1, event.getTouches());

        eventBus.dispatch(event);
        assertFalse(eventBus.subscribed(TestEvent.class));
        assertEquals(1, event.getTouches());
    }

    @Test
    void expiresWhen() {
        EventBus eventBus = new EventBusImpl(NOP_EXCEPTION_HANDLER);
        eventBus.subscribe(
            EventSubscriber.builder(TestEvent.class)
                .expireWhen(e -> e.getTouches() == 1)
                .handler(TestEvent::touch)
                .build()
        );

        TestEvent event = new TestEvent(false);

        eventBus.dispatch(event);
        assertEquals(1, event.getTouches());

        eventBus.dispatch(event);
        assertFalse(eventBus.subscribed(TestEvent.class));
        assertEquals(1, event.getTouches());
    }

    @Test
    void filters() {
        EventBus eventBus = new EventBusImpl(NOP_EXCEPTION_HANDLER);
        eventBus.subscribe(
            EventSubscriber.builder(TestEvent.class)
                .filters(Collections.singleton(e -> e.getTouches() == 0 || e.getTouches() == 2))
                .filters(e -> e.getTouches() < 3)
                .handler(TestEvent::touch)
                .build()
        );

        TestEvent event = new TestEvent(false);
        eventBus.dispatch(event);
        assertEquals(1, event.getTouches());

        eventBus.dispatch(event);
        assertEquals(1, event.getTouches());
        event.touch();

        eventBus.dispatch(event);
        assertEquals(3, event.getTouches());

        eventBus.dispatch(event);
        assertEquals(3, event.getTouches());
    }

    @Test
    void testExceptionHandler() {
        EventBusImpl eventBus = new EventBusImpl(new DummyChameleonLogger());
        // Register subscriber that throws an exception
        eventBus.subscribe(TestEvent.class, e -> {
            throw new ChameleonException("Hello, world!");
        });

        // Exception should be handled by the default ExceptionHandler
        TestEvent event = new TestEvent(false);
        eventBus.dispatch(event);

        // #setExceptionHandler(ExceptionHandler) should change the exception handler
        TestExceptionHandler handler = new TestExceptionHandler();
        eventBus.setExceptionHandler(handler);

        // Exception should be handled by the new ExceptionHandler
        eventBus.dispatch(event);
        assertEquals(1, handler.getErrorCount());
    }

    static final class TestEvent extends AbstractCancellable implements ChameleonEvent {

        private int touches = 0;

        private TestEvent(boolean cancelled) {
            super(cancelled);
        }

        public void touch() {
            this.touches++;
        }

        public int getTouches() {
            return this.touches;
        }

    }

    static final class TestExceptionHandler implements ExceptionHandler {

        private int errorCount = 0;

        @Override
        public void handle(@NotNull EventBus eventBus, @NotNull EventSubscriber<? super ChameleonEvent> subscriber, @NotNull ChameleonEvent event, @NotNull Throwable throwable) {
            this.errorCount++;
        }

        public int getErrorCount() {
            return this.errorCount;
        }

    }

}
