/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.logging.impl;

import dev.hypera.chameleon.logging.ChameleonLogger;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

/**
 * Log4J {@link ChameleonLogger} implementation.
 */
public class ChameleonLog4jLogger implements ChameleonLogger {

    private final @NotNull Logger logger;
    private boolean debug = false;

    /**
     * {@link ChameleonLog4jLogger} constructor.
     *
     * @param logger {@link Logger} instance to use.
     */
    public ChameleonLog4jLogger(@NotNull Logger logger) {
        this.logger = logger;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String message, @NotNull Object... o) {
        this.logger.info(String.format(message, o));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String message, @NotNull Object... o) {
        if (this.debug) {
            this.logger.debug(String.format(message, o));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String message, @NotNull Object... o) {
        this.logger.warn(String.format(message, o));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String message, @NotNull Throwable throwable, @NotNull Object... o) {
        this.logger.warn(String.format(message, o), throwable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String message, @NotNull Object... o) {
        this.logger.error(String.format(message, o));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String message, @NotNull Throwable throwable, @NotNull Object... o) {
        this.logger.error(String.format(message, o), throwable);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChameleonLogger enableDebug() {
        this.debug = true;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChameleonLogger disableDebug() {
        this.debug = false;
        return this;
    }

}
