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
package dev.hypera.chameleon.platform.objects;

import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.user.ProxyUser;
import java.net.SocketAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.NotNull;

public final class PlatformProxyUserImpl extends PlatformUserImpl implements ProxyUser {


    public PlatformProxyUserImpl(@NotNull PlatformPlayer platformPlayer) {
        super(platformPlayer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<Server> getConnectedServer() {
        return Optional.ofNullable(this.platformPlayer.serverName())
            .map(n -> n.isEmpty() ? null : n)
            .map(name -> new Server() {
                @Override
                public @NotNull String getName() {
                    return name;
                }

                @Override
                public @NotNull SocketAddress getSocketAddress() {
                    throw new UnsupportedOperationException("not implemented");
                }

                @Override
                public @NotNull Collection<ProxyUser> getPlayers() {
                    return Collections.singleton(PlatformProxyUserImpl.this);
                }

                @Override
                public void sendData(@NotNull String channel, byte[] data) {
                    throw new UnsupportedOperationException("not implemented");
                }
            });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NotNull Server server) {
        throw new UnsupportedOperationException("not implemented");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback) {
        throw new UnsupportedOperationException("not implemented");
    }

}
