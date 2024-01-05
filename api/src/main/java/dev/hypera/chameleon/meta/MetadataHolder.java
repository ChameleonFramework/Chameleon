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

import java.util.Optional;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents an object that can hold metadata.
 */
public interface MetadataHolder {

    /**
     * Returns the value of the given metadata {@code key}.
     *
     * @param key Metadata key.
     * @param <V> Metadata value type.
     *
     * @return an optional containing the metadata value, if available, otherwise an empty optional.
     */
    <V> @NotNull Optional<V> getMetadata(@NotNull MetadataKey<V> key);

    /**
     * Stores metadata with the given {@code key}.
     * <p>Stored metadata can be retrieved later using {@link #getMetadata(MetadataKey)}.</p>
     *
     * @param key   Metadata key.
     * @param value Metadata value.
     * @param <V>   Metadata value type.
     *
     * @see #getMetadata(MetadataKey)
     */
    default <V> void setMetadata(@NotNull MetadataKey<V> key, @Nullable V value) {
        setDynamicMetadata(key, (Supplier<? extends V>) () -> value);
    }

    /**
     * Stores dynamic metadata with the given {@code key}.
     *
     * <p>Dynamic metadata is retrieved when required, e.g. when {@link #getMetadata(MetadataKey)}
     * is executed.</p>
     *
     * <p>Stored metadata can be retrieved later using {@link #getMetadata(MetadataKey)}.</p>
     *
     * @param key   Metadata key.
     * @param value Metadata value.
     * @param <V>   Metadata value type.
     */
    <V> void setDynamicMetadata(@NotNull MetadataKey<V> key, @NotNull Supplier<? extends V> value);

    /**
     * Removes stored metadata with the given {@code key}.
     *
     * @param key Metadata key.
     */
    void removeMetadata(@NotNull MetadataKey<?> key);

}
