/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package dev.hypera.chameleon.core;

import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.core.logging.ChameleonLogger;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@link Chameleon} bootstrap. Allows for runtime dependency loading, etc. before Chameleon is actually loaded.
 *
 * @param <T> {@link Chameleon} implementation
 */
public abstract class ChameleonBootstrap<T extends Chameleon> {

    private @Nullable Consumer<ChameleonLogger> preLoad;

    /**
     * Set pre-load handler.
     *
     * @param preLoad Pre-load handler.
     *
     * @return {@code this}
     */
    public final @NotNull ChameleonBootstrap<T> onPreLoad(@NotNull Consumer<ChameleonLogger> preLoad) {
        this.preLoad = preLoad;
        return this;
    }

    /**
     * Load {@link Chameleon} implementation.
     *
     * @return {@link Chameleon} implementation instance.
     * @throws ChameleonInstantiationException if something goes wrong while loading the {@link Chameleon} implementation.
     */
    public final @NotNull T load() throws ChameleonInstantiationException {
        if (null != this.preLoad) {
            this.preLoad.accept(createLogger());
        }

        return loadInternal();
    }

    protected abstract @NotNull T loadInternal() throws ChameleonInstantiationException;

    protected abstract @NotNull ChameleonLogger createLogger();

}
