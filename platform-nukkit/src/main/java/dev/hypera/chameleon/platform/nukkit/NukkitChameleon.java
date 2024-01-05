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
package dev.hypera.chameleon.platform.nukkit;

import cn.nukkit.plugin.PluginBase;
import dev.hypera.chameleon.ChameleonPluginBootstrap;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.command.CommandManager;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.extension.ExtensionMap;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PlatformChameleon;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.platform.adventure.StandaloneAudienceProvider;
import dev.hypera.chameleon.platform.nukkit.command.NukkitCommandManager;
import dev.hypera.chameleon.platform.nukkit.event.NukkitEventDispatcher;
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
public final class NukkitChameleon extends PlatformChameleon<PluginBase> {

    private final @NotNull NukkitPlatform platform = new NukkitPlatform();
    private final @NotNull NukkitCommandManager commandManager = new NukkitCommandManager(this);
    private final @NotNull NukkitPluginManager pluginManager = new NukkitPluginManager();
    private final @NotNull NukkitEventDispatcher eventDispatcher = new NukkitEventDispatcher(this);
    private final @NotNull NukkitUserManager userManager = new NukkitUserManager(this);
    private final @NotNull NukkitScheduler scheduler = new NukkitScheduler(this);
    private final @NotNull ChameleonAudienceProvider audienceProvider = new StandaloneAudienceProvider(this.userManager);

    @Internal
    NukkitChameleon(
        @NotNull ChameleonPluginBootstrap pluginBootstrap,
        @NotNull PluginBase nukkitPlugin,
        @NotNull EventBus eventBus,
        @NotNull ChameleonLogger logger,
        @NotNull ExtensionMap extensions
    ) {
        super(pluginBootstrap, nukkitPlugin, eventBus, logger, extensions);
    }

    /**
     * Returns a new Nukkit Chameleon bootstrap instance.
     *
     * @param pluginBootstrap Chameleon plugin bootstrap.
     * @param nukkitPlugin    Nukkit PluginBase instance.
     *
     * @return new Nukkit Chameleon bootstrap.
     */
    public static @NotNull NukkitChameleonBootstrap create(@NotNull ChameleonPluginBootstrap pluginBootstrap, @NotNull PluginBase nukkitPlugin) {
        return new NukkitChameleonBootstrap(pluginBootstrap, nukkitPlugin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        this.userManager.registerListeners();
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
        this.userManager.unregisterListeners();
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
    public @NotNull Path getDataDirectory() {
        return this.plugin.getDataFolder().toPath();
    }

}
