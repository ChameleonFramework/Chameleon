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
package dev.hypera.chameleon.platforms.mock.user;

import dev.hypera.chameleon.annotations.PlatformSpecific;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.platforms.mock.MockChameleon;
import dev.hypera.chameleon.users.platforms.ProxyUser;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock {@link ProxyUser} implementation.
 */
@PlatformSpecific(Platform.Type.PROXY)
public final class MockProxyUser extends MockUser implements ProxyUser {

    private @Nullable Server server;

    /**
     * {@link MockProxyUser} constructor.
     *
     * @param id        Unique id.
     * @param name      Name.
     * @param chameleon {@link MockChameleon} instance.
     * @param server    Server.
     */
    @Internal
    public MockProxyUser(@NotNull UUID id, @NotNull String name, @NotNull MockChameleon chameleon, @NotNull Server server) {
        super(id, name, chameleon);
        this.server = server;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<Server> getServer() {
        return Optional.ofNullable(this.server);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@Nullable Server server) {
        this.server = server;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void connect(@Nullable Server server, @NotNull BiConsumer<Boolean, Throwable> callback) {
        this.server = server;
        callback.accept(null != server, null);
    }

}
