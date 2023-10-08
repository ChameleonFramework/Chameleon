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
package dev.hypera.chameleon.logger;

import dev.hypera.chameleon.util.logger.FormattedMessage;
import dev.hypera.chameleon.util.logger.MessageFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Java Chameleon logger implementation.
 */
@Internal
public final class ChameleonJavaLogger implements ChameleonLogger {

    private static final @NotNull Level LEVEL_TRACE = Level.FINER;
    private static final @NotNull Level LEVEL_DEBUG = Level.FINE;
    private static final @NotNull Level LEVEL_INFO = Level.INFO;
    private static final @NotNull Level LEVEL_WARN = Level.WARNING;
    private static final @NotNull Level LEVEL_ERROR = Level.SEVERE;

    private final @NotNull Logger logger;

    /**
     * Chameleon Java logger constructor.
     *
     * @param logger Java logger instance to use.
     */
    public ChameleonJavaLogger(@NotNull Logger logger) {
        this.logger = logger;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String msg) {
        if (isTraceEnabled()) {
            this.logger.log(LEVEL_TRACE, msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object arg) {
        if (isTraceEnabled()) {
            logFormatted(LEVEL_TRACE, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isTraceEnabled()) {
            logFormatted(LEVEL_TRACE, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isTraceEnabled()) {
            logFormatted(LEVEL_TRACE, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String msg, @Nullable Throwable t) {
        if (isTraceEnabled()) {
            this.logger.log(LEVEL_TRACE, msg, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String msg) {
        if (isDebugEnabled()) {
            this.logger.log(LEVEL_DEBUG, msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object arg) {
        if (isDebugEnabled()) {
            logFormatted(LEVEL_DEBUG, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isDebugEnabled()) {
            logFormatted(LEVEL_DEBUG, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isDebugEnabled()) {
            logFormatted(LEVEL_DEBUG, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String msg, @Nullable Throwable t) {
        if (isDebugEnabled()) {
            this.logger.log(LEVEL_DEBUG, msg, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String msg) {
        if (isInfoEnabled()) {
            this.logger.log(LEVEL_INFO, msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object arg) {
        if (isInfoEnabled()) {
            logFormatted(LEVEL_INFO, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isInfoEnabled()) {
            logFormatted(LEVEL_INFO, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isInfoEnabled()) {
            logFormatted(LEVEL_INFO, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String msg, @Nullable Throwable t) {
        if (isInfoEnabled()) {
            this.logger.log(LEVEL_INFO, msg, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String msg) {
        if (isWarnEnabled()) {
            this.logger.log(LEVEL_WARN, msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg) {
        if (isWarnEnabled()) {
            logFormatted(LEVEL_WARN, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isWarnEnabled()) {
            logFormatted(LEVEL_WARN, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isWarnEnabled()) {
            logFormatted(LEVEL_WARN, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String msg, @Nullable Throwable t) {
        if (isWarnEnabled()) {
            this.logger.log(LEVEL_WARN, msg, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String msg) {
        if (isErrorEnabled()) {
            this.logger.log(LEVEL_ERROR, msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object arg) {
        if (isErrorEnabled()) {
            logFormatted(LEVEL_ERROR, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isErrorEnabled()) {
            logFormatted(LEVEL_ERROR, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isErrorEnabled()) {
            logFormatted(LEVEL_ERROR, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String msg, @Nullable Throwable t) {
        if (isErrorEnabled()) {
            this.logger.log(LEVEL_ERROR, msg, t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTraceEnabled() {
        return this.logger.isLoggable(LEVEL_TRACE);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled() {
        return this.logger.isLoggable(LEVEL_DEBUG);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled() {
        return this.logger.isLoggable(LEVEL_INFO);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWarnEnabled() {
        return this.logger.isLoggable(LEVEL_WARN);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErrorEnabled() {
        return this.logger.isLoggable(LEVEL_ERROR);
    }

    private void logFormatted(@NotNull Level level, @NotNull String format, @Nullable Object @NotNull ... args) {
        FormattedMessage m = MessageFormatter.format(format, args);
        if (m.throwable() == null) {
            this.logger.log(level, m.message());
            return;
        }
        this.logger.log(level, m.message(), m.throwable());
    }

}
