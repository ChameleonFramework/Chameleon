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
package dev.hypera.chameleon.platform.nukkit;

import cn.nukkit.Server;
import cn.nukkit.plugin.PluginBase;
import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.data.PluginData;
import dev.hypera.chameleon.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extensions.ChameleonExtension;
import dev.hypera.chameleon.managers.CommandManager;
import dev.hypera.chameleon.managers.PluginManager;
import dev.hypera.chameleon.managers.Scheduler;
import dev.hypera.chameleon.managers.UserManager;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.nukkit.adventure.NukkitAudienceProvider;
import dev.hypera.chameleon.platform.nukkit.listeners.NukkitListener;
import dev.hypera.chameleon.platform.nukkit.logging.ChameleonNukkitLogger;
import dev.hypera.chameleon.platform.nukkit.managers.NukkitCommandManager;
import dev.hypera.chameleon.platform.nukkit.managers.NukkitPluginManager;
import dev.hypera.chameleon.platform.nukkit.managers.NukkitScheduler;
import dev.hypera.chameleon.platform.nukkit.managers.NukkitUserManager;
import dev.hypera.chameleon.platform.nukkit.platform.NukkitPlatform;
import java.nio.file.Path;
import java.util.Collection;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit {@link Chameleon} implementation.
 */
public final class NukkitChameleon extends Chameleon {

    private final @NotNull PluginBase plugin;
    private final @NotNull NukkitAudienceProvider audienceProvider = new NukkitAudienceProvider();
    private final @NotNull NukkitPlatform platform = new NukkitPlatform();
    private final @NotNull NukkitCommandManager commandManager = new NukkitCommandManager(this);
    private final @NotNull NukkitPluginManager pluginManager = new NukkitPluginManager();
    private final @NotNull NukkitUserManager userManager = new NukkitUserManager();
    private final @NotNull NukkitScheduler scheduler = new NukkitScheduler(this);

    @Internal
    NukkitChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull Collection<ChameleonExtension<?>> extensions, @NotNull PluginBase nukkitPlugin, @NotNull PluginData pluginData) throws ChameleonInstantiationException {
        super(chameleonPlugin, extensions, pluginData, new ChameleonNukkitLogger(nukkitPlugin.getLogger()));
        this.plugin = nukkitPlugin;
    }

    /**
     * Create a new {@link NukkitChameleonBootstrap} instance.
     *
     * @param chameleonPlugin {@link ChameleonPlugin} to load.
     * @param nukkitPlugin    Nukkit {@link PluginBase}.
     * @param pluginData      {@link PluginData}.
     *
     * @return new {@link NukkitChameleonBootstrap}.
     */
    public static @NotNull NukkitChameleonBootstrap create(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull PluginBase nukkitPlugin, @NotNull PluginData pluginData) {
        return new NukkitChameleonBootstrap(chameleonPlugin, nukkitPlugin, pluginData);
    }

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
        return this.plugin.getDataFolder().toPath();
    }


    /**
     * Get stored {@link PluginBase}.
     *
     * @return stored {@link PluginBase}.
     */
    public @NotNull PluginBase getPlatformPlugin() {
        return this.plugin;
    }

}
