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
package dev.hypera.chameleon.platform.nukkit.users;

import cn.nukkit.Player;
import dev.hypera.chameleon.platform.nukkit.adventure.AbstractNukkitAudience;
import dev.hypera.chameleon.platform.server.GameMode;
import dev.hypera.chameleon.users.platforms.ServerUser;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit {@link ServerUser} implementation.
 */
@Internal
public class NukkitUser extends AbstractNukkitAudience implements ServerUser {

    private final @NotNull Player player;

    /**
     * {@link NukkitUser} constructor.
     *
     * @param player {@link Player} to be wrapped.
     */
    public NukkitUser(@NotNull Player player) {
        super(player);
        this.player = player;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.player.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasInteractiveChat() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull UUID getUniqueId() {
        return this.player.getUniqueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<SocketAddress> getAddress() {
        return Optional.ofNullable(this.player.getSocketAddress());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPing() {
        return this.player.getPing();
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

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect(@NotNull Component reason) {
        this.player.kick(LegacyComponentSerializer.legacySection().serialize(reason));
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
        return convertGameModeToChameleon(this.player.getGamemode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameMode(@NotNull GameMode gameMode) {
        this.player.setGamemode(convertGameModeToNukkit(gameMode));
    }

    private int convertGameModeToNukkit(@NotNull GameMode gameMode) {
        switch (gameMode) {
            case CREATIVE:
                return Player.CREATIVE;
            case ADVENTURE:
                return Player.ADVENTURE;
            case SPECTATOR:
                return Player.SPECTATOR;
            default:
                return Player.SURVIVAL;
        }
    }

    private @NotNull GameMode convertGameModeToChameleon(int gameMode) {
        switch (gameMode) {
            case Player.CREATIVE:
                return GameMode.CREATIVE;
            case Player.ADVENTURE:
                return GameMode.ADVENTURE;
            case Player.SPECTATOR:
                return GameMode.SPECTATOR;
            default:
                return GameMode.SURVIVAL;
        }
    }

}
