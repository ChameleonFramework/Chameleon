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

import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.ClientConnectEvent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PermissionCheckEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PlayerHandshakeEvent;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.event.ProxyReloadEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.event.SettingsChangedEvent;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.event.TabCompleteResponseEvent;
import net.md_5.bungee.api.plugin.Event;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * This is an adapter used for listening to all events at once.
 * (This only exists because BungeeCord doesn't allow us to listen to a specific event class and receive the event in a callback)
 */
public abstract class BungeeCordListenerAdapter implements Listener {

	public abstract void onEvent(Event event);

	@EventHandler
	public void onChatEvent(ChatEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onClientConnectEvent(ClientConnectEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onLoginEvent(LoginEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onPermissionCheckEvent(PermissionCheckEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onPlayerDisconnectEvent(PlayerDisconnectEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onPlayerHandshakeEvent(PlayerHandshakeEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onPluginMessageEvent(PluginMessageEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onPostLoginEvent(PostLoginEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onPreLoginEvent(PreLoginEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onProxyPingEvent(ProxyPingEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onProxyReloadEvent(ProxyReloadEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onServerConnectedEvent(ServerConnectedEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onServerConnectEvent(ServerConnectEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onServerDisconnectEvent(ServerDisconnectEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onServerKickEvent(ServerKickEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onServerSwitchEvent(ServerSwitchEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onSettingsChangedEvent(SettingsChangedEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onTabCompleteEvent(TabCompleteEvent event) {
		onEvent(event);
	}

	@EventHandler
	public void onTabCompleteResponseEvent(TabCompleteResponseEvent event) {
		onEvent(event);
	}

}
