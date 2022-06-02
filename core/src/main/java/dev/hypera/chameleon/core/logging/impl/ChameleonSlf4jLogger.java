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
package dev.hypera.chameleon.core.logging.impl;

import dev.hypera.chameleon.core.logging.ChameleonLogger;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class ChameleonSlf4jLogger implements ChameleonLogger {

    private final @NotNull Logger logger;
    private boolean debug = false;

    public ChameleonSlf4jLogger(@NotNull Logger logger) {
        this.logger = logger;
    }


    @Override
    public void info(@NotNull String message, @NotNull Object... o) {
        logger.info(String.format(message, o));
    }

    @Override
    public void debug(@NotNull String message, @NotNull Object... o) {
        if (debug) {
            logger.debug(String.format(message, o));
        }
    }

    @Override
    public void warn(@NotNull String message, @NotNull Object... o) {
        logger.warn(String.format(message, o));
    }

    @Override
    public void warn(@NotNull String message, @NotNull Throwable throwable, @NotNull Object... o) {
        logger.warn(String.format(message, o), throwable);
    }

    @Override
    public void error(@NotNull String message, @NotNull Object... o) {
        logger.error(String.format(message, o));
    }

    @Override
    public void error(@NotNull String message, @NotNull Throwable throwable, @NotNull Object... o) {
        logger.error(String.format(message, o), throwable);
    }


    @Override
    public @NotNull ChameleonLogger enableDebug() {
        this.debug = true;
        return this;
    }

    @Override
    public @NotNull ChameleonLogger disableDebug() {
        this.debug = false;
        return this;
    }

}
