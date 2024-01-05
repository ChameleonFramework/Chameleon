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
import java.util.Objects;
import net.kyori.adventure.title.TitlePart;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure TitlePart mapper.
 */
public final class TitlePartMapper implements Mapper<TitlePart<?>> {

    private @Nullable Object titlePartTitle;
    private @Nullable Object titlePartSubtitle;
    private @Nullable Object titlePartTimes;

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        Class<?> titlePartClass = Class.forName(AdventureMapper.ORIGINAL_TITLE_PART_CLASS_NAME);
        this.titlePartTitle = titlePartClass.getField("TITLE").get(null);
        this.titlePartSubtitle = titlePartClass.getField("SUBTITLE").get(null);
        this.titlePartTimes = titlePartClass.getField("TIMES").get(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.titlePartTitle != null &&
            this.titlePartSubtitle != null &&
            this.titlePartTimes != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull TitlePart<?> titlePart) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("titlePart", titlePart);
        if (titlePart.equals(TitlePart.TITLE)) {
            return Objects.requireNonNull(this.titlePartTitle);
        } else if (titlePart.equals(TitlePart.SUBTITLE)) {
            return Objects.requireNonNull(this.titlePartSubtitle);
        } else if (titlePart.equals(TitlePart.TIMES)) {
            return Objects.requireNonNull(this.titlePartTimes);
        } else {
            throw new IllegalArgumentException("unsupported TitlePart type");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull TitlePart<?> mapBackwards(@NotNull Object titlePart) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("titlePart", titlePart);
        if (titlePart.equals(this.titlePartTitle)) {
            return TitlePart.TITLE;
        } else if (titlePart.equals(this.titlePartSubtitle)) {
            return TitlePart.SUBTITLE;
        } else if (titlePart.equals(this.titlePartTimes)) {
            return TitlePart.TIMES;
        } else {
            throw new IllegalArgumentException("unsupported TitlePart type");
        }
    }

}
