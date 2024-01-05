/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.platform.logger;

import dev.hypera.chameleon.logger.AbstractChameleonLogger;
import dev.hypera.chameleon.logger.ChameleonLogger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

/**
 * SLF4J Chameleon logger implementation.
 */
public final class ChameleonSlf4jLogger extends AbstractChameleonLogger {

    private final @NotNull Logger logger;

    /**
     * Chameleon SLF4J logger constructor.
     *
     * @param logger SLF4J logger to forward logs to.
     */
    public ChameleonSlf4jLogger(@NotNull Logger logger) {
        this.logger = logger;
    }

    /**
     * Returns a new Chameleon SLF4J logger that wraps the given SLF4J logger.
     *
     * @param slf4jLogger SLF4J logger object.
     *
     * @return new Chameleon logger.
     * @throws IllegalArgumentException if the provided {@code slf4jLogger} is not instanceof
     *                                  {@code org.slf4j.Logger}.
     */
    public static @NotNull ChameleonLogger create(@NotNull Object slf4jLogger) {
        if (slf4jLogger instanceof Logger) {
            return new ChameleonSlf4jLogger((Logger) slf4jLogger);
        }
        throw new IllegalArgumentException("slf4jLogger must be an SLF4J logger instance");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String msg) {
        this.logger.trace(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object arg) {
        this.logger.trace(format, arg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        this.logger.trace(format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        this.logger.trace(format, arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String msg, @Nullable Throwable t) {
        this.logger.trace(msg, t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String msg) {
        this.logger.debug(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object arg) {
        this.logger.debug(format, arg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        this.logger.debug(format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        this.logger.debug(format, arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String msg, @Nullable Throwable t) {
        this.logger.debug(msg, t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String msg) {
        this.logger.info(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object arg) {
        this.logger.info(format, arg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        this.logger.info(format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        this.logger.info(format, arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String msg, @Nullable Throwable t) {
        this.logger.info(msg, t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String msg) {
        this.logger.warn(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg) {
        this.logger.warn(format, arg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        this.logger.warn(format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        this.logger.warn(format, arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String msg, @Nullable Throwable t) {
        this.logger.warn(msg, t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String msg) {
        this.logger.error(msg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object arg) {
        this.logger.error(format, arg);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        this.logger.error(format, arg1, arg2);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        this.logger.error(format, arguments);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String msg, @Nullable Throwable t) {
        this.logger.error(msg, t);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWarnEnabled() {
        return this.logger.isWarnEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErrorEnabled() {
        return this.logger.isErrorEnabled();
    }

}
