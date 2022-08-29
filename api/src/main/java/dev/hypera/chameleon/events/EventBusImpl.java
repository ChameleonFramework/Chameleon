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

import dev.hypera.chameleon.events.cancellable.Cancellable;
import dev.hypera.chameleon.logging.ChameleonLogger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * {@link EventBus} implementation.
 */
@Internal
public final class EventBusImpl implements EventBus {

    private static final @NotNull Comparator<EventSubscriber<? super ChameleonEvent>> PRIORITY_COMPARATOR = Comparator.comparingInt(EventSubscriber::getPriority);

    private final @NotNull ChameleonLogger logger;
    private final @NotNull Map<Class<? extends ChameleonEvent>, Set<EventSubscriber<? super ChameleonEvent>>> subscriptions = new ConcurrentHashMap<>();
    private final @NotNull Map<Class<? extends ChameleonEvent>, List<EventSubscriber<? super ChameleonEvent>>> sortedSubscriptions = new ConcurrentHashMap<>();

    /**
     * {@link EventBusImpl} constructor.
     *
     * @param logger {@link ChameleonLogger} instance.
     */
    @Internal
    public EventBusImpl(@NotNull ChameleonLogger logger) {
        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispatch(@NotNull ChameleonEvent event) {
        List<EventSubscriber<? super ChameleonEvent>> subscribers = getSubscribers(event.getClass());

        synchronized (subscribers) {
            subscribers.iterator().forEachRemaining(subscriber -> {
                if ((event instanceof Cancellable && !((Cancellable) event).isCancelled()) || subscriber.acceptsCancelled()) {
                    try {
                        subscriber.on(event);
                    } catch (Exception ex) {
                        this.logger.error("An error occurred while dispatching an event to " + subscriber.getClass().getCanonicalName(), ex);
                    }
                }
            });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ChameleonEvent> @NotNull EventSubscription subscribe(@NotNull Class<T> event, @NotNull EventSubscriber<T> subscriber) {
        this.subscriptions.computeIfAbsent(event, key -> Collections.synchronizedSet(new HashSet<>())).add((EventSubscriber<ChameleonEvent>) subscriber);
        this.sortedSubscriptions.clear();

        EventSubscription subscription = () -> unsubscribeIf(sub -> sub.equals(subscriber));
        if (subscriber instanceof EventSubscriberImpl) {
            ((EventSubscriberImpl<T>) subscriber).setSubscription(subscription);
        }

        return subscription;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean subscribed(@NotNull Class<? extends ChameleonEvent> event) {
        return this.subscriptions.entrySet().stream().anyMatch(entry -> entry.getKey().isAssignableFrom(event) && !entry.getValue().isEmpty());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unsubscribeIf(@NotNull Predicate<EventSubscriber<? super ChameleonEvent>> predicate) {
        boolean removed = false;
        for (Set<EventSubscriber<? super ChameleonEvent>> subscribers : this.subscriptions.values()) {
            removed |= subscribers.removeIf(predicate);
        }

        if (removed) {
            this.sortedSubscriptions.clear();
        }
    }

    private @NotNull List<EventSubscriber<? super ChameleonEvent>> getSubscribers(@NotNull Class<? extends ChameleonEvent> event) {
        List<EventSubscriber<? super ChameleonEvent>> subscribers = this.sortedSubscriptions.entrySet().stream()
            .filter(entry -> entry.getKey().isAssignableFrom(event)).map(Entry::getValue).findFirst().orElse(null);

        if (null == subscribers) {
            if (subscribed(event)) {
                List<EventSubscriber<? super ChameleonEvent>> sortedSubscribers = Collections.synchronizedList(new ArrayList<>(this.subscriptions.entrySet().stream()
                    .filter(entry -> entry.getKey().isAssignableFrom(event)).map(Entry::getValue).findFirst().orElse(Collections.synchronizedSet(new HashSet<>()))));

                sortedSubscribers.sort(PRIORITY_COMPARATOR);
                this.sortedSubscriptions.put(event, sortedSubscribers);
                return sortedSubscribers;
            }

            return new ArrayList<>();
        }

        return subscribers;
    }


}
