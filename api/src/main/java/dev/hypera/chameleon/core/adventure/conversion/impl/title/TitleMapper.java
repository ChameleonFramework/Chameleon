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
package dev.hypera.chameleon.core.adventure.conversion.impl.title;

import dev.hypera.chameleon.core.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.core.adventure.conversion.IMapper;
import java.lang.reflect.Method;
import java.util.Objects;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

/**
 * Maps shaded to platform {@link Title}.
 */
public final class TitleMapper implements IMapper<Title> {

    private final @NotNull TimesMapper timesConverter = new TimesMapper();
    private final @NotNull Method createMethod;

    /**
     * {@link TimesMapper} constructor.
     */
    public TitleMapper() {
        try {
            Class<?> titleClass = Class.forName(AdventureConverter.PACKAGE + "title.Title");
            Class<?> timesClass = Class.forName(AdventureConverter.PACKAGE + "title.Title$Times");
            Class<?> componentClass = Class.forName(AdventureConverter.PACKAGE + "text.Component");
            this.createMethod = titleClass.getMethod("title", componentClass, componentClass, timesClass);
        } catch (ReflectiveOperationException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Map {@link Title} to the platform version of Adventure.
     *
     * @param title {@link Title} to be mapped.
     *
     * @return Platform instance of {@link Title}.
     */
    @Override
    public @NotNull Object map(@NotNull Title title) {
        try {
            return this.createMethod.invoke(null,
                AdventureConverter.convertComponent(title.title()),
                AdventureConverter.convertComponent(title.subtitle()),
                this.timesConverter.map(null == title.times() ? Title.DEFAULT_TIMES : Objects.requireNonNull(title.times()))
            );
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

}
