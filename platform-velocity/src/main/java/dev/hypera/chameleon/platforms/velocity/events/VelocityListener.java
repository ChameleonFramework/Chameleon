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
package dev.hypera.chameleon.platforms.velocity.events;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent.ChatResult;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.hypera.chameleon.events.impl.common.UserChatEvent;
import dev.hypera.chameleon.events.impl.common.UserConnectEvent;
import dev.hypera.chameleon.events.impl.common.UserDisconnectEvent;
import dev.hypera.chameleon.events.impl.proxy.ProxyUserSwitchEvent;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.platforms.velocity.VelocityChameleon;
import dev.hypera.chameleon.platforms.velocity.platform.objects.VelocityServer;
import dev.hypera.chameleon.platforms.velocity.user.VelocityUser;
import dev.hypera.chameleon.users.platforms.ProxyUser;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity listener.
 */
@Internal
public class VelocityListener {

    private final @NotNull VelocityChameleon chameleon;

    /**
     * {@link VelocityListener} constructor.
     *
     * @param chameleon {@link VelocityChameleon} instance.
     */
    @Internal
    public VelocityListener(@NotNull VelocityChameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * Platform {@link UserConnectEvent} handler.
     *
     * @param event Platform {@link PostLoginEvent}.
     */
    @Subscribe
    public void onPostLoginEvent(@NotNull PostLoginEvent event) {
        this.chameleon.getEventManager().dispatch(new UserConnectEvent(wrap(event.getPlayer())));
    }

    /**
     * Platform {@link UserChatEvent} handler.
     *
     * @param event Platform {@link PlayerChatEvent}.
     */
    @Subscribe
    public void onChatEvent(@NotNull PlayerChatEvent event) {
        UserChatEvent chameleonEvent = this.chameleon.getEventManager().dispatch(new UserChatEvent(wrap(event.getPlayer()), event.getMessage()));
        if (!event.getMessage().equals(chameleonEvent.getMessage())) {
            event.setResult(ChatResult.message(chameleonEvent.getMessage()));
        }

        if (chameleonEvent.isCancelled()) {
            event.setResult(ChatResult.denied());
        }
    }

    /**
     * Platform {@link UserDisconnectEvent} handler.
     *
     * @param event Platform {@link DisconnectEvent}.
     */
    @Subscribe
    public void onPlayerDisconnectEvent(@NotNull DisconnectEvent event) {
        this.chameleon.getEventManager().dispatch(new UserDisconnectEvent(wrap(event.getPlayer())));
    }

    /**
     * Platform {@link ProxyUserSwitchEvent} handler.
     *
     * @param event Platform {@link ServerConnectedEvent}.
     */
    @Subscribe
    public void onServerSwitchEvent(@NotNull ServerConnectedEvent event) {
        this.chameleon.getEventManager().dispatch(new ProxyUserSwitchEvent(wrap(event.getPlayer()), event.getPreviousServer().map(this::wrap).orElse(null), wrap(event.getServer())));
    }


    private @NotNull ProxyUser wrap(@NotNull Player player) {
        return new VelocityUser(this.chameleon, player);
    }

    private @NotNull Server wrap(@NotNull RegisteredServer server) {
        return new VelocityServer(this.chameleon, server);
    }

}
