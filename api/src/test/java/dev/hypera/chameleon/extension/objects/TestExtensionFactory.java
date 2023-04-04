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
package dev.hypera.chameleon.extension.objects;

import dev.hypera.chameleon.exception.extension.ChameleonExtensionException;
import dev.hypera.chameleon.extension.ChameleonExtension;
import dev.hypera.chameleon.extension.ChameleonExtensionDependency;
import dev.hypera.chameleon.extension.ChameleonExtensionFactory;
import dev.hypera.chameleon.extension.ChameleonPlatformExtension;
import java.util.Collection;
import org.jetbrains.annotations.NotNull;

public class TestExtensionFactory<T extends ChameleonExtension> implements ChameleonExtensionFactory<T> {

    private final @NotNull ChameleonPlatformExtension extension;
    private final @NotNull Collection<ChameleonExtensionDependency> dependencies;
    private final @NotNull Class<T> type;

    public TestExtensionFactory(@NotNull ChameleonPlatformExtension extension, @NotNull Collection<ChameleonExtensionDependency> dependencies, @NotNull Class<T> type) {
        this.extension = extension;
        this.dependencies = dependencies;
        this.type = type;
    }

    @Override
    public @NotNull ChameleonPlatformExtension create(@NotNull String platformId) throws ChameleonExtensionException {
        return this.extension;
    }

    @Override
    public @NotNull Collection<ChameleonExtensionDependency> getDependencies(@NotNull String platformId) {
        return this.dependencies;
    }

    @Override
    public @NotNull Class<T> getType() {
        return this.type;
    }

}
