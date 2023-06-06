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
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.user.ConsoleUser;
import dev.hypera.chameleon.user.ProxyUser;
import dev.hypera.chameleon.user.User;
import dev.hypera.chameleon.user.UserManager;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * BungeeCord user manager implementation.
 */
@Internal
public final class BungeeCordUserManager implements UserManager {

    private final @NotNull BungeeCordChameleon chameleon;

    private @Nullable BungeeCordConsoleUser consoleUser;

    /**
     * BungeeCord user manager constructor.
     *
     * @param chameleon BungeeCord Chameleon implementation.
     */
    @Internal
    public BungeeCordUserManager(@NotNull BungeeCordChameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ConsoleUser getConsole() {
        if (this.consoleUser == null) {
            this.consoleUser = new BungeeCordConsoleUser(this.chameleon);
        }
        return this.consoleUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<User> getUsers() {
        return ProxyServer.getInstance().getPlayers().stream()
            .map(this::wrap).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<User> getUserById(@NotNull UUID id) {
        return Optional.ofNullable(ProxyServer.getInstance().getPlayer(id)).map(this::wrap);
    }

    /**
     * Wrap a BungeeCord ProxiedPlayer.
     *
     * @param player ProxiedPlayer to wrap.
     *
     * @return user wrapping the given player.
     */
    @Internal
    public @NotNull ProxyUser wrap(@NotNull ProxiedPlayer player) {
        return new BungeeCordUser(this.chameleon, player);
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
        if (sender instanceof ProxiedPlayer) {
            return wrap((ProxiedPlayer) sender);
        } else {
            return getConsole();
        }
    }

}
