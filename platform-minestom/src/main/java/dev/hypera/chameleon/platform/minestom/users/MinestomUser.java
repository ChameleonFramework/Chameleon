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
package dev.hypera.chameleon.platform.minestom.users;

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
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom {@link ServerUser} implementation.
 */
@Internal
public class MinestomUser extends AbstractReflectedAudience implements ServerUser {

    private static final @NotNull Method KICK_METHOD;

    private final @NotNull Player player;

    static {
        try {
            KICK_METHOD = Player.class.getMethod("kick", Class.forName(AdventureConverter.PACKAGE + "text.Component"));
        } catch (ClassNotFoundException | NoSuchMethodException ex) {
            throw new IllegalStateException("Failed to initialise MinestomUser");
        }
    }

    /**
     * {@link MinestomUser} constructor.
     *
     * @param player {@link Player} to be wrapped.
     */
    @Internal
    public MinestomUser(@NotNull Player player) {
        super(player);
        this.player = player;
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
    public @NotNull UUID getUniqueId() {
        return this.player.getUuid();
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
    public int getPing() {
        return this.player.getLatency();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chat(@NotNull Component message) {
        this.player.chat(LegacyComponentSerializer.legacySection().serialize(message));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        this.player.sendPluginMessage(channel, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect(@NotNull Component reason) {
        try {
            KICK_METHOD.invoke(this.player, AdventureConverter.convertComponent(reason));
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
        return convertGameModeToChameleon(this.player.getGameMode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameMode(@NotNull GameMode gameMode) {
        this.player.setGameMode(convertGameModeToMinestom(gameMode));
    }

    
    private @NotNull net.minestom.server.entity.GameMode convertGameModeToMinestom(@NotNull GameMode gameMode) {
        return switch (gameMode) {
            case CREATIVE -> net.minestom.server.entity.GameMode.CREATIVE;
            case ADVENTURE -> net.minestom.server.entity.GameMode.ADVENTURE;
            case SPECTATOR -> net.minestom.server.entity.GameMode.SPECTATOR;
            default -> net.minestom.server.entity.GameMode.SURVIVAL;
        };
    }

    private @NotNull GameMode convertGameModeToChameleon(@NotNull net.minestom.server.entity.GameMode gameMode) {
        return switch (gameMode) {
            case CREATIVE -> GameMode.CREATIVE;
            case ADVENTURE -> GameMode.ADVENTURE;
            case SPECTATOR -> GameMode.SPECTATOR;
            default -> GameMode.SURVIVAL;
        };
    }
    
}
