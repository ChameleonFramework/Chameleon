/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2023 The Chameleon Framework Authors.
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
import dev.hypera.chameleon.event.proxy.ProxyUserSwitchEvent;
import dev.hypera.chameleon.platform.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.platform.bungeecord.platform.objects.BungeeCordServer;
import dev.hypera.chameleon.platform.bungeecord.user.BungeeCordUser;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.user.ProxyUser;
import dev.hypera.chameleon.user.User;
import java.util.Optional;
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
 * BungeeCord listener.
 */
@Internal
@SuppressWarnings("unused")
public final class BungeeCordListener implements Listener {

    private final @NotNull BungeeCordChameleon chameleon;

    /**
     * BungeeCord listener constructor.
     *
     * @param chameleon BungeeCord Chameleon implementation.
     */
    @Internal
    public BungeeCordListener(@NotNull BungeeCordChameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * Platform user connect event handler.
     *
     * @param event Platform event.
     */
    @EventHandler
    public void onPostLoginEvent(@NotNull PostLoginEvent event) {
        User user = wrap(event.getPlayer());
        UserConnectEvent chameleonEvent = new UserConnectEvent(user, false);

        this.chameleon.getEventBus().dispatch(chameleonEvent);
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
        UserChatEvent chameleonEvent = new UserChatEvent(
            wrap((ProxiedPlayer) event.getSender()),
            event.getMessage(),
            event.isCancelled()
        );
        this.chameleon.getEventBus().dispatch(chameleonEvent);

        if (!event.getMessage().equals(chameleonEvent.getMessage())) {
            event.setMessage(chameleonEvent.getMessage());
        }

        if (chameleonEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    /**
     * Platform user disconnect event handler.
     *
     * @param event Platform event.
     */
    @EventHandler
    public void onPlayerDisconnectEvent(@NotNull PlayerDisconnectEvent event) {
        this.chameleon.getEventBus().dispatch(new UserDisconnectEvent(wrap(event.getPlayer())));
    }

    /**
     * Platform proxy user switch event handler.
     *
     * @param event Platform event.
     */
    @EventHandler
    public void onServerSwitchEvent(@NotNull ServerSwitchEvent event) {
        this.chameleon.getEventBus()
            .dispatch(new ProxyUserSwitchEvent(wrap(event.getPlayer()),
                Optional.ofNullable(event.getFrom()).map(this::wrap).orElse(null),
                wrap(event.getPlayer().getServer().getInfo())
            ));
    }

    private @NotNull ProxyUser wrap(@NotNull ProxiedPlayer player) {
        return new BungeeCordUser(this.chameleon, player);
    }

    private @NotNull Server wrap(@NotNull ServerInfo server) {
        return new BungeeCordServer(this.chameleon, server);
    }

}
