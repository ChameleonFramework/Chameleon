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
import java.util.Collections;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon extension factory.
 *
 * @param <T> Chameleon extension type.
 */
public interface ChameleonExtensionFactory<T extends ChameleonExtension> {

    /**
     * Create an extension instance for the given platform.
     * <p>Note that the returned ChameleonPlatformExtension <strong>must</strong> implement
     * {@code T}.</p>
     *
     * @param platformId Platform identifier to create the extension for. This identifier can be
     *                   compared against known platform identifiers stored as constants in
     *                   {@link dev.hypera.chameleon.platform.Platform}.
     *
     * @return new extension instance.
     * @throws ChameleonExtensionException if something goes wrong while creating the extension.
     */
    @NotNull ChameleonPlatformExtension create(@NotNull String platformId) throws ChameleonExtensionException;

    /**
     * Returns the dependencies this extension requires on the given platform.
     *
     * @param platformId Platform identifier.
     *
     * @return collection of dependencies.
     */
    default @NotNull Collection<ChameleonExtensionDependency> getDependencies(@NotNull String platformId) {
        return Collections.emptySet();
    }

    /**
     * Returns the class of the Chameleon extension implementation that this factory supports.
     *
     * @return Chameleon extension class.
     */
    @NotNull Class<T> getType();

}
