/*
 * Chameleon - Cross-platform Minecraft plugin framework
 * Copyright (c) 2021 Joshua Sing <joshua@hypera.dev>
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

package dev.hypera.chameleon.minestom.events;

import dev.hypera.chameleon.core.annotations.MappedClass;
import dev.hypera.chameleon.core.events.Cancellable;
import dev.hypera.chameleon.core.events.ChameleonEvent;
import dev.hypera.chameleon.core.exceptions.MapFailedException;
import dev.hypera.chameleon.minestom.MinestomChameleon;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.trait.CancellableEvent;

import java.util.Optional;

public class MinestomEventHandler {

	public MinestomEventHandler(MinestomChameleon chameleon) {
		EventNode<Event> eventNode = chameleon.getExtension().getEventNode();
		chameleon.getEventManager().getRegisteredEvents().forEach(event -> {
			Optional<Class<?>> minestomEvent = Optional.empty();
			for (String className : event.getAnnotation(MappedClass.class).value()) {
				try {
					Class<?> clazz = Class.forName(className);
					if (Event.class.isAssignableFrom(clazz)) {
						minestomEvent = Optional.of(clazz);
						break;
					}
				} catch (Exception ignored) {

				}
			}

			minestomEvent.ifPresent(clazz -> eventNode.addListener((Class<Event>) clazz, (e) -> {
				try {
					ChameleonEvent chameleonEvent = chameleon.getEventManager().dispatch(e);
					if (chameleonEvent instanceof Cancellable && e instanceof CancellableEvent) {
						if (((Cancellable) chameleonEvent).isCancelled()) {
							((CancellableEvent) e).setCancelled(true);
						}
					}
				} catch (MapFailedException ex) {
					chameleon.getLogger(this.getClass()).error(ex.getMessage(), ex);
				}
			}));
		});
	}

}
