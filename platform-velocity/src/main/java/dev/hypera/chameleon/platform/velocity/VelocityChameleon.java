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
package dev.hypera.chameleon.platform.velocity;

import dev.hypera.chameleon.ChameleonPluginBootstrap;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.adventure.mapper.AdventureMapper;
import dev.hypera.chameleon.command.CommandManager;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.exception.reflection.ChameleonReflectiveException;
import dev.hypera.chameleon.extension.ExtensionMap;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PlatformChameleon;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.platform.adventure.StandaloneAudienceProvider;
import dev.hypera.chameleon.platform.velocity.command.VelocityCommandManager;
import dev.hypera.chameleon.platform.velocity.event.VelocityEventDispatcher;
import dev.hypera.chameleon.platform.velocity.platform.VelocityPlatform;
import dev.hypera.chameleon.platform.velocity.platform.VelocityPluginManager;
import dev.hypera.chameleon.platform.velocity.scheduler.VelocityScheduler;
import dev.hypera.chameleon.platform.velocity.user.VelocityUserManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import java.nio.file.Path;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity Chameleon implementation.
 */
public final class VelocityChameleon extends PlatformChameleon<VelocityPlugin> {

    private final @NotNull AdventureMapper adventureMapper = new AdventureMapper(this);
    private final @NotNull VelocityCommandManager commandManager = new VelocityCommandManager(this);
    private final @NotNull VelocityPlatform platform = new VelocityPlatform(this);
    private final @NotNull VelocityPluginManager pluginManager = new VelocityPluginManager(this);
    private final @NotNull VelocityScheduler scheduler = new VelocityScheduler(this);
    private final @NotNull VelocityEventDispatcher eventDispatcher = new VelocityEventDispatcher(this);
    private final @NotNull VelocityUserManager userManager = new VelocityUserManager(this);
    private final @NotNull ChameleonAudienceProvider audienceProvider = new StandaloneAudienceProvider(this.userManager);

    @Internal
    VelocityChameleon(
        @NotNull ChameleonPluginBootstrap pluginBootstrap,
        @NotNull VelocityPlugin velocityPlugin,
        @NotNull EventBus eventBus,
        @NotNull ChameleonLogger logger,
        @NotNull ExtensionMap extensions
    ) {
        super(pluginBootstrap, velocityPlugin, eventBus, logger, extensions);
    }

    /**
     * Returns a new Velocity Chameleon bootstrap instance.
     *
     * @param pluginBootstrap Chameleon plugin bootstrap.
     * @param velocityPlugin  Velocity plugin instance.
     *
     * @return new Velocity Chameleon bootstrap.
     */
    public static @NotNull VelocityChameleonBootstrap create(@NotNull ChameleonPluginBootstrap pluginBootstrap, @NotNull VelocityPlugin velocityPlugin) {
        return new VelocityChameleonBootstrap(pluginBootstrap, velocityPlugin);
    }

    /**
     * Get stored Adventure mapper instance.
     *
     * @return mapper instance.
     */
    public @NotNull AdventureMapper getAdventureMapper() {
        return this.adventureMapper;
    }

    @Override
    public void onLoad() {
        try {
            this.adventureMapper.load();
            this.userManager.load();
        } catch (ReflectiveOperationException ex) {
            throw new ChameleonReflectiveException(ex);
        }
        super.onLoad();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
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
    public @NotNull VelocityUserManager getUserManager() {
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
        return this.plugin.getDataDirectory();
    }

}
