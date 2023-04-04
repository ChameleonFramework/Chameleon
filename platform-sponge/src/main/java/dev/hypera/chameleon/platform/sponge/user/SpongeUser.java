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

import dev.hypera.chameleon.adventure.ReflectedAudience;
import dev.hypera.chameleon.platform.server.GameMode;
import dev.hypera.chameleon.user.ServerUser;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.network.channel.raw.RawDataChannel;

/**
 * Sponge server user implementation.
 */
public final class SpongeUser implements ServerUser, ForwardingAudience.Single {

    private final @NotNull ServerPlayer player;
    private final @NotNull ReflectedAudience audience;
    private final @NotNull PlayerReflection playerReflection;

    /**
     * Sponge user constructor.
     *
     * @param player           Sponge server player to be wrapped.
     * @param audience         Reflected audience instance.
     * @param playerReflection Player reflection instance.
     */
    @Internal
    SpongeUser(@NotNull ServerPlayer player, @NotNull ReflectedAudience audience, @NotNull PlayerReflection playerReflection) {
        this.player = player;
        this.audience = audience;
        this.playerReflection = playerReflection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.player.name();
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
    public @NotNull UUID getId() {
        return this.player.uniqueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<SocketAddress> getAddress() {
        return Optional.ofNullable(this.player.connection().address());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLatency() {
        return this.player.connection().latency();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        Sponge.game().channelManager()
            .ofType(ResourceKey.resolve(channel), RawDataChannel.class)
            .play().sendTo(this.player, channelBuf -> channelBuf.writeBytes(data))
            .join();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect(@NotNull Component reason) {
        this.playerReflection.kick(this.player, reason);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(@NotNull String permission) {
        return this.player.hasPermission(permission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull GameMode getGameMode() {
        return convertGameModeToChameleon(this.player.gameMode().get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameMode(@NotNull GameMode gameMode) {
        this.player.gameMode().set(convertGameModeToSponge(gameMode));
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

    /**
     * Get the Sponge player for this user.
     *
     * @return Sponge player.
     */
    public @NotNull Player getPlayer() {
        return this.player;
    }

    private @NotNull org.spongepowered.api.entity.living.player.gamemode.GameMode convertGameModeToSponge(@NotNull GameMode gameMode) {
        switch (gameMode) {
            case CREATIVE:
                return GameModes.CREATIVE.get();
            case ADVENTURE:
                return GameModes.ADVENTURE.get();
            case SPECTATOR:
                return GameModes.SPECTATOR.get();
            default:
                return GameModes.SURVIVAL.get();
        }
    }

    private @NotNull GameMode convertGameModeToChameleon(@NotNull org.spongepowered.api.entity.living.player.gamemode.GameMode gameMode) {
        if (gameMode.equals(GameModes.CREATIVE.get())) {
            return GameMode.CREATIVE;
        } else if (gameMode.equals(GameModes.ADVENTURE.get())) {
            return GameMode.ADVENTURE;
        } else if (gameMode.equals(GameModes.SPECTATOR.get())) {
            return GameMode.SPECTATOR;
        } else {
            return GameMode.SURVIVAL;
        }
    }

}
