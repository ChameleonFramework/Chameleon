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
package dev.hypera.chameleon.platform.minestom;

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
import dev.hypera.chameleon.platform.minestom.adventure.MinestomAudienceProvider;
import dev.hypera.chameleon.platform.minestom.command.MinestomCommandManager;
import dev.hypera.chameleon.platform.minestom.event.MinestomListener;
import dev.hypera.chameleon.platform.minestom.platform.MinestomPlatform;
import dev.hypera.chameleon.platform.minestom.platform.MinestomPluginManager;
import dev.hypera.chameleon.platform.minestom.scheduler.MinestomScheduler;
import dev.hypera.chameleon.platform.minestom.user.MinestomUserManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import dev.hypera.chameleon.user.UserManager;
import java.nio.file.Path;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom Chameleon implementation.
 */
public final class MinestomChameleon extends PlatformChameleon<Extension> {

    private final @NotNull AdventureMapper adventureMapper = new AdventureMapper(this);
    private final @NotNull MinestomUserManager userManager = new MinestomUserManager(this);
    private final @NotNull MinestomAudienceProvider audienceProvider = new MinestomAudienceProvider(this);
    private final @NotNull MinestomPlatform platform = new MinestomPlatform();
    private final @NotNull MinestomCommandManager commandManager = new MinestomCommandManager(this);
    private final @NotNull MinestomPluginManager pluginManager = new MinestomPluginManager();
    private final @NotNull MinestomScheduler scheduler = new MinestomScheduler();

    @Internal
    MinestomChameleon(
        @NotNull ChameleonPluginBootstrap pluginBootstrap,
        @NotNull Extension extension,
        @NotNull EventBus eventBus,
        @NotNull ChameleonLogger logger,
        @NotNull ExtensionMap extensions
    ) {
        super(pluginBootstrap, extension, eventBus, logger, extensions);
        new MinestomListener(this);
    }

    /**
     * Returns a new Minestom Chameleon bootstrap instance.
     *
     * @param pluginBootstrap Chameleon plugin bootstrap.
     * @param extension       Minestom Extension instance.
     *
     * @return new Minestom Chameleon bootstrap.
     */
    public static @NotNull MinestomChameleonBootstrap create(@NotNull ChameleonPluginBootstrap pluginBootstrap, @NotNull Extension extension) {
        return new MinestomChameleonBootstrap(pluginBootstrap, extension);
    }

    /**
     * {@inheritDoc}
     */
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
    public @NotNull Path getDataDirectory() {
        return getPlatformPlugin().getDataDirectory();
    }

}
