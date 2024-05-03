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
package dev.hypera.chameleon.platform.velocity.event;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent.ChatResult;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.hypera.chameleon.event.common.UserChatEvent;
import dev.hypera.chameleon.event.common.UserConnectEvent;
import dev.hypera.chameleon.event.common.UserDisconnectEvent;
import dev.hypera.chameleon.event.proxy.ProxyUserSwitchEvent;
import dev.hypera.chameleon.platform.event.PlatformEventDispatcher;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.platform.util.PlatformEventUtil;
import dev.hypera.chameleon.platform.velocity.VelocityChameleon;
import dev.hypera.chameleon.platform.velocity.platform.objects.VelocityServer;
import dev.hypera.chameleon.user.ProxyUser;
import dev.hypera.chameleon.user.User;
import dev.hypera.chameleon.util.internal.ChameleonProperty;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity listener.
 */
@Internal
public final class VelocityEventDispatcher extends PlatformEventDispatcher {

    private final @NotNull VelocityChameleon chameleon;

    /**
     * Velocity constructor.
     *
     * @param chameleon Velocity Chameleon implementation.
     */
    @Internal
    public VelocityEventDispatcher(@NotNull VelocityChameleon chameleon) {
        super(chameleon.getEventBus());
        this.chameleon = chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerListeners() {
        this.chameleon.getPlatformPlugin().getServer().getEventManager()
            .register(this.chameleon.getPlatformPlugin(), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregisterListeners() {
        this.chameleon.getPlatformPlugin().getServer().getEventManager()
            .unregisterListener(this.chameleon.getPlatformPlugin(), this);
    }

    /**
     * Platform user connect event handler.
     *
     * @param event Platform event.
     */
    @Subscribe
    public void onPostLoginEvent(@NotNull PostLoginEvent event) {
        User user = this.chameleon.getUserManager().wrapUser(event.getPlayer());
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
    @Subscribe
    public void onChatEvent(@NotNull PlayerChatEvent event) {
        boolean immutable = ChameleonProperty.PREVENT_CHAT_PROTOCOL_ERRORS.get() &&
            event.getPlayer().getProtocolVersion().compareTo(ProtocolVersion.MINECRAFT_1_19_1) >= 0;

        UserChatEvent chameleonEvent = new UserChatEvent(
            this.chameleon.getUserManager().wrapUser(event.getPlayer()),
            event.getMessage(),
            !event.getResult().isAllowed(),
            immutable, immutable
        );
        this.chameleon.getEventBus().dispatch(chameleonEvent);

        // Event message modification
        if (!event.getMessage().equals(chameleonEvent.getMessage())) {
            if (immutable) {
                PlatformEventUtil.logChatModificationFailure(this.chameleon.getInternalLogger());
                return;
            }
            event.setResult(ChatResult.message(chameleonEvent.getMessage()));
        }

        // Event cancellation
        if (chameleonEvent.isCancelled() && event.getResult().isAllowed()) {
            if (immutable) {
                PlatformEventUtil.logChatCancellationFailure(this.chameleon.getInternalLogger());
                return;
            }
            event.setResult(ChatResult.denied());
        }
    }

    /**
     * Platform user disconnect event handler.
     *
     * @param event Platform event.
     */
    @Subscribe
    public void onPlayerDisconnectEvent(@NotNull DisconnectEvent event) {
        this.chameleon.getEventBus().dispatch(new UserDisconnectEvent(
            this.chameleon.getUserManager().wrapUser(event.getPlayer())));
    }

    /**
     * Platform proxy user server switch event handler.
     *
     * @param event Platform event.
     */
    @Subscribe
    public void onServerSwitchEvent(@NotNull ServerConnectedEvent event) {
        this.chameleon.getEventBus().dispatch(new ProxyUserSwitchEvent(
            (ProxyUser) this.chameleon.getUserManager().wrapUser(event.getPlayer()),
            event.getPreviousServer().map(this::wrap).orElse(null),
            wrap(event.getServer())
        ));
    }

    private @NotNull Server wrap(@NotNull RegisteredServer server) {
        return new VelocityServer(this.chameleon, server);
    }

}
