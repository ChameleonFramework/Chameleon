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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Chameleon logger.
 */
public interface ChameleonLogger {

    /**
     * Log a trace message.
     *
     * @param msg Message to be logged.
     */
    void trace(@NotNull String msg);

    /**
     * Log a trace message with the specified format and argument.
     *
     * <p>This method avoids unnecessary object creation when the underlying logger has trace logs
     * disabled.</p>
     *
     * @param format Message format.
     * @param arg    Format argument.
     */
    void trace(@NotNull String format, @Nullable Object arg);

    /**
     * Log a trace message with the specified format and arguments.
     *
     * <p>This method avoids unnecessary object creation when the underlying logger has trace logs
     * disabled.</p>
     *
     * @param format Message format.
     * @param arg1   First format argument.
     * @param arg2   Second format argument.
     */
    void trace(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2);

    /**
     * Log a trace message with the specified format and arguments.
     *
     * <p>This method avoids unnecessary string concatenation when the logger has trace logs
     * disabled. However, this method incurs the hidden cost of creating a {@code Object[]} before
     * invoking the method, even if the underlying logger has trace logs disabled.</p>
     *
     * @param format    Message format.
     * @param arguments Format arguments.
     */
    void trace(@NotNull String format, @Nullable Object @NotNull ... arguments);

    /**
     * Log a trace message with an exception.
     *
     * @param msg Message to be logged.
     * @param t   Throwable to be logged.
     */
    void trace(@NotNull String msg, @Nullable Throwable t);

    /**
     * Log a debug message.
     *
     * @param msg Message to be logged.
     */
    void debug(@NotNull String msg);

    /**
     * Log a debug message with the specified format and argument.
     *
     * <p>This method avoids unnecessary object creation when the underlying logger has debug logs
     * disabled.</p>
     *
     * @param format Message format.
     * @param arg    Format argument.
     */
    void debug(@NotNull String format, @Nullable Object arg);


    /**
     * Log a debug message with the specified format and arguments.
     *
     * <p>This method avoids unnecessary object creation when the underlying logger has debug logs
     * disabled.</p>
     *
     * @param format Message format.
     * @param arg1   First format argument.
     * @param arg2   Second format argument.
     */
    void debug(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2);

    /**
     * Log a debug message with the specified format and arguments.
     *
     * <p>This method avoids unnecessary string concatenation when the logger has debug logs
     * disabled. However, this method incurs the hidden cost of creating a {@code Object[]} before
     * invoking the method, even if the underlying logger has debug logs disabled.</p>
     *
     * @param format    Message format.
     * @param arguments Format arguments.
     */
    void debug(@NotNull String format, @Nullable Object @NotNull ... arguments);

    /**
     * Log a debug message with an exception.
     *
     * @param msg Message to be logged.
     * @param t   Throwable to be logged.
     */
    void debug(@NotNull String msg, @Nullable Throwable t);

    /**
     * Log an informational message.
     *
     * @param msg Message to be logged.
     */
    void info(@NotNull String msg);

    /**
     * Log an informational message with the specified format and argument.
     *
     * <p>This method avoids unnecessary object creation when the underlying logger has info logs
     * disabled.</p>
     *
     * @param format Message format.
     * @param arg    Format argument.
     */
    void info(@NotNull String format, @Nullable Object arg);

    /**
     * Log an informational message with the specified format and arguments.
     *
     * <p>This method avoids unnecessary object creation when the underlying logger has info logs
     * disabled.</p>
     *
     * @param format Message format.
     * @param arg1   First format argument.
     * @param arg2   Second format argument.
     */
    void info(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2);

    /**
     * Log an informational message with the specified format and arguments.
     *
     * <p>This method avoids unnecessary string concatenation when the logger has info logs
     * disabled. However, this method incurs the hidden cost of creating a {@code Object[]} before
     * invoking the method, even if the underlying logger has info logs disabled.</p>
     *
     * @param format    Message format.
     * @param arguments Format arguments.
     */
    void info(@NotNull String format, @Nullable Object @NotNull ... arguments);

    /**
     * Log an informational message with an exception.
     *
     * @param msg Message to be logged.
     * @param t   Throwable to be logged.
     */
    void info(@NotNull String msg, @Nullable Throwable t);

    /**
     * Log a warning message.
     *
     * @param msg Message to be logged.
     */
    void warn(@NotNull String msg);

    /**
     * Log a warning message with the specified format and argument.
     *
     * <p>This method avoids unnecessary object creation when the underlying logger has warn logs
     * disabled.</p>
     *
     * @param format Message format.
     * @param arg    Format argument.
     */
    void warn(@NotNull String format, @Nullable Object arg);

    /**
     * Log a warning message with the specified format and arguments.
     *
     * <p>This method avoids unnecessary object creation when the underlying logger has warn logs
     * disabled.</p>
     *
     * @param format Message format.
     * @param arg1   First format argument.
     * @param arg2   Second format argument.
     */
    void warn(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2);

    /**
     * Log a warning message with the specified format and arguments.
     *
     * <p>This method avoids unnecessary string concatenation when the logger has warn logs
     * disabled. However, this method incurs the hidden cost of creating a {@code Object[]} before
     * invoking the method, even if the underlying logger has warn logs disabled.</p>
     *
     * @param format    Message format.
     * @param arguments Format arguments.
     */
    void warn(@NotNull String format, @Nullable Object @NotNull ... arguments);

    /**
     * Log a warning message with an exception.
     *
     * @param msg Message to be logged.
     * @param t   Throwable to be logged.
     */
    void warn(@NotNull String msg, @Nullable Throwable t);

    /**
     * Log an error message.
     *
     * @param msg Message to be logged.
     */
    void error(@NotNull String msg);

    /**
     * Log an error message with the specified format and argument.
     *
     * <p>This method avoids unnecessary object creation when the underlying logger has error logs
     * disabled.</p>
     *
     * @param format Message format.
     * @param arg    Format argument.
     */
    void error(@NotNull String format, @Nullable Object arg);

    /**
     * Log an error message with the specified format and arguments.
     *
     * <p>This method avoids unnecessary object creation when the underlying logger has error logs
     * disabled.</p>
     *
     * @param format Message format.
     * @param arg1   First format argument.
     * @param arg2   Second format argument.
     */
    void error(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2);

    /**
     * Log an error message with the specified format and arguments.
     *
     * <p>This method avoids unnecessary string concatenation when the logger has error logs
     * disabled. However, this method incurs the hidden cost of creating a {@code Object[]} before
     * invoking the method, even if the underlying logger has error logs disabled.</p>
     *
     * @param format    Message format.
     * @param arguments Format arguments.
     */
    void error(@NotNull String format, @Nullable Object @NotNull ... arguments);

    /**
     * Log an error message with an exception.
     *
     * @param msg Message to be logged.
     * @param t   Throwable to be logged.
     */
    void error(@NotNull String msg, @Nullable Throwable t);

    /**
     * Returns whether trace logs are enabled.
     *
     * @return trace logs enabled.
     */
    boolean isTraceEnabled();

    /**
     * Returns whether debug logs are enabled.
     *
     * @return debug logs enabled.
     */
    boolean isDebugEnabled();

    /**
     * Returns whether informational logs are enabled.
     *
     * @return info logs enabled.
     */
    boolean isInfoEnabled();

    /**
     * Returns whether warning logs are enabled.
     *
     * @return warn logs enabled.
     */
    boolean isWarnEnabled();

    /**
     * Returns whether error logs are enabled.
     *
     * @return error logs enabled.
     */
    boolean isErrorEnabled();

}
