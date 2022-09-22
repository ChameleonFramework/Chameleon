/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.events;

import dev.hypera.chameleon.events.EventSubscriberImpl.BuilderImpl;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An event subscriber, used to listen for events.
 *
 * @param <T> Event type.
 */
@FunctionalInterface
public interface EventSubscriber<T extends ChameleonEvent> {

    /**
     * Create a new event subscriber builder.
     *
     * @param type Dummy event type class, only used to enforce the type.
     * @param <T>  Event type.
     *
     * @return new builder.
     */
    static <T extends ChameleonEvent> @NotNull Builder<T> builder(@NotNull Class<T> type) {
        return new BuilderImpl<>();
    }


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


    /**
     * Event subscriber builder.
     *
     * @param <T> Event type.
     */
    @NonExtendable
    interface Builder<T extends ChameleonEvent> {

        /**
         * Set the event handler.
         *
         * @param handler Event handler.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder<T> handler(@NotNull Consumer<T> handler);

        /**
         * Set the subscriber priority.
         *
         * @param priority Priority.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder<T> priority(int priority);

        /**
         * Accept cancelled events.
         *
         * @return {@code this}.
         */
        @Contract("-> this")
        default @NotNull Builder<T> acceptCancelled() {
            return acceptsCancelled(true);
        }

        /**
         * Set whether this subscriber should accept cancelled events.
         *
         * @param acceptsCancelled Whether this subscriber should accept cancelled events.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder<T> acceptsCancelled(boolean acceptsCancelled);

        /**
         * Set a filter for this subscriber.
         *
         * @param filter Event filter.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder<T> filter(Predicate<T> filter);

        /**
         * Expire when this predicate returns {@code true}.
         *
         * @param expireWhen Expire when.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder<T> expireWhen(@NotNull Predicate<T> expireWhen);

        /**
         * Expire after {@code expiresAfter} executions.
         *
         * @param expiresAfter Expires after.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder<T> expireAfter(int expiresAfter);

        /**
         * Build.
         *
         * @return new {@link EventSubscriber} instance.
         */
        @Contract(value = "-> new", pure = true)
        @NotNull EventSubscriber<T> build();

    }

}
