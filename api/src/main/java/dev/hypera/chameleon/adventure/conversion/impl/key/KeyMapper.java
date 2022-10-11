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
package dev.hypera.chameleon.adventure.conversion.impl.key;

import dev.hypera.chameleon.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.adventure.conversion.IMapper;
import dev.hypera.chameleon.exceptions.ChameleonRuntimeException;
import java.lang.reflect.Method;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

/**
 * Maps shaded to platform {@link Key}.
 */
public final class KeyMapper implements IMapper<Key> {

    private final @NotNull Method createMethod;

    /**
     * {@link KeyMapper} constructor.
     */
    public KeyMapper() {
        try {
            Class<?> keyClass = Class.forName(AdventureConverter.PACKAGE + "key.Key");
            this.createMethod = keyClass.getMethod("key", String.class);
        } catch (ReflectiveOperationException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Map {@link Key} to the platform version of Adventure.
     *
     * @param key {@link Key} to be mapped.
     *
     * @return Platform instance of {@link Key}.
     */
    @Override
    public @NotNull Object map(@NotNull Key key) {
        try {
            return this.createMethod.invoke(null, key.asString());
        } catch (ReflectiveOperationException ex) {
            throw new ChameleonRuntimeException(ex);
        }
    }

}
