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

import dev.hypera.chameleon.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class EventSubscriberImpl<E extends ChameleonEvent> implements EventSubscriber<E> {

    private final @NotNull Class<E> type;

    private final @NotNull EventConsumer<E> handler;
    private final @NotNull EventSubscriptionPriority priority;
    private final boolean acceptsCancelled;

    private final @NotNull Collection<Predicate<E>> filters;
    private final @NotNull Predicate<E> expireWhen;
    private final @Nullable AtomicInteger expirationCount;

    private @Nullable EventSubscription subscription;

    EventSubscriberImpl(@NotNull Class<E> type, @NotNull EventConsumer<E> handler, @NotNull EventSubscriptionPriority priority, boolean acceptsCancelled, @NotNull Collection<Predicate<E>> filters, @NotNull Predicate<E> expireWhen, int expiresAfter) {
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
    public void on(@NotNull E event) throws Throwable {
        Preconditions.checkNotNull("event", event);
        if (this.subscription == null) {
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

        if (this.expirationCount != null && this.expirationCount.decrementAndGet() == 0) {
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
    public @NotNull Class<E> getType() {
        return this.type;
    }

    void setSubscription(@NotNull EventSubscription subscription) {
        Preconditions.checkNotNull("subscription", subscription);
        this.subscription = subscription;
    }

    static final class BuilderImpl<E extends ChameleonEvent> implements Builder<E> {

        private final @NotNull Class<E> type;
        private @Nullable EventConsumer<E> handler;
        private @NotNull EventSubscriptionPriority priority = EventSubscriptionPriority.NORMAL;
        private boolean acceptsCancelled = false;

        private final @NotNull Collection<Predicate<E>> filters = new ArrayList<>();
        private @NotNull Predicate<E> expireWhen = event -> false;
        private int expiresAfter = -1;

        BuilderImpl(@NotNull Class<E> type) {
            Preconditions.checkNotNull("type", type);
            this.type = type;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<E> handler(@NotNull EventConsumer<E> handler) {
            Preconditions.checkNotNull("handler", handler);
            this.handler = handler;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<E> priority(@NotNull EventSubscriptionPriority priority) {
            Preconditions.checkNotNull("priority", priority);
            this.priority = priority;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<E> acceptsCancelled(boolean acceptsCancelled) {
            this.acceptsCancelled = acceptsCancelled;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<E> filters(@NotNull Predicate<E> filter) {
            Preconditions.checkNotNull("filter", filter);
            this.filters.add(filter);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<E> filters(@NotNull Collection<Predicate<E>> filters) {
            Preconditions.checkNotNull("filters", filters);
            this.filters.addAll(filters);
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<E> expireAfter(int expiresAfter) {
            this.expiresAfter = expiresAfter;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<E> expireWhen(@NotNull Predicate<E> expireWhen) {
            Preconditions.checkNotNull("expireWhen", expireWhen);
            this.expireWhen = expireWhen;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull EventSubscriber<E> build() {
            Preconditions.checkState(this.handler != null, "handler is required");
            return new EventSubscriberImpl<>(
                this.type, Objects.requireNonNull(this.handler), this.priority,
                this.acceptsCancelled, this.filters, this.expireWhen, this.expiresAfter
            );
        }

    }

}
