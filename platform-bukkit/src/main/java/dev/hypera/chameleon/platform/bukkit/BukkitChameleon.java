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
package dev.hypera.chameleon.platform.bukkit;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.data.PluginData;
import dev.hypera.chameleon.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extensions.ChameleonExtension;
import dev.hypera.chameleon.logging.impl.ChameleonJavaLogger;
import dev.hypera.chameleon.managers.CommandManager;
import dev.hypera.chameleon.managers.PluginManager;
import dev.hypera.chameleon.managers.Scheduler;
import dev.hypera.chameleon.managers.UserManager;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.bukkit.adventure.BukkitAudienceProvider;
import dev.hypera.chameleon.platform.bukkit.events.BukkitListener;
import dev.hypera.chameleon.platform.bukkit.managers.BukkitCommandManager;
import dev.hypera.chameleon.platform.bukkit.managers.BukkitPluginManager;
import dev.hypera.chameleon.platform.bukkit.managers.BukkitScheduler;
import dev.hypera.chameleon.platform.bukkit.managers.BukkitUserManager;
import dev.hypera.chameleon.platform.bukkit.platform.BukkitPlatform;
import java.nio.file.Path;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Bukkit {@link Chameleon} implementation.
 */
public final class BukkitChameleon extends Chameleon {

    private final @NotNull JavaPlugin plugin;
    private final @NotNull BukkitPlatform platform = new BukkitPlatform();
    private final @NotNull BukkitCommandManager commandManager = new BukkitCommandManager(this);
    private final @NotNull BukkitPluginManager pluginManager = new BukkitPluginManager();
    private final @NotNull BukkitUserManager userManager = new BukkitUserManager(this);
    private final @NotNull BukkitScheduler scheduler = new BukkitScheduler(this);

    private @Nullable ChameleonAudienceProvider audienceProvider;

    @Internal
    BukkitChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull Collection<ChameleonExtension<?>> extensions, @NotNull JavaPlugin bukkitPlugin, @NotNull PluginData pluginData) throws ChameleonInstantiationException {
        super(chameleonPlugin, extensions, pluginData, new ChameleonJavaLogger(bukkitPlugin.getLogger()));
        this.plugin = bukkitPlugin;
    }

    /**
     * Create a new {@link BukkitChameleonBootstrap} instance.
     *
     * @param chameleonPlugin {@link ChameleonPlugin} to load.
     * @param bukkitPlugin    {@link JavaPlugin}.
     * @param pluginData      {@link PluginData}.
     *
     * @return new {@link BukkitChameleonBootstrap}.
     */
    public static @NotNull BukkitChameleonBootstrap create(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull JavaPlugin bukkitPlugin, @NotNull PluginData pluginData) {
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
    public @NotNull ChameleonAudienceProvider getAdventure() {
        if (this.audienceProvider == null) throw new IllegalStateException("Chameleon has not been enabled yet.");
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
        return this.plugin.getDataFolder().toPath().toAbsolutePath();
    }

    /**
     * Get stored {@link JavaPlugin}.
     *
     * @return stored {@link JavaPlugin}
     */
    public @NotNull JavaPlugin getPlatformPlugin() {
        return this.plugin;
    }

}
