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
package dev.hypera.chameleon.platform.nukkit.logger;

import cn.nukkit.Nukkit;
import cn.nukkit.utils.LogLevel;
import cn.nukkit.utils.Logger;
import dev.hypera.chameleon.logger.AbstractChameleonLogger;
import dev.hypera.chameleon.util.logger.FormattedMessage;
import dev.hypera.chameleon.util.logger.MessageFormatter;
import org.apache.logging.log4j.Level;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Nukkit Chameleon logger implementation.
 */
@Internal
public final class ChameleonNukkitLogger extends AbstractChameleonLogger {

    private final @NotNull Logger logger;

    /**
     * Chameleon Nukkit logger constructor.
     *
     * @param logger Nukkit logger instance to use.
     */
    public ChameleonNukkitLogger(@NotNull Logger logger) {
        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String msg) {
        if (isTraceEnabled()) {
            this.logger.debug(msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object arg) {
        if (isTraceEnabled()) {
            logFormatted(LogLevel.DEBUG, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isTraceEnabled()) {
            logFormatted(LogLevel.DEBUG, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isTraceEnabled()) {
            logFormatted(LogLevel.DEBUG, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String msg, @Nullable Throwable t) {
        if (isTraceEnabled()) {
            this.logger.debug(msg, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String msg) {
        if (isDebugEnabled()) {
            this.logger.debug(msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object arg) {
        if (isDebugEnabled()) {
            logFormatted(LogLevel.DEBUG, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isDebugEnabled()) {
            logFormatted(LogLevel.DEBUG, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isDebugEnabled()) {
            logFormatted(LogLevel.DEBUG, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String msg, @Nullable Throwable t) {
        if (isDebugEnabled()) {
            this.logger.debug(msg, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String msg) {
        if (isInfoEnabled()) {
            this.logger.info(msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object arg) {
        if (isInfoEnabled()) {
            logFormatted(LogLevel.INFO, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isInfoEnabled()) {
            logFormatted(LogLevel.INFO, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isInfoEnabled()) {
            logFormatted(LogLevel.INFO, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String msg, @Nullable Throwable t) {
        if (isInfoEnabled()) {
            this.logger.info(msg, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String msg) {
        if (isWarnEnabled()) {
            this.logger.warning(msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg) {
        if (isWarnEnabled()) {
            logFormatted(LogLevel.WARNING, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isWarnEnabled()) {
            logFormatted(LogLevel.WARNING, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isWarnEnabled()) {
            logFormatted(LogLevel.WARNING, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String msg, @Nullable Throwable t) {
        if (isWarnEnabled()) {
            this.logger.warning(msg, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String msg) {
        if (isErrorEnabled()) {
            this.logger.error(msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object arg) {
        if (isErrorEnabled()) {
            logFormatted(LogLevel.ERROR, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isErrorEnabled()) {
            logFormatted(LogLevel.ERROR, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isErrorEnabled()) {
            logFormatted(LogLevel.ERROR, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String msg, @Nullable Throwable t) {
        if (isErrorEnabled()) {
            this.logger.error(msg, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTraceEnabled() {
        return Nukkit.getLogLevel().isLessSpecificThan(Level.TRACE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled() {
        return Nukkit.getLogLevel().isLessSpecificThan(Level.DEBUG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled() {
        return Nukkit.getLogLevel().isLessSpecificThan(Level.INFO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWarnEnabled() {
        return Nukkit.getLogLevel().isLessSpecificThan(Level.WARN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErrorEnabled() {
        return Nukkit.getLogLevel().isLessSpecificThan(Level.ERROR);
    }

    private void logFormatted(@NotNull LogLevel level, @NotNull String format, @Nullable Object @NotNull ... args) {
        FormattedMessage m = MessageFormatter.format(format, args);
        if (m.throwable() == null) {
            this.logger.log(level, m.message());
            return;
        }
        this.logger.log(level, m.message(), m.throwable());
    }

}
