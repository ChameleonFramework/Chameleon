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
package dev.hypera.chameleon.meta;

import dev.hypera.chameleon.util.Preconditions;
import java.util.Objects;
import java.util.regex.Pattern;
import net.kyori.adventure.key.KeyPattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Metadata key implementation.
 *
 * @param <V> Metadata value type.
 *
 * @see MetadataKey
 */
final class MetadataKeyImpl<V> implements MetadataKey<V> {

    private static final @NotNull Pattern NAMESPACE_PATTERN = Pattern.compile("[a-z0-9_\\-.]+");
    private static final @NotNull Pattern VALUE_PATTERN = Pattern.compile("[a-z0-9_\\-./]+");

    private final @NotNull Class<V> type;
    private final @NotNull String namespace;
    private final @NotNull String value;

    MetadataKeyImpl(@NotNull Class<V> type, @NotNull String namespace, @NotNull String value) {
        Preconditions.checkNotNull("type", type);
        Preconditions.checkNotNullOrEmpty("namespace", namespace);
        Preconditions.checkMatches("namespace", NAMESPACE_PATTERN, namespace);
        Preconditions.checkNotNullOrEmpty("value", value);
        Preconditions.checkMatches("value", VALUE_PATTERN, value);
        this.type = type;
        this.namespace = namespace;
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Class<V> type() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull @KeyPattern.Namespace String namespace() {
        return this.namespace;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull @KeyPattern.Value String value() {
        return this.value;
    }

    @Override
    public @NotNull String asString() {
        return this.namespace + ':' + this.value;
    }

    @Override
    public String toString() {
        return asString() + "<" + this.type.getSimpleName() + ">";
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        MetadataKeyImpl<?> that = (MetadataKeyImpl<?>) object;
        return Objects.equals(this.namespace, that.namespace())
            && Objects.equals(this.value, that.value())
            && Objects.equals(this.type, that.type());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.namespace, this.value, this.type);
    }

}
