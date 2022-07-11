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
package dev.hypera.chameleon.platforms.mock.logging;

import com.google.errorprone.annotations.FormatMethod;
import com.google.errorprone.annotations.FormatString;
import dev.hypera.chameleon.logging.ChameleonLogger;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedTransferQueue;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;

/**
 * Mock {@link ChameleonLogger} implementation.
 */
public final class ChameleonMockLogger implements ChameleonLogger {

    private final @NotNull Queue<LogEntry> logEntries = new LinkedTransferQueue<>();
    private boolean debug = false;

    /**
     * Get the next log entry.
     *
     * @return the next log entry.
     */
    public @Nullable LogEntry nextLog() {
        return this.logEntries.poll();
    }

    /**
     * Peek at the next log entry.
     *
     * @return the next log entry.
     */
    public @Nullable LogEntry peekNextLog() {
        return this.logEntries.peek();
    }

    /**
     * Get the amount of stored logs.
     *
     * @return the amount of stored logs.
     */
    public int count() {
        return this.logEntries.size();
    }

    /**
     * Clear the stored logs.
     */
    public void clear() {
        this.logEntries.clear();
    }

    /**
     * Get the amount of stored logs and then clear the stored logs.
     *
     * @return the amount of stored logs.
     */
    public int countAndClear() {
        try {
            return count();
        } finally {
            clear();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FormatMethod
    public void info(@NotNull @FormatString String message, @NotNull Object... args) {
        this.logEntries.add(new LogEntry(Level.INFO, String.format(message, args), null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FormatMethod
    public void debug(@NotNull @FormatString String message, @NotNull Object... args) {
        if (this.debug) {
            this.logEntries.add(new LogEntry(Level.DEBUG, String.format(message, args), null));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FormatMethod
    public void warn(@NotNull @FormatString String message, @NotNull Object... args) {
        this.logEntries.add(new LogEntry(Level.WARN, String.format(message, args), null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String message, @NotNull Throwable throwable, @NotNull Object... args) {
        this.logEntries.add(new LogEntry(Level.WARN, String.format(message, args), throwable));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FormatMethod
    public void error(@NotNull @FormatString String message, @NotNull Object... args) {
        this.logEntries.add(new LogEntry(Level.ERROR, String.format(message, args), null));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String message, @NotNull Throwable throwable, @NotNull Object... args) {
        this.logEntries.add(new LogEntry(Level.ERROR, String.format(message, args), throwable));
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

    /**
     * Log entry.
     */
    public static final class LogEntry {

        private final @NotNull Level level;
        private final @NotNull String message;
        private final @Nullable Throwable throwable;

        private LogEntry(@NotNull Level level, @NotNull String message, @Nullable Throwable throwable) {
            this.level = level;
            this.message = message;
            this.throwable = throwable;
        }

        /**
         * Assert that the log level is equal to {@code level}.
         *
         * @param level {@link Level}.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        public @NotNull LogEntry assertLevelEquals(@NotNull Level level) {
            Assertions.assertEquals(this.level, level);
            return this;
        }

        /**
         * Assert that the log message is equal to {@code message}.
         *
         * @param message Formatted message string.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        public @NotNull LogEntry assertMessageEquals(@NotNull String message) {
            Assertions.assertEquals(this.message, message);
            return this;
        }

        /**
         * Assert that the log throwable is equal to {@code throwable}.
         *
         * @param throwable {@link Throwable}.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        public @NotNull LogEntry assertThrowableEquals(@NotNull Throwable throwable) {
            Assertions.assertEquals(this.throwable, throwable);
            return this;
        }

        /**
         * Get log {@link Level}.
         *
         * @return log {@link Level}.
         */
        public @NotNull Level getLevel() {
            return this.level;
        }

        /**
         * Get log message.
         *
         * @return message.
         */
        public @NotNull String getMessage() {
            return this.message;
        }

        /**
         * Get log throwable.
         *
         * @return log throwable, optionally.
         */
        public @NotNull Optional<Throwable> getThrowable() {
            return Optional.ofNullable(this.throwable);
        }

    }

    /**
     * Log level.
     */
    public enum Level {
        INFO, DEBUG, WARN, ERROR
    }

}
