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
package dev.hypera.chameleon.platform.user;

import dev.hypera.chameleon.meta.MetadataKey;
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.util.Preconditions;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

/**
 * Base platform chat user implementation.
 */
public abstract class PlatformChatUser implements ChatUser {

    private final @NotNull Map<MetadataKey<?>, Supplier<?>> metadata = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NotNull <V> Optional<V> getMetadata(@NotNull MetadataKey<V> key) {
        Preconditions.checkNotNull("key", key);
        return Optional.ofNullable(this.metadata.get(key)).map(v -> (V) v.get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <V> void setDynamicMetadata(@NotNull MetadataKey<V> key, @NotNull Supplier<? extends V> value) {
        Preconditions.checkNotNull("key", key);
        Preconditions.checkNotNull("value", value);
        this.metadata.put(key, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeMetadata(@NotNull MetadataKey<?> key) {
        Preconditions.checkNotNull("key", key);
        this.metadata.remove(key);
    }

}
