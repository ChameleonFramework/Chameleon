/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.platform.bungeecord.event;

import dev.hypera.chameleon.event.common.UserChatEvent;
import dev.hypera.chameleon.event.common.UserConnectEvent;
import dev.hypera.chameleon.event.common.UserDisconnectEvent;
import dev.hypera.chameleon.event.proxy.ProxyUserConnectedEvent;
import dev.hypera.chameleon.event.proxy.ProxyUserServerConnectedEvent;
import dev.hypera.chameleon.platform.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.platform.bungeecord.platform.objects.BungeeCordServer;
import dev.hypera.chameleon.platform.event.PlatformEventDispatcher;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.user.ProxyUser;
import dev.hypera.chameleon.user.User;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord event dispatcher.
 */
public final class BungeeCordEventDispatcher extends PlatformEventDispatcher implements Listener {

    private final @NotNull BungeeCordChameleon chameleon;

    /**
     * BungeeCord event dispatcher constructor.
     *
     * @param chameleon Chameleon.
     */
    @Internal
    public BungeeCordEventDispatcher(@NotNull BungeeCordChameleon chameleon) {
        super(chameleon.getEventBus());
        this.chameleon = chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerListeners() {
        this.chameleon.getPlatformPlugin().getProxy().getPluginManager()
            .registerListener(this.chameleon.getPlatformPlugin(), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregisterListeners() {
        this.chameleon.getPlatformPlugin().getProxy().getPluginManager().unregisterListener(this);
    }

    /**
     * Platform user connect event handler.
     *
     * @param event Platform event.
     */
    @EventHandler
    public void onPostLoginEvent(@NotNull PostLoginEvent event) {
        User user = this.chameleon.getUserManager().wrapUser(event.getPlayer());
        UserConnectEvent chameleonEvent = dispatch(new UserConnectEvent(user, false));

        // Cancel platform event
        if (chameleonEvent.isCancelled()) {
            user.disconnect(chameleonEvent.getCancelReason());
        }
    }

    /**
     * Platform user chat event handler.
     *
     * @param event Platform event.
     */
    @EventHandler
    public void onChatEvent(@NotNull ChatEvent event) {
        UserChatEvent chameleonEvent = dispatch(new UserChatEvent(
            this.chameleon.getUserManager().wrapUser(event.getSender()),
            event.getMessage(), event.isCancelled(),
            true, true
        ));

        // Update message
        if (!event.getMessage().equals(chameleonEvent.getMessage())) {
            event.setMessage(chameleonEvent.getMessage());
        }

        // Cancel platform event
        if (chameleonEvent.isCancelled() != event.isCancelled()) {
            event.setCancelled(chameleonEvent.isCancelled());
        }
    }

    /**
     * Platform user disconnect event handler.
     *
     * @param event Platform event.
     */
    @EventHandler
    public void onPlayerDisconnectEvent(@NotNull PlayerDisconnectEvent event) {
        dispatch(new UserDisconnectEvent(this.chameleon.getUserManager()
            .wrapUser(event.getPlayer())));
    }

    /**
     * Platform proxy user switch event handler.
     *
     * @param event Platform event.
     */
    @EventHandler
    public void onServerSwitchEvent(@NotNull ServerSwitchEvent event) {
        ProxyUser user = (ProxyUser) this.chameleon.getUserManager().wrapUser(event.getPlayer());
        Server server = wrapServer(event.getPlayer().getServer().getInfo());

        if (event.getFrom() == null) {
            // Dispatched on initial connection only.
            dispatch(new ProxyUserConnectedEvent(user, server));
        }

        dispatch(new ProxyUserServerConnectedEvent(user, server,
            event.getFrom() != null ? wrapServer(event.getFrom()) : null));
    }

    @Contract(value = "_ -> new", pure = true)
    private @NotNull Server wrapServer(@NotNull ServerInfo server) {
        return new BungeeCordServer(this.chameleon, server);
    }

}
