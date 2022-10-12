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
package dev.hypera.chameleon.adventure.conversion.mapping;

import dev.hypera.chameleon.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.exceptions.ChameleonRuntimeException;
import java.lang.reflect.Method;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;

/**
 * Maps shaded to platform {@link Sound}.
 */
public final class SoundMapper implements Mapper<Sound> {

    private final @NotNull Method createMethod;
    private final @NotNull Method sourceValueOf;

    /**
     * {@link SoundMapper} constructor.
     */
    public SoundMapper() {
        try {
            Class<?> soundClass = Class.forName(AdventureConverter.PACKAGE + "sound.Sound");
            Class<?> keyClass = Class.forName(AdventureConverter.PACKAGE + "key.Key");
            Class<?> sourceClass = Class.forName(soundClass.getCanonicalName() + "$Source");
            this.createMethod = soundClass.getMethod("sound", keyClass, sourceClass, float.class, float.class);
            this.sourceValueOf = sourceClass.getMethod("valueOf", String.class);
        } catch (ReflectiveOperationException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Map {@link Sound} to the platform version of Adventure.
     *
     * @param sound {@link Sound} to be mapped.
     *
     * @return Platform instance of {@link Sound}.
     */
    @Override
    public @NotNull Object map(@NotNull Sound sound) {
        try {
            return this.createMethod.invoke(null, AdventureConverter.convertKey(sound.name()), this.sourceValueOf.invoke(null, sound.source().name()), sound.volume(), sound.pitch());
        } catch (ReflectiveOperationException ex) {
            throw new ChameleonRuntimeException(ex);
        }
    }

}
