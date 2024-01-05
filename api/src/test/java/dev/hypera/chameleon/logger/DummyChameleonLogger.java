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

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Dummy Chameleon logger implementation.
 */
public final class DummyChameleonLogger extends AbstractChameleonLogger {

    private final @NotNull List<Throwable> exceptions = new ArrayList<>();

    @Override
    public void trace(@NotNull String msg) {

    }

    @Override
    public void trace(@NotNull String format, @Nullable Object arg) {

    }

    @Override
    public void trace(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {

    }

    @Override
    public void trace(@NotNull String format, @Nullable Object @NotNull ... arguments) {

    }

    @Override
    public void trace(@NotNull String msg, @Nullable Throwable t) {
        this.exceptions.add(t);
    }

    @Override
    public void debug(@NotNull String msg) {

    }

    @Override
    public void debug(@NotNull String format, @Nullable Object arg) {

    }

    @Override
    public void debug(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {

    }

    @Override
    public void debug(@NotNull String format, @Nullable Object @NotNull ... arguments) {

    }

    @Override
    public void debug(@NotNull String msg, @Nullable Throwable t) {
        this.exceptions.add(t);
    }

    @Override
    public void info(@NotNull String msg) {

    }

    @Override
    public void info(@NotNull String format, @Nullable Object arg) {

    }

    @Override
    public void info(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {

    }

    @Override
    public void info(@NotNull String format, @Nullable Object @NotNull ... arguments) {

    }

    @Override
    public void info(@NotNull String msg, @Nullable Throwable t) {
        this.exceptions.add(t);
    }

    @Override
    public void warn(@NotNull String msg) {

    }

    @Override
    public void warn(@NotNull String format, @Nullable Object arg) {

    }

    @Override
    public void warn(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {

    }

    @Override
    public void warn(@NotNull String format, @Nullable Object @NotNull ... arguments) {

    }

    @Override
    public void warn(@NotNull String msg, @Nullable Throwable t) {
        this.exceptions.add(t);
    }

    @Override
    public void error(@NotNull String msg) {

    }

    @Override
    public void error(@NotNull String format, @Nullable Object arg) {

    }

    @Override
    public void error(@NotNull String format, @Nullable Object arg1, @Nullable Object arg2) {

    }

    @Override
    public void error(@NotNull String format, @Nullable Object @NotNull ... arguments) {

    }

    @Override
    public void error(@NotNull String msg, @Nullable Throwable t) {
        this.exceptions.add(t);
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
    }

    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    @Override
    public boolean isWarnEnabled() {
        return false;
    }

    @Override
    public boolean isErrorEnabled() {
        return false;
    }

    /**
     * Get stored exceptions.
     *
     * @return stored exceptions.
     */
    public @NotNull List<Throwable> getExceptions() {
        return this.exceptions;
    }

}
