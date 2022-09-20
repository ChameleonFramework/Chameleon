/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.extensions;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.utils.ChameleonUtil;
import java.lang.reflect.InvocationTargetException;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon platform extension.
 *
 * @param <T> {@link ChameleonExtension} type.
 * @param <E> {@link CustomPlatformExtension} type.
 * @param <C> {@link Chameleon} implementation type.
 */
@NonExtendable
public abstract class ChameleonPlatformExtension<T extends ChameleonExtension<E>, E extends CustomPlatformExtension, C extends Chameleon> {

    protected final @NotNull T extension;

    /**
     * {@link ChameleonPlatformExtension} constructor.
     */
    @SuppressWarnings("unchecked")
    public ChameleonPlatformExtension() {
        try {
            Class<E> customExtension = (Class<E>) ChameleonUtil.getGenericTypeAsClass(getClass(), 1);
            if (!customExtension.isAssignableFrom(getClass())) {
                throw new IllegalStateException("ChameleonPlatformExtension must implement the used CustomPlatformExtension");
            }

            this.extension = (T) ChameleonUtil.getGenericTypeAsClass(getClass(), 0)
                .getConstructor(customExtension)
                .newInstance(customExtension.cast(this));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException(ex);
        }
    }

    /**
     * Called after Chameleon has loaded.
     *
     * @param chameleon Initialised {@link Chameleon} instance.
     */
    public void onLoad(@NotNull C chameleon) {

    }

    /**
     * Get {@link ChameleonExtension} instance.
     *
     * @return {@link ChameleonExtension} instance.
     */
    public final @NotNull T getExtension() {
        return this.extension;
    }

}
