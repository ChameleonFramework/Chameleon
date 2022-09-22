/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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

import dev.hypera.chameleon.platform.objects.PlatformPlugin;
import dev.hypera.chameleon.utils.ChameleonUtil;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit {@link PlatformPlugin} implementation.
 */
@Internal
public class BukkitPlugin implements PlatformPlugin {

    private final @NotNull Plugin plugin;

    /**
     * {@link BukkitPlugin} constructor.
     *
     * @param plugin {@link Plugin} to be wrapped.
     */
    @Internal
    public BukkitPlugin(@NotNull Plugin plugin) {
        this.plugin = plugin;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return ChameleonUtil.getOrDefault(this.plugin.getDescription().getName(), "unknown");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getVersion() {
        return ChameleonUtil.getOrDefault(this.plugin.getDescription().getVersion(), "unknown");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<String> getDescription() {
        return Optional.ofNullable(this.plugin.getDescription().getDescription());
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
        return ChameleonUtil.getOrDefault(this.plugin.getDescription().getAuthors(), Collections.emptyList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> getDependencies() {
        return new HashSet<>(this.plugin.getDescription().getDepend());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> getSoftDependencies() {
        return new HashSet<>(this.plugin.getDescription().getSoftDepend());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Path getDataFolder() {
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
