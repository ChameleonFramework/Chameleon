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
package dev.hypera.chameleon.platforms.mock.platform;

import com.google.common.base.Preconditions;
import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.annotations.PlatformSpecific;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.proxy.ProxyPlatform;
import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.platforms.mock.platform.objects.MockServer;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Mock {@link ProxyPlatform} implementation.
 */
@PlatformSpecific(Platform.Type.PROXY)
public final class MockProxyPlatform extends ProxyPlatform implements MockPlatform {

    private final @NotNull Set<Server> servers = new HashSet<>();
    private final @NotNull Chameleon chameleon;

    /**
     * {@link MockProxyPlatform} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     */
    @Internal
    public MockProxyPlatform(@NotNull Chameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getAPIName() {
        return API_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return PROXY_NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getVersion() {
        return Chameleon.getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<Server> getServers() {
        return this.servers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<Server> getServer(@NotNull String name) {
        return this.servers.stream().filter(s -> s.getName().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * Create a new {@link Server}.
     *
     * @param name {@link Server} name.
     *
     * @return new {@link Server} instance.
     */
    public @NotNull Server createServer(@NotNull String name) {
        Preconditions.checkArgument(name.matches("[a-zA-Z0-9_-]+"), "name must match a-zA-Z0-9_-");
        Preconditions.checkArgument(this.servers.stream().noneMatch(s -> s.getName().equalsIgnoreCase(name)), "server with name '" + name + "' already exists");
        Server server = new MockServer(name, this.chameleon);
        this.servers.add(server);
        return server;
    }

    /**
     * Remove a {@link Server}.
     *
     * @param name {@link Server} name.
     */
    public void removeServer(@NotNull String name) {
        Preconditions.checkArgument(this.servers.stream().anyMatch(s -> s.getName().equalsIgnoreCase(name)), "server with name '" + name + "' does not exist");
        this.servers.removeIf(s -> s.getName().equalsIgnoreCase(name));
    }

}
