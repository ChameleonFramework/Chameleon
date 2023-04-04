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
package dev.hypera.chameleon.platform.bukkit;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonBootstrap;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.ChameleonPluginData;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.command.CommandManager;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extension.ExtensionMap;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.platform.bukkit.adventure.BukkitAudienceProvider;
import dev.hypera.chameleon.platform.bukkit.command.BukkitCommandManager;
import dev.hypera.chameleon.platform.bukkit.event.BukkitListener;
import dev.hypera.chameleon.platform.bukkit.platform.BukkitPlatform;
import dev.hypera.chameleon.platform.bukkit.platform.BukkitPluginManager;
import dev.hypera.chameleon.platform.bukkit.scheduler.BukkitScheduler;
import dev.hypera.chameleon.platform.bukkit.user.BukkitUserManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import dev.hypera.chameleon.util.Preconditions;
import java.nio.file.Path;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Bukkit Chameleon implementation.
 *
 * <p>Not final to allow Folia implementation to extend this class.</p>
 */
@NonExtendable
public class BukkitChameleon extends Chameleon {

    private final @NotNull JavaPlugin plugin;
    private final @NotNull BukkitPlatform platform = new BukkitPlatform();
    private final @NotNull BukkitCommandManager commandManager = new BukkitCommandManager(this);
    private final @NotNull BukkitPluginManager pluginManager = new BukkitPluginManager();
    private final @NotNull BukkitUserManager userManager = new BukkitUserManager(this);
    private final @NotNull BukkitScheduler scheduler = new BukkitScheduler(this);

    private @Nullable ChameleonAudienceProvider audienceProvider;

    @Internal
    protected BukkitChameleon(
        @NotNull Class<? extends ChameleonPlugin> chameleonPlugin,
        @NotNull JavaPlugin bukkitPlugin,
        @NotNull ChameleonPluginData pluginData,
        @NotNull EventBus eventBus,
        @NotNull ChameleonLogger logger,
        @NotNull ExtensionMap extensions
    ) throws ChameleonInstantiationException {
        super(chameleonPlugin, pluginData, eventBus, logger, extensions);
        this.plugin = bukkitPlugin;
    }

    /**
     * Create a new Bukkit Chameleon bootstrap instance.
     *
     * @param chameleonPlugin Chameleon plugin to be loaded.
     * @param bukkitPlugin    Bukkit JavaPlugin instance.
     * @param pluginData      Chameleon plugin data.
     *
     * @return new Bukkit Chameleon bootstrap.
     */
    public static @NotNull ChameleonBootstrap<BukkitChameleon> create(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull JavaPlugin bukkitPlugin, @NotNull ChameleonPluginData pluginData) {
        return new BukkitChameleonBootstrap(chameleonPlugin, bukkitPlugin, pluginData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        this.audienceProvider = new BukkitAudienceProvider(this, this.plugin);
        Bukkit.getPluginManager().registerEvents(new BukkitListener(this), this.plugin);
        super.onEnable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisable() {
        this.getAdventure().close();
        super.onDisable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChameleonAudienceProvider getAdventure() {
        Preconditions.checkState(
            this.audienceProvider != null, "Chameleon has not been loaded"
        );
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
    public @NotNull BukkitUserManager getUserManager() {
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
    public @NotNull Path getDataFolder() {
        return this.plugin.getDataFolder().toPath().toAbsolutePath();
    }

    /**
     * Get stored platform JavaPlugin.
     *
     * @return stored JavaPlugin.
     */
    public @NotNull JavaPlugin getPlatformPlugin() {
        return this.plugin;
    }

}
