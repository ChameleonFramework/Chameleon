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
package dev.hypera.chameleon.platform.bungeecord;

import dev.hypera.chameleon.ChameleonBootstrap;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.command.CommandManager;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extension.ExtensionMap;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PlatformChameleon;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.platform.bungeecord.adventure.BungeeCordAudienceProvider;
import dev.hypera.chameleon.platform.bungeecord.command.BungeeCordCommandManager;
import dev.hypera.chameleon.platform.bungeecord.event.BungeeCordListener;
import dev.hypera.chameleon.platform.bungeecord.platform.BungeeCordPlatform;
import dev.hypera.chameleon.platform.bungeecord.platform.BungeeCordPluginManager;
import dev.hypera.chameleon.platform.bungeecord.scheduler.BungeeCordScheduler;
import dev.hypera.chameleon.platform.bungeecord.user.BungeeCordUserManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import java.nio.file.Path;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord Chameleon implementation.
 */
public final class BungeeCordChameleon extends PlatformChameleon<Plugin> {

    private final @NotNull ChameleonAudienceProvider audienceProvider;
    private final @NotNull BungeeCordPlatform platform = new BungeeCordPlatform(this);
    private final @NotNull BungeeCordCommandManager commandManager = new BungeeCordCommandManager(this);
    private final @NotNull BungeeCordPluginManager pluginManager = new BungeeCordPluginManager();
    private final @NotNull BungeeCordUserManager userManager = new BungeeCordUserManager(this);
    private final @NotNull BungeeCordScheduler scheduler = new BungeeCordScheduler(this);

    @Internal
    BungeeCordChameleon(
        @NotNull Class<? extends ChameleonPlugin> chameleonPlugin,
        @NotNull Plugin bungeePlugin,
        @NotNull EventBus eventBus,
        @NotNull ChameleonLogger logger,
        @NotNull ExtensionMap extensions
    ) throws ChameleonInstantiationException {
        super(chameleonPlugin, bungeePlugin, eventBus, logger, extensions);
        this.audienceProvider = new BungeeCordAudienceProvider(this, bungeePlugin);
        ProxyServer.getInstance().getPluginManager().registerListener(bungeePlugin, new BungeeCordListener(this));
    }

    /**
     * Create a new BungeeCord Chameleon bootstrap instance.
     *
     * @param chameleonPlugin Chameleon plugin to be loaded.
     * @param bungeePlugin    BungeeCord plugin instance.
     *
     * @return new BungeeCord Chameleon boostrap.
     */
    public static @NotNull ChameleonBootstrap<BungeeCordChameleon> create(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull Plugin bungeePlugin) {
        return new BungeeCordChameleonBootstrap(chameleonPlugin, bungeePlugin);
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
    public @NotNull BungeeCordUserManager getUserManager() {
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

}
