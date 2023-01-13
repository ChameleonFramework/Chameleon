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
package dev.hypera.chameleon.event;

import java.util.function.Predicate;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Event bus.
 * <p>Inspired by <a href="https://github.com/KyoriPowered/event">KyoriPowered/event</a></p>
 */
@NonExtendable
public interface EventBus {

    /**
     * Dispatch an event to subscribers.
     *
     * @param event The event to be dispatched.
     */
    void dispatch(@NotNull ChameleonEvent event);

    /**
     * Register the given subscriber.
     *
     * @param event      The event type.
     * @param subscriber The event subscriber.
     * @param <T>        The event type.
     *
     * @return an event subscription.
     */
    <T extends ChameleonEvent> @NotNull EventSubscription subscribe(@NotNull Class<T> event, @NotNull EventSubscriber<T> subscriber);

    /**
     * Register the given subscriber.
     *
     * @param subscriber The event subscriber.
     * @param <T>        The event type.
     *
     * @return an event subscription.
     * @throws IllegalArgumentException if the given {@code subscriber} does not have a set type.
     */
    <T extends ChameleonEvent> @NotNull EventSubscription subscribe(@NotNull EventSubscriber<T> subscriber);

    /**
     * Determines whether the given event has been subscribed to.
     *
     * @param event The event type.
     *
     * @return {@code true} if the event has subscribers, otherwise {@code false}.
     */
    boolean subscribed(@NotNull Class<? extends ChameleonEvent> event);

    /**
     * Unregister subscribers matching the given predicate.
     *
     * @param predicate The predicate to test subscribers against.
     */
    void unsubscribeIf(@NotNull Predicate<EventSubscriber<? super ChameleonEvent>> predicate);

}
