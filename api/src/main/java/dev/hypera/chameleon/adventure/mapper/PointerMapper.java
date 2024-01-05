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
package dev.hypera.chameleon.adventure.mapper;

import dev.hypera.chameleon.util.Preconditions;
import java.lang.reflect.Method;
import java.util.Objects;
import net.kyori.adventure.pointer.Pointer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure Pointer mapper.
 */
public final class PointerMapper implements Mapper<Pointer<?>> {

    private final @NotNull KeyMapper keyMapper;
    private @Nullable Method pointerCreateMethod;
    private @Nullable Method pointerTypeMethod;
    private @Nullable Method pointerKeyMethod;

    PointerMapper(@NotNull KeyMapper keyMapper) {
        this.keyMapper = keyMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        Class<?> pointerClass = Class.forName(AdventureMapper.ORIGINAL_POINTER_CLASS_NAME);
        Class<?> keyClass = Class.forName(AdventureMapper.ORIGINAL_KEY_CLASS_NAME);
        this.pointerCreateMethod = pointerClass.getMethod("pointer", Class.class, keyClass);
        this.pointerTypeMethod = pointerClass.getMethod("type");
        this.pointerKeyMethod = pointerClass.getMethod("key");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.keyMapper.isLoaded() &&
            this.pointerCreateMethod != null &&
            this.pointerTypeMethod != null &&
            this.pointerKeyMethod != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull Pointer<?> pointer) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("pointer", pointer);
        return Objects.requireNonNull(this.pointerCreateMethod).invoke(
            null, pointer.type(), this.keyMapper.map(pointer.key()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Pointer<?> mapBackwards(@NotNull Object pointer) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("pointer", pointer);
        return Pointer.pointer(
            (Class<?>) Objects.requireNonNull(this.pointerTypeMethod).invoke(pointer),
            this.keyMapper.mapBackwards(Objects.requireNonNull(this.pointerKeyMethod).invoke(pointer))
        );
    }

}
