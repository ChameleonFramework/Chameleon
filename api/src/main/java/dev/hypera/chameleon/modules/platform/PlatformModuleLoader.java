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
package dev.hypera.chameleon.modules.platform;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.platform.Platform;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * {@link PlatformModule} loader.
 */
public class PlatformModuleLoader {

    private final @NotNull Chameleon chameleon;

    /**
     * {@link PlatformModuleLoader} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     */
    @Internal
    public PlatformModuleLoader(@NotNull Chameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * Load {@link PlatformModule}s.
     *
     * @param modules {@link PlatformModule}s to be loaded.
     *
     * @return Loaded {@link PlatformModule} for the current {@link Platform}.
     */
    @SafeVarargs
    public final @NotNull Optional<PlatformModule<?>> load(@NotNull Class<PlatformModule<?>>... modules) {
        for (Class<PlatformModule<?>> module : modules) {
            try {
                for (Constructor<?> constructor : module.getDeclaredConstructors()) {
                    if (constructor.getParameterCount() == 1 && this.chameleon.getClass().isAssignableFrom(constructor.getParameterTypes()[0])) {
                        return Optional.of(module.cast(constructor.newInstance(constructor.getParameterTypes()[0].cast(this.chameleon))));
                    }
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                throw new IllegalStateException(ex);
            }
        }

        return Optional.empty();
    }

}
