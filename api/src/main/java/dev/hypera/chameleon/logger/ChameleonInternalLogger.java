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

import dev.hypera.chameleon.util.internal.ChameleonProperty;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Internal Chameleon logger wrapper.
 *
 * <p>Warning: This is designed for internal use within Chameleon. This API is NOT designed for
 * end-users, and use is not recommended or supported.</p>
 */
@Internal
public final class ChameleonInternalLogger extends AbstractChameleonLogger {

    static final @NotNull String CHAMELEON_PREFIX = "[Chameleon] ";
    private final @NotNull ChameleonLogger logger;

    private ChameleonInternalLogger(@NotNull ChameleonLogger logger) {
        this.logger = logger;
    }

    /**
     * Returns a new internal Chameleon logger that logs to the given {@code logger}.
     *
     * <p>Warning: This is designed for internal use within Chameleon. This API is not designed for
     * end-users, and use is not recommended or supported.</p>
     *
     * @param logger Logger to be wrapped.
     *
     * @return new internal logger.
     */
    @Internal
    public static @NotNull ChameleonLogger create(@NotNull ChameleonLogger logger) {
        return new ChameleonInternalLogger(logger);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String msg) {
        if (isTraceEnabled()) {
            this.logger.trace(CHAMELEON_PREFIX.concat(msg));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object arg) {
        if (isTraceEnabled()) {
            this.logger.trace(CHAMELEON_PREFIX.concat(format), arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isTraceEnabled()) {
            this.logger.trace(CHAMELEON_PREFIX.concat(format), arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isTraceEnabled()) {
            this.logger.trace(CHAMELEON_PREFIX.concat(format), arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String msg, @Nullable Throwable t) {
        if (isTraceEnabled()) {
            this.logger.trace(CHAMELEON_PREFIX.concat(msg), t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String msg) {
        if (isDebugEnabled()) {
            this.logger.debug(CHAMELEON_PREFIX.concat(msg));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object arg) {
        if (isDebugEnabled()) {
            this.logger.debug(CHAMELEON_PREFIX.concat(format), arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isDebugEnabled()) {
            this.logger.debug(CHAMELEON_PREFIX.concat(format), arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isDebugEnabled()) {
            this.logger.debug(CHAMELEON_PREFIX.concat(format), arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String msg, @Nullable Throwable t) {
        if (isDebugEnabled()) {
            this.logger.debug(CHAMELEON_PREFIX.concat(msg), t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String msg) {
        if (isInfoEnabled()) {
            this.logger.info(CHAMELEON_PREFIX.concat(msg));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object arg) {
        if (isInfoEnabled()) {
            this.logger.info(CHAMELEON_PREFIX.concat(format), arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isInfoEnabled()) {
            this.logger.info(CHAMELEON_PREFIX.concat(format), arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isInfoEnabled()) {
            this.logger.info(CHAMELEON_PREFIX.concat(format), arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String msg, @Nullable Throwable t) {
        if (isInfoEnabled()) {
            this.logger.info(CHAMELEON_PREFIX.concat(msg), t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String msg) {
        if (isWarnEnabled()) {
            this.logger.warn(CHAMELEON_PREFIX.concat(msg));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg) {
        if (isWarnEnabled()) {
            this.logger.warn(CHAMELEON_PREFIX.concat(format), arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isWarnEnabled()) {
            this.logger.warn(CHAMELEON_PREFIX.concat(format), arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isWarnEnabled()) {
            this.logger.warn(CHAMELEON_PREFIX.concat(format), arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String msg, @Nullable Throwable t) {
        if (isWarnEnabled()) {
            this.logger.warn(CHAMELEON_PREFIX.concat(msg), t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String msg) {
        if (isErrorEnabled()) {
            this.logger.error(CHAMELEON_PREFIX.concat(msg));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object arg) {
        if (isErrorEnabled()) {
            this.logger.error(CHAMELEON_PREFIX.concat(format), arg);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        if (isErrorEnabled()) {
            this.logger.error(CHAMELEON_PREFIX.concat(format), arg1, arg2);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        if (isErrorEnabled()) {
            this.logger.error(CHAMELEON_PREFIX.concat(format), arguments);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String msg, @Nullable Throwable t) {
        if (isErrorEnabled()) {
            this.logger.error(CHAMELEON_PREFIX.concat(msg), t);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTraceEnabled() {
        return ChameleonProperty.DEBUG.get() && this.logger.isTraceEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled() {
        return ChameleonProperty.DEBUG.get() && this.logger.isDebugEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled() {
        return ChameleonProperty.DEBUG.get() && this.logger.isInfoEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWarnEnabled() {
        return ChameleonProperty.DEBUG.get() && this.logger.isWarnEnabled();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErrorEnabled() {
        return ChameleonProperty.LOG_ERRORS.get() && this.logger.isErrorEnabled();
    }

}
