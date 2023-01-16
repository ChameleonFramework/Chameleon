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
package dev.hypera.chameleon.platform.velocity.user;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import dev.hypera.chameleon.platform.velocity.VelocityChameleon;
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.user.ConsoleUser;
import dev.hypera.chameleon.user.ProxyUser;
import dev.hypera.chameleon.user.User;
import dev.hypera.chameleon.user.UserManager;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity user manager implementation.
 */
@Internal
public final class VelocityUserManager implements UserManager {

    private final @NotNull VelocityChameleon chameleon;
    private final @NotNull VelocityConsoleUser consoleUser;
    private final @NotNull PlayerReflection playerReflection;

    /**
     * Velocity user manager constructor.
     *
     * @param chameleon Velocity Chameleon implementation.
     */
    @Internal
    public VelocityUserManager(@NotNull VelocityChameleon chameleon) {
        this.chameleon = chameleon;
        CommandSource consoleSource = this.chameleon.getPlatformPlugin().getServer()
            .getConsoleCommandSource();
        this.consoleUser = new VelocityConsoleUser(consoleSource,
            this.chameleon.getAdventureMapper().createReflectedAudience(consoleSource));
        this.playerReflection = new PlayerReflection(this.chameleon.getAdventureMapper()
            .getComponentMapper());
    }

    /**
     * Load reflection utilities.
     */
    public void load() {
        this.playerReflection.load();
    }

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
        return this.chameleon.getPlatformPlugin().getServer().getAllPlayers()
            .stream().map(this::wrap).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<User> getUserById(@NotNull UUID id) {
        return this.chameleon.getPlatformPlugin().getServer().getPlayer(id).map(this::wrap);
    }

    /**
     * Wrap a Velocity Player.
     *
     * @param player Player to wrap.
     *
     * @return user wrapping the given player.
     */
    @Internal
    public @NotNull ProxyUser wrap(@NotNull Player player) {
        return new VelocityUser(this.chameleon, player, this.chameleon.getAdventureMapper()
            .createReflectedAudience(player), this.playerReflection);
    }

    /**
     * Wrap a command source.
     *
     * @param source Command source to wrap.
     *
     * @return user wrapping the given command source.
     */
    @Internal
    public @NotNull ChatUser wrap(@NotNull CommandSource source) {
        if (source instanceof Player) {
            return wrap((Player) source);
        } else {
            return getConsole();
        }
    }

}
