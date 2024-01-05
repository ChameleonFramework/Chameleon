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
package dev.hypera.chameleon.util;

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Pair, stores two values together.
 *
 * @param <A> First type.
 * @param <B> Second type.
 */
@NonExtendable
public interface Pair<A, B> {

    /**
     * Returns a new pair with the given values.
     *
     * @param first  First value.
     * @param second Second value.
     * @param <A>    First type.
     * @param <B>    Second type.
     *
     * @return new pair.
     */
    static @NotNull <A, B> Pair<A, B> of(@NotNull A first, @NotNull B second) {
        return new PairImpl<>(first, second);
    }

    /**
     * Returns the first value.
     *
     * @return an optional containing the first value, if not null, otherwise an empty optional.
     */
    @NotNull A first();

    /**
     * Returns the second value.
     *
     * @return an optional containing the second value, if not null, otherwise an empty optional.
     */
    @NotNull B second();

}
