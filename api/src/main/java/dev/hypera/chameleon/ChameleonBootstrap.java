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
import dev.hypera.chameleon.exception.extension.ChameleonExtensionException;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extension.ChameleonExtension;
import dev.hypera.chameleon.extension.ChameleonExtensionFactory;
import dev.hypera.chameleon.extension.ChameleonPlatformExtension;
import dev.hypera.chameleon.extension.ExtensionMap;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.util.Preconditions;
import java.util.List;
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
    private final @NotNull String platform;
    protected final @NotNull EventBus eventBus;
    protected final @NotNull ExtensionMap extensions = new ExtensionMap();

    protected ChameleonBootstrap(@NotNull ChameleonLogger logger, @NotNull String platform) {
        this.logger = logger;
        this.platform = platform;
        this.eventBus = new EventBusImpl(logger);
    }

    /**
     * Load with a Chameleon extension.
     *
     * @param factory The factory to create the Chameleon extension.
     * @param <E>     Extension type.
     *
     * @return {@code this}.
     */
    @Contract("_ -> this")
    public final <E extends ChameleonExtension> @NotNull ChameleonBootstrap<T> withExtension(@NotNull ChameleonExtensionFactory<E> factory) {
        Preconditions.checkNotNull("factory", this.extensions);
        ChameleonPlatformExtension extension = factory.create(this.platform);
        if (!factory.getType().isAssignableFrom(extension.getClass())) {
            throw ChameleonExtensionException.create(
                "Cannot load %s: not assignable from %s",
                factory.getType().getSimpleName(), extension.getClass().getSimpleName()
            );
        }
        this.extensions.put(factory.getType(), factory.create(this.platform));
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
        List<ChameleonPlatformExtension> sortedExtensions = this.extensions.loadSort();
        sortedExtensions.forEach(ext -> ext.init(this.logger, this.eventBus));

        // Load Chameleon
        T chameleon = loadInternal();
        chameleon.onLoad();

        // Load extensions
        sortedExtensions.forEach(ext -> ext.load(chameleon));
        return chameleon;
    }

    protected abstract @NotNull T loadInternal() throws ChameleonInstantiationException;

}
