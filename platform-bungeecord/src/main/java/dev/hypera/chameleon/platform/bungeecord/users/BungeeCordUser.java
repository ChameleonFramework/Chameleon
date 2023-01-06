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
package dev.hypera.chameleon.platform.bungeecord.users;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.adventure.AbstractAudience;
import dev.hypera.chameleon.platform.bungeecord.platform.objects.BungeeCordServer;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.users.ProxyUser;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord {@link ProxyUser} implementation.
 */
@Internal
public class BungeeCordUser extends AbstractAudience implements ProxyUser {

    private final @NotNull Chameleon chameleon;
    private final @NotNull ProxiedPlayer player;

    /**
     * {@link BungeeCordUser} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     * @param player    {@link ProxiedPlayer} to be wrapped.
     */
    @Internal
    public BungeeCordUser(@NotNull Chameleon chameleon, @NotNull ProxiedPlayer player) {
        super(chameleon.getAdventure().player(player.getUniqueId()));
        this.chameleon = chameleon;
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
        return Optional.ofNullable(this.player.getSocketAddress());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLatency() {
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
        this.player.sendData(channel, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect(@NotNull Component reason) {
        this.player.disconnect(BungeeComponentSerializer.get().serialize(reason));
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
        net.md_5.bungee.api.connection.Server server = this.player.getServer();
        if (server == null) {
            return Optional.empty();
        }
        return Optional.of(new BungeeCordServer(this.chameleon, server.getInfo()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NotNull Server server) {
        this.player.connect(((BungeeCordServer) server).getBungeeCord());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback) {
        this.player.connect(((BungeeCordServer) server).getBungeeCord(), callback::accept);
    }

}
