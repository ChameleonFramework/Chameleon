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
package dev.hypera.chameleon.util.internal;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Common internal Chameleon utilities.
 */
@Internal
public final class ChameleonUtil {

    private ChameleonUtil() {
        throw new UnsupportedOperationException(
            "ChameleonUtil is a utility class and cannot be instantiated");
    }

    /**
     * Check if first argument is null, return it if it isn't, otherwise return the default value.
     *
     * @param s            Object to check if null.
     * @param defaultValue Default return value.
     * @param <T>          Type.
     *
     * @return {@code s} if not null, otherwise {@code defaultValue}.
     */
    public static <T> @NotNull T getOrDefault(@Nullable T s, @NotNull T defaultValue) {
        return s == null ? defaultValue : s;
    }

    /**
     * Returns the first non-empty string. If all strings are empty, the last one will be returned.
     *
     * @param s1 First string.
     * @param s2 Second string.
     *
     * @return first non-empty string.
     */
    public static @NotNull String firstNonEmpty(@NotNull String s1, @NotNull String s2) {
        return !s1.isEmpty() ? s1 : s2;
    }

    /**
     * Returns {@code null} if the given string is {@code null} or empty, otherwise the given
     * string.
     *
     * @param s String.
     *
     * @return {@code null} if the given string is equal to {@code null} or empty, otherwise the
     *     given string.
     */
    public static @Nullable String nullifyEmpty(@Nullable String s) {
        if (isNullOrEmpty(s)) {
            return null;
        }
        return s;
    }

    /**
     * Returns whether the given string is {@code null} or empty.
     *
     * @param s String.
     *
     * @return {@code true} if the given string is equal to {@code null} or empty, otherwise
     *     {@code true}.
     */
    public static boolean isNullOrEmpty(@Nullable String s) {
        return s == null || s.isEmpty();
    }

}
