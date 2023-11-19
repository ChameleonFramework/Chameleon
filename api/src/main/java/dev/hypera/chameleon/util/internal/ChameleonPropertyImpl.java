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
package dev.hypera.chameleon.util.internal;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * {@link ChameleonProperty} implementation.
 *
 * @param <T> Value type.
 */
final class ChameleonPropertyImpl<T> implements ChameleonProperty<T> {

    private static final @NotNull String NAMESPACE = String.join(".", "dev", "hypera", "chameleon");

    private final @NotNull String name;
    private final @NotNull Function<String, T> parser;
    private final @NotNull T defaultValue;
    private final @NotNull AtomicBoolean retrieved = new AtomicBoolean(false);
    private @NotNull T value;

    ChameleonPropertyImpl(@NotNull String name, @NotNull Function<String, T> parser, @NotNull T defaultValue) {
        this.name = name;
        this.parser = parser;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String name() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull T get() {
        if (this.retrieved.compareAndSet(false, true)) {
            this.value = retrieveValue();
        }
        return this.value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(@NotNull T t) {
        this.retrieved.set(true);
        this.value = t;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        this.retrieved.set(false);
        this.value = this.defaultValue;
    }

    private @NotNull T retrieveValue() {
        String v = System.getProperty(NAMESPACE + '.' + this.name);
        T parsed = v != null ? this.parser.apply(v) : null;
        if (parsed == null) {
            return this.defaultValue;
        }
        return parsed;
    }

}
