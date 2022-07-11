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
package dev.hypera.chameleon.platforms.mock.platform.objects;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.users.ChatUser;
import dev.hypera.chameleon.users.platforms.ProxyUser;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Mock {@link Server} implementation.
 */
public final class MockServer implements Server {

    private final @NotNull String name;
    private final @NotNull Set<UUID> players = new HashSet<>();
    private final @NotNull Chameleon chameleon;

    /**
     * {@link MockServer} constructor.
     *
     * @param name      {@link Server} name.
     * @param chameleon {@link Chameleon} instance.
     */
    @Internal
    public MockServer(@NotNull String name, @NotNull Chameleon chameleon) {
        this.name = name;
        this.chameleon = chameleon;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull SocketAddress getSocketAddress() {
        return new InetSocketAddress(InetAddress.getLoopbackAddress(), 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<ProxyUser> getPlayers() {
        return this.players.stream().map(p -> this.chameleon.getUserManager().getPlayer(p).map(ChatUser::proxy).orElse(null)).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    /**
     * Add a {@link ProxyUser} to this server.
     *
     * @param user {@link ProxyUser} to be added to this server.
     */
    @Internal
    public void addPlayer(@NotNull ProxyUser user) {
        this.players.add(user.getUniqueId());
    }

    /**
     * Remove a {@link ProxyUser} from this server.
     *
     * @param user {@link ProxyUser} to be removed from this server.
     */
    @Internal
    public void removePlayer(@NotNull ProxyUser user) {
        this.players.removeIf(p -> user.getUniqueId().equals(p));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {

    }

}
