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
package dev.hypera.chameleon.platform.velocity.managers;

import dev.hypera.chameleon.managers.PluginManager;
import dev.hypera.chameleon.platform.objects.PlatformPlugin;
import dev.hypera.chameleon.platform.velocity.VelocityChameleon;
import dev.hypera.chameleon.platform.velocity.platform.objects.VelocityPlugin;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity {@link PluginManager} implementation.
 */
@Internal
public final class VelocityPluginManager extends PluginManager {

    private final @NotNull VelocityChameleon chameleon;

    /**
     * {@link VelocityPluginManager} constructor.
     *
     * @param chameleon {@link VelocityChameleon} instance.
     */
    @Internal
    public VelocityPluginManager(@NotNull VelocityChameleon chameleon) {
        this.chameleon = chameleon;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<PlatformPlugin> getPlugins() {
        return this.chameleon.getVelocityPlugin().getServer().getPluginManager().getPlugins().stream().map(VelocityPlugin::new).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<PlatformPlugin> getPlugin(@NotNull String name) {
        return this.chameleon.getVelocityPlugin().getServer().getPluginManager().getPlugin(name.toLowerCase()).map(VelocityPlugin::new);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPluginEnabled(@NotNull String name) {
        return this.chameleon.getVelocityPlugin().getServer().getPluginManager().isLoaded(name.toLowerCase());
    }

}
