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

package dev.hypera.chameleon.bungeecord.events;

import dev.hypera.chameleon.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.core.annotations.MappedClass;
import dev.hypera.chameleon.core.events.Cancellable;
import dev.hypera.chameleon.core.events.ChameleonEvent;
import dev.hypera.chameleon.core.exceptions.MapFailedException;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class BungeeCordEventHandler implements Listener {

	public BungeeCordEventHandler(BungeeCordChameleon chameleon) {
		Set<Class<?>> bungeeEvents = new HashSet<>();
		chameleon.getEventManager().getRegisteredEvents().forEach(event -> {
			Optional<Class<?>> bungeeEvent = Optional.empty();
			for (String className : event.getAnnotation(MappedClass.class).value()) {
				try {
					Class<?> clazz = Class.forName(className);
					if (Event.class.isAssignableFrom(clazz)) {
						bungeeEvent = Optional.of(clazz);
						break;
					}
				} catch (Exception ignored) {

				}
			}

			bungeeEvent.ifPresent(bungeeEvents::add);
		});

		if (bungeeEvents.size() > 0) {
			ProxyServer.getInstance().getPluginManager().registerListener(chameleon.getBungeeCordPlugin(), new BungeeCordListenerAdapter() {
				@Override
				public void onEvent(Event e) {
					try {
						if (bungeeEvents.contains(e.getClass())) {
							ChameleonEvent chameleonEvent = chameleon.getEventManager().dispatch(e);
							if (chameleonEvent instanceof Cancellable && e instanceof net.md_5.bungee.api.plugin.Cancellable) {
								if (((Cancellable) chameleonEvent).isCancelled()) {
									((Cancellable) e).setCancelled(true);
								}
							}
						}
					} catch (MapFailedException ignored) {
						// TODO: Handle this error!
					}
				}
			});
		}
	}

}
