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

package dev.hypera.chameleon.velocity.events;

import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.ResultedEvent.Result;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.event.command.CommandExecuteEvent.CommandResult;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.KickedFromServerEvent.ServerKickResult;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent.ChatResult;
import dev.hypera.chameleon.core.annotations.MappedClass;
import dev.hypera.chameleon.core.events.Cancellable;
import dev.hypera.chameleon.core.events.ChameleonEvent;
import dev.hypera.chameleon.core.exceptions.MapFailedException;
import dev.hypera.chameleon.core.exceptions.TransformFailedException;
import dev.hypera.chameleon.core.transformers.Transformer;
import dev.hypera.chameleon.velocity.VelocityChameleon;
import java.lang.reflect.ParameterizedType;
import net.kyori.adventure.bossbar.BossBar.Listener;

import java.util.Optional;

public class VelocityEventHandler implements Listener {

	public VelocityEventHandler(VelocityChameleon chameleon) {
		chameleon.getEventManager().getRegisteredEvents().forEach(event -> {
			Optional<Class<?>> velocityEvent = Optional.empty();
			for (String className : event.getAnnotation(MappedClass.class).value()) {
				try {
					Class<?> clazz = Class.forName(className);
					velocityEvent = Optional.of(clazz);
					break;
				} catch (Exception ignored) {

				}
			}

			velocityEvent.ifPresent(clazz -> chameleon.getVelocityPlugin().getServer().getEventManager().register(chameleon.getVelocityPlugin(), clazz, (e) -> {
				if (chameleon.getPlugin().getData().isAsyncEvents()) {
					chameleon.getScheduler().runAsync(() -> handleEvent(chameleon, e));
				} else {
					handleEvent(chameleon, e);
				}
			}));
		});
	}

	private void handleEvent(VelocityChameleon chameleon, Object e) {
		try {
			ChameleonEvent chameleonEvent = chameleon.getEventManager().dispatch(e);
			if (chameleonEvent instanceof Cancellable && e instanceof ResultedEvent<?>) {
				if (((Cancellable) chameleonEvent).isCancelled()) {
					cancelEvent((ResultedEvent<?>) e);
				}
			}
		} catch (MapFailedException ex) {
			chameleon.getLogger(this.getClass()).error(ex.getMessage(), ex);
		}
	}

	private void cancelEvent(ResultedEvent<?> event) {
		if (event instanceof CommandExecuteEvent) {
			((CommandExecuteEvent) event).setResult(CommandResult.denied());
		} else if (event instanceof PlayerChatEvent) {
			((PlayerChatEvent) event).setResult(ChatResult.denied());
		}
	}

}
