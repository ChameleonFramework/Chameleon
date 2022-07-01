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
package dev.hypera.chameleon.platforms.sponge;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.ChameleonPlugin;
import dev.hypera.chameleon.core.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.core.data.PluginData;
import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.core.logging.impl.ChameleonLog4jLogger;
import dev.hypera.chameleon.core.managers.CommandManager;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.managers.Scheduler;
import dev.hypera.chameleon.core.managers.UserManager;
import dev.hypera.chameleon.core.platform.Platform;
import dev.hypera.chameleon.platforms.sponge.adventure.SpongeAudienceProvider;
import dev.hypera.chameleon.platforms.sponge.events.SpongeListener;
import dev.hypera.chameleon.platforms.sponge.managers.SpongeCommandManager;
import dev.hypera.chameleon.platforms.sponge.managers.SpongePluginManager;
import dev.hypera.chameleon.platforms.sponge.managers.SpongeScheduler;
import dev.hypera.chameleon.platforms.sponge.managers.SpongeUserManager;
import dev.hypera.chameleon.platforms.sponge.platform.SpongePlatform;
import java.nio.file.Path;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.plugin.PluginContainer;

/**
 * Sponge {@link Chameleon} implementation.
 */
public final class SpongeChameleon extends Chameleon {

    private final @NotNull SpongePlugin plugin;
    private final @NotNull SpongeAudienceProvider audienceProvider = new SpongeAudienceProvider();
    private final @NotNull SpongePlatform platform = new SpongePlatform();
    private final @NotNull SpongeCommandManager commandManager = new SpongeCommandManager(this);
    private final @NotNull SpongePluginManager pluginManager = new SpongePluginManager();
    private final @NotNull SpongeUserManager userManager = new SpongeUserManager();
    private final @NotNull SpongeScheduler scheduler = new SpongeScheduler(this);

    @Internal
    SpongeChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull SpongePlugin spongePlugin, @NotNull PluginData pluginData) throws ChameleonInstantiationException {
        super(chameleonPlugin, pluginData, new ChameleonLog4jLogger(spongePlugin.getLogger()));
        this.plugin = spongePlugin;
        Sponge.eventManager().registerListeners(getSpongePlugin(), new SpongeListener(this));
    }

    /**
     * Create a new {@link SpongeChameleonBootstrap} instance.
     *
     * @param chameleonPlugin {@link ChameleonPlugin} to load.
     * @param spongePlugin    {@link SpongePlugin}.
     * @param pluginData      {@link PluginData}.
     *
     * @return new {@link SpongeChameleonBootstrap}.
     */
    public static @NotNull SpongeChameleonBootstrap create(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull SpongePlugin spongePlugin, @NotNull PluginData pluginData) {
        return new SpongeChameleonBootstrap(chameleonPlugin, spongePlugin, pluginData);
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
     * Get the plugin's {@link PluginContainer Container} using the provided id.
     *
     * @return {@link PluginContainer}.
     */
    @Internal
    public @NotNull PluginContainer getSpongePlugin() {
        return Sponge.pluginManager().plugin(this.plugin.getId()).orElseThrow(IllegalStateException::new);
    }

}
