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
package dev.hypera.chameleon.platform.nukkit;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
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
import dev.hypera.chameleon.platform.nukkit.adventure.NukkitAudienceProvider;
import dev.hypera.chameleon.platform.nukkit.command.NukkitCommandManager;
import dev.hypera.chameleon.platform.nukkit.event.NukkitListener;
import dev.hypera.chameleon.platform.nukkit.platform.NukkitPlatform;
import dev.hypera.chameleon.platform.nukkit.platform.NukkitPluginManager;
import dev.hypera.chameleon.platform.nukkit.scheduler.NukkitScheduler;
import dev.hypera.chameleon.platform.nukkit.user.NukkitUserManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import java.nio.file.Path;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit Chameleon implementation.
 */
public final class NukkitChameleon extends Chameleon {

    private final @NotNull PluginBase plugin;
    private final @NotNull NukkitAudienceProvider audienceProvider = new NukkitAudienceProvider(this);
    private final @NotNull NukkitPlatform platform = new NukkitPlatform();
    private final @NotNull NukkitCommandManager commandManager = new NukkitCommandManager(this);
    private final @NotNull NukkitPluginManager pluginManager = new NukkitPluginManager();
    private final @NotNull NukkitUserManager userManager = new NukkitUserManager();
    private final @NotNull NukkitScheduler scheduler = new NukkitScheduler(this);

    @Internal
    NukkitChameleon(
        @NotNull Class<? extends ChameleonPlugin> chameleonPlugin,
        @NotNull PluginBase nukkitPlugin,
        @NotNull ChameleonPluginData pluginData,
        @NotNull EventBus eventBus,
        @NotNull ChameleonLogger logger,
        @NotNull ExtensionMap extensions
    ) throws ChameleonInstantiationException {
        super(chameleonPlugin, pluginData, eventBus, logger, extensions);
        this.plugin = nukkitPlugin;
    }

    /**
     * Create a new Nukkit Chameleon bootstrap instance.
     *
     * @param chameleonPlugin Chameleon plugin to load.
     * @param nukkitPlugin    Nukkit PluginBase instance.
     * @param pluginData      Chameleon plugin data.
     *
     * @return new Nukkit Chameleon bootstrap.
     */
    public static @NotNull ChameleonBootstrap<NukkitChameleon> create(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull PluginBase nukkitPlugin, @NotNull ChameleonPluginData pluginData) {
        return new NukkitChameleonBootstrap(chameleonPlugin, nukkitPlugin, pluginData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        Server.getInstance().getPluginManager().registerEvents(new NukkitListener(this), this.plugin);
        super.onEnable();
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
    public @NotNull NukkitUserManager getUserManager() {
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
        return this.plugin.getDataFolder().toPath();
    }


    /**
     * Get the stored Nukkit PluginBase.
     *
     * @return the stored Nukkit PluginBase.
     */
    public @NotNull PluginBase getPlatformPlugin() {
        return this.plugin;
    }

}
