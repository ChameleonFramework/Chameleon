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
package dev.hypera.chameleon.platform.minestom.user;

import dev.hypera.chameleon.adventure.ReflectedAudience;
import dev.hypera.chameleon.platform.server.GameMode;
import dev.hypera.chameleon.user.ServerUser;
import dev.hypera.chameleon.util.Preconditions;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom server user implementation.
 */
@Internal
public final class MinestomUser implements ServerUser, ForwardingAudience.Single {

    private final @NotNull Player player;
    private final @NotNull ReflectedAudience audience;
    private final @NotNull PlayerReflection playerReflection;

    /**
     * Minestom user constructor.
     *
     * @param player           Player to be wrapped.
     * @param audience         Reflected audience instance.
     * @param playerReflection Player reflection instance.
     */
    @Internal
    MinestomUser(@NotNull Player player, @NotNull ReflectedAudience audience, @NotNull PlayerReflection playerReflection) {
        this.player = player;
        this.audience = audience;
        this.playerReflection = playerReflection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull UUID getId() {
        return this.player.getUuid();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.player.getUsername();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasInteractiveChat() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<SocketAddress> getAddress() {
        return Optional.of(this.player.getPlayerConnection().getRemoteAddress());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLatency() {
        return this.player.getLatency();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        Preconditions.checkNotNull("channel", channel);
        this.player.sendPluginMessage(channel, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect(@NotNull Component reason) {
        Preconditions.checkNotNull("reason", reason);
        this.playerReflection.kick(this.player, reason);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(@NotNull String permission) {
        Preconditions.checkNotNull("permission", permission);
        return this.player.hasPermission(permission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull GameMode getGameMode() {
        return GameMode.valueOf(this.player.getGameMode().name());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameMode(@NotNull GameMode gameMode) {
        Preconditions.checkNotNull("gameMode", gameMode);
        this.player.setGameMode(net.minestom.server.entity.GameMode.valueOf(gameMode.name()));
    }

    /**
     * Gets the audience.
     *
     * @return the audience.
     */
    @Override
    public @NotNull Audience audience() {
        return this.audience;
    }

}
