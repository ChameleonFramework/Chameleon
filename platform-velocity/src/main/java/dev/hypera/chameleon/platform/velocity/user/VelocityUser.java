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
package dev.hypera.chameleon.platform.velocity.user;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.hypera.chameleon.adventure.AbstractReflectedAudience;
import dev.hypera.chameleon.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.platform.velocity.VelocityChameleon;
import dev.hypera.chameleon.platform.velocity.platform.objects.VelocityServer;
import dev.hypera.chameleon.users.ProxyUser;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity {@link ProxyUser} implementation.
 */
@Internal
public class VelocityUser extends AbstractReflectedAudience implements ProxyUser {

    private static final @NotNull Method DISCONNECT_METHOD;

    private final @NotNull VelocityChameleon chameleon;
    private final @NotNull Player player;

    static {
        try {
            DISCONNECT_METHOD = Player.class.getMethod("disconnect", Class.forName(AdventureConverter.PACKAGE + "text.Component"));
        } catch (ClassNotFoundException | NoSuchMethodException ex) {
            throw new IllegalStateException("Failed to initialise VelocityUser");
        }
    }

    /**
     * {@link VelocityUser} constructor.
     *
     * @param chameleon {@link VelocityChameleon} instance.
     * @param player    {@link Player} to be wrapped.
     */
    @Internal
    public VelocityUser(@NotNull VelocityChameleon chameleon, @NotNull Player player) {
        super(player);
        this.chameleon = chameleon;
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
        return this.player.getPing() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) this.player.getPing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chat(@NotNull Component message) {
        this.player.spoofChatInput(LegacyComponentSerializer.legacyAmpersand().serialize(message));
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
    public @NotNull Optional<Server> getServer() {
        return this.player.getCurrentServer().map(s -> new VelocityServer(this.chameleon, s.getServer()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NotNull Server server) {
        this.player.createConnectionRequest(((VelocityServer) server).getVelocity()).fireAndForget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback) {
        this.player.createConnectionRequest(((VelocityServer) server).getVelocity()).connect().whenComplete((result, ex) -> {
            callback.accept(result.isSuccessful(), ex);
        }).join();
    }

}
