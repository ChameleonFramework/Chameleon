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
import dev.hypera.chameleon.core.users.ChatUser;

/**
 * UserJoinEvent - Dispatched whenever a player connects to the server.
 *
 * Currently supports:
 *  - Bukkit (org.bukkit.event.player.PlayerJoinEvent)
 *  - BungeeCord (net.md_5.bungee.api.event.PostLoginEvent)
 *  - Minestom (net.minestom.server.event.player.PlayerLoginEvent)
 *  - Velocity (com.velocitypowered.api.event.connection.PostLoginEvent)
 */
@MappedClass({
		"org.bukkit.event.player.PlayerJoinEvent",
		"net.md_5.bungee.api.event.PostLoginEvent",
		"net.minestom.server.event.player.PlayerLoginEvent",
		"com.velocitypowered.api.event.connection.PostLoginEvent"
})
public class UserJoinEvent implements UserEvent {

	@Transform
	@MappedField("getPlayer()")
	private ChatUser player;


	public ChatUser getPlayer() {
		return player;
	}

}
