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
package dev.hypera.chameleon.core.events;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.events.listener.ChameleonListener;
import dev.hypera.chameleon.core.events.listener.InlineChameleonListener;
import dev.hypera.chameleon.core.events.listener.annotations.EventHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Event manager
 */
public final class EventManager {

	private final @NotNull Chameleon chameleon;
	private final @NotNull Set<ChameleonListener> registeredListeners = new HashSet<>();

	@Internal
	public EventManager(@NotNull Chameleon chameleon) {
		this.chameleon = chameleon;
	}

	/**
	 * Register a listener
	 *
	 * @param listener Listener to be registered
	 */
	public void registerListener(@NotNull ChameleonListener listener) {
		registeredListeners.add(listener);
	}

	/**
	 * Unregister a listener
	 *
	 * @param listener Listener to be unregistered
	 */
	public void unregisterListener(@NotNull ChameleonListener listener) {
		registeredListeners.remove(listener);
	}

	/**
	 * Register an inline listener
	 *
	 * @param type  Event type
	 * @param event Event consumer
	 * @param <T>   Event type
	 */
	public <T extends ChameleonEvent> void registerListener(@NotNull Class<T> type, @NotNull Consumer<T> event) {
		registerListener(new InlineChameleonListener<>(type, event));
	}


	/**
	 * Dispatch an event to registered listeners
	 *
	 * @param event Event to be dispatched
	 */
	@Internal
	public void dispatch(@NotNull ChameleonEvent event) {
		registeredListeners.stream()
				.map(listener -> Arrays.stream(listener.getClass().getDeclaredMethods()).filter(method ->
						method.isAnnotationPresent(EventHandler.class) &&
								method.getParameterCount() == 1 &&
								method.getParameterTypes()[0].isInstance(event)
				).findFirst().map(m -> new EventMethod(m, listener)).orElse(null)).filter(Objects::nonNull)
				.sorted(Comparator.comparingInt(m -> m.getMethod().getAnnotation(EventHandler.class).value().getPriority())).forEachOrdered(method -> {
					try {
						method.getMethod().invoke(method.getListener(), event);
					} catch (IllegalAccessException | InvocationTargetException ex) {
						chameleon.getInternalLogger().error(
								"Failed to dispatch event '%s' to method '%s' of '%s'", ex,
								event.getClass().getSimpleName(),
								method.getMethod().toGenericString(),
								method.getListener().getClass().getCanonicalName()
						);
					}
				});
	}

	@Internal
	private static class EventMethod {

		private final @NotNull Method method;
		private final @NotNull ChameleonListener listener;

		public EventMethod(@NotNull Method method, @NotNull ChameleonListener listener) {
			this.method = method;
			this.listener = listener;
		}

		public @NotNull Method getMethod() {
			return method;
		}

		public @NotNull ChameleonListener getListener() {
			return listener;
		}

	}

}
