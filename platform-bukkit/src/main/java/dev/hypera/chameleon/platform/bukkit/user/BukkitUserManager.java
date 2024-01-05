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
package dev.hypera.chameleon.platform.bukkit.user;

import dev.hypera.chameleon.platform.PlatformChameleon;
import dev.hypera.chameleon.platform.bukkit.event.BukkitEventDispatcher;
import dev.hypera.chameleon.platform.user.PlatformUserManager;
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.user.ConsoleUser;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit user manager.
 */
public final class BukkitUserManager extends PlatformUserManager<Player, BukkitUser> implements Listener {

    private final @NotNull PlatformChameleon<JavaPlugin> chameleon;

    /**
     * Bukkit user manager constructor.
     *
     * @param chameleon       Bukkit Chameleon.
     */
    @Internal
    public BukkitUserManager(@NotNull PlatformChameleon<JavaPlugin> chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * Registers the platform listeners.
     */
    public void registerListeners() {
        BukkitEventDispatcher.registerListener(
            this.chameleon, this, PlayerJoinEvent.class, EventPriority.LOW,
            event -> addUser(event.getPlayer().getUniqueId(), event.getPlayer())
        );
        BukkitEventDispatcher.registerListener(
            this.chameleon, this, PlayerQuitEvent.class, EventPriority.MONITOR,
            event -> removeUser(event.getPlayer().getUniqueId())
        );
    }

    /**
     * Unregisters the platform listeners.
     */
    public void unregisterListeners() {
        HandlerList.unregisterAll(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull ConsoleUser createConsoleUser() {
        return new BukkitConsoleUser(this.chameleon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull BukkitUser createUser(@NotNull Player player) {
        return new BukkitUser(this.chameleon, player);
    }

    /**
     * {@inheritDoc}
     */
    @Internal
    @Override
    public @NotNull ChatUser wrap(@NotNull Object obj) {
        if (obj instanceof Player) {
            return getUserOrThrow(((Player) obj).getUniqueId());
        }
        if (obj instanceof ConsoleCommandSender) {
            return getConsole();
        }
        throw new IllegalArgumentException("cannot return a chat user representing the given object");
    }

}
