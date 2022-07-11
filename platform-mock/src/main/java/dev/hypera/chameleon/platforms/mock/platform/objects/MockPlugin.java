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

import dev.hypera.chameleon.platform.objects.PlatformPlugin;
import dev.hypera.chameleon.platforms.mock.managers.MockPluginManager;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Mock {@link PlatformPlugin} implementation.
 */
public class MockPlugin implements PlatformPlugin {

    private final @NotNull String name;
    private final @NotNull String version;
    private final @NotNull MockPluginManager pluginManager;

    /**
     * {@link MockPlugin} constructor.
     *
     * @param name          Plugin name.
     * @param version       Plugin version.
     * @param pluginManager {@link MockPluginManager} instance.
     */
    @Internal
    public MockPlugin(@NotNull String name, @NotNull String version, @NotNull MockPluginManager pluginManager) {
        this.name = name;
        this.version = version;
        this.pluginManager = pluginManager;
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
    public @NotNull String getVersion() {
        return this.version;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<String> getDescription() {
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Class<?> getMainClass() {
        return MockPlugin.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> getAuthors() {
        return new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> getDependencies() {
        return new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> getSoftDependencies() {
        return new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Path getDataFolder() {
        return this.pluginManager.getPluginsDirectory().resolve(Normalizer.normalize(this.name.trim(), Normalizer.Form.NFD).replaceAll("[^\\x00-\\x7F]", ""));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Experimental
    public void enable() {
        this.pluginManager.enablePlugin(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Experimental
    public void disable() {
        this.pluginManager.disablePlugin(this);
    }

}
