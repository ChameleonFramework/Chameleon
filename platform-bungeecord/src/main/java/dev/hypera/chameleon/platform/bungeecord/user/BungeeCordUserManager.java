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
package dev.hypera.chameleon.platform.bungeecord.user;

import dev.hypera.chameleon.platform.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.platform.user.PlatformUserManager;
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.user.ConsoleUser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord user manager.
 */
public final class BungeeCordUserManager extends PlatformUserManager<ProxiedPlayer, BungeeCordUser> {

    private final @NotNull BungeeCordChameleon chameleon;
    private final @NotNull BungeeCordUserManager.Listener listener = new BungeeCordUserManager.Listener();

    /**
     * BungeeCord user manager constructor.
     *
     * @param chameleon BungeeCord Chameleon.
     */
    @Internal
    public BungeeCordUserManager(@NotNull BungeeCordChameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * Registers the platform listeners.
     */
    public void registerListeners() {
        this.chameleon.getPlatformPlugin().getProxy().getPluginManager()
            .registerListener(this.chameleon.getPlatformPlugin(), this.listener);
    }

    /**
     * Unregisters the platform listeners.
     */
    public void unregisterListeners() {
        this.chameleon.getPlatformPlugin().getProxy().getPluginManager()
            .unregisterListener(this.listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull ConsoleUser createConsoleUser() {
        return new BungeeCordConsoleUser(this.chameleon.getAdventure().console());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull BungeeCordUser createUser(@NotNull ProxiedPlayer proxiedPlayer) {
        return new BungeeCordUser(this.chameleon, proxiedPlayer);
    }

    /**
     * {@inheritDoc}
     */
    @Internal
    @Override
    public @NotNull ChatUser wrap(@NotNull Object obj) {
        if (obj instanceof ProxiedPlayer) {
            return getUserOrThrow(((ProxiedPlayer) obj).getUniqueId());
        }
        if (obj instanceof CommandSender) {
            return getConsole();
        }
        throw new IllegalArgumentException("cannot return a chat user representing the given object");
    }

    /**
     * BungeeCord platform listener.
     */
    @Internal
    @SuppressWarnings("unused")
    private final class Listener implements net.md_5.bungee.api.plugin.Listener {

        @EventHandler(priority = EventPriority.LOWEST)
        public void onLogin(@NotNull PostLoginEvent event) {
            addUser(event.getPlayer().getUniqueId(), event.getPlayer());
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void onDisconnect(@NotNull PlayerDisconnectEvent event) {
            removeUser(event.getPlayer().getUniqueId());
        }

    }

}
