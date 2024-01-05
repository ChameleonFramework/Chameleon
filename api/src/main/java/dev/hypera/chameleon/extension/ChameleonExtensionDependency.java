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
package dev.hypera.chameleon.extension;

import dev.hypera.chameleon.util.Preconditions;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon extension dependency.
 */
@NonExtendable
public interface ChameleonExtensionDependency {

    /**
     * Create a new required extension dependency.
     *
     * @param extension Extension class.
     *
     * @return new dependency.
     */
    @Contract("_ -> new")
    static @NotNull ChameleonExtensionDependency required(@NotNull Class<? extends ChameleonExtension> extension) {
        Preconditions.checkNotNull("extension", extension);
        return new ChameleonExtensionDependencyImpl(extension.getSimpleName(), extension, true);
    }

    /**
     * Create a new required extension dependency with the extension name.
     *
     * @param name      Extension name.
     * @param extension Extension class.
     *
     * @return new dependency.
     */
    @Contract("_, _ -> new")
    static @NotNull ChameleonExtensionDependency required(@NotNull String name, @NotNull Class<? extends ChameleonExtension> extension) {
        Preconditions.checkNotNull("name", name);
        Preconditions.checkNotNull("extension", extension);
        return new ChameleonExtensionDependencyImpl(name, extension, true);
    }

    /**
     * Create a new required extension dependency.
     *
     * @param extension Extension class name.
     *
     * @return new dependency.
     */
    @Contract("_ -> new")
    static @NotNull ChameleonExtensionDependency required(@NotNull String extension) {
        Preconditions.checkNotNull("extension", extension);
        return required(extension, extension);
    }

    /**
     * Create a new required extension dependency with the extension name.
     *
     * @param name      Extension name.
     * @param extension Extension class name.
     *
     * @return new dependency.
     */
    @Contract("_, _ -> new")
    static @NotNull ChameleonExtensionDependency required(@NotNull String name, @NotNull String extension) {
        Preconditions.checkNotNull("name", name);
        Preconditions.checkNotNull("extension", extension);
        return new ChameleonExtensionDependencyImpl(name, extension, true);
    }

    /**
     * Returns whether this dependency is a required for loading the extension.
     *
     * @return required.
     */
    boolean required();

    /**
     * Create a new optional extension dependency.
     *
     * @param extension Extension class.
     *
     * @return new dependency.
     */
    @Contract("_ -> new")
    static @NotNull ChameleonExtensionDependency optional(@NotNull Class<? extends ChameleonExtension> extension) {
        Preconditions.checkNotNull("extension", extension);
        return optional(extension.getSimpleName(), extension);
    }

    /**
     * Create a new optional extension dependency with the extension name.
     *
     * @param name      Extension name.
     * @param extension Extension class.
     *
     * @return new dependency.
     */
    @Contract("_, _ -> new")
    static @NotNull ChameleonExtensionDependency optional(@NotNull String name, @NotNull Class<? extends ChameleonExtension> extension) {
        Preconditions.checkNotNull("name", name);
        Preconditions.checkNotNull("extension", extension);
        return new ChameleonExtensionDependencyImpl(name, extension, false);
    }

    /**
     * Create a new optional extension dependency.
     *
     * @param extension Extension class name.
     *
     * @return new dependency.
     */
    @Contract("_ -> new")
    static @NotNull ChameleonExtensionDependency optional(@NotNull String extension) {
        Preconditions.checkNotNull("extension", extension);
        return optional(extension, extension);
    }

    /**
     * Create a new optional extension dependency with the extension name.
     *
     * @param name      Extension name.
     * @param extension Extension class name.
     *
     * @return new dependency.
     */
    @Contract("_, _ -> new")
    static @NotNull ChameleonExtensionDependency optional(@NotNull String name, @NotNull String extension) {
        Preconditions.checkNotNull("name", name);
        Preconditions.checkNotNull("extension", extension);
        return new ChameleonExtensionDependencyImpl(name, extension, false);
    }

    /**
     * Returns whether this dependency is optional for loading the extension.
     *
     * @return optional.
     */
    default boolean optional() {
        return !required();
    }

    /**
     * Returns the name of the extension this dependency is for.
     *
     * @return extension name.
     */
    @NotNull String name();

    /**
     * Returns the extension class this dependency is for.
     *
     * @return an optional containing the extension class, if found, otherwise an empty optional.
     */
    @NotNull Optional<Class<? extends ChameleonExtension>> extension();

}
