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
package dev.hypera.chameleon.adventure.mapper;

import dev.hypera.chameleon.util.Preconditions;
import java.lang.reflect.Method;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Enum mapper.
 *
 * @param <E> Enum type.
 */
public final class EnumMapper<E extends Enum<E>> implements Mapper<E> {

    private final @NotNull Class<E> clazz;
    private final @NotNull Class<?> platformClass;
    private @Nullable Method nameMethod;
    private @Nullable Method valueOfMethod;

    /**
     * Enum mapper constructor.
     *
     * @param clazz         Enum class.
     * @param platformClass Platform enum class.
     */
    public EnumMapper(@NotNull Class<E> clazz, @NotNull Class<?> platformClass) {
        this.clazz = clazz;
        this.platformClass = platformClass;
    }

    /**
     * Create a new Enum mapper for the given enum class and platform class.
     *
     * @param clazz         Enum class.
     * @param platformClass Platform enum class.
     * @param <E>           Enum type.
     *
     * @return new enum mapper.
     */
    public static <E extends Enum<E>> @NotNull EnumMapper<E> createAndLoad(@NotNull Class<E> clazz, @NotNull Class<?> platformClass) throws ReflectiveOperationException {
        EnumMapper<E> mapper = new EnumMapper<>(clazz, platformClass);
        mapper.load();
        return mapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        this.nameMethod = this.platformClass.getMethod("name");
        this.valueOfMethod = this.platformClass.getMethod("valueOf", String.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.nameMethod != null && this.valueOfMethod != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull E e) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("e", e);
        return Objects.requireNonNull(this.valueOfMethod).invoke(null, e.name());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull E mapBackwards(@NotNull Object e) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("e", e);
        return Enum.valueOf(this.clazz, (String) Objects.requireNonNull(this.nameMethod).invoke(e));
    }

}
