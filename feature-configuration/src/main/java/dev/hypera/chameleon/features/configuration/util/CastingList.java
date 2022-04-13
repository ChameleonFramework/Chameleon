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
package dev.hypera.chameleon.features.configuration.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;

@SuppressWarnings("unused")
public class CastingList extends ArrayList<Object> {

    private static final long serialVersionUID = 273219730771590150L;

    public @NotNull Optional<Object> getOptional(int index) {
        return Optional.ofNullable(get(index));
    }

    public @NotNull Optional<Class<?>> getType(int index) {
        return getOptional(index).map(Object::getClass);
    }
    public boolean isType(int index, @NotNull Class<?> type) {
        return getOptional(index).filter(type::isInstance).isPresent();
    }

    public @NotNull List<String> asStringList() {
        return stream().map(CastingUtil::asString).collect(Collectors.toList());
    }
    public @NotNull List<Integer> asIntegerList() {
        return stream().map(CastingUtil::asInt).filter(Objects::nonNull).collect(Collectors.toList());
    }
    public @NotNull List<Double> asDoubleList() {
        return stream().map(CastingUtil::asDouble).filter(Objects::nonNull).collect(Collectors.toList());
    }
    public @NotNull List<Long> asLongList() {
        return stream().map(CastingUtil::asLong).filter(Objects::nonNull).collect(Collectors.toList());
    }
    public @NotNull List<Boolean> asBooleanList() {
        return stream().map(CastingUtil::asBoolean).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public @NotNull Optional<String> getString(int index) {
        return getOptional(index).map(CastingUtil::asString);
    }
    public @NotNull Optional<Integer> getInt(int index) {
        return getOptional(index).map(CastingUtil::asInt);
    }
    public @NotNull Optional<Double> getDouble(int index) {
        return getOptional(index).map(CastingUtil::asDouble);
    }
    public @NotNull Optional<Long> getLong(int index) {
        return getOptional(index).map(CastingUtil::asLong);
    }
    public @NotNull Optional<Boolean> getBoolean(int index) {
        return getOptional(index).map(CastingUtil::asBoolean);
    }
    public @NotNull Optional<CastingList> getList(int index) {
        return getOptional(index).map(CastingUtil::asList);
    }

}
