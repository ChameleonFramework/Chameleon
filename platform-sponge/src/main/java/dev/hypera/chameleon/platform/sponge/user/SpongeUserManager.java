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
package dev.hypera.chameleon.platform.sponge.user;

import dev.hypera.chameleon.platform.sponge.SpongeChameleon;
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.user.ConsoleUser;
import dev.hypera.chameleon.user.ServerUser;
import dev.hypera.chameleon.user.User;
import dev.hypera.chameleon.user.UserManager;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.service.permission.Subject;

/**
 * Sponge user manager implementation.
 */
public final class SpongeUserManager implements UserManager {

    private final @NotNull SpongeChameleon chameleon;
    private final @NotNull PlayerReflection playerReflection;
    private @Nullable SpongeConsoleUser consoleUser;

    /**
     * Sponge user manager constructor.
     *
     * @param chameleon Sponge Chameleon implementation.
     */
    public SpongeUserManager(@NotNull SpongeChameleon chameleon) {
        this.chameleon = chameleon;
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
        if (this.consoleUser == null) {
            this.consoleUser = new SpongeConsoleUser(this.chameleon.getAdventureMapper()
                    .createReflectedAudience(Sponge.game().systemSubject()));
        }
        return this.consoleUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<User> getUsers() {
        return Sponge.server().onlinePlayers().stream().map(this::wrap).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<User> getUserById(@NotNull UUID uniqueId) {
        return Sponge.server().player(uniqueId).map(this::wrap);
    }

    /**
     * Wrap a Sponge Player.
     *
     * @param player Player to wrap.
     *
     * @return user wrapping the given player.
     */
    @Internal
    public @NotNull ServerUser wrap(@NotNull ServerPlayer player) {
        return new SpongeUser(player, this.chameleon.getAdventureMapper()
            .createReflectedAudience(player), this.playerReflection);
    }

    /**
     * Wrap a subject.
     *
     * @param subject Subject to wrap.
     *
     * @return user wrapping the given subject.
     */
    @Internal
    public @NotNull ChatUser wrap(@NotNull Subject subject) {
        if (subject instanceof ServerPlayer) {
            return wrap((ServerPlayer) subject);
        } else {
            return getConsole();
        }
    }

}
