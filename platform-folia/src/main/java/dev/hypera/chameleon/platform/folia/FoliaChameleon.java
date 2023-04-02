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
package dev.hypera.chameleon.platform.folia;

import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.ChameleonPluginData;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extension.ChameleonExtension;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.platform.bukkit.BukkitChameleon;
import dev.hypera.chameleon.platform.bukkit.BukkitChameleonBootstrap;
import dev.hypera.chameleon.platform.folia.platform.FoliaPlatform;
import dev.hypera.chameleon.platform.folia.platform.FoliaPluginManager;
import dev.hypera.chameleon.platform.folia.scheduler.FoliaScheduler;
import dev.hypera.chameleon.scheduler.Scheduler;
import java.util.Collection;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Folia Chameleon implementation.
 */
// We need to extend BukkitChameleon because all Bukkit managers require an instance of it to instantiate. Once this is solved, this can be made its own class.
@Experimental
public final class FoliaChameleon extends BukkitChameleon {

    private final @Nullable FoliaPlatform platform;
    private final @Nullable FoliaPluginManager pluginManager;
    private final @Nullable FoliaScheduler scheduler;

    @Internal
    FoliaChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull Collection<ChameleonExtension<?>> extensions, @NotNull JavaPlugin foliaPlugin, @NotNull ChameleonPluginData pluginData) throws ChameleonInstantiationException {
        super(chameleonPlugin, extensions, foliaPlugin, pluginData);
        boolean isFolia = isFolia();
        this.platform = isFolia ? new FoliaPlatform() : null;
        this.pluginManager = isFolia ? new FoliaPluginManager() : null;
        this.scheduler = isFolia ? new FoliaScheduler(this) : null;
    }

    /**
     * Unsupported.
     *
     * @param chameleonPlugin Unsupported.
     * @param bukkitPlugin    Unsupported.
     * @param pluginData      Unsupported.
     * @return                Unsupported.
     */
    @Deprecated
    @SuppressWarnings("unused")
    public static @NotNull BukkitChameleonBootstrap create(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull JavaPlugin bukkitPlugin, @NotNull ChameleonPluginData pluginData) {
        throw new UnsupportedOperationException("Folia does not support Bukkit.");
    }

    /**
     * Create a new Folia Chameleon bootstrap instance.
     *
     * @param chameleonPlugin Chameleon plugin to be loaded.
     * @param foliaPlugin     Folia JavaPlugin instance.
     * @param pluginData      Chameleon plugin data.
     *
     * @return new Folia Chameleon bootstrap.
     */
    @Experimental
    public static @NotNull FoliaChameleonBootstrap createFoliaBootstrap(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull JavaPlugin foliaPlugin, @NotNull ChameleonPluginData pluginData) {
        return new FoliaChameleonBootstrap(chameleonPlugin, foliaPlugin, pluginData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Platform getPlatform() {
        return this.platform != null ? this.platform : super.getPlatform();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull PluginManager getPluginManager() {
        return this.pluginManager != null ? this.pluginManager : super.getPluginManager();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Scheduler getScheduler() {
        return this.scheduler != null ? this.scheduler : super.getScheduler();
    }

    /**
     * Check if Folia is present.
     *
     * @return true if Folia is present.
     */
    private static boolean isFolia() {
        try {
            // find a better class to use for this?
            Class.forName("io.papermc.paper.threadedregions.scheduler.AsyncScheduler");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

}
