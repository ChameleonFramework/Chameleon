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
package dev.hypera.chameleon.platforms.sponge.events;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.events.impl.common.UserChatEvent;
import dev.hypera.chameleon.events.impl.common.UserConnectEvent;
import dev.hypera.chameleon.events.impl.common.UserDisconnectEvent;
import dev.hypera.chameleon.platforms.sponge.users.SpongeUsers;
import dev.hypera.chameleon.users.platforms.ServerUser;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.message.PlayerChatEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.service.permission.Subject;

/**
 * Sponge listener.
 */
@Internal
public class SpongeListener {

    private final @NotNull Chameleon chameleon;

    /**
     * {@link SpongeListener} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     */
    @Internal
    public SpongeListener(@NotNull Chameleon chameleon) {
        this.chameleon = chameleon;
    }


    /**
     * Platform {@link UserDisconnectEvent} handler.
     *
     * @param event Platform {@link ServerSideConnectionEvent.Join}.
     */
    @Listener
    public void onJoinEvent(@NotNull ServerSideConnectionEvent.Join event) {
        this.chameleon.getEventManager().dispatch(new UserConnectEvent(wrap(event.player())));
    }

    /**
     * Platform {@link UserDisconnectEvent} handler.
     *
     * @param event Platform {@link PlayerChatEvent}.
     */
    @Listener
    public void onChatEvent(@NotNull PlayerChatEvent event) {
        ServerPlayer sender = (ServerPlayer) event.cause().first(Player.class).orElse(null);
        if (null != sender) {
            String serialized = LegacyComponentSerializer.legacySection().serialize(event.message());
            UserChatEvent chameleonEvent = this.chameleon.getEventManager().dispatch(new UserChatEvent(wrap(sender), serialized));
            if (!serialized.equals(chameleonEvent.getMessage())) {
                event.setMessage(LegacyComponentSerializer.legacySection().deserialize(chameleonEvent.getMessage()));
            }

            if (chameleonEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * Platform {@link UserDisconnectEvent} handler.
     *
     * @param event Platform {@link ServerSideConnectionEvent.Disconnect}.
     */
    @Listener
    public void onLoginEvent(@NotNull ServerSideConnectionEvent.Disconnect event) {
        this.chameleon.getEventManager().dispatch(new UserDisconnectEvent(wrap(event.player())));
    }

    private @NotNull ServerUser wrap(@NotNull Subject subject) {
        return (ServerUser) SpongeUsers.wrap(subject);
    }

}
