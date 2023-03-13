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

import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ChameleonExtensionDependencyImpl implements ChameleonExtensionDependency {

    private final @NotNull String name;
    private final boolean required;
    private @Nullable String className;
    private @Nullable Class<? extends ChameleonExtension> extension;

    ChameleonExtensionDependencyImpl(@NotNull String name, @NotNull Class<? extends ChameleonExtension> clazz, boolean required) {
        this.name = name;
        this.required = required;
        this.extension = clazz;
    }

    ChameleonExtensionDependencyImpl(@NotNull String name, @NotNull String className, boolean required) {
        this.name = name;
        this.required = required;
        this.className = className;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String name() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Optional<Class<? extends ChameleonExtension>> extension() {
        if (this.extension != null) {
            return Optional.of(this.extension);
        }

        try {
            this.extension = (Class<? extends ChameleonExtension>) Class.forName(this.className);
            return Optional.of(this.extension);
        } catch (ClassNotFoundException ex) {
            return Optional.empty();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean required() {
        return this.required;
    }

}
