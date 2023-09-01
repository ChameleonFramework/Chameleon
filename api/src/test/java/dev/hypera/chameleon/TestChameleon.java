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
package dev.hypera.chameleon;

import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.command.CommandManager;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.event.EventBusImpl;
import dev.hypera.chameleon.extension.ExtensionMap;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.logger.DummyChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PlatformChameleon;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import dev.hypera.chameleon.user.UserManager;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

/**
 * Dummy Chameleon implementation.
 */
public final class TestChameleon extends PlatformChameleon<Object> {

    public static final @NotNull String PLATFORM_ID = "Test";
    private final @NotNull Platform platform = new TestChameleonPlatform();

    /**
     * Dummy Chameleon implementation constructor.
     */
    public TestChameleon() {
        this(new DummyChameleonLogger());
    }

    /**
     * Dummy Chameleon implementation constructor.
     *
     * @param platformPlugin Platform plugin instance.
     */
    public TestChameleon(@NotNull Object platformPlugin) {
        this(new DummyChameleonLogger(), platformPlugin);
    }

    /**
     * Dummy Chameleon implementation constructor.
     *
     * @param logger Logger.
     * @param platformPlugin Platform plugin instance.
     */
    public TestChameleon(@NotNull ChameleonLogger logger, @NotNull Object platformPlugin) {
        this(TestChameleonPlugin::new, logger, platformPlugin, new EventBusImpl(logger), new ExtensionMap());
    }

    /**
     * Dummy Chameleon implementation constructor.
     *
     * @param logger Logger.
     */
    public TestChameleon(@NotNull ChameleonLogger logger) {
        this(TestChameleonPlugin::new, logger, 0, new EventBusImpl(logger), new ExtensionMap());
    }

    /**
     * Dummy Chameleon implementation constructor.
     *
     * @param logger         Logger.
     * @param platformPlugin Platform plugin.
     * @param eventBus       Event bus.
     * @param extensions     Extensions.
     */
    public TestChameleon(
        @NotNull ChameleonPluginBootstrap pluginBootstrap,
        @NotNull ChameleonLogger logger,
        @NotNull Object platformPlugin,
        @NotNull EventBus eventBus,
        @NotNull ExtensionMap extensions
    ) {
        super(pluginBootstrap, platformPlugin, eventBus, logger, extensions);
    }

    public static @NotNull ChameleonBootstrap<TestChameleon> create() {
        return new TestChameleonBootstrap();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChameleonAudienceProvider getAdventure() {
        throw new UnsupportedOperationException("unsupported");
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
        throw new UnsupportedOperationException("unsupported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull PluginManager getPluginManager() {
        throw new UnsupportedOperationException("unsupported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull UserManager getUserManager() {
        throw new UnsupportedOperationException("unsupported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Scheduler getScheduler() {
        throw new UnsupportedOperationException("unsupported");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Path getDataDirectory() {
        throw new UnsupportedOperationException("unsupported");
    }

}
