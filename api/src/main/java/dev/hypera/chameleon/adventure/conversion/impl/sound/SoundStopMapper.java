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
package dev.hypera.chameleon.adventure.conversion.impl.sound;

import dev.hypera.chameleon.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.adventure.conversion.IMapper;
import java.lang.reflect.Method;
import java.util.Objects;
import net.kyori.adventure.sound.SoundStop;
import org.jetbrains.annotations.NotNull;

/**
 * Maps shaded to platform {@link SoundStop}.
 */
public final class SoundStopMapper implements IMapper<SoundStop> {

    private final @NotNull Method allMethod;
    private final @NotNull Method createMethod;

    /**
     * {@link SoundStopMapper} constructor.
     */
    public SoundStopMapper() {
        try {
            Class<?> soundStopClass = Class.forName(AdventureConverter.PACKAGE + "sound.SoundStop");
            Class<?> keyClass = Class.forName(AdventureConverter.PACKAGE + "key.Key");
            this.allMethod = soundStopClass.getMethod("all");
            this.createMethod = soundStopClass.getMethod("named", keyClass);
        } catch (ReflectiveOperationException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Map {@link SoundStop} to the platform version of Adventure.
     *
     * @param soundStop {@link SoundStop} to be mapped.
     *
     * @return Platform instance of {@link SoundStop}.
     */
    @Override
    public @NotNull Object map(@NotNull SoundStop soundStop) {
        try {
            return null == soundStop.sound() ? this.allMethod.invoke(null) : this.createMethod.invoke(null, AdventureConverter.convertKey(Objects.requireNonNull(soundStop.sound())));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

}
