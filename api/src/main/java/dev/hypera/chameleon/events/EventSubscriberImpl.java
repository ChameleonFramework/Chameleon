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
package dev.hypera.chameleon.events;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class EventSubscriberImpl<T extends ChameleonEvent> implements EventSubscriber<T> {

    private final @NotNull Class<T> type;

    private final @NotNull EventSubscriber<T> handler;
    private final @NotNull EventSubscriptionPriority priority;
    private final boolean acceptsCancelled;

    private final @NotNull List<Predicate<T>> filters;
    private final @NotNull Predicate<T> expireWhen;
    private final @Nullable AtomicInteger expirationCount;

    private @Nullable EventSubscription subscription;

    EventSubscriberImpl(@NotNull Class<T> type, @NotNull EventSubscriber<T> handler, @NotNull EventSubscriptionPriority priority, boolean acceptsCancelled, @NotNull List<Predicate<T>> filters, @NotNull Predicate<T> expireWhen, int expiresAfter) {
        this.type = type;
        this.handler = handler;
        this.priority = priority;
        this.acceptsCancelled = acceptsCancelled;

        this.filters = filters;
        this.expireWhen = expireWhen;
        this.expirationCount = expiresAfter > 0 ? new AtomicInteger(expiresAfter) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void on(@NotNull T event) throws Exception {
        if (null == this.subscription) {
            throw new IllegalStateException();
        }

        if (!this.filters.isEmpty() && !this.filters.stream().allMatch(filter -> filter.test(event))) {
            return;
        }

        if (this.expireWhen.test(event)) {
            this.subscription.unsubscribe();
            return;
        }

        this.handler.on(event);

        if (null != this.expirationCount && this.expirationCount.decrementAndGet() == 0) {
            this.subscription.unsubscribe();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull EventSubscriptionPriority getPriority() {
        return this.priority;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean acceptsCancelled() {
        return this.acceptsCancelled;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Class<T> getType() {
        return this.type;
    }

    void setSubscription(@NotNull EventSubscription subscription) {
        this.subscription = subscription;
    }

    static final class BuilderImpl<T extends ChameleonEvent> implements Builder<T> {

        private final @NotNull Class<T> type;
        private @Nullable EventSubscriber<T> handler;
        private @NotNull EventSubscriptionPriority priority = EventSubscriptionPriority.NORMAL;
        private boolean acceptsCancelled = false;

        private final @NotNull List<Predicate<T>> filters = new ArrayList<>();
        private @NotNull Predicate<T> expireWhen = event -> false;
        private int expiresAfter = -1;

        BuilderImpl(@NotNull Class<T> type) {
            this.type = type;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<T> handler(@NotNull Consumer<T> handler) {
            this.handler = handler::accept;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<T> priority(@NotNull EventSubscriptionPriority priority) {
            this.priority = priority;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<T> acceptsCancelled(boolean acceptsCancelled) {
            this.acceptsCancelled = acceptsCancelled;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<T> filter(Predicate<T> filter) {
            this.filters.add(filter);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<T> expireAfter(int expiresAfter) {
            this.expiresAfter = expiresAfter;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<T> expireWhen(@NotNull Predicate<T> expireWhen) {
            this.expireWhen = expireWhen;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull EventSubscriber<T> build() {
            if (null == this.handler) {
                throw new IllegalStateException("handler must be provided");
            }

            return new EventSubscriberImpl<>(this.type, this.handler, this.priority, this.acceptsCancelled, this.filters, this.expireWhen, this.expiresAfter);
        }

    }

}
