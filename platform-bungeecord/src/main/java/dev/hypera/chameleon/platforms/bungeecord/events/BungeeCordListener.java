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

package dev.hypera.chameleon.platforms.bungeecord.events;

import dev.hypera.chameleon.core.events.impl.common.UserChatEvent;
import dev.hypera.chameleon.core.events.impl.common.UserConnectEvent;
import dev.hypera.chameleon.core.events.impl.common.UserDisconnectEvent;
import dev.hypera.chameleon.core.events.impl.proxy.ProxyUserSwitchEvent;
import dev.hypera.chameleon.core.platform.proxy.Server;
import dev.hypera.chameleon.core.users.platforms.ProxyUser;
import dev.hypera.chameleon.platforms.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.platforms.bungeecord.platform.objects.BungeeCordServer;
import dev.hypera.chameleon.platforms.bungeecord.users.BungeeCordUser;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord listener
 */
@Internal
@SuppressWarnings("unused")
public class BungeeCordListener implements Listener {

	private final @NotNull BungeeCordChameleon chameleon;

	public BungeeCordListener(@NotNull BungeeCordChameleon chameleon) {
		this.chameleon = chameleon;
	}


	@EventHandler
	public void onPostLoginEvent(@NotNull PostLoginEvent event) {
		chameleon.getEventManager().dispatch(new UserConnectEvent(wrap(event.getPlayer())));
	}

	@EventHandler
	public void onChatEvent(@NotNull ChatEvent event) {
		chameleon.getEventManager().dispatch(new UserChatEvent(wrap((ProxiedPlayer) event.getSender()), event.getMessage()));
	}

	@EventHandler
	public void onPlayerDisconnectEvent(@NotNull PlayerDisconnectEvent event) {
		chameleon.getEventManager().dispatch(new UserDisconnectEvent(wrap(event.getPlayer())));
	}

	@EventHandler
	public void onServerSwitchEvent(@NotNull ServerSwitchEvent event) {
		chameleon.getEventManager().dispatch(new ProxyUserSwitchEvent(wrap(event.getPlayer()), wrap(event.getFrom()), wrap(event.getPlayer().getServer().getInfo())));
	}


	private @NotNull ProxyUser wrap(@NotNull ProxiedPlayer player) {
		return new BungeeCordUser(chameleon, player);
	}

	private @NotNull Server wrap(@NotNull ServerInfo server) {
		return new BungeeCordServer(chameleon, server);
	}

}
