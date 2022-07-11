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
package dev.hypera.chameleon.platforms.mock.managers;

import com.google.common.base.Preconditions;
import dev.hypera.chameleon.managers.PluginManager;
import dev.hypera.chameleon.platform.objects.PlatformPlugin;
import dev.hypera.chameleon.platforms.mock.platform.objects.MockPlugin;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Mock {@link PluginManager} implementation.
 */
public final class MockPluginManager extends PluginManager {

    private final @NotNull Path pluginsDirectory;
    private final @NotNull Map<PlatformPlugin, Boolean> plugins = new HashMap<>();

    /**
     * {@link MockPluginManager} constructor.
     *
     * @param pluginsDirectory {@link Path} used as the '/plugins/' directory on a normal platform.
     */
    public MockPluginManager(@NotNull Path pluginsDirectory) {
        this.pluginsDirectory = pluginsDirectory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<PlatformPlugin> getPlugins() {
        return this.plugins.keySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<PlatformPlugin> getPlugin(@NotNull String name) {
        return this.plugins.keySet().stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPluginEnabled(@NotNull String name) {
        return getPlugin(name).map(this.plugins::get).orElse(false);
    }

    /**
     * Create and enable a {@link PlatformPlugin}.
     *
     * @param name    Name of the plugin.
     * @param version Version of the plugin.
     *
     * @return {@link PlatformPlugin}.
     */
    public @NotNull PlatformPlugin createPlugin(@NotNull String name, @NotNull String version) {
        Preconditions.checkArgument(this.plugins.keySet().stream().noneMatch(k -> k.getName().equalsIgnoreCase(name)), "plugin with name '" + name + "' already exists");
        return addPlugin(new MockPlugin(name, version, this));
    }

    /**
     * Add and enable a {@link PlatformPlugin}.
     *
     * @param plugin {@link PlatformPlugin} instance.
     *
     * @return {@link PlatformPlugin}.
     */
    @Internal
    public @NotNull PlatformPlugin addPlugin(@NotNull PlatformPlugin plugin) {
        this.plugins.put(plugin, true);
        return plugin;
    }

    /**
     * Disable and remove a {@link PlatformPlugin}.
     *
     * @param name Name of the plugin to be removed.
     */
    public void removePlugin(@NotNull String name) {
        Optional<PlatformPlugin> plugin = this.plugins.keySet().stream().filter(k -> k.getName().equalsIgnoreCase(name)).findFirst();
        if (plugin.isPresent()) {
            this.plugins.remove(plugin.get());
        } else {
            throw new IllegalArgumentException("plugin with name '" + name + "' does not exist");
        }
    }

    /**
     * Enable {@link PlatformPlugin}.
     *
     * @param plugin {@link PlatformPlugin} to be enabled.
     *
     * @see PlatformPlugin#enable()
     */
    @Internal
    public void enablePlugin(@NotNull PlatformPlugin plugin) {
        Preconditions.checkArgument(!this.plugins.containsKey(plugin), "plugin is already registered");
        this.plugins.put(plugin, true);
    }

    /**
     * Disable {@link PlatformPlugin}.
     *
     * @param plugin {@link PlatformPlugin} to be disabled.
     *
     * @see PlatformPlugin#disable()
     */
    @Internal
    public void disablePlugin(@NotNull PlatformPlugin plugin) {
        Preconditions.checkArgument(this.plugins.containsKey(plugin), "plugin is not registered");
        this.plugins.put(plugin, false);
    }

    /**
     * Get {@link Path} used as the '/plugins/' directory on a normal platform.
     *
     * @return {@link Path}.
     */
    @Internal
    public @NotNull Path getPluginsDirectory() {
        return this.pluginsDirectory;
    }

}
