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
package dev.hypera.chameleon.platform.nukkit.user;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.CommandSender;
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.user.ConsoleUser;
import dev.hypera.chameleon.user.ServerUser;
import dev.hypera.chameleon.user.User;
import dev.hypera.chameleon.user.UserManager;
import dev.hypera.chameleon.util.Preconditions;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit user manager implementation.
 */
@Internal
public final class NukkitUserManager implements UserManager {

    private final @NotNull NukkitConsoleUser consoleUser = new NukkitConsoleUser();

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ConsoleUser getConsole() {
        return this.consoleUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<User> getUsers() {
        return Server.getInstance().getOnlinePlayers().values()
            .stream().map(this::wrap).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<User> getUserById(@NotNull UUID id) {
        Preconditions.checkNotNull("id", id);
        return Optional.ofNullable(Server.getInstance().getOnlinePlayers().get(id)).map(this::wrap);
    }

    /**
     * Wrap a Nukkit Player.
     *
     * @param player Player to wrap.
     *
     * @return user wrapping the given player.
     */
    @Internal
    public @NotNull ServerUser wrap(@NotNull Player player) {
        return new NukkitUser(player);
    }

    /**
     * Wrap a command sender.
     *
     * @param sender Command sender to wrap.
     *
     * @return user wrapping the given command sender.
     */
    @Internal
    public @NotNull ChatUser wrap(@NotNull CommandSender sender) {
        if (sender instanceof Player) {
            return wrap((Player) sender);
        } else {
            return getConsole();
        }
    }

}
