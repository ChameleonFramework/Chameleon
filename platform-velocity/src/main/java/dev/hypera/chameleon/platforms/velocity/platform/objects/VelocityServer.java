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
package dev.hypera.chameleon.platforms.velocity.platform.objects;

import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.platforms.velocity.VelocityChameleon;
import dev.hypera.chameleon.platforms.velocity.user.VelocityUser;
import dev.hypera.chameleon.users.platforms.ProxyUser;
import java.net.SocketAddress;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity {@link Server} implementation.
 */
@Internal
public class VelocityServer implements Server {

    private final @NotNull VelocityChameleon chameleon;
    private final @NotNull RegisteredServer server;

    /**
     * {@link VelocityServer} constructor.
     *
     * @param chameleon {@link VelocityChameleon} instance.
     * @param server    {@link RegisteredServer} to be wrapped.
     */
    @Internal
    public VelocityServer(@NotNull VelocityChameleon chameleon, @NotNull RegisteredServer server) {
        this.chameleon = chameleon;
        this.server = server;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.server.getServerInfo().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull SocketAddress getSocketAddress() {
        return this.server.getServerInfo().getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<ProxyUser> getPlayers() {
        return this.server.getPlayersConnected().stream().map(p -> new VelocityUser(this.chameleon, p)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        this.server.sendPluginMessage(MinecraftChannelIdentifier.from(channel), data);
    }

    /**
     * Get stored {@link RegisteredServer}.
     *
     * @return stored {@link RegisteredServer}.
     */
    @Internal
    public @NotNull RegisteredServer getVelocity() {
        return this.server;
    }

}
