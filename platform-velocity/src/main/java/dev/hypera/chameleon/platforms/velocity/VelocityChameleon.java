/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package dev.hypera.chameleon.platforms.velocity;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.data.PluginData;
import dev.hypera.chameleon.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.logging.impl.ChameleonSlf4jLogger;
import dev.hypera.chameleon.managers.CommandManager;
import dev.hypera.chameleon.managers.PluginManager;
import dev.hypera.chameleon.managers.Scheduler;
import dev.hypera.chameleon.managers.UserManager;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platforms.velocity.adventure.VelocityAudienceProvider;
import dev.hypera.chameleon.platforms.velocity.events.VelocityListener;
import dev.hypera.chameleon.platforms.velocity.managers.VelocityCommandManager;
import dev.hypera.chameleon.platforms.velocity.managers.VelocityPluginManager;
import dev.hypera.chameleon.platforms.velocity.managers.VelocityScheduler;
import dev.hypera.chameleon.platforms.velocity.managers.VelocityUserManager;
import dev.hypera.chameleon.platforms.velocity.platform.VelocityPlatform;
import java.nio.file.Path;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity {@link Chameleon} implementation.
 */
public final class VelocityChameleon extends Chameleon {

    private final @NotNull VelocityPlugin plugin;
    private final @NotNull VelocityAudienceProvider audienceProvider = new VelocityAudienceProvider(this);
    private final @NotNull VelocityPlatform platform = new VelocityPlatform(this);
    private final @NotNull VelocityCommandManager commandManager = new VelocityCommandManager(this);
    private final @NotNull VelocityPluginManager pluginManager = new VelocityPluginManager(this);
    private final @NotNull VelocityUserManager userManager = new VelocityUserManager(this);
    private final @NotNull VelocityScheduler scheduler = new VelocityScheduler(this);

    @Internal
    VelocityChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull VelocityPlugin velocityPlugin, @NotNull PluginData pluginData) throws ChameleonInstantiationException {
        super(chameleonPlugin, pluginData, new ChameleonSlf4jLogger(velocityPlugin.getLogger()));
        this.plugin = velocityPlugin;
        this.plugin.getServer().getEventManager().register(this.plugin, new VelocityListener(this));
    }

    /**
     * Create a new {@link VelocityChameleonBootstrap} instance.
     *
     * @param chameleonPlugin {@link ChameleonPlugin} to load.
     * @param velocityPlugin  {@link VelocityPlugin}.
     * @param pluginData      {@link PluginData}.
     *
     * @return new {@link VelocityChameleonBootstrap}.
     */
    public static @NotNull VelocityChameleonBootstrap create(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull VelocityPlugin velocityPlugin, @NotNull PluginData pluginData) {
        return new VelocityChameleonBootstrap(chameleonPlugin, velocityPlugin, pluginData);
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
    public @NotNull Path getDataFolder() {
        return this.plugin.getDataDirectory();
    }

    /**
     * Get stored {@link VelocityPlugin}.
     *
     * @return stored {@link VelocityPlugin}.
     */
    @Internal
    public @NotNull VelocityPlugin getVelocityPlugin() {
        return this.plugin;
    }

}
