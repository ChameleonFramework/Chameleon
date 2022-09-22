/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.platform.sponge.users;

import dev.hypera.chameleon.adventure.AbstractReflectedAudience;
import dev.hypera.chameleon.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.platform.server.GameMode;
import dev.hypera.chameleon.users.platforms.ServerUser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketAddress;
import java.util.Optional;
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

    private static final @NotNull Method SIMULATE_CHAT_METHOD;
    private static final @NotNull Method DISCONNECT_METHOD;
    private final @NotNull ServerPlayer player;

    static {
        try {
            SIMULATE_CHAT_METHOD = ServerPlayer.class.getMethod("simulateChat", Class.forName(AdventureConverter.PACKAGE + "text.Component"), Cause.class);
            DISCONNECT_METHOD = ServerPlayer.class.getMethod("kick", Class.forName(AdventureConverter.PACKAGE + "text.Component"));
        } catch (ClassNotFoundException | NoSuchMethodException ex) {
            throw new IllegalStateException("Failed to initialise SpongeUser");
        }
    }


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
    public boolean hasInteractiveChat() {
        return true;
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
    public @NotNull Optional<SocketAddress> getAddress() {
        return Optional.ofNullable(this.player.connection().address());
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
    public void chat(@NotNull Component message) {
        try {
            SIMULATE_CHAT_METHOD.invoke(this.player, AdventureConverter.convertComponent(message), Cause.builder().append(message).build());
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        Sponge.game().channelManager().ofType(ResourceKey.resolve(channel), RawDataChannel.class).play().sendTo(this.player, channelBuf -> channelBuf.writeBytes(data)).join();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect(@NotNull Component reason) {
        try {
            DISCONNECT_METHOD.invoke(this.player, AdventureConverter.convertComponent(reason));
        } catch (IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException(ex);
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
