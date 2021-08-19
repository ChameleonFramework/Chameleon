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

package dev.hypera.chameleon.core.events.impl.common;

import dev.hypera.chameleon.core.annotations.MappedClass;
import dev.hypera.chameleon.core.annotations.MappedField;
import dev.hypera.chameleon.core.annotations.Transform;
import dev.hypera.chameleon.core.events.Cancellable;
import dev.hypera.chameleon.core.users.ChatUser;
import net.kyori.adventure.text.Component;

/**
 * UserChatEvent - Dispatched whenever a player sends a chat message.
 *
 * Currently supports:
 *  - Bukkit (org.bukkit.event.player.AsyncPlayerChatEvent)
 *  - BungeeCord (net.md_5.bungee.api.event.ChatEvent)
 *  - Minestom (net.minestom.server.event.player.PlayerChatEvent)
 *  - Velocity (com.velocitypowered.api.event.player.PlayerChatEvent)
 */
@MappedClass({
		"org.bukkit.event.player.AsyncPlayerChatEvent",
		"net.md_5.bungee.api.event.ChatEvent",
		"net.minestom.server.event.player.PlayerChatEvent",
		"com.velocitypowered.api.event.player.PlayerChatEvent"
})
public class UserChatEvent implements UserEvent, Cancellable {

	@Transform
	@MappedField({ "getPlayer()", "getSender()" })
	private ChatUser sender;

	@Transform
	@MappedField("message")
	private Component message;

	@Transform
	@MappedField({ "cancel", "cancelled", "result" })
	private boolean cancelled;


	public ChatUser getSender() {
		return sender;
	}

	public Component getMessage() {
		return message;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

}
