/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.adventure.mapper;

import dev.hypera.chameleon.util.Preconditions;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Objects;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure Times mapper.
 */
public final class TimesMapper implements Mapper<Title.Times> {

    private @Nullable Method timesCreateMethod;
    private @Nullable Method timesFadeInMethod;
    private @Nullable Method timesStayMethod;
    private @Nullable Method timesFadeOutMethod;

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        Class<?> timesClass = Class.forName(AdventureMapper.ORIGINAL_TITLE_TIMES_CLASS_NAME);
        this.timesCreateMethod = timesClass.getMethod(
            "times", Duration.class, Duration.class, Duration.class
        );
        this.timesFadeInMethod = timesClass.getMethod("fadeIn");
        this.timesStayMethod = timesClass.getMethod("stay");
        this.timesFadeOutMethod = timesClass.getMethod("fadeOut");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.timesCreateMethod != null && this.timesFadeInMethod != null &&
            this.timesStayMethod != null && this.timesFadeOutMethod != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull Title.Times times) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("times", times);
        return Objects.requireNonNull(this.timesCreateMethod)
            .invoke(null, times.fadeIn(), times.stay(), times.fadeOut());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Title.Times mapBackwards(@NotNull Object times) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("times", times);
        return Title.Times.times(
            (Duration) Objects.requireNonNull(this.timesFadeInMethod).invoke(times),
            (Duration) Objects.requireNonNull(this.timesStayMethod).invoke(times),
            (Duration) Objects.requireNonNull(this.timesFadeOutMethod).invoke(times)
        );
    }

}
