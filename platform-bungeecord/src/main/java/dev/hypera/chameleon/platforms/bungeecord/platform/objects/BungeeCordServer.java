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
package dev.hypera.chameleon.platforms.bungeecord.platform.objects;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.platform.proxy.Server;
import dev.hypera.chameleon.core.users.platforms.ProxyUser;
import dev.hypera.chameleon.platforms.bungeecord.users.BungeeCordUser;
import java.net.SocketAddress;
import java.util.Set;
import java.util.stream.Collectors;
import net.md_5.bungee.api.config.ServerInfo;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord server implementation
 */
public class BungeeCordServer implements Server {

    private final @NotNull Chameleon chameleon;
    private final @NotNull ServerInfo server;

    public BungeeCordServer(@NotNull Chameleon chameleon, @NotNull ServerInfo server) {
        this.chameleon = chameleon;
        this.server = server;
    }


    @Override
    public @NotNull String getName() {
        return server.getName();
    }

    @Override
    public @NotNull SocketAddress getSocketAddress() {
        return server.getSocketAddress();
    }

    @Override
    public @NotNull Set<ProxyUser> getPlayers() {
        return server.getPlayers().stream().map(p -> new BungeeCordUser(chameleon, p)).collect(Collectors.toSet());
    }

    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        server.sendData(channel, data);
    }

    @Internal
    public @NotNull ServerInfo getBungeeCord() {
        return server;
    }

}
