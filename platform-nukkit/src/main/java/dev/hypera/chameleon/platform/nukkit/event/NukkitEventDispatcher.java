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

import cn.nukkit.Player;
import cn.nukkit.event.Event;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerKickEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.plugin.PluginBase;
import dev.hypera.chameleon.event.common.UserChatEvent;
import dev.hypera.chameleon.event.common.UserConnectEvent;
import dev.hypera.chameleon.event.common.UserDisconnectEvent;
import dev.hypera.chameleon.event.server.ServerUserKickEvent;
import dev.hypera.chameleon.platform.PlatformChameleon;
import dev.hypera.chameleon.platform.event.PlatformEventDispatcher;
import dev.hypera.chameleon.platform.nukkit.user.NukkitUser;
import dev.hypera.chameleon.platform.nukkit.user.NukkitUserManager;
import dev.hypera.chameleon.platform.user.PlatformUserManager;
import dev.hypera.chameleon.user.ServerUser;
import dev.hypera.chameleon.user.User;
import java.util.function.Consumer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit event dispatcher.
 */
public final class NukkitEventDispatcher extends PlatformEventDispatcher implements Listener {

    private final @NotNull PlatformChameleon<PluginBase> chameleon;

    /**
     * Nukkit event dispatcher constructor.
     *
     * @param chameleon Chameleon.
     */
    @Internal
    public NukkitEventDispatcher(@NotNull PlatformChameleon<PluginBase> chameleon) {
        super(chameleon.getEventBus());
        this.chameleon = chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerListeners() {
        PlatformUserManager<Player, NukkitUser> userManager = (NukkitUserManager) this.chameleon.getUserManager();

        // Connect event
        registerListener(this.chameleon, this, PlayerJoinEvent.class, EventPriority.NORMAL, event -> {
            User user = userManager.wrapUser(event.getPlayer());
            UserConnectEvent chameleonEvent = dispatch(new UserConnectEvent(user, false));

            // Cancel platform event
            if (chameleonEvent.isCancelled()) {
                user.disconnect(chameleonEvent.getCancelReason());
            }
        });

        // Chat event
        registerListener(this.chameleon, this, PlayerChatEvent.class, EventPriority.NORMAL, false, event -> {
            UserChatEvent chameleonEvent = dispatch(new UserChatEvent(
                userManager.wrapUser(event.getPlayer()),
                event.getMessage(), event.isCancelled(),
                true, true
            ));

            // Update message
            if (!event.getMessage().equals(chameleonEvent.getMessage())) {
                event.setMessage(chameleonEvent.getMessage());
            }
            // Cancel platform event
            if (chameleonEvent.isCancellable() != event.isCancelled()) {
                event.setCancelled(chameleonEvent.isCancelled());
            }
        });

        // Disconnect event
        registerListener(this.chameleon, this, PlayerQuitEvent.class, EventPriority.NORMAL, event ->
            dispatch(new UserDisconnectEvent(userManager.wrapUser(event.getPlayer()))));

        // Kick event
        registerListener(this.chameleon, this, PlayerKickEvent.class, EventPriority.NORMAL, event ->
            dispatch(new ServerUserKickEvent(
                (ServerUser) userManager.wrapUser(event.getPlayer()),
                LegacyComponentSerializer.legacySection().deserialize(event.getReason())
            )));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unregisterListeners() {
        HandlerList.unregisterAll(this);
    }

    /**
     * Registers an event listener with a handler callback.
     *
     * <p>Cancelled events will be ignored. To receive cancelled events, see
     * {@link #registerListener(PlatformChameleon, Listener, Class, EventPriority, boolean,
     * Consumer)}.</p>
     *
     * @param chameleon Chameleon.
     * @param listener  Listener object.
     * @param type      Nukkit event type.
     * @param priority  Nukkit listener priority.
     * @param handler   Listener handler callback.
     * @param <T>       Nukkit event type.
     *
     * @see #registerListener(PlatformChameleon, Listener, Class, EventPriority, boolean, Consumer)
     */
    @Internal
    public static <T extends Event> void registerListener(
        @NotNull PlatformChameleon<PluginBase> chameleon,
        @NotNull Listener listener,
        @NotNull Class<T> type,
        @NotNull EventPriority priority,
        @NotNull Consumer<T> handler
    ) {
        registerListener(chameleon, listener, type, priority, true, handler);
    }

    /**
     * Registers an event listener with a handler callback.
     *
     * @param chameleon       Chameleon.
     * @param listener        Listener object.
     * @param type            Nukkit event type.
     * @param priority        Nukkit listener priority.
     * @param ignoreCancelled Whether to ignore cancelled events.
     * @param handler         Listener handler callback.
     * @param <T>             Nukkit event type.
     */
    @Internal
    @SuppressWarnings("unchecked")
    public static <T extends Event> void registerListener(
        @NotNull PlatformChameleon<PluginBase> chameleon,
        @NotNull Listener listener,
        @NotNull Class<T> type,
        @NotNull EventPriority priority,
        boolean ignoreCancelled,
        @NotNull Consumer<T> handler
    ) {
        chameleon.getPlatformPlugin().getServer().getPluginManager().registerEvent(
            type, listener, priority,
            (l, event) -> handler.accept((T) event),
            chameleon.getPlatformPlugin(), ignoreCancelled
        );
    }

}
