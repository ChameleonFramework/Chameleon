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
package dev.hypera.chameleon.platform.sponge.event;

import dev.hypera.chameleon.event.common.UserChatEvent;
import dev.hypera.chameleon.event.common.UserConnectEvent;
import dev.hypera.chameleon.event.common.UserDisconnectEvent;
import dev.hypera.chameleon.event.server.ServerUserKickEvent;
import dev.hypera.chameleon.exception.reflection.ChameleonReflectiveException;
import dev.hypera.chameleon.platform.sponge.SpongeChameleon;
import dev.hypera.chameleon.user.ServerUser;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.living.player.KickPlayerEvent;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.message.PlayerChatEvent;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.util.Tristate;

/**
 * Sponge listener.
 */
@Internal
public final class SpongeListener {

    private final @NotNull SpongeChameleon chameleon;
    private final @NotNull EventReflection eventReflection;

    /**
     * Sponge listener constructor.
     *
     * @param chameleon Sponge Chameleon implementation.
     */
    @Internal
    public SpongeListener(@NotNull SpongeChameleon chameleon) {
        this.chameleon = chameleon;
        this.eventReflection = new EventReflection(chameleon.getAdventureMapper()
            .getComponentMapper());
    }

    /**
     * Load reflection utilities.
     */
    public void load() {
        this.eventReflection.load();
    }

    /**
     * Platform user connect event handler.
     *
     * @param event Platform event.
     */
    @Listener
    public void onJoinEvent(@NotNull ServerSideConnectionEvent.Join event) {
        ServerUser user = this.chameleon.getUserManager().wrap(event.player());
        UserConnectEvent chameleonEvent = new UserConnectEvent(user, false);

        this.chameleon.getEventBus().dispatch(chameleonEvent);
        if (chameleonEvent.isCancelled()) {
            user.disconnect(chameleonEvent.getCancelReason());
        }
    }

    /**
     * Platform user disconnect event handler.
     *
     * @param event Platform event.
     */
    @Listener
    @IsCancelled(Tristate.UNDEFINED)
    public void onChatEvent(@NotNull PlayerChatEvent event) {
        ServerPlayer sender = (ServerPlayer) event.cause().first(Player.class).orElse(null);
        if (sender != null) {
            String serialized;
            try {
                serialized = LegacyComponentSerializer.legacySection().serialize(
                    this.chameleon.getAdventureMapper().getComponentMapper()
                        .mapBackwards(event.message())
                );
            } catch (ReflectiveOperationException ex) {
                throw new ChameleonReflectiveException(ex);
            }

            UserChatEvent chameleonEvent = new UserChatEvent(
                this.chameleon.getUserManager().wrap(sender), serialized, event.isCancelled());
            this.chameleon.getEventBus().dispatch(chameleonEvent);

            if (!serialized.equals(chameleonEvent.getMessage())) {
                this.eventReflection.setPlayerChatEventMessage(
                    event, LegacyComponentSerializer.legacySection()
                        .deserialize(chameleonEvent.getMessage())
                );
            }

            if (chameleonEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }
    }

    /**
     * Platform user disconnect event handler.
     *
     * @param event Platform event.
     */
    @Listener
    public void onDisconnectEvent(@NotNull ServerSideConnectionEvent.Disconnect event) {
        this.chameleon.getEventBus().dispatch(new UserDisconnectEvent(
            this.chameleon.getUserManager().wrap(event.player())));
    }

    /**
     * Platform server user kick event handler.
     *
     * @param event Platform event.
     */
    @Listener
    public void onKickEvent(@NotNull KickPlayerEvent event) {
        try {
            this.chameleon.getEventBus().dispatch(new ServerUserKickEvent(
                this.chameleon.getUserManager().wrap(event.player()), event.message() == null ? null :
                this.chameleon.getAdventureMapper().getComponentMapper().mapBackwards(event.message()))
            );
        } catch (ReflectiveOperationException ex) {
            throw new ChameleonReflectiveException(ex);
        }
    }

}
