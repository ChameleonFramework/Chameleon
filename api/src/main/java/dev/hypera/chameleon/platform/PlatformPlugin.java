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
package dev.hypera.chameleon.platform;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a plugin running on the platform.
 */
@NonExtendable
public interface PlatformPlugin {

    /**
     * Get the name of this plugin.
     *
     * @return name.
     */
    @NotNull String getName();

    /**
     * Get the version of this plugin.
     *
     * @return version.
     */
    @NotNull String getVersion();

    /**
     * Get the description of this plugin.
     *
     * @return an {@code Optional} containing the plugin description, if available, otherwise an
     *     empty optional.
     */
    @NotNull Optional<String> getDescription();

    /**
     * Get the main class of this plugin.
     *
     * @return main class.
     */
    @NotNull Class<?> getMainClass();

    /**
     * Get the authors of this plugin.
     *
     * @return authors.
     */
    @NotNull Collection<String> getAuthors();

    /**
     * Get the required dependencies of this plugin.
     *
     * @return required dependencies.
     */
    @NotNull Collection<String> getDependencies();

    /**
     * Get the optional dependencies of this plugin.
     *
     * @return optional dependencies.
     */
    @NotNull Collection<String> getSoftDependencies();

    /**
     * Get the data folder of this plugin.
     *
     * @return data folder.
     */
    @NotNull Path getDataFolder();

    /**
     * Attempt to enable this plugin.
     * <p>This is not guaranteed to work and is currently experimental. Use at your own risk!</p>
     */
    @Experimental
    void enable();

    /**
     * Attempt to disable this plugin.
     * <p>This is not guaranteed to work and is currently experimental. Use at your own risk!</p>
     */
    @Experimental
    void disable();

}
