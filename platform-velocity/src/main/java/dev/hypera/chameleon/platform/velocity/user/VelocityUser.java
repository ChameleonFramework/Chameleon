/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 The Chameleon Framework Authors.
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

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.hypera.chameleon.adventure.ReflectedAudience;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.platform.user.PlatformUser;
import dev.hypera.chameleon.platform.velocity.VelocityChameleon;
import dev.hypera.chameleon.platform.velocity.platform.objects.VelocityServer;
import dev.hypera.chameleon.user.ProxyUser;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity proxy user implementation.
 */
public final class VelocityUser extends PlatformUser<Player> implements ProxyUser, ForwardingAudience.Single {

    private final @NotNull VelocityChameleon chameleon;
    private final @NotNull Player player;
    private final @NotNull ReflectedAudience audience;
    private final @NotNull PlayerReflection playerReflection;

    /**
     * Velocity user constructor.
     *
     * @param chameleon        Velocity Chameleon implementation.
     * @param player           Player to be wrapped.
     * @param audience         Reflected audience instance.
     * @param playerReflection Player reflection instance.
     */
    @Internal
    VelocityUser(
        @NotNull VelocityChameleon chameleon,
        @NotNull Player player,
        @NotNull ReflectedAudience audience,
        @NotNull PlayerReflection playerReflection
    ) {
        this.chameleon = chameleon;
        this.player = player;
        this.audience = audience;
        this.playerReflection = playerReflection;
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
    public @NotNull UUID getId() {
        return this.player.getUniqueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<SocketAddress> getAddress() {
        return Optional.ofNullable(this.player.getRemoteAddress());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLatency() {
        return this.player.getPing() > Integer.MAX_VALUE
            ? Integer.MAX_VALUE : (int) this.player.getPing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        this.player.sendPluginMessage(MinecraftChannelIdentifier.from(channel), data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect(@NotNull Component reason) {
        this.playerReflection.disconnect(this.player, reason);
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
    public @NotNull Optional<Server> getConnectedServer() {
        return this.player.getCurrentServer()
            .map(s -> new VelocityServer(this.chameleon, s.getServer()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NotNull Server server) {
        this.player.createConnectionRequest(((VelocityServer) server).getVelocity())
            .fireAndForget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback) {
        this.player.createConnectionRequest(((VelocityServer) server).getVelocity()).connect()
            .whenComplete((result, ex) -> callback.accept(result.isSuccessful(), ex)).join();
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
     * Get the Velocity player for this user.
     *
     * @return Velocity player.
     */
    @Override
    public @NotNull Player getPlayer() {
        return this.player;
    }

}
