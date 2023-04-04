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
package dev.hypera.chameleon.platform.sponge;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonBootstrap;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.ChameleonPluginData;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.adventure.mapper.AdventureMapper;
import dev.hypera.chameleon.command.CommandManager;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.exception.reflection.ChameleonReflectiveException;
import dev.hypera.chameleon.extension.ExtensionMap;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.platform.sponge.adventure.SpongeAudienceProvider;
import dev.hypera.chameleon.platform.sponge.command.SpongeCommandManager;
import dev.hypera.chameleon.platform.sponge.event.SpongeListener;
import dev.hypera.chameleon.platform.sponge.platform.SpongePlatform;
import dev.hypera.chameleon.platform.sponge.platform.SpongePluginManager;
import dev.hypera.chameleon.platform.sponge.scheduler.SpongeScheduler;
import dev.hypera.chameleon.platform.sponge.user.SpongeUserManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import java.nio.file.Path;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;

/**
 * Sponge Chameleon implementation.
 */
public final class SpongeChameleon extends Chameleon {

    private final @NotNull SpongePlugin plugin;
    private final @NotNull AdventureMapper adventureMapper = new AdventureMapper(this);
    private final @NotNull SpongeAudienceProvider audienceProvider = new SpongeAudienceProvider(this);
    private final @NotNull SpongePlatform platform = new SpongePlatform();
    private final @NotNull SpongeCommandManager commandManager = new SpongeCommandManager(this);
    private final @NotNull SpongePluginManager pluginManager = new SpongePluginManager();
    private final @NotNull SpongeUserManager userManager = new SpongeUserManager(this);
    private final @NotNull SpongeScheduler scheduler = new SpongeScheduler(this);
    private final @NotNull SpongeListener listener = new SpongeListener(this);

    @Internal
    SpongeChameleon(
        @NotNull Class<? extends ChameleonPlugin> chameleonPlugin,
        @NotNull SpongePlugin spongePlugin,
        @NotNull ChameleonPluginData pluginData,
        @NotNull EventBus eventBus,
        @NotNull ChameleonLogger logger,
        @NotNull ExtensionMap extensions
    ) throws ChameleonInstantiationException {
        super(chameleonPlugin, pluginData, eventBus, logger, extensions);
        this.plugin = spongePlugin;
    }

    /**
     * Create a new Sponge Chameleon bootstrap instance.
     *
     * @param chameleonPlugin Chameleon plugin to be loaded.
     * @param spongePlugin    Sponge plugin instance.
     * @param pluginData      Chameleon plugin data.
     *
     * @return new Sponge Chameleon bootstrap.
     */
    public static @NotNull ChameleonBootstrap<SpongeChameleon> create(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull SpongePlugin spongePlugin, @NotNull ChameleonPluginData pluginData) {
        return new SpongeChameleonBootstrap(chameleonPlugin, spongePlugin, pluginData);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onLoad() {
        try {
            this.adventureMapper.load();
            this.userManager.load();
            this.listener.load();
        } catch (ReflectiveOperationException ex) {
            throw new ChameleonReflectiveException(ex);
        }
        Sponge.eventManager().registerListeners(this.plugin.getPluginContainer(), this.listener);
        super.onLoad();
    }

    /**
     * Get stored Adventure mapper instance.
     *
     * @return mapper instance.
     */
    public @NotNull AdventureMapper getAdventureMapper() {
        return this.adventureMapper;
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
    public @NotNull SpongeUserManager getUserManager() {
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
     * Get the stored Sponge plugin.
     *
     * @return the stored Sponge plugin.
     */
    public @NotNull SpongePlugin getPlatformPlugin() {
        return this.plugin;
    }

}
