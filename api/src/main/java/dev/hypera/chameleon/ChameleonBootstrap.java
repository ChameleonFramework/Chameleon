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
package dev.hypera.chameleon;

import dev.hypera.chameleon.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extensions.ChameleonExtension;
import dev.hypera.chameleon.extensions.ChameleonPlatformExtension;
import dev.hypera.chameleon.logging.ChameleonLogger;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@link Chameleon} bootstrap. Allows for runtime dependency loading, etc. before Chameleon is actually loaded.
 *
 * @param <T> {@link Chameleon} implementation type.
 * @param <E> {@link ChameleonPlatformExtension} implementation type.
 */
public abstract class ChameleonBootstrap<T extends Chameleon, E extends ChameleonPlatformExtension<?, ?, T>> {

    private @Nullable Consumer<ChameleonLogger> preLoad;

    private final @NotNull Collection<E> platformExtensions = new HashSet<>();


    /**
     * Load with extensions.
     *
     * @param extensions {@link ChameleonPlatformExtension}s to be loaded.
     *
     * @return {@code this}.
     */
    @SafeVarargs
    @SuppressWarnings("varargs")
    @Contract("_ -> this")
    public final @NotNull ChameleonBootstrap<T, E> withExtensions(@NotNull E... extensions) {
        return withExtensions(Arrays.asList(extensions));
    }

    /**
     * Load with extensions.
     *
     * @param extensions {@link ChameleonPlatformExtension}s to be loaded.
     *
     * @return {@code this}.
     */
    @Contract("_ -> this")
    public final @NotNull ChameleonBootstrap<T, E> withExtensions(@NotNull Collection<E> extensions) {
        this.platformExtensions.addAll(extensions);
        return this;
    }


    /**
     * Set pre-load handler.
     *
     * @param preLoad Pre-load handler.
     *
     * @return {@code this}.
     */
    @Contract("_ -> this")
    public final @NotNull ChameleonBootstrap<T, E> onPreLoad(@NotNull Consumer<ChameleonLogger> preLoad) {
        this.preLoad = preLoad;
        return this;
    }

    /**
     * Load {@link Chameleon} implementation.
     *
     * @return {@link Chameleon} implementation instance.
     * @throws ChameleonInstantiationException if something goes wrong while loading the {@link Chameleon} implementation.
     */
    @Contract("-> new")
    public final @NotNull T load() throws ChameleonInstantiationException {
        if (null != this.preLoad) {
            this.preLoad.accept(createLogger());
        }

        Collection<ChameleonExtension<?>> extensions = this.platformExtensions.stream().map(ext -> ext.getExtension()).collect(Collectors.toSet());
        extensions.forEach(ChameleonExtension::onPreLoad);

        T chameleon = loadInternal(extensions);
        chameleon.onLoad();
        this.platformExtensions.forEach(ext -> ext.onLoad(chameleon));
        extensions.forEach(ext -> ext.onLoad(chameleon));
        return chameleon;
    }

    protected abstract @NotNull T loadInternal(@NotNull Collection<ChameleonExtension<?>> extensions) throws ChameleonInstantiationException;

    protected abstract @NotNull ChameleonLogger createLogger();

}
