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
package dev.hypera.chameleon.platforms.nukkit.listeners;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import dev.hypera.chameleon.core.events.impl.common.UserChatEvent;
import dev.hypera.chameleon.core.events.impl.common.UserConnectEvent;
import dev.hypera.chameleon.core.events.impl.common.UserDisconnectEvent;
import dev.hypera.chameleon.core.users.platforms.ServerUser;
import dev.hypera.chameleon.platforms.nukkit.NukkitChameleon;
import dev.hypera.chameleon.platforms.nukkit.users.NukkitUser;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit {@link Listener}.
 */
@Internal
public class NukkitListener implements Listener {

    private final @NotNull NukkitChameleon chameleon;

    /**
     * {@link NukkitListener} constructor.
     *
     * @param chameleon {@link NukkitChameleon} instance.
     */
    @Internal
    public NukkitListener(@NotNull NukkitChameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * Platform {@link UserConnectEvent} handler.
     *
     * @param event Platform {@link PlayerJoinEvent}.
     */
    @EventHandler
    public void onPlayerJoinEvent(@NotNull PlayerJoinEvent event) {
        this.chameleon.getEventManager().dispatch(new UserConnectEvent(wrap(event.getPlayer())));
    }

    /**
     * Platform {@link UserChatEvent} handler.
     *
     * @param event Platform {@link PlayerChatEvent}.
     */
    @EventHandler
    public void onPlayerChatEvent(@NotNull PlayerChatEvent event) {
        UserChatEvent chameleonEvent = this.chameleon.getEventManager().dispatch(new UserChatEvent(wrap(event.getPlayer()), event.getMessage()));
        if (!event.getMessage().equals(chameleonEvent.getMessage())) {
            event.setMessage(chameleonEvent.getMessage());
        }

        if (chameleonEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

    /**
     * Platform {@link UserDisconnectEvent} handler.
     *
     * @param event Platform {@link PlayerQuitEvent}.
     */
    @EventHandler
    public void onPlayerQuitEvent(@NotNull PlayerQuitEvent event) {
        this.chameleon.getEventManager().dispatch(new UserDisconnectEvent(wrap(event.getPlayer())));
    }


    private @NotNull ServerUser wrap(@NotNull Player player) {
        return new NukkitUser(player);
    }

}
