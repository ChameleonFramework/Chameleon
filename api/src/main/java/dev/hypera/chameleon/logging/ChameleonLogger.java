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
package dev.hypera.chameleon.logging;

import dev.hypera.chameleon.Chameleon;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Chameleon} logger.
 */
@NonExtendable
public interface ChameleonLogger {

    /**
     * Log an informational message.
     *
     * @param message Message to be logged.
     * @param args    Message arguments.
     */
    void info(@NotNull String message, @NotNull Object... args);

    /**
     * Log a debug message.
     *
     * @param message Message to be logged.
     * @param args    Message arguments.
     */
    void debug(@NotNull String message, @NotNull Object... args);

    /**
     * Log a warning message.
     *
     * @param message Message to be logged.
     * @param args    Message arguments.
     */
    void warn(@NotNull String message, @NotNull Object... args);

    /**
     * Log a warning message with an exception.
     *
     * @param message   Message to be logged.
     * @param throwable Exception to be logged.
     * @param args      Message arguments.
     */
    void warn(@NotNull String message, @NotNull Throwable throwable, @NotNull Object... args);

    /**
     * Log an error message.
     *
     * @param message Message to be logged.
     * @param args    Message arguments.
     */
    void error(@NotNull String message, @NotNull Object... args);

    /**
     * Log an error message with an exception.
     *
     * @param message   Message to be logged.
     * @param throwable Exception to be logged.
     * @param args      Message arguments.
     */
    void error(@NotNull String message, @NotNull Throwable throwable, @NotNull Object... args);

    /**
     * Enable debug logs.
     *
     * @return {@code this}.
     */
    @NotNull ChameleonLogger enableDebug();

    /**
     * Disable debug logs.
     *
     * @return {@code this}.
     */
    @NotNull ChameleonLogger disableDebug();

}
