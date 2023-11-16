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

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon property.
 *
 * @param <T> Value type.
 */
@Internal
@NonExtendable
public interface ChameleonProperty<T> {

    /**
     * Specifies whether debug mode is enabled.
     *
     * <p>When enabled, Chameleon will log additional messages that could be helpful when
     * debugging.</p>
     */
    @NotNull ChameleonProperty<Boolean> DEBUG = ChameleonPropertyImpl.of("debug", Boolean::parseBoolean, false);

    /**
     * Specifies whether errors in Chameleon should be logged.
     */
    @NotNull ChameleonProperty<Boolean> LOG_ERRORS = ChameleonPropertyImpl.of("logErrors", Boolean::parseBoolean, true);

    /**
     * Returns the name of this property.
     *
     * @return property name.
     */
    @Contract(value = "-> _", pure = true)
    @NotNull String name();

    /**
     * Returns the value of this property.
     *
     * @return property value.
     */
    @Contract(value = "-> _", pure = true)
    @NotNull T get();

    /**
     * Sets the value of this property.
     *
     * @param t New property value.
     */
    void set(@NotNull T t);

    /**
     * Resets the value of this property back to the default value.
     */
    void reset();

}
