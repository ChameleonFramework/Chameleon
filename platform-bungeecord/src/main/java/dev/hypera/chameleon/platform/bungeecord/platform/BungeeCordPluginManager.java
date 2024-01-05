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
package dev.hypera.chameleon.platform.bungeecord.platform;

import dev.hypera.chameleon.platform.PlatformPlugin;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.platform.bungeecord.platform.objects.BungeeCordPlugin;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ProxyServer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord plugin manager implementation.
 */
@Internal
public final class BungeeCordPluginManager implements PluginManager {

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<PlatformPlugin> getPlugins() {
        return ProxyServer.getInstance().getPluginManager().getPlugins().stream()
            .map(BungeeCordPlugin::new).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<PlatformPlugin> getPlugin(@NotNull String name) {
        return Optional.ofNullable(ProxyServer.getInstance().getPluginManager().getPlugin(name))
            .map(BungeeCordPlugin::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPluginEnabled(@NotNull String name) {
        return ProxyServer.getInstance().getPluginManager().getPlugin(name) != null;
    }

}
