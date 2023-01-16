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
package dev.hypera.chameleon.platform.nukkit.event;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import dev.hypera.chameleon.event.common.UserChatEvent;
import dev.hypera.chameleon.event.common.UserConnectEvent;
import dev.hypera.chameleon.event.common.UserDisconnectEvent;
import dev.hypera.chameleon.event.server.ServerUserKickEvent;
import dev.hypera.chameleon.platform.nukkit.NukkitChameleon;
import dev.hypera.chameleon.user.ServerUser;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit event listener.
 */
@Internal
public final class NukkitListener implements Listener {

    private final @NotNull NukkitChameleon chameleon;

    /**
     * Nukkit listener constructor.
     *
     * @param chameleon Nukkit Chameleon implementation.
     */
    @Internal
    public NukkitListener(@NotNull NukkitChameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * Platform user connect event handler.
     *
     * @param event Platform event.
     */
    @EventHandler
    public void onPlayerJoinEvent(@NotNull PlayerJoinEvent event) {
        ServerUser user = this.chameleon.getUserManager().wrap(event.getPlayer());
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
    public void onPlayerChatEvent(@NotNull PlayerChatEvent event) {
        UserChatEvent chameleonEvent = new UserChatEvent(
            this.chameleon.getUserManager().wrap(event.getPlayer()),
            event.getMessage(), event.isCancelled()
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
    public void onPlayerQuitEvent(@NotNull PlayerQuitEvent event) {
        this.chameleon.getEventBus().dispatch(new UserDisconnectEvent(
            this.chameleon.getUserManager().wrap(event.getPlayer())
        ));
    }

    /**
     * Platform server user kick event handler.
     *
     * @param event Platform event.
     */
    @EventHandler
    public void onPlayerKickEvent(@NotNull PlayerKickEvent event) {
        this.chameleon.getEventBus().dispatch(new ServerUserKickEvent(
            this.chameleon.getUserManager().wrap(event.getPlayer()),
            LegacyComponentSerializer.legacySection().deserialize(event.getReason())
        ));
    }

}
