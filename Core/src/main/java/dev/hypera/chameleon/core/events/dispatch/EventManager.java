/*
 * Chameleon - Cross-platform Minecraft plugin framework
 * Copyright (c) 2021 Joshua Sing <joshua@hypera.dev>, SLLCoding <luisjk266@gmail.com>
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

package dev.hypera.chameleon.core.events.dispatch;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.annotations.MappedClass;
import dev.hypera.chameleon.core.events.ChameleonEvent;
import dev.hypera.chameleon.core.events.listener.ChameleonListener;
import dev.hypera.chameleon.core.events.listener.EventHandler;
import dev.hypera.chameleon.core.events.listener.InlineChameleonListener;
import dev.hypera.chameleon.core.events.listener.ListenerPriority;
import dev.hypera.chameleon.core.events.mapping.EventMapper;
import dev.hypera.chameleon.core.exceptions.MapFailedException;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EventManager {

	private final Chameleon chameleon;
	private final EventMapper mapper = new EventMapper();
	private final Set<ChameleonListener> registeredListeners = new HashSet<>();
	private final Set<InlineChameleonListener> registeredInlineListeners = new HashSet<>();
	private final Set<Class<? extends ChameleonEvent>> registeredChameleonEvents = new HashSet<>();

	@Internal
	public EventManager(@NotNull Chameleon chameleon) {
		this.chameleon = chameleon;
	}

	/**
	 * Register a chameleon listener.
	 * @param listener Listener to be registered.
	 * @throws IllegalArgumentException if the listener has already been registered.
	 */
	public void registerListener(@NotNull ChameleonListener listener) {
		if (registeredListeners.contains(listener)) {
			throw new IllegalArgumentException("Listener '" + listener.getClass().getCanonicalName() + "' has already been registered");
		}

		registeredListeners.add(listener);
	}

	/**
	 * Register an inline chameleon listener.
	 * @param event Event to listen for.
	 * @param listener Listener.
	 */
	public void registerListener(@NotNull Class<? extends ChameleonEvent> event, @NotNull Consumer<ChameleonEvent> listener) {
		registeredInlineListeners.add(new InlineChameleonListener(event, listener));
	}

	/**
	 * Unregister a chameleon listener.
	 * @param listener Listener to be unregistered.
	 * @throws IllegalArgumentException if the listener is not registered.
	 */
	public void unregisterListener(@NotNull ChameleonListener listener) {
		if (!registeredListeners.contains(listener)) {
			throw new IllegalArgumentException("Listener '" + listener.getClass().getCanonicalName() + "' is not registered");
		}

		registeredListeners.remove(listener);
	}

	/**
	 * Registers chameleon events. (Internal)
	 * @param events Chameleon events to be registered.
	 */
	@SafeVarargs
	@Internal
	public final void registerEvents(@NotNull Class<? extends ChameleonEvent>... events) {
		for (Class<? extends ChameleonEvent> event : events) {
			registerEvent(event);
		}
	}

	/**
	 * Registers a chameleon event. (Internal)
	 * @param event Chameleon event to be registered.
	 * @throws IllegalArgumentException if the event has already been registered or the event is not annotated with {@link MappedClass}.
	 */
	@Internal
	public void registerEvent(@NotNull Class<? extends ChameleonEvent> event) {
		if (registeredChameleonEvents.contains(event)) {
			throw new IllegalArgumentException("Event '" + event.getCanonicalName() + "' has already been registered");
		}

		if (!event.isAnnotationPresent(MappedClass.class)) {
			throw new IllegalArgumentException("Event '" + event.getCanonicalName() + "' is not annotated with @MappedClass");
		}

		registeredChameleonEvents.add(event);
	}

	/**
	 * Unregisters a chameleon event. (Internal)
	 * @param event Chameleon event to be unregistered.
	 * @throws IllegalArgumentException if the event is not registered.
	 */
	@Internal
	public void unregisterEvent(@NotNull Class<? extends ChameleonEvent> event) {
		if (!registeredChameleonEvents.contains(event)) {
			throw new IllegalArgumentException("Event '" + event.getCanonicalName() + "' is not registered");
		}

		registeredChameleonEvents.remove(event);
	}

	/**
	 * Get all registered chameleon events.
	 * @return Unmodifiable set of registered chameleon events.
	 */
	@Internal
	public Set<Class<? extends ChameleonEvent>> getRegisteredEvents() {
		return Collections.unmodifiableSet(registeredChameleonEvents);
	}

	/**
	 * Dispatch an event. (Internal)
	 * @param event Event to be dispatched.
	 * @return Mapped chameleon event. (null if a chameleon event was not found for the event)
	 * @throws MapFailedException if the event could not be mapped to a chameleon event.
	 */
	@Internal
	@Nullable
	public ChameleonEvent dispatch(Object event) throws MapFailedException {
		Optional<Class<? extends ChameleonEvent>> chameleonEvent = registeredChameleonEvents.stream().filter(e -> Arrays.stream(e.getAnnotation(MappedClass.class).value()).anyMatch(ec -> ec.equals(event.getClass().getCanonicalName()))).findFirst();
		if (chameleonEvent.isPresent()) {
			try {
				ChameleonEvent mappedEvent = mapper.map(chameleon, event, chameleonEvent.get());
				dispatchToListeners(mappedEvent);
				return mappedEvent;
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new MapFailedException("Failed to map event '" + event.getClass().getCanonicalName() + "' to " + chameleonEvent.get().getCanonicalName());
			}
		} else {
			return null;
		}
	}

	/**
	 * Dispatch an event to listeners. (Internal)
	 * @param event Chameleon event to be dispatched.
	 */
	@Internal
	private void dispatchToListeners(ChameleonEvent event) {
		List<EventMethod> handlers = new ArrayList<>();
		registeredListeners.forEach(listener -> handlers.addAll(Arrays.stream(listener.getClass().getDeclaredMethods()).filter(method -> method.isAnnotationPresent(EventHandler.class) && method.getParameterCount() == 1 && method.getParameterTypes()[0].isInstance(event)).map(m -> new EventMethod(m, listener)).collect(Collectors.toSet())));
		handlers.sort((o1, o2) -> ListenerPriority.getMethodComparator().compare(o1.getMethod(), o2.getMethod()));
		handlers.forEach(handler -> {
			try {
				handler.getMethod().setAccessible(true);
				handler.getMethod().invoke(handler.getOwner(), event);
			} catch (Exception ex) {
				chameleon.getLogger(this.getClass()).error("Failed to dispatch %s to %s", ex, event.getClass(), handler.getOwner().getClass());
			}
		});
		registeredInlineListeners.stream().filter(l -> l.getEvent().equals(event.getClass()) || l.getEvent().isAssignableFrom(event.getClass())).forEach(l -> l.getListener().accept(event));
	}

	@Internal
	private static class EventMethod {

		private final Method method;
		private final ChameleonListener owner;

		public EventMethod(Method method, ChameleonListener owner) {
			this.method = method;
			this.owner = owner;
		}

		public Method getMethod() {
			return method;
		}

		public ChameleonListener getOwner() {
			return owner;
		}

	}

}
