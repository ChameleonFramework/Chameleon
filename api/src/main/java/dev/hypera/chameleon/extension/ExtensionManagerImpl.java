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

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.exception.extension.ChameleonExtensionException;
import dev.hypera.chameleon.util.Pair;
import dev.hypera.chameleon.util.Preconditions;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Extension manager implementation.
 *
 * @see ExtensionManager
 */
@Internal
public final class ExtensionManagerImpl implements ExtensionManager {

    private final @NotNull Chameleon chameleon;
    private final @NotNull ExtensionMap loadedExtensions;

    /**
     * Extension manager constructor.
     *
     * @param chameleon  Chameleon instance.
     * @param loadedExtensions Extensions.
     */
    @Internal
    public ExtensionManagerImpl(@NotNull Chameleon chameleon, @NotNull ExtensionMap loadedExtensions) {
        this.chameleon = chameleon;
        this.loadedExtensions = loadedExtensions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ChameleonExtension> @NotNull T loadExtension(@NotNull ChameleonExtensionFactory<T> factory) throws ChameleonExtensionException {
        Preconditions.checkNotNull("factory", factory);
        return getExtension(factory.getType()).orElseGet(() -> {
            // Check dependencies
            Collection<ChameleonExtensionDependency> dependencies = factory.getDependencies(this.chameleon.getPlatform().getId());
            Collection<ChameleonExtensionDependency> missingDependencies = dependencies.parallelStream()
                .filter(d -> d.required() && (d.extension().isEmpty() || getExtension(d.extension().get()).isEmpty()))
                .collect(Collectors.toSet());
            if (!missingDependencies.isEmpty()) {
                throw ChameleonExtensionException.create(
                    "%s requires dependencies but some are missing: %s",
                    factory.getType().getSimpleName(),
                    missingDependencies.parallelStream().map(ChameleonExtensionDependency::name)
                        .collect(Collectors.joining(", "))
                );
            }

            // Create the extension
            ChameleonPlatformExtension extension = factory.create(this.chameleon.getPlatform().getId());
            Preconditions.checkNotNullState("extension", extension);
            if (!factory.getType().isAssignableFrom(extension.getClass())) {
                throw ChameleonExtensionException.create(
                    "Cannot load %s: not assignable from %s",
                    factory.getType().getSimpleName(), extension.getClass().getSimpleName()
                );
            }

            // Initialise and load the extension
            extension.init(this.chameleon.getLogger(), this.chameleon.getEventBus());
            extension.load(this.chameleon);
            this.loadedExtensions.put(factory.getType(), Pair.of(extension, dependencies));
            return factory.getType().cast(extension);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T extends ChameleonExtension> @NotNull Optional<T> getExtension(@NotNull Class<T> clazz) {
        return this.loadedExtensions.getExtension(clazz);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<ChameleonExtension> getExtensions() {
        return this.loadedExtensions.entrySet()
            .parallelStream().map(e -> e.getKey().cast(e.getValue().first()))
            .collect(Collectors.toUnmodifiableSet());
    }

}
