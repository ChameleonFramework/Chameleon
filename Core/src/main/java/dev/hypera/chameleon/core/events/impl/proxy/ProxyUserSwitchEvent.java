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

package dev.hypera.chameleon.core.events.impl.proxy;

import dev.hypera.chameleon.core.annotations.MappedClass;
import dev.hypera.chameleon.core.annotations.MappedField;
import dev.hypera.chameleon.core.annotations.Transform;
import dev.hypera.chameleon.core.events.ChameleonEvent;
import dev.hypera.chameleon.core.objects.Server;
import dev.hypera.chameleon.core.users.ProxyUser;

/**
 * ProxyUserSwitchEvent - Dispatched whenever a player switches server.
 *
 * Currently supports:
 *  - BungeeCord (net.md_5.bungee.api.event.ServerSwitchEvent)
 *  - Velocity (com.velocitypowered.api.event.player.ServerConnectedEvent)
 */
@MappedClass({
		"net.md_5.bungee.api.event.ServerSwitchEvent",
		"com.velocitypowered.api.event.player.ServerConnectedEvent"
})
public class ProxyUserSwitchEvent implements ChameleonEvent {

	@Transform
	@MappedField("player")
	private ProxyUser player;

	@Transform
	@MappedField({ "from", "previousServer" })
	private Server server;


	public ProxyUser getPlayer() {
		return player;
	}

	public Server getServer() {
		return server;
	}

}
