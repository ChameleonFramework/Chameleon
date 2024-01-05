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
package dev.hypera.chameleon.logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * No-op Chameleon logger implementation.
 */
public final class ChameleonNoopLogger extends AbstractChameleonLogger {

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String msg) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object arg) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trace(@NotNull String msg, @Nullable Throwable t) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String msg) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object arg) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void debug(@NotNull String msg, @Nullable Throwable t) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String msg) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object arg) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void info(@NotNull String msg, @Nullable Throwable t) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String msg) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void warn(@NotNull String msg, @Nullable Throwable t) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String msg) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object arg) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String format, @Nullable Object @NotNull ... arguments) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void error(@NotNull String msg, @Nullable Throwable t) {
        // No-op
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isErrorEnabled() {
        return false;
    }

}
