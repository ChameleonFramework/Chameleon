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
package dev.hypera.chameleon.platform.minestom.event;

import dev.hypera.chameleon.event.common.UserChatEvent;
import dev.hypera.chameleon.event.common.UserConnectEvent;
import dev.hypera.chameleon.event.common.UserDisconnectEvent;
import dev.hypera.chameleon.event.server.ServerUserKickEvent;
import dev.hypera.chameleon.exception.reflection.ChameleonReflectiveException;
import dev.hypera.chameleon.platform.minestom.MinestomChameleon;
import dev.hypera.chameleon.platform.minestom.user.MinestomUserManager;
import dev.hypera.chameleon.user.ServerUser;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventListener;
import net.minestom.server.event.GlobalEventHandler;
import net.minestom.server.event.player.PlayerChatEvent;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerLoginEvent;
import net.minestom.server.event.player.PlayerPacketOutEvent;
import net.minestom.server.network.packet.server.play.DisconnectPacket;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom listener.
 */
@Internal
@SuppressWarnings("UnstableApiUsage")
public final class MinestomListener {

    /**
     * Minestom listener constructor.
     *
     * @param chameleon Minestom Chameleon implementation.
     */
    @Internal
    public MinestomListener(@NotNull MinestomChameleon chameleon) {
        MinestomUserManager userManager = (MinestomUserManager) chameleon.getUserManager();
        GlobalEventHandler handler = MinecraftServer.getGlobalEventHandler();

        // Login
        handler.addListener(EventListener.builder(PlayerLoginEvent.class).handler(event -> {
            ServerUser user = userManager.wrap(event.getPlayer());
            UserConnectEvent chameleonEvent = new UserConnectEvent(user, false);

            chameleon.getEventBus().dispatch(chameleonEvent);
            if (chameleonEvent.isCancelled()) {
                user.disconnect(chameleonEvent.getCancelReason());
            }
        }).ignoreCancelled(false).build());

        // Play
        handler.addListener(EventListener.builder(PlayerChatEvent.class).handler(event -> {
            UserChatEvent chameleonEvent = new UserChatEvent(
                userManager.wrap(event.getPlayer()), event.getMessage(), event.isCancelled()
            );
            chameleon.getEventBus().dispatch(chameleonEvent);

            if (!event.getMessage().equals(chameleonEvent.getMessage())) {
                event.setMessage(chameleonEvent.getMessage());
            }

            if (chameleonEvent.isCancelled()) {
                event.setCancelled(true);
            }
        }).ignoreCancelled(false).build());

        // Disconnect
        handler.addListener(EventListener.builder(PlayerDisconnectEvent.class).handler(event -> {
            chameleon.getEventBus().dispatch(new UserDisconnectEvent(
                userManager.wrap(event.getPlayer())
            ));
        }).ignoreCancelled(false).build());

        handler.addListener(EventListener.builder(PlayerPacketOutEvent.class).handler(event -> {
            if (event.getPacket() instanceof DisconnectPacket) {
                try {
                    chameleon.getEventBus().dispatch(new ServerUserKickEvent(
                        userManager.wrap(event.getPlayer()),
                        chameleon.getAdventureMapper().getComponentMapper()
                            .mapBackwards(((DisconnectPacket) event.getPacket()).message())
                    ));
                } catch (ReflectiveOperationException ex) {
                    throw new ChameleonReflectiveException(ex);
                }
            }
        }).ignoreCancelled(false).build());
    }

}
