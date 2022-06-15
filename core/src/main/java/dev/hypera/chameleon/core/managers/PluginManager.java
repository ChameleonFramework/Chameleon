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
package dev.hypera.chameleon.core.managers;

import dev.hypera.chameleon.core.platform.objects.PlatformPlugin;
import java.util.Optional;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

/**
 * {@link dev.hypera.chameleon.core.platform.Platform} plugin manager.
 */
public abstract class PluginManager {

    /**
     * Get {@link PlatformPlugin}s.
     *
     * @return set of {@link PlatformPlugin}s.
     */
    public abstract @NotNull Set<PlatformPlugin> getPlugins();

    /**
     * Attempt to find {@link PlatformPlugin} by name.
     *
     * @param name The name to search for.
     *
     * @return {@link Optional} containing the {@link PlatformPlugin} if found, otherwise empty.
     */
    public abstract @NotNull Optional<PlatformPlugin> getPlugin(@NotNull String name);

    /**
     * Check if a {@link PlatformPlugin} is enabled, by name.
     *
     * @param name The name to search for.
     *
     * @return {@code true} if the {@link PlatformPlugin} was found and if it's enabled, otherwise false.
     */
    public abstract boolean isPluginEnabled(@NotNull String name);

}
