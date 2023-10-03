/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2023 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.platform.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.hypera.chameleon.event.ChameleonEvent;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.event.EventBusImpl;
import dev.hypera.chameleon.logger.ChameleonNoopLogger;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

final class PlatformEventDispatcherTests {

    private final @NotNull EventBus eventBus = new EventBusImpl(new ChameleonNoopLogger());
    private final @NotNull PlatformEventDispatcherImpl eventDispatcher = new PlatformEventDispatcherImpl(this.eventBus);

    @Test
    void testListenerRegistration() {
        // Register listeners
        this.eventDispatcher.registerListeners();
        assertTrue(this.eventDispatcher.isRegistered());
        // Unregister listeners
        this.eventDispatcher.unregisterListeners();
        assertFalse(this.eventDispatcher.isRegistered());
    }

    @Test
    void testDispatch() {
        TestEvent event = this.eventDispatcher.handleEvent(new TestEvent());
        assertEquals(0, event.getTouches());

        // Add an event subscriber
        this.eventBus.subscribe(TestEvent.class, TestEvent::touch);

        // Dispatching the event again should now result in touches being 1
        this.eventDispatcher.handleEvent(event);
        assertEquals(1, event.getTouches());
    }

    private static final class PlatformEventDispatcherImpl extends PlatformEventDispatcher {

        private boolean registered = false;

        PlatformEventDispatcherImpl(@NotNull EventBus eventBus) {
            super(eventBus);
        }

        public <T extends ChameleonEvent> @NotNull T handleEvent(@NotNull T event) {
            return dispatch(event);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void registerListeners() {
            this.registered = true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void unregisterListeners() {
            this.registered = false;
            super.unregisterListeners();
        }

        boolean isRegistered() {
            return this.registered;
        }

    }

    private static final class TestEvent implements ChameleonEvent {

        private int touches = 0;

        void touch() {
            this.touches++;
        }

        int getTouches() {
            return this.touches;
        }

    }

}
