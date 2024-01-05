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
package dev.hypera.chameleon.meta;

import dev.hypera.chameleon.util.Preconditions;
import java.util.UUID;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.KeyPattern;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * An identifying object used to store and retrieve metadata from {@link MetadataHolder}s.
 *
 * <p>Metadata key are similar to the Adventure {@link Key} objects, except metadata keys also
 * consist of a value type.</p>
 *
 * <p>Key namespaces must match {@code [a-z0-9_\-.]+}</p>
 * <p>Key values must match {@code [a-z0-9_\-./]+}</p>
 *
 * @param <V> Metadata value type.
 */
@NonExtendable
public interface MetadataKey<V> extends Key {

    /**
     * Returns a new string metadata key.
     * <p>Equivalent to calling {@link #of(Class, String)} with {@code String.class} as the
     * type.</p>
     *
     * @param string Metadata key string.
     *
     * @return new metadata key.
     * @see #of(Class, String)
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull MetadataKey<String> string(@NotNull @KeyPattern String string) {
        return of(String.class, string);
    }

    /**
     * Returns a new string metadata key.
     * <p>Equivalent to calling {@link #of(Class, String, String)} with {@code String.class} as the
     * type.</p>
     *
     * @param namespace Metadata key namespace.
     * @param value     Metadata key value.
     *
     * @return new metadata key.
     * @see #of(Class, String)
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull MetadataKey<String> string(@NotNull @KeyPattern.Namespace String namespace, @NotNull @KeyPattern.Value String value) {
        return of(String.class, namespace, value);
    }

    /**
     * Returns a new boolean metadata key.
     * <p>Equivalent to calling {@link #of(Class, String)} with {@code Boolean.class} as the
     * type.</p>
     *
     * @param string Metadata key string.
     *
     * @return new metadata key.
     * @see #of(Class, String)
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull MetadataKey<Boolean> bool(@NotNull @KeyPattern String string) {
        return of(Boolean.class, string);
    }

    /**
     * Returns a new boolean metadata key.
     * <p>Equivalent to calling {@link #of(Class, String, String)} with {@code Boolean.class} as
     * the type.</p>
     *
     * @param namespace Metadata key namespace.
     * @param value     Metadata key value.
     *
     * @return new metadata key.
     * @see #of(Class, String, String)
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull MetadataKey<Boolean> bool(@NotNull @KeyPattern.Namespace String namespace, @NotNull @KeyPattern.Value String value) {
        return of(Boolean.class, namespace, value);
    }

    /**
     * Returns a new boolean metadata key.
     * <p>Equivalent to calling {@link #of(Class, String)} with {@code Integer.class} as the
     * type.</p>
     *
     * @param string Metadata key string.
     *
     * @return new metadata key.
     * @see #of(Class, String)
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull MetadataKey<Integer> integer(@NotNull @KeyPattern String string) {
        return of(Integer.class, string);
    }

    /**
     * Returns a new boolean metadata key.
     * <p>Equivalent to calling {@link #of(Class, String, String)} with {@code Integer.class} as
     * the type.</p>
     *
     * @param namespace Metadata key namespace.
     * @param value     Metadata key value.
     *
     * @return new metadata key.
     * @see #of(Class, String, String)
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull MetadataKey<Integer> integer(@NotNull @KeyPattern.Namespace String namespace, @NotNull @KeyPattern.Value String value) {
        return of(Integer.class, namespace, value);
    }

    /**
     * Returns a new UUID metadata key.
     * <p>Equivalent to calling {@link #of(Class, String)} with {@code UUID.class} as the
     * type.</p>
     *
     * @param string Metadata key string.
     *
     * @return new metadata key.
     * @see #of(Class, String)
     */
    @Contract(value = "_ -> new", pure = true)
    static @NotNull MetadataKey<UUID> uuid(@NotNull @KeyPattern String string) {
        return of(UUID.class, string);
    }

    /**
     * Returns a new UUID metadata key.
     * <p>Equivalent to calling {@link #of(Class, String, String)} with {@code UUID.class} as the
     * type.</p>
     *
     * @param namespace Metadata key namespace.
     * @param value     Metadata key value.
     *
     * @return new metadata key.
     * @see #of(Class, String, String)
     */
    @Contract(value = "_, _ -> new", pure = true)
    static @NotNull MetadataKey<UUID> uuid(@NotNull @KeyPattern.Namespace String namespace, @KeyPattern.Value @NotNull String value) {
        return of(UUID.class, namespace, value);
    }

    /**
     * Returns a new metadata key.
     *
     * <p>{@code string} will be parsed as a key, using {@code DEFAULT_SEPARATOR} as a separator
     * between the namespace and value.</p>
     *
     * <p>The key namespace is optional. If you do not provide one, then
     * {@code MINECRAFT_NAMESPACE} will be used as the namespace, and the string will be used as the
     * value.</p>
     *
     * @param type   Metadata key value type class.
     * @param string Metadata key string.
     * @param <V>    Metadata value type.
     *
     * @return new metadata key.
     * @throws IllegalArgumentException if the namespace or valid contains an invalid character.
     */
    @SuppressWarnings("PatternValidation")
    @Contract(value = "_, _ -> new", pure = true)
    static <V> @NotNull MetadataKey<V> of(@NotNull Class<V> type, @NotNull @KeyPattern String string) {
        Preconditions.checkNotNullOrEmpty("string", string);
        int separatorIndex = string.indexOf(DEFAULT_SEPARATOR);
        return of(type,
            separatorIndex >= 1 ? string.substring(0, separatorIndex) : MINECRAFT_NAMESPACE,
            separatorIndex >= 0 ? string.substring(separatorIndex + 1) : string
        );
    }

    /**
     * Returns a new metadata key.
     *
     * @param type      Metadata value type class.
     * @param namespace Metadata key namespace.
     * @param value     Metadata key value.
     * @param <V>       Metadata value type.
     *
     * @return new metadata key.
     * @throws IllegalArgumentException if the namespace or value contains an invalid character.
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    static <V> @NotNull MetadataKey<V> of(@NotNull Class<V> type, @NotNull @KeyPattern.Namespace String namespace, @NotNull @KeyPattern.Value String value) {
        return new MetadataKeyImpl<>(type, namespace, value);
    }

    /**
     * Returns the metadata value type.
     *
     * @return metadata value type.
     */
    @NotNull Class<V> type();

}
