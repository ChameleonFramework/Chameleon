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
package dev.hypera.chameleon.platforms.minestom.platform.objects;

import dev.hypera.chameleon.platform.objects.PlatformPlugin;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom {@link PlatformPlugin} implementation.
 */
@Internal
public class MinestomPlugin implements PlatformPlugin {

    private final @NotNull Extension extension;

    /**
     * {@link MinestomPlugin} constructor.
     *
     * @param extension {@link Extension} instance.
     */
    @Internal
    public MinestomPlugin(@NotNull Extension extension) {
        this.extension = extension;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.extension.getOrigin().getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getVersion() {
        return this.extension.getOrigin().getVersion();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<String> getDescription() {
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Class<?> getMainClass() {
        return this.extension.getClass();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> getAuthors() {
        return Arrays.asList(this.extension.getOrigin().getAuthors());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> getDependencies() {
        return new HashSet<>(Arrays.asList(this.extension.getOrigin().getDependencies()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<String> getSoftDependencies() {
        return Collections.emptySet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Path getDataFolder() {
        return this.extension.getDataDirectory();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enable() {
        throw new UnsupportedOperationException("Minestom extensions cannot be enabled");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disable() {
        throw new UnsupportedOperationException("Minestom extensions cannot be disabled");
    }

}
