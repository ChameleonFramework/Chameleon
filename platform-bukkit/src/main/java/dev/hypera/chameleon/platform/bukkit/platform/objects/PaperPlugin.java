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
package dev.hypera.chameleon.platform.bukkit.platform.objects;

import dev.hypera.chameleon.platform.PlatformPlugin;
import dev.hypera.chameleon.platform.util.ReflectionUtil;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Paper platform plugin implementation.
 */
@Internal
@SuppressWarnings("UnstableApiUsage") // Paper - PluginMeta is @Experimental, and subject to change
final class PaperPlugin implements PlatformPlugin {

    static final boolean SUPPORTED = ReflectionUtil.hasClass("io.papermc.paper.plugin.configuration.PluginMeta");
    private final @NotNull Plugin plugin;

    PaperPlugin(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.plugin.getPluginMeta().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getVersion() {
        return this.plugin.getPluginMeta().getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<String> getDescription() {
        return Optional.ofNullable(this.plugin.getPluginMeta().getDescription());
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
    public @NotNull Collection<String> getAuthors() {
        return this.plugin.getPluginMeta().getAuthors();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<String> getDependencies() {
        return this.plugin.getPluginMeta().getPluginDependencies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<String> getSoftDependencies() {
        return this.plugin.getPluginMeta().getPluginSoftDependencies();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Path getDataDirectory() {
        return this.plugin.getDataFolder().toPath().toAbsolutePath();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enable() {
        Bukkit.getPluginManager().enablePlugin(this.plugin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disable() {
        Bukkit.getPluginManager().disablePlugin(this.plugin);
    }

}
