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
package dev.hypera.chameleon.platform.objects;

import dev.hypera.chameleon.platform.Platform;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Platform} plugin.
 */
public interface PlatformPlugin {

    /**
     * Get {@link PlatformPlugin} name.
     *
     * @return {@link PlatformPlugin} name.
     */
    @NotNull String getName();

    /**
     * Get {@link PlatformPlugin} version.
     *
     * @return {@link PlatformPlugin} version.
     */
    @NotNull String getVersion();

    /**
     * Get {@link PlatformPlugin} description.
     *
     * @return optionally {@link PlatformPlugin} description.
     */
    @NotNull Optional<String> getDescription();

    /**
     * Get {@link PlatformPlugin} main class.
     *
     * @return {@link PlatformPlugin} main class.
     */
    @NotNull Class<?> getMainClass();

    /**
     * Get {@link PlatformPlugin} authors.
     *
     * @return {@link PlatformPlugin} authors.
     */
    @NotNull List<String> getAuthors();

    /**
     * Get {@link PlatformPlugin} required dependencies.
     *
     * @return {@link PlatformPlugin} required dependencies.
     */
    @NotNull Set<String> getDependencies();

    /**
     * Get {@link PlatformPlugin} optional dependencies.
     *
     * @return {@link PlatformPlugin} optional dependencies.
     */
    @NotNull Set<String> getSoftDependencies();

    /**
     * Get {@link PlatformPlugin} data folder.
     *
     * @return {@link PlatformPlugin} data folder.
     */
    @NotNull Path getDataFolder();

    /**
     * Attempt to enable the {@link PlatformPlugin}.
     * May not work on some platforms.
     */
    @Experimental
    void enable();

    /**
     * Attempt to disable the {@link PlatformPlugin}.
     * May not work on some platforms.
     */
    @Experimental
    void disable();

}
