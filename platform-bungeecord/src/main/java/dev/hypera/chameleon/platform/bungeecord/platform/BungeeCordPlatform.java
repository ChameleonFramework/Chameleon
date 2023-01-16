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
package dev.hypera.chameleon.platform.bungeecord.platform;

import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.platform.bungeecord.platform.objects.BungeeCordServer;
import dev.hypera.chameleon.platform.proxy.ProxyPlatform;
import dev.hypera.chameleon.platform.proxy.Server;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ProxyServer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord proxy platform implementation.
 */
@Internal
public final class BungeeCordPlatform implements ProxyPlatform {

    private final @NotNull BungeeCordChameleon chameleon;

    /**
     * BungeeCord platform constructor.
     *
     * @param chameleon BungeeCord Chameleon implementation.
     */
    @Internal
    public BungeeCordPlatform(@NotNull BungeeCordChameleon chameleon) {
        this.chameleon = chameleon;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getId() {
        return Platform.BUNGEECORD;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return ProxyServer.getInstance().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getVersion() {
        return ProxyServer.getInstance().getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<Server> getServers() {
        return ProxyServer.getInstance().getServers().values().stream()
            .map(s -> new BungeeCordServer(this.chameleon, s)).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<Server> getServer(@NotNull String name) {
        return Optional.ofNullable(ProxyServer.getInstance().getServerInfo(name))
            .map(s -> new BungeeCordServer(this.chameleon, s));
    }

}
