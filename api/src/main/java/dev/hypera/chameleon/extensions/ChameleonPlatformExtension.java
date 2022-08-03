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
package dev.hypera.chameleon.extensions;

import java.lang.reflect.InvocationTargetException;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon platform extension.
 *
 * @param <T> {@link ChameleonExtension} type.
 * @param <C> {@link CustomPlatformExtension} type.
 */
@NonExtendable
public abstract class ChameleonPlatformExtension<T extends ChameleonExtension<C>, C extends CustomPlatformExtension> {

    protected final @NotNull T extension;

    /**
     * {@link ChameleonPlatformExtension} constructor.
     */
    @SuppressWarnings("unchecked")
    public ChameleonPlatformExtension() {
        try {
            if (!ReflectionUtils.getGenericTypeAsClass(getClass(), 1).isAssignableFrom(getClass())) {
                throw new IllegalStateException("must implement C"); //TODO: better error message
            }

            this.extension = (T) ReflectionUtils.getGenericTypeAsClass(getClass(), 0)
                .getConstructor(ReflectionUtils.getGenericTypeAsClass(getClass(), 1))
                .newInstance(this);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new RuntimeException(ex);
        }
    }

}
