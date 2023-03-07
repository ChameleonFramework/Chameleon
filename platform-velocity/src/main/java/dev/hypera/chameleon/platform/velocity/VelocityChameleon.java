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

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.ChameleonPluginData;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.adventure.mapper.AdventureMapper;
import dev.hypera.chameleon.command.CommandManager;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.exception.reflection.ChameleonReflectiveException;
import dev.hypera.chameleon.extension.ChameleonExtension;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.platform.velocity.adventure.VelocityAudienceProvider;
import dev.hypera.chameleon.platform.velocity.command.VelocityCommandManager;
import dev.hypera.chameleon.platform.velocity.event.VelocityListener;
import dev.hypera.chameleon.platform.velocity.platform.VelocityPlatform;
import dev.hypera.chameleon.platform.velocity.platform.VelocityPluginManager;
import dev.hypera.chameleon.platform.velocity.scheduler.VelocityScheduler;
import dev.hypera.chameleon.platform.velocity.user.VelocityUserManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity Chameleon implementation.
 */
public final class VelocityChameleon extends Chameleon {

    private final @NotNull VelocityPlugin plugin;
    private final @NotNull AdventureMapper adventureMapper = new AdventureMapper(this);
    private final @NotNull VelocityAudienceProvider audienceProvider = new VelocityAudienceProvider(this);
    private final @NotNull VelocityCommandManager commandManager = new VelocityCommandManager(this);
    private final @NotNull VelocityPlatform platform = new VelocityPlatform(this);
    private final @NotNull VelocityPluginManager pluginManager = new VelocityPluginManager(this);
    private final @NotNull VelocityScheduler scheduler = new VelocityScheduler(this);
    private final @NotNull VelocityUserManager userManager = new VelocityUserManager(this);

    @Internal
    VelocityChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull VelocityPlugin velocityPlugin, @NotNull ChameleonPluginData pluginData, @NotNull EventBus eventBus, @NotNull ChameleonLogger logger, @NotNull Collection<? super ChameleonExtension> extensions) throws ChameleonInstantiationException {
        super(chameleonPlugin, pluginData, eventBus, logger, new HashSet<>(extensions));
        this.plugin = velocityPlugin;
    }

    /**
     * Create a new Velocity Chameleon bootstrap instance.
     *
     * @param chameleonPlugin Chameleon plugin to be loaded.
     * @param velocityPlugin  Velocity plugin instance.
     * @param pluginData      Chameleon plugin data.
     *
     * @return new Velocity Chameleon bootstrap.
     */
    public static @NotNull VelocityChameleonBootstrap create(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull VelocityPlugin velocityPlugin, @NotNull ChameleonPluginData pluginData) {
        return new VelocityChameleonBootstrap(chameleonPlugin, velocityPlugin, pluginData);
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
        this.plugin.getServer().getEventManager().register(this.plugin, new VelocityListener(this));
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
    public @NotNull Path getDataFolder() {
        return this.plugin.getDataDirectory();
    }

    /**
     * Get the stored Velocity plugin instance.
     *
     * @return stored plugin instance.
     */
    public @NotNull VelocityPlugin getPlatformPlugin() {
        return this.plugin;
    }

}
