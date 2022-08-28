/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package dev.hypera.chameleon.events;

import org.jetbrains.annotations.NotNull;

/**
 * An event subscriber, used to listen for events.
 *
 * @param <T> Event type.
 */
@FunctionalInterface
public interface EventSubscriber<T extends ChameleonEvent> {

    /**
     * Executed when this event is dispatched.
     *
     * @param event Dispatched event.
     *
     * @throws Exception if something goes wrong while handling this event.
     */
    void on(@NotNull T event) throws Exception;

    /**
     * Get the priority of this subscriber.
     * <p>Defaults to {@code 0} (normal)</p>
     *
     * @return subscriber priority.
     */
    default int getPriority() {
        return EventSubscriptionPriority.NORMAL;
    }

    /**
     * Whether this subscriber should be given cancelled events.
     * <p>Defaults to {@code false}</p>
     *
     * @return {@code true} if cancelled events should be handled, otherwise {@code false}.
     */
    default boolean acceptsCancelled() {
        return false;
    }

}
