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
package dev.hypera.chameleon.util.logger;

import dev.hypera.chameleon.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Message formatting utility.
 *
 * <p>This utility was designed to replicate SLF4J's message formatting on other logging libraries.
 * Outputs from the formatting methods should match SLF4J's. If a message is formatted differently
 * by SLF4J, please <a
 * href="https://github.com/ChameleonFramework/Chameleon/issues/new?template=bug_report.yml">create
 * an issue</a> so this can be resolved.</p>
 *
 * <p>Formatting works by using "placeholders" {@code {}} in message patterns. Placeholders
 * designate the location where arguments will be placed within a message pattern, e.g.
 * <pre>{@code MessageFormatter.basicFormat("Hello, {}!", "world")}</pre> will return
 * {@code "Hello, world!"}</p>
 *
 * <p>If you need to place the string {@code {}} in a message without it being used as a
 * placeholder, you can escape the sequence with a backslash, e.g.
 * <pre>{@code MessageFormatter.basicFormat("Hello \\{}, I'm {}.", "Bob")}</pre> will return
 * {@code "Hello {}, I'm Bob."}.</p>
 *
 * <p>If you need to place a backslash before a placeholder, without escaping the
 * placeholder, you can "double escape" (escape the escape) to prevent the placeholder from being
 * escaped, e.g.
 * <pre>{@code MessageFormatter.basicFormat("Saved to C:\\\\{}.", "chameleon\\image.png")}</pre>
 * will return {@code "Saved to C:\\chameleon\\image.png}</p>
 */
public final class MessageFormatter {

    private static final char START_CHAR = '{';
    private static final @NotNull String PLACEHOLDER = "{}";
    private static final char ESCAPE_CHAR = '\\';

    private MessageFormatter() {
        throw new UnsupportedOperationException(
            "MessageFormatter is a utility class and cannot be instantiated");
    }

    /**
     * Formats the given message pattern with the provided arguments.
     *
     * @param pattern Message pattern containing placeholders.
     * @param args    Arguments to replace placeholders in the pattern.
     *
     * @return formatted message string, or {@code null} if the given pattern is {@code null}.
     */
    @Contract(value = "!null, _ -> !null; null, _ -> null", pure = true)
    public static @Nullable String basicFormat(@Nullable String pattern, @Nullable Object... args) {
        return format(pattern, args, null).message();
    }

    /**
     * Formats the given message pattern with the provided arguments.
     *
     * @param pattern Message pattern containing placeholders.
     * @param args    Arguments to replace the placeholders in the pattern.
     *
     * @return a {@link FormattedMessage} object containing the formatted message and extracted
     *     throwable.
     */
    public static @NotNull FormattedMessage format(@Nullable String pattern, @Nullable Object... args) {
        Throwable t = getThrowable(args);
        if (t != null) {
            return format(pattern, trimLast(args), t);
        }
        return format(pattern, args, null);
    }

    /**
     * Format the given message pattern with the provided arguments.
     *
     * @param pattern Message pattern containing placeholders.
     * @param args    Arguments to replace the placeholders in the pattern.
     * @param t       Throwable.
     *
     * @return a {@link FormattedMessage} object containing the formatted message and throwable.
     */
    public static @NotNull FormattedMessage format(@Nullable String pattern, @Nullable Object[] args, @Nullable Throwable t) {
        if (pattern == null) {
            return new FormattedMessage(null, t);
        }
        return new FormattedMessage(formatPattern(pattern, args), t);
    }

    private static @NotNull String formatPattern(@NotNull String pattern, @Nullable Object[] args) {
        if (args == null) {
            return pattern;
        }

        int a; // args index
        int i = 0; // pattern index
        int v; // variable placeholder index ({})
        StringBuilder sb = new StringBuilder(pattern.length());

        for (a = 0; a < args.length; a++) {
            v = pattern.indexOf(PLACEHOLDER, i);
            if (v == -1) { // Variable placeholder not found
                if (i == 0) {
                    return pattern;
                }
                break;
            }

            if (isEscaped(pattern, v) && !isDoubleEscaped(pattern, v)) {
                a--;
                sb.append(pattern, i, v - 1);
                sb.append(START_CHAR);
                i = v + 1;
                continue;
            }

            sb.append(pattern, i, isDoubleEscaped(pattern, v) ? v - 1 : v);
            appendArg(sb, args[a], new ArrayList<>());
            i = v + 2;
        }

        sb.append(pattern, i, pattern.length());
        return sb.toString();
    }

    private static boolean isEscaped(@NotNull String pattern, int startIndex) {
        return startIndex != 0 && pattern.charAt(startIndex - 1) == ESCAPE_CHAR;
    }

    private static boolean isDoubleEscaped(@NotNull String pattern, int startIndex) {
        return startIndex >= 2 && pattern.charAt(startIndex - 2) == ESCAPE_CHAR;
    }

    private static void appendArg(@NotNull StringBuilder sb, @Nullable Object arg, @NotNull Collection<Object[]> seen) {
        if (arg == null) {
            sb.append("null");
            return;
        }

        if (arg.getClass().isArray()) {
            appendArray(sb, arg, seen);
            return;
        }
        appendObject(sb, arg);
    }

    private static void appendObject(@NotNull StringBuilder sb, @NotNull Object arg) {
        try {
            sb.append(arg);
        } catch (Exception ex) {
            sb.append("[FAILED toString()]");
        }
    }

    private static void appendArray(@NotNull StringBuilder sb, @NotNull Object arg, @NotNull Collection<Object[]> seen) {
        // Primitive arrays cannot be cast to Object[]
        if (arg instanceof boolean[]) {
            appendArray(sb, (boolean[]) arg);
            return;
        }
        if (arg instanceof byte[]) {
            appendArray(sb, (byte[]) arg);
            return;
        }
        if (arg instanceof char[]) {
            appendArray(sb, (char[]) arg);
            return;
        }
        if (arg instanceof short[]) {
            appendArray(sb, (short[]) arg);
            return;
        }
        if (arg instanceof int[]) {
            appendArray(sb, (int[]) arg);
            return;
        }
        if (arg instanceof long[]) {
            appendArray(sb, (long[]) arg);
            return;
        }
        if (arg instanceof float[]) {
            appendArray(sb, (float[]) arg);
            return;
        }
        if (arg instanceof double[]) {
            appendArray(sb, (double[]) arg);
            return;
        }
        appendArray(sb, (Object[]) arg, seen);
    }

    private static void appendArray(@NotNull StringBuilder sb, @NotNull Object[] arg, @NotNull Collection<Object[]> seen) {
        if (seen.contains(arg)) {
            sb.append("[...]");
            return;
        }

        seen.add(arg);
        sb.append('[');
        for (int i = 0; i < arg.length; i++) {
            appendArg(sb, arg[i], seen);
            if (i != arg.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
        seen.remove(arg);
    }

    private static void appendArray(@NotNull StringBuilder sb, boolean[] arg) {
        sb.append('[');
        for (int i = 0; i < arg.length; i++) {
            sb.append(arg[i]);
            if (i != arg.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void appendArray(@NotNull StringBuilder sb, byte[] arg) {
        sb.append('[');
        for (int i = 0; i < arg.length; i++) {
            sb.append(arg[i]);
            if (i != arg.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void appendArray(@NotNull StringBuilder sb, char[] arg) {
        sb.append('[');
        for (int i = 0; i < arg.length; i++) {
            sb.append(arg[i]);
            if (i != arg.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void appendArray(@NotNull StringBuilder sb, short[] arg) {
        sb.append('[');
        for (int i = 0; i < arg.length; i++) {
            sb.append(arg[i]);
            if (i != arg.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void appendArray(@NotNull StringBuilder sb, int[] arg) {
        sb.append('[');
        for (int i = 0; i < arg.length; i++) {
            sb.append(arg[i]);
            if (i != arg.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void appendArray(@NotNull StringBuilder sb, long[] arg) {
        sb.append('[');
        for (int i = 0; i < arg.length; i++) {
            sb.append(arg[i]);
            if (i != arg.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void appendArray(@NotNull StringBuilder sb, float[] arg) {
        sb.append('[');
        for (int i = 0; i < arg.length; i++) {
            sb.append(arg[i]);
            if (i != arg.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    private static void appendArray(@NotNull StringBuilder sb, double[] arg) {
        sb.append('[');
        for (int i = 0; i < arg.length; i++) {
            sb.append(arg[i]);
            if (i != arg.length - 1) {
                sb.append(", ");
            }
        }
        sb.append(']');
    }

    /**
     * Removes the last element from the given array.
     *
     * @param array Array to remove the last element from.
     *
     * @return a copy of the given array with the last element removed.
     */
    @SuppressWarnings("NullAway") // NullAway doesn't seem to understand TYPE_USE annotations
    private static @NotNull Object[] trimLast(@Nullable Object @NotNull [] array) {
        Preconditions.checkNotNullOrEmpty("array", array);
        Object[] trimmedArray = new Object[array.length - 1];
        if (array.length > 1) {
            System.arraycopy(array, 0, trimmedArray, 0, array.length - 1);
        }
        return trimmedArray;
    }

    /**
     * Returns the last element of the given array if the last element is a Throwable.
     *
     * @param array Array to retrieve throwable from.
     *
     * @return a throwable, if the last element of the array was a throwable, otherwise
     *     {@code null}.
     */
    @Contract(value = "null -> null", pure = true)
    private static @Nullable Throwable getThrowable(@Nullable Object @Nullable [] array) {
        if (array == null || array.length < 1) {
            return null;
        }
        Object lastElement = array[array.length - 1];
        if (lastElement instanceof Throwable) {
            return (Throwable) lastElement;
        }
        return null;
    }

}
