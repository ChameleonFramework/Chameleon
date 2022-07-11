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

import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.platform.objects.PlatformPlugin;
import dev.hypera.chameleon.platforms.mock.MockChameleon;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Mock {@link PlatformPlugin} implementation, used for representing the Chameleon plugin.
 */
public class MockSelfPlugin implements PlatformPlugin {

    private final @NotNull ChameleonPlugin plugin;
    private final @NotNull MockChameleon chameleon;

    /**
     * {@link MockSelfPlugin} constructor.
     *
     * @param plugin    {@link ChameleonPlugin} instance.
     * @param chameleon {@link MockChameleon} instance.
     */
    @Internal
    public MockSelfPlugin(@NotNull ChameleonPlugin plugin, @NotNull MockChameleon chameleon) {
        this.plugin = plugin;
        this.chameleon = chameleon;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.plugin.getData().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getVersion() {
        return this.plugin.getData().getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<String> getDescription() {
        return Optional.of(this.plugin.getData().getDescription());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Class<?> getMainClass() {
        return this.plugin.getClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> getAuthors() {
        return this.plugin.getData().getAuthors();
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
        return this.chameleon.getDataFolder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enable() {
        this.chameleon.getPluginManager().enablePlugin(this);
        this.plugin.onEnable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disable() {
        this.chameleon.getPluginManager().disablePlugin(this);
        this.plugin.onDisable();
    }

}
