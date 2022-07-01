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
package dev.hypera.chameleon.platforms.sponge.users;

import dev.hypera.chameleon.core.adventure.AbstractReflectedAudience;
import dev.hypera.chameleon.core.platform.server.GameMode;
import dev.hypera.chameleon.core.users.platforms.ServerUser;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.ResourceKey;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.network.channel.raw.RawDataChannel;

/**
 * Sponge {@link ServerUser} implementation.
 */
public class SpongeUser extends AbstractReflectedAudience implements ServerUser {

    private final @NotNull ServerPlayer player;

    /**
     * {@link SpongeUser} constructor.
     *
     * @param player {@link ServerPlayer} to be wrapped.
     */
    @Internal
    SpongeUser(@NotNull ServerPlayer player) {
        super(player);
        this.player = player;
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
    public @NotNull UUID getUniqueId() {
        return this.player.uniqueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPing() {
        return this.player.connection().latency();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chat(@NotNull String message) {
        this.player.simulateChat(Component.text(message), Cause.builder().append(message).build()); // TODO: figure out what the heck Cause does
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        if (Sponge.game().isServerAvailable()) {
            Sponge.game().channelManager().ofType(ResourceKey.resolve(channel), RawDataChannel.class).play().sendTo(this.player, channelBuf -> channelBuf.writeBytes(data));
        }
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
