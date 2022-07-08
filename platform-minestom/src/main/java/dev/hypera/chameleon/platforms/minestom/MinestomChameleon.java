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
package dev.hypera.chameleon.platforms.minestom;

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
import dev.hypera.chameleon.platforms.minestom.adventure.MinestomAudienceProvider;
import dev.hypera.chameleon.platforms.minestom.events.MinestomListener;
import dev.hypera.chameleon.platforms.minestom.managers.MinestomCommandManager;
import dev.hypera.chameleon.platforms.minestom.managers.MinestomPluginManager;
import dev.hypera.chameleon.platforms.minestom.managers.MinestomScheduler;
import dev.hypera.chameleon.platforms.minestom.managers.MinestomUserManager;
import dev.hypera.chameleon.platforms.minestom.platform.MinestomPlatform;
import java.nio.file.Path;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom {@link Chameleon} implementation.
 */
public final class MinestomChameleon extends Chameleon {

    private final @NotNull Extension extension;
    private final @NotNull MinestomAudienceProvider audienceProvider = new MinestomAudienceProvider();
    private final @NotNull MinestomPlatform platform = new MinestomPlatform();
    private final @NotNull MinestomCommandManager commandManager = new MinestomCommandManager(this);
    private final @NotNull MinestomPluginManager pluginManager = new MinestomPluginManager();
    private final @NotNull MinestomUserManager userManager = new MinestomUserManager();
    private final @NotNull MinestomScheduler scheduler = new MinestomScheduler();

    @Internal
    MinestomChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull Extension extension, @NotNull PluginData pluginData) throws ChameleonInstantiationException {
        super(chameleonPlugin, pluginData, new ChameleonSlf4jLogger(extension.getLogger()));
        this.extension = extension;
        new MinestomListener(this);
    }

    /**
     * Create a new {@link MinestomChameleonBootstrap} instance.
     *
     * @param chameleonPlugin {@link ChameleonPlugin} to load.
     * @param extension       Minestom {@link Extension}.
     * @param pluginData      {@link PluginData}.
     *
     * @return new {@link MinestomChameleonBootstrap}.
     */
    public static @NotNull MinestomChameleonBootstrap create(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull Extension extension, @NotNull PluginData pluginData) {
        return new MinestomChameleonBootstrap(chameleonPlugin, extension, pluginData);
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
        return this.extension.getDataDirectory();
    }

}
