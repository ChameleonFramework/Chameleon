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
package dev.hypera.chameleon.extension;

import dev.hypera.chameleon.exception.extension.ChameleonExtensionException;
import java.util.Collection;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Extension manager.
 *
 * @see ChameleonExtension
 * @see ChameleonExtensionFactory
 */
@NonExtendable
public interface ExtensionManager {

    /**
     * Load a Chameleon extension.
     *
     * @param factory The factory to create the Chameleon extension.
     * @param <T>     Chameleon extension type.
     *
     * @return new Chameleon extension.
     * @throws ChameleonExtensionException if something goes wrong while loading the extension.
     */
    <T extends ChameleonExtension> @NotNull T loadExtension(@NotNull ChameleonExtensionFactory<T> factory) throws ChameleonExtensionException;

    /**
     * Get a loaded Chameleon extension.
     *
     * @param clazz Chameleon extension class.
     * @param <T>   Chameleon extension type.
     *
     * @return an optional containing the loaded Chameleon extension, if loaded, otherwise an empty
     *     optional.
     */
    <T extends ChameleonExtension> @NotNull Optional<T> getExtension(@NotNull Class<T> clazz);

    /**
     * Get all loaded Chameleon extensions.
     *
     * @return loaded Chameleon extensions.
     */
    @NotNull Collection<ChameleonExtension> getExtensions();

}
