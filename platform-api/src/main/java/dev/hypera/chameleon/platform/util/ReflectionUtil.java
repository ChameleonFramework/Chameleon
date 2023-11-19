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
package dev.hypera.chameleon.platform.util;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Utilities for working with reflection.
 */
@Internal
public final class ReflectionUtil {

    private static final @NotNull MethodHandles.Lookup LOOKUP = MethodHandles.lookup();

    private ReflectionUtil() {
        throw new UnsupportedOperationException(
            "ReflectionUtil is a utility class and cannot be instantiated");
    }

    /**
     * Returns a class.
     * <p>This will search for each class in the order they are provided, the first found class
     * will be returned.</p>
     *
     * @param classNames Class names.
     *
     * @return class, if found, otherwise {@code null}.
     */
    public static @Nullable Class<?> findClass(@NotNull String @NotNull ... classNames) {
        for (String className : classNames) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException ignored) {
                // continue
            }
        }
        return null;
    }

    /**
     * Returns whether a class is available.
     *
     * @param classNames Class names to search for.
     *
     * @return {@code true} if a class was found, otherwise {@code false}.
     */
    public static boolean hasClass(@NotNull String @NotNull ... classNames) {
        return findClass(classNames) != null;
    }

    /**
     * Returns a handle for a method.
     *
     * @param clazz          Class the method exists on.
     * @param methodName     Name of the method to return.
     * @param returnType     Method return type.
     * @param parameterTypes Method parameter types.
     *
     * @return method handle, if the method was found, otherwise {@code null}.
     */
    @SuppressWarnings("NullAway") // NullAway doesn't seem to understand TYPE_USE annotations
    public static @Nullable MethodHandle getMethod(
        @Nullable Class<?> clazz,
        @NotNull String methodName,
        @Nullable Class<?> returnType,
        @Nullable Class<?> @NotNull ... parameterTypes
    ) {
        if (clazz == null) {
            return null;
        }
        for (Class<?> paramType : parameterTypes) {
            if (paramType == null) {
                return null;
            }
        }
        try {
            return LOOKUP.findVirtual(clazz, methodName, MethodType.methodType(returnType, parameterTypes));
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            return null;
        }
    }

}
