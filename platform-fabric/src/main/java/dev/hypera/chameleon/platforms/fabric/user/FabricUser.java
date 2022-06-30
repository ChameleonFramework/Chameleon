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
package dev.hypera.chameleon.platforms.fabric.user;

import dev.hypera.chameleon.core.adventure.AbstractAudience;
import dev.hypera.chameleon.core.platform.server.GameMode;
import dev.hypera.chameleon.core.users.platforms.ServerUser;
import java.util.UUID;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Fabric {@link ServerUser} implementation.
 */
@Internal
public class FabricUser extends AbstractAudience implements ServerUser {

    private final @NotNull ServerPlayerEntity player;

    /**
     * {@link FabricUser} constructor.
     *
     * @param player {@link ServerPlayerEntity} to be wrapped.
     */
    public FabricUser(@NotNull ServerPlayerEntity player) {
        super(player);
        this.player = player;
    }

    @Override
    public @NotNull String getName() {
        return this.player.getEntityName();
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return this.player.getUuid();
    }

    @Override
    public int getPing() {
        return -1; // TODO: Implement
    }

    @Override
    public void chat(@NotNull String message) {
        // TODO: Implement
    }

    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        Identifier channelName = Identifier.validate(channel).result().orElseThrow(() -> new IllegalArgumentException("Invalid channel name"));
        PacketByteBuf packetByteBuf = PacketByteBufs.create();
        packetByteBuf.writeBytes(data);
        ServerPlayNetworking.send(this.player, Identifier.validate(channel).getOrThrow(true, s -> {}), packetByteBuf);
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return this.player.hasPermissionLevel(4); // TODO: Add LuckPerms integration.
    }

    @Override
    public @NotNull GameMode getGameMode() {
        return convertGameModeToChameleon(this.player.interactionManager.getGameMode());
    }

    @Override
    public void setGameMode(@NotNull GameMode gameMode) {
        this.player.changeGameMode(convertGameModeToFabric(gameMode));
    }

    private @NotNull net.minecraft.world.GameMode convertGameModeToFabric(@NotNull GameMode gameMode) {
        switch (gameMode) {
            case CREATIVE:
                return net.minecraft.world.GameMode.CREATIVE;
            case ADVENTURE:
                return net.minecraft.world.GameMode.ADVENTURE;
            case SPECTATOR:
                return net.minecraft.world.GameMode.SPECTATOR;
            default:
                return net.minecraft.world.GameMode.SURVIVAL;
        }
    }

    private @NotNull GameMode convertGameModeToChameleon(@NotNull net.minecraft.world.GameMode gameMode) {
        switch (gameMode) {
            case CREATIVE:
                return GameMode.CREATIVE;
            case ADVENTURE:
                return GameMode.ADVENTURE;
            case SPECTATOR:
                return GameMode.SPECTATOR;
            default:
                return GameMode.SURVIVAL;
        }
    }

}
