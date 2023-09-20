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

import dev.hypera.chameleon.ChameleonPluginBootstrap;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.command.CommandManager;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.extension.ExtensionMap;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PlatformChameleon;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.platform.bukkit.adventure.BukkitAudienceProvider;
import dev.hypera.chameleon.platform.bukkit.command.BukkitCommandManager;
import dev.hypera.chameleon.platform.bukkit.event.BukkitEventDispatcher;
import dev.hypera.chameleon.platform.bukkit.platform.BukkitPlatform;
import dev.hypera.chameleon.platform.bukkit.platform.BukkitPluginManager;
import dev.hypera.chameleon.platform.bukkit.scheduler.BukkitScheduler;
import dev.hypera.chameleon.platform.bukkit.user.BukkitUserManager;
import dev.hypera.chameleon.platform.folia.platform.FoliaPlatform;
import dev.hypera.chameleon.platform.folia.platform.FoliaPluginManager;
import dev.hypera.chameleon.platform.folia.scheduler.FoliaScheduler;
import dev.hypera.chameleon.scheduler.Scheduler;
import dev.hypera.chameleon.user.UserManager;
import java.nio.file.Path;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Folia Chameleon implementation.
 */
@Experimental
public final class FoliaChameleon extends PlatformChameleon<JavaPlugin> {

    private final @NotNull Platform platform;
    private final @NotNull BukkitEventDispatcher eventDispatcher = new BukkitEventDispatcher(this);
    private final @NotNull BukkitUserManager userManager = new BukkitUserManager(this, this.eventDispatcher);
    private final @NotNull CommandManager commandManager = new BukkitCommandManager(this, this.userManager);
    private final @NotNull PluginManager pluginManager;
    private final @NotNull Scheduler scheduler;
    private final @NotNull BukkitAudienceProvider audienceProvider = new BukkitAudienceProvider(this.userManager);

    @Internal
    FoliaChameleon(
        @NotNull ChameleonPluginBootstrap pluginBootstrap,
        @NotNull JavaPlugin foliaPlugin,
        @NotNull EventBus eventBus,
        @NotNull ChameleonLogger logger,
        @NotNull ExtensionMap extensions
    ) {
        super(pluginBootstrap, foliaPlugin, eventBus, logger, extensions);
        boolean folia = isFolia();
        this.platform = folia ? new FoliaPlatform() : new BukkitPlatform();
        this.pluginManager = folia ? new FoliaPluginManager() : new BukkitPluginManager();
        this.scheduler = folia ? new FoliaScheduler(foliaPlugin) : new BukkitScheduler(foliaPlugin);
    }

    /**
     * Returns a new Folia Chameleon bootstrap instance.
     *
     * @param pluginBootstrap Chameleon plugin bootstrap.
     * @param foliaPlugin     Folia JavaPlugin instance.
     *
     * @return new Folia Chameleon bootstrap.
     */
    @Experimental
    public static @NotNull FoliaChameleonBootstrap create(@NotNull ChameleonPluginBootstrap pluginBootstrap, @NotNull JavaPlugin foliaPlugin) {
        return new FoliaChameleonBootstrap(pluginBootstrap, foliaPlugin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        this.audienceProvider.init(this.plugin);
        this.eventDispatcher.registerListeners();
        super.onEnable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisable() {
        super.onDisable();
        this.audienceProvider.close();
        this.eventDispatcher.unregisterListeners();
        this.userManager.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChameleonAudienceProvider getAdventure() {
        return this.audienceProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Platform getPlatform() {
        return this.platform;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull CommandManager getCommandManager() {
        return this.commandManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull PluginManager getPluginManager() {
        return this.pluginManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull UserManager getUserManager() {
        return this.userManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Scheduler getScheduler() {
        return this.scheduler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Path getDataDirectory() {
        return this.plugin.getDataFolder().toPath().toAbsolutePath();
    }

    /**
     * Returns whether this server is a Folia server.
     *
     * @return {@code true} if this server is a Folia server.
     */
    private static boolean isFolia() {
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            return true;
        } catch (ClassNotFoundException ex) {
            return false;
        }
    }

}
