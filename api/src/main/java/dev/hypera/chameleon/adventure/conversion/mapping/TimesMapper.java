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
package dev.hypera.chameleon.adventure.conversion.mapping;

import dev.hypera.chameleon.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.exceptions.reflection.ChameleonReflectiveException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Arrays;
import net.kyori.adventure.title.Title.Times;
import org.jetbrains.annotations.NotNull;

/**
 * Maps shaded to platform {@link Times}.
 */
public final class TimesMapper implements Mapper<Times> {

    private final @NotNull Method createMethod;

    /**
     * {@link TimesMapper} constructor.
     */
    public TimesMapper() {
        try {
            Class<?> timesClass = Class.forName(AdventureConverter.PACKAGE + "title.Title$Times");
            if (Arrays.stream(timesClass.getMethods()).anyMatch(m -> m.getName().equals("times"))) {
                this.createMethod = timesClass.getMethod("times", Duration.class, Duration.class, Duration.class);
            } else {
                this.createMethod = timesClass.getMethod("of", Duration.class, Duration.class, Duration.class);
            }
        } catch (ReflectiveOperationException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Map {@link Times} to the platform version of Adventure.
     *
     * @param times {@link Times} to be mapped.
     *
     * @return Platform instance of {@link Times}.
     */
    @Override
    public @NotNull Object map(@NotNull Times times) {
        try {
            return this.createMethod.invoke(null, times.fadeIn(), times.stay(), times.fadeOut());
        } catch (ReflectiveOperationException ex) {
            throw new ChameleonReflectiveException("Failed to map Times object to platform object", ex);
        }
    }

}
