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
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extension.ChameleonExtension;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.logger.DummyChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import dev.hypera.chameleon.user.UserManager;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import org.jetbrains.annotations.NotNull;

/**
 * Dummy Chameleon implementation.
 */
public final class TestChameleon extends Chameleon {

    private final @NotNull Platform platform = new TestChameleonPlatform();

    /**
     * Dummy Chameleon implementation constructor.
     *
     * @throws ChameleonInstantiationException if something goes wrong whilst starting.
     */
    public TestChameleon() throws ChameleonInstantiationException {
        this(new DummyChameleonLogger());
    }

    /**
     * Dummy Chameleon implementation constructor.
     *
     * @param logger Logger.
     *
     * @throws ChameleonInstantiationException if something goes wrong whilst starting.
     */
    public TestChameleon(@NotNull ChameleonLogger logger) throws ChameleonInstantiationException {
        this(logger, new EventBusImpl(logger), new HashSet<>());
    }

    /**
     * Dummy Chameleon implementation constructor.
     *
     * @param logger     Logger.
     * @param eventBus   Event bus.
     * @param extensions Extensions.
     *
     * @throws ChameleonInstantiationException if something goes wrong whilst starting.
     */
    public TestChameleon(@NotNull ChameleonLogger logger, @NotNull EventBus eventBus, @NotNull Collection<? super ChameleonExtension<?>> extensions) throws ChameleonInstantiationException {
        super(
            TestChameleonPlugin.class,
            ChameleonPluginData.create("Chameleon", Chameleon.getVersion()),
            eventBus,
            logger,
            extensions
        );
    }

    public static @NotNull TestChameleonBootstrap create() {
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
    public @NotNull Path getDataFolder() {
        throw new UnsupportedOperationException("unsupported");
    }

}
