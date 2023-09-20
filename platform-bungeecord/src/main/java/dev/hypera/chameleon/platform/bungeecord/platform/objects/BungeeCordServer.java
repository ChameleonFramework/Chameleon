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
package dev.hypera.chameleon.platform.bungeecord.platform.objects;

import dev.hypera.chameleon.platform.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.user.ProxyUser;
import java.net.SocketAddress;
import java.util.Set;
import java.util.stream.Collectors;
import net.md_5.bungee.api.config.ServerInfo;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord server implementation.
 */
@Internal
public final class BungeeCordServer implements Server {

    private final @NotNull BungeeCordChameleon chameleon;
    private final @NotNull ServerInfo server;

    /**
     * BungeeCord server implementation.
     *
     * @param chameleon Chameleon implementation.
     * @param server    ServerInfo instance.
     */
    @Internal
    public BungeeCordServer(@NotNull BungeeCordChameleon chameleon, @NotNull ServerInfo server) {
        this.chameleon = chameleon;
        this.server = server;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.server.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull SocketAddress getSocketAddress() {
        return this.server.getSocketAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<ProxyUser> getPlayers() {
        return this.server.getPlayers().stream()
            .map(p -> this.chameleon.getUserManager().wrapUser(p))
            .map(p -> (ProxyUser) p).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        this.server.sendData(channel, data);
    }

    /**
     * Get the stored ServerInfo.
     *
     * @return the stored ServerInfo.
     */
    @Internal
    public @NotNull ServerInfo getBungeeCord() {
        return this.server;
    }

}
