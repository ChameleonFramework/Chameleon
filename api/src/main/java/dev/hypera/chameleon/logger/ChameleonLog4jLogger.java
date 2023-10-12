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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Log4J Chameleon logger implementation.
 */
@Internal
public final class ChameleonLog4jLogger extends AbstractChameleonLogger {

    private final @NotNull Logger logger;

    /**
     * Chameleon Log4J logger constructor.
     *
     * @param logger Log4J logger instance to use.
     */
    public ChameleonLog4jLogger(@NotNull Logger logger) {
        this.logger = logger;
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
        if (isTraceEnabled()) {
            logFormatted(Level.TRACE, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isTraceEnabled()) {
            logFormatted(Level.TRACE, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isTraceEnabled()) {
            logFormatted(Level.TRACE, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String msg, @Nullable Throwable t) {
        if (isTraceEnabled()) {
            this.logger.trace(msg, t);
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
            logFormatted(Level.DEBUG, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isDebugEnabled()) {
            logFormatted(Level.DEBUG, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isDebugEnabled()) {
            logFormatted(Level.DEBUG, format, arguments);
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
            logFormatted(Level.INFO, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isInfoEnabled()) {
            logFormatted(Level.INFO, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isInfoEnabled()) {
            logFormatted(Level.INFO, format, arguments);
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
            this.logger.warn(msg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg) {
        if (isWarnEnabled()) {
            logFormatted(Level.WARN, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isWarnEnabled()) {
            logFormatted(Level.WARN, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isWarnEnabled()) {
            logFormatted(Level.WARN, format, arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String msg, @Nullable Throwable t) {
        if (isWarnEnabled()) {
            this.logger.warn(msg, t);
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
            logFormatted(Level.ERROR, format, arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isErrorEnabled()) {
            logFormatted(Level.ERROR, format, arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isErrorEnabled()) {
            logFormatted(Level.ERROR, format, arguments);
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

    private void logFormatted(@NotNull Level level, @NotNull String format, @Nullable Object @NotNull ... args) {
        FormattedMessage m = MessageFormatter.format(format, args);
        if (m.throwable() == null) {
            this.logger.atLevel(level).log(m.message());
            return;
        }
        this.logger.atLevel(level).withThrowable(m.throwable()).log(m.message());
    }

}
