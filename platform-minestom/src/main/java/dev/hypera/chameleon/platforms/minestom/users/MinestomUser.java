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
package dev.hypera.chameleon.platforms.minestom.users;

import dev.hypera.chameleon.core.adventure.AbstractReflectedAudience;
import dev.hypera.chameleon.core.platform.server.GameMode;
import dev.hypera.chameleon.core.users.platforms.ServerUser;
import java.util.UUID;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom {@link ServerUser} implementation.
 */
@Internal
public class MinestomUser extends AbstractReflectedAudience implements ServerUser {

    private final @NotNull Player player;

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
    public @NotNull UUID getUniqueId() {
        return this.player.getUuid();
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
    public void chat(@NotNull String message) {
        this.player.chat(message);
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
