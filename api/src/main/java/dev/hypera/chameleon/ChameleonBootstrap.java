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

import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.event.EventBusImpl;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extension.ChameleonExtension;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.util.Preconditions;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.function.Consumer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon bootstrap.
 * <p>Allows you to perform actions and load extensions before Chameleon has been loaded.</p>
 *
 * @param <T> Chameleon implementation type.
 */
public abstract class ChameleonBootstrap<T extends Chameleon> {

    private @NotNull Consumer<ChameleonLogger> preLoad = l -> {};
    protected final @NotNull ChameleonLogger logger;
    protected final @NotNull EventBus eventBus;
    protected final @NotNull Collection<? super ChameleonExtension> extensions = new HashSet<>();

    protected ChameleonBootstrap(@NotNull ChameleonLogger logger) {
        this.logger = logger;
        this.eventBus = new EventBusImpl(logger);
    }

    /**
     * Load with extensions.
     *
     * @param extensions Chameleon platform extensions to be loaded.
     *
     * @return {@code this}.
     */
    @Contract("_ -> this")
    public final @NotNull ChameleonBootstrap<T> withExtensions(@NotNull ChameleonExtension... extensions) {
        Preconditions.checkNotNull("extensions", extensions);
        return withExtensions(Arrays.asList(extensions));
    }

    /**
     * Load with extensions.
     *
     * @param extensions Chameleon platform extensions to be loaded.
     *
     * @return {@code this}.
     */
    @Contract("_ -> this")
    public final @NotNull ChameleonBootstrap<T> withExtensions(@NotNull Collection<? extends ChameleonExtension> extensions) {
        Preconditions.checkNoneNull("extensions", extensions);
        this.extensions.addAll(extensions);
        return this;
    }

    /**
     * Set preload handler.
     *
     * @param preLoad Preload handler.
     *
     * @return {@code this}.
     */
    @Contract("_ -> this")
    public final @NotNull ChameleonBootstrap<T> onPreLoad(@NotNull Consumer<ChameleonLogger> preLoad) {
        Preconditions.checkNotNull("preLoad", preLoad);
        this.preLoad = preLoad;
        return this;
    }

    /**
     * Load Chameleon implementation.
     *
     * @return Chameleon implementation instance.
     * @throws ChameleonInstantiationException if something goes wrong while loading the Chameleon
     *                                         implementation.
     */
    @Contract("-> new")
    public final @NotNull T load() throws ChameleonInstantiationException {
        // Run preload and initialise extensions.
        this.preLoad.accept(this.logger);
        this.extensions.forEach(ext -> ((ChameleonExtension) ext).init(this.logger, this.eventBus));

        // Load Chameleon
        T chameleon = loadInternal();
        chameleon.onLoad();

        // Load extensions
        this.extensions.forEach(ext -> ((ChameleonExtension) ext).load(chameleon));
        return chameleon;
    }

    protected abstract @NotNull T loadInternal() throws ChameleonInstantiationException;

}
