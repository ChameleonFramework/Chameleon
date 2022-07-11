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
package dev.hypera.chameleon.platforms.mock;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.annotations.PlatformSpecific;
import dev.hypera.chameleon.data.PluginData;
import dev.hypera.chameleon.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platforms.mock.adventure.MockAudienceProvider;
import dev.hypera.chameleon.platforms.mock.logging.ChameleonMockLogger;
import dev.hypera.chameleon.platforms.mock.managers.MockCommandManager;
import dev.hypera.chameleon.platforms.mock.managers.MockPluginManager;
import dev.hypera.chameleon.platforms.mock.managers.MockScheduler;
import dev.hypera.chameleon.platforms.mock.managers.MockUserManager;
import dev.hypera.chameleon.platforms.mock.platform.MockProxyPlatform;
import dev.hypera.chameleon.platforms.mock.platform.MockServerPlatform;
import dev.hypera.chameleon.platforms.mock.platform.objects.MockSelfPlugin;
import java.nio.file.Path;
import java.text.Normalizer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Mock {@link Chameleon} implementation.
 */
public final class MockChameleon extends Chameleon {

    private final @NotNull Path pluginsDirectory;
    private final @NotNull Platform platform;
    private final @NotNull MockAudienceProvider audienceProvider;
    private final @NotNull MockCommandManager commandManager;
    private final @NotNull MockPluginManager pluginManager;
    private final @NotNull MockUserManager userManager;
    private final @NotNull MockScheduler scheduler;

    @Internal
    MockChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull ChameleonMockLogger logger, @NotNull Path pluginsDirectory, @NotNull Platform.Type platformType, @NotNull PluginData pluginData) throws ChameleonInstantiationException {
        super(chameleonPlugin, pluginData, logger);
        this.pluginsDirectory = pluginsDirectory;

        if (platformType.equals(Platform.Type.PROXY)) {
            this.platform = new MockProxyPlatform(this);
        } else {
            this.platform = new MockServerPlatform();
        }

        this.userManager = new MockUserManager(this);
        this.audienceProvider = new MockAudienceProvider(this.userManager);
        this.commandManager = new MockCommandManager(this);
        this.pluginManager = new MockPluginManager(pluginsDirectory);
        this.scheduler = new MockScheduler();

        this.pluginManager.addPlugin(new MockSelfPlugin(getPlugin(), this));
    }

    /**
     * Create a new {@link MockChameleonBootstrap} instance.
     *
     * @param chameleonPlugin  {@link ChameleonPlugin} to load.
     * @param pluginsDirectory {@link Path} used as the '/plugins/' directory on a normal platform.
     * @param pluginData       {@link PluginData}.
     *
     * @return new {@link MockChameleonBootstrap}.
     */
    public static @NotNull MockChameleonBootstrap createProxy(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull Path pluginsDirectory, @NotNull PluginData pluginData) {
        return new MockChameleonBootstrap(chameleonPlugin, pluginData, pluginsDirectory, Platform.Type.PROXY);
    }

    /**
     * Create a new {@link MockChameleonBootstrap} instance.
     *
     * @param chameleonPlugin  {@link ChameleonPlugin} to load.
     * @param pluginsDirectory {@link Path} used as the '/plugins/' directory on a normal platform.
     * @param pluginData       {@link PluginData}.
     *
     * @return new {@link MockChameleonBootstrap}.
     */
    public static @NotNull MockChameleonBootstrap createServer(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull Path pluginsDirectory, @NotNull PluginData pluginData) {
        return new MockChameleonBootstrap(chameleonPlugin, pluginData, pluginsDirectory, Platform.Type.SERVER);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChameleonMockLogger getLogger() {
        return (ChameleonMockLogger) super.getLogger();
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
     * Get {@link Platform} instance as a {@link MockProxyPlatform} instance.
     *
     * @return {@link MockProxyPlatform}.
     * @throws IllegalStateException if this {@link Platform} is not an {@link MockProxyPlatform}.
     */
    @PlatformSpecific(Platform.Type.PROXY)
    public @NotNull MockProxyPlatform getProxyPlatform() {
        return (MockProxyPlatform) getPlatform().proxy();
    }

    /**
     * Get {@link Platform} instance as a {@link MockServerPlatform} instance.
     *
     * @return {@link MockServerPlatform}.
     * @throws IllegalStateException if this {@link Platform} is not an {@link MockServerPlatform}.
     */
    @PlatformSpecific(Platform.Type.SERVER)
    public @NotNull MockServerPlatform getServerPlatform() {
        return (MockServerPlatform) getPlatform().server();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MockCommandManager getCommandManager() {
        return this.commandManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MockPluginManager getPluginManager() {
        return this.pluginManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MockUserManager getUserManager() {
        return this.userManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MockScheduler getScheduler() {
        return this.scheduler;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Path getDataFolder() {
        return this.pluginsDirectory.resolve(Normalizer.normalize(getData().getName().trim(), Normalizer.Form.NFD).replaceAll("[^\\x00-\\x7F]", ""));
    }

}
