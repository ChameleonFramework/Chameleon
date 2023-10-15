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
package dev.hypera.chameleon.util;

import java.util.Collection;
import java.util.Objects;
import java.util.regex.Pattern;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Preconditions, a set of convenience methods to aid in validating method or constructor
 * invocation.
 *
 * <p>We use JetBrains Annotations for nullability indication, however this makes everything a lot
 * stricter so that Chameleon does not end up in an illegal state.</p>
 *
 * <p>Heavily inspired by <a href="https://github.com/google/guava/">Google Guava's
 * Preconditions</a>.</p>
 */
public final class Preconditions {

    private Preconditions() {
        throw new UnsupportedOperationException(
            "Preconditions is a utility class and cannot be instantiated");
    }

    /**
     * Ensures the expression is {@code true} to validate an argument.
     *
     * @param expression Expression.
     *
     * @throws IllegalArgumentException if {@code expression} is {@code false}.
     */
    @Contract("false -> fail")
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Ensures the expression is {@code true} to validate an argument.
     *
     * @param expression Expression.
     * @param message    The exception message to use if check fails.
     *
     * @throws IllegalArgumentException if {@code expression} is {@code false}.
     */
    @Contract("false, _ -> fail")
    public static void checkArgument(boolean expression, @NotNull String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the expression is {@code true} to validate an argument.
     *
     * @param expression    Expression.
     * @param messageFormat The exception message format to use if check fails.
     * @param messageArgs   The arguments to use with the message format if the check fails.
     *
     * @throws IllegalArgumentException if {@code expression} is {@code false}.
     */
    @Contract("false, _, _ -> fail")
    public static void checkArgument(boolean expression, @NotNull String messageFormat, @NotNull Object... messageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(messageFormat, messageArgs));
        }
    }

    /**
     * Ensures that expression is {@code true} to validate a state.
     *
     * @param expression Expression.
     *
     * @throws IllegalStateException if {@code expression} is {@code false}.
     */
    @Contract("false -> fail")
    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    /**
     * Ensures the expression is {@code true} to validate a state.
     *
     * @param expression Expression.
     * @param message    The exception message to use if check fails.
     *
     * @throws IllegalStateException if {@code expression} is {@code false}.
     */
    @Contract("false, _ -> fail")
    public static void checkState(boolean expression, @NotNull String message) {
        if (!expression) {
            throw new IllegalStateException(message);
        }
    }

    /**
     * Ensures the expression is {@code true} to validate a state.
     *
     * @param expression    Expression.
     * @param messageFormat The exception message format to use if check fails.
     * @param messageArgs   The arguments to use with the message format if the check fails.
     *
     * @throws IllegalStateException if {@code expression} is {@code false}.
     */
    @Contract("false, _, _ -> fail")
    public static void checkState(boolean expression, @NotNull String messageFormat, @NotNull Object... messageArgs) {
        if (!expression) {
            throw new IllegalStateException(String.format(messageFormat, messageArgs));
        }
    }

    /**
     * Ensures the given {@code value} is not null.
     *
     * @param value Argument value.
     * @param <T>   Value type.
     *
     * @return {@code value}
     * @throws NullPointerException if {@code value} is {@code null}.
     */
    @Contract("!null -> param1; null -> fail")
    public static <T> @NotNull T checkNotNull(@Nullable T value) {
        if (value == null) {
            throw new NullPointerException();
        }
        return value;
    }

    /**
     * Ensures the given {@code value} is not null.
     *
     * @param name  Argument name, used in the exception message if {@code value} is null.
     * @param value Argument value.
     * @param <T>   Value type.
     *
     * @return {@code value}.
     * @throws IllegalArgumentException if {@code value} is {@code null}.
     */
    @Contract("_, !null -> param2; _, null -> fail")
    public static <T> @NotNull T checkNotNull(@NotNull String name, @Nullable T value) {
        if (value == null) {
            throw new IllegalArgumentException(name.concat(" cannot be null"));
        }
        return value;
    }

    /**
     * Ensures the given {@code value} is not null.
     *
     * @param name  Argument name, used in the exception message if {@code value} is null.
     * @param value Argument value.
     * @param <T>   Value type.
     *
     * @return {@code value}.
     * @throws IllegalStateException if {@code value} is {@code null}.
     */
    @Contract("_, !null -> param2; _, null -> fail")
    public static <T> @NotNull T checkNotNullState(@NotNull String name, @Nullable T value) {
        if (value == null) {
            throw new IllegalStateException(name.concat(" cannot be null"));
        }
        return value;
    }

    /**
     * Ensures the given {@code value} is not null or empty.
     *
     * @param value Argument value.
     *
     * @return {@code value}.
     * @throws IllegalArgumentException if {@code value} is {@code null} or empty.
     */
    @Contract("!null -> param1; null -> fail")
    public static @NotNull String checkNotNullOrEmpty(@Nullable String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("cannot be null or empty");
        }
        return value;
    }

    /**
     * Ensures the given {@code value} is not null or empty.
     *
     * @param name  Argument name.
     * @param value Argument value.
     *
     * @return {@code value}.
     * @throws IllegalArgumentException if {@code value} is {@code null} or empty.
     */
    @Contract("_, !null -> param2; _, null -> fail")
    public static @NotNull String checkNotNullOrEmpty(@NotNull String name, @Nullable String value) {
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException(name.concat(" cannot be null or empty"));
        }
        return value;
    }

    /**
     * Ensures the given {@code value} is not null or empty.
     *
     * @param name  Argument name, used in the exception message if {@code value} is null or empty.
     * @param value Argument value.
     * @param <T>   Value type.
     *
     * @return {@code value}.
     */
    @Contract("_, !null -> param2; _, null -> fail")
    public static <T> @Nullable T @NotNull [] checkNotNullOrEmpty(@NotNull String name, @Nullable T @Nullable [] value) {
        Preconditions.checkNotNull(name, value);
        if (value.length < 1) {
            throw new IllegalArgumentException(name.concat(" cannot be empty"));
        }
        return value;
    }

    /**
     * Ensures the given {@code value} is not null or empty.
     *
     * @param name  Argument name, used in the exception message if {@code value} is null or empty.
     * @param value Argument value.
     * @param <T>   Value type.
     *
     * @return {@code value}.
     */
    @Contract("_, !null -> param2; _, null -> fail")
    public static <T> @NotNull Collection<T> checkNotNullOrEmpty(@NotNull String name, @Nullable Collection<T> value) {
        Preconditions.checkNotNull(name, value);
        if (value.isEmpty()) {
            throw new IllegalArgumentException(name.concat(" cannot be empty"));
        }
        return value;
    }

    /**
     * Ensures the given {@code value} does not contain null.
     *
     * @param name  Argument name, used in the exception message if {@code value} contains null.
     * @param value Argument value.
     * @param <T>   Value type.
     *
     * @return {@code value}.
     * @throws IllegalArgumentException if {@code value} contains {@code null}.
     */
    @Contract("_, !null -> param2; _, null -> fail")
    public static <T> @NotNull Collection<T> checkNoneNull(@NotNull String name, @Nullable Collection<T> value) {
        Preconditions.checkNotNull(name, value);
        if (value.parallelStream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException(name.concat(" cannot contain null"));
        }
        return value;
    }

    /**
     * Ensures the given {@code value} matches the given pattern.
     *
     * @param name  Argument name.
     * @param regex RegEx pattern to match against.
     * @param value Argument value.
     *
     * @return {@code value}.
     * @throws IllegalArgumentException if the given {@code value} does not match the given
     *                                  pattern.
     */
    @Contract("_, _, null -> fail; _, _, _ -> param3")
    public static @NotNull String checkMatches(@NotNull String name, @NotNull Pattern regex, @Nullable String value) {
        Preconditions.checkNotNull(name, value);
        if (!regex.matcher(value).matches()) {
            throw new IllegalArgumentException(name.concat(" must match ").concat(regex.pattern()));
        }
        return value;
    }

}
