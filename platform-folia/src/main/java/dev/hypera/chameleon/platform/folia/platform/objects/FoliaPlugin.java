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
package dev.hypera.chameleon.platform.folia.platform.objects;

import dev.hypera.chameleon.platform.PlatformPlugin;
import dev.hypera.chameleon.util.internal.ChameleonUtil;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Folia platform plugin implementation.
 */
@Internal
@SuppressWarnings({ "UnstableApiUsage" })
public final class FoliaPlugin implements PlatformPlugin {

    private final @NotNull Plugin plugin;

    /**
     * Folia plugin constructor.
     *
     * @param plugin Plugin to be wrapped.
     */
    @Internal
    public FoliaPlugin(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return ChameleonUtil.getOrDefault(this.plugin.getPluginMeta().getName(), "unknown");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getVersion() {
        return ChameleonUtil.getOrDefault(this.plugin.getPluginMeta().getVersion(), "unknown");
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
        return ChameleonUtil.getOrDefault(this.plugin.getPluginMeta().getAuthors(), Collections.emptyList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> getDependencies() {
        return new HashSet<>(this.plugin.getPluginMeta().getPluginDependencies());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> getSoftDependencies() {
        return new HashSet<>(this.plugin.getPluginMeta().getPluginSoftDependencies());
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
