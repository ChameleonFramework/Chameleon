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
package dev.hypera.chameleon.utils;

import dev.hypera.chameleon.Chameleon;
import java.lang.reflect.ParameterizedType;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@link Chameleon} utilities.
 */
@Internal
public final class ChameleonUtil {

    private ChameleonUtil() {

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
        return null == s ? defaultValue : s;
    }

    /**
     * Get the class of a generic type.
     *
     * @param clazz Class to get the generic type on.
     * @param generic Generic type index.
     *
     * @return Generic type as a class.
     */
    public static @NotNull Class<?> getGenericTypeAsClass(@NotNull Class<?> clazz, int generic) {
        return (Class<?>) ((ParameterizedType) clazz.getGenericSuperclass()).getActualTypeArguments()[generic];
    }

}
