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
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.logger.DummyChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import dev.hypera.chameleon.user.UserManager;
import java.nio.file.Path;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;

/**
 * Dummy Chameleon implementation.
 */
public final class DummyChameleon extends Chameleon {

    /**
     * Dummy Chameleon implementation constructor.
     *
     * @throws ChameleonInstantiationException if something goes wrong whilst starting.
     */
    public DummyChameleon() throws ChameleonInstantiationException {
        this(new DummyChameleonLogger());
    }

    /**
     * Dummy Chameleon implementation constructor.
     *
     * @param logger Logger.
     *
     * @throws ChameleonInstantiationException if something goes wrong whilst starting.
     */
    public DummyChameleon(@NotNull ChameleonLogger logger) throws ChameleonInstantiationException {
        super(
            DummyChameleonPlugin.class, Collections.emptySet(),
            ChameleonPluginData.create("Chameleon", Chameleon.getVersion()),
            logger
        );
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
        throw new UnsupportedOperationException("unsupported");
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
