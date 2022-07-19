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
package dev.hypera.chameleon.platform.velocity.platform.objects;

import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.meta.PluginDependency;
import dev.hypera.chameleon.platform.objects.PlatformPlugin;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity {@link PlatformPlugin} implementation.
 */
@Internal
public class VelocityPlugin implements PlatformPlugin {

    private final @NotNull PluginContainer plugin;

    /**
     * {@link VelocityPlugin} constructor.
     *
     * @param plugin {@link PluginContainer} to be wrapped.
     */
    @Internal
    public VelocityPlugin(@NotNull PluginContainer plugin) {
        this.plugin = plugin;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.plugin.getDescription().getName().orElse(this.plugin.getDescription().getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getVersion() {
        return this.plugin.getDescription().getVersion().orElse("0.0.0");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<String> getDescription() {
        return this.plugin.getDescription().getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Class<?> getMainClass() {
        return this.plugin.getInstance().orElseThrow(IllegalStateException::new).getClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> getAuthors() {
        return this.plugin.getDescription().getAuthors();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> getDependencies() {
        return this.plugin.getDescription().getDependencies().stream().filter(d -> !d.isOptional()).map(PluginDependency::getId).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> getSoftDependencies() {
        return this.plugin.getDescription().getDependencies().stream().filter(PluginDependency::isOptional).map(PluginDependency::getId).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Path getDataFolder() {
        return Paths.get("/");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enable() {
        throw new UnsupportedOperationException("Velocity plugins cannot be enabled");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disable() {
        throw new UnsupportedOperationException("Velocity plugins cannot be disabled");
    }

}
