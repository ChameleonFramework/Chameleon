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
package dev.hypera.chameleon.platform.bukkit.managers;

import dev.hypera.chameleon.managers.UserManager;
import dev.hypera.chameleon.platform.bukkit.BukkitChameleon;
import dev.hypera.chameleon.platform.bukkit.user.BukkitConsoleUser;
import dev.hypera.chameleon.platform.bukkit.user.BukkitUser;
import dev.hypera.chameleon.users.ChatUser;
import dev.hypera.chameleon.users.User;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit {@link UserManager} implementation.
 */
@Internal
public final class BukkitUserManager extends UserManager {

    private final @NotNull BukkitChameleon chameleon;

    /**
     * {@link BukkitUserManager} constructor.
     *
     * @param chameleon {@link BukkitChameleon} instance.
     */
    @Internal
    public BukkitUserManager(@NotNull BukkitChameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChatUser getConsole() {
        return new BukkitConsoleUser(this.chameleon);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<User> getPlayers() {
        return Bukkit.getOnlinePlayers().stream().map(p -> new BukkitUser(this.chameleon, p)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<User> getPlayer(@NotNull UUID uniqueId) {
        return Optional.ofNullable(Bukkit.getPlayer(uniqueId)).map(player -> new BukkitUser(this.chameleon, player));
    }

}
