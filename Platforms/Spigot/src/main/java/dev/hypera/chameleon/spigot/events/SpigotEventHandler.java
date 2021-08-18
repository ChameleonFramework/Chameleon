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

package dev.hypera.chameleon.spigot.events;

import dev.hypera.chameleon.core.annotations.MappedClass;
import dev.hypera.chameleon.core.events.Cancellable;
import dev.hypera.chameleon.core.events.ChameleonEvent;
import dev.hypera.chameleon.core.exceptions.MapFailedException;
import dev.hypera.chameleon.spigot.SpigotChameleon;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class SpigotEventHandler implements Listener {

	public SpigotEventHandler(SpigotChameleon chameleon) {
		chameleon.getEventDispatcher().getRegisteredEvents().forEach(event -> {
			Optional<Class<?>> bukkitEvent = Optional.empty();
			for (String className : event.getAnnotation(MappedClass.class).value()) {
				try {
					Class<?> clazz = Class.forName(className);
					if (Event.class.isAssignableFrom(clazz)) {
						bukkitEvent = Optional.of(clazz);
						break;
					}
				} catch (Exception ignored) {

				}
			}

			bukkitEvent.ifPresent(clazz -> Bukkit.getPluginManager()
					.registerEvent((Class<? extends Event>) clazz, this, EventPriority.NORMAL, (listener, e) -> {
						try {
							ChameleonEvent chameleonEvent = chameleon.getEventDispatcher().dispatch(e);
							if (chameleonEvent instanceof Cancellable && e instanceof org.bukkit.event.Cancellable) {
								if (((Cancellable) chameleonEvent).isCancelled()) {
									((org.bukkit.event.Cancellable) e).setCancelled(true);
								}
							}
						} catch (MapFailedException ignored) {
							// TODO: Handle this error!
						}
					}, chameleon.getSpigotPlugin()));
		});
	}

}
