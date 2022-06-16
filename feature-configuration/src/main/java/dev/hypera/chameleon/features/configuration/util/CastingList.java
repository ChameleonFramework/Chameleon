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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Casting {@link List} implementation.
 */
@SuppressWarnings("unused")
public class CastingList extends ArrayList<Object> {

    private static final long serialVersionUID = 273219730771590150L;

    /**
     * Optionally returns the element at the specified position in this list.
     *
     * @param index Index of the element to return.
     *
     * @return an {@link Optional} containing the element at the specified position in this list, if found, otherwise empty.
     */
    public @NotNull Optional<Object> getOptional(int index) {
        return Optional.ofNullable(get(index));
    }

    /**
     * Optionally returns the element type at the specified position in this list.
     *
     * @param index Index of the element to get the type of.
     *
     * @return an {@link Optional} containing the element type at the specified position in this list, if found, otherwise empty.
     */
    public @NotNull Optional<Class<?>> getType(int index) {
        return getOptional(index).map(Object::getClass);
    }

    /**
     * Check if the element type and the specified position in this list is instanceof the given type.
     *
     * @param index Index of the element.
     * @param type  Type to check if the element is instanceof.
     *
     * @return {@code true} if the element at the specified position in this list was found and is instanceof {@code type}, otherwise {@code false}.
     */
    public boolean isType(int index, @NotNull Class<?> type) {
        return getOptional(index).filter(type::isInstance).isPresent();
    }

    /**
     * Convert this List to a {@link List} containing only {@link String}s.
     *
     * @return {@link List} containing only {@link String}s.
     */
    public @NotNull List<String> asStringList() {
        return stream().map(CastingUtil::asString).collect(Collectors.toList());
    }

    /**
     * Convert this List to a {@link List} containing only {@link Integer}s.
     *
     * @return {@link List} containing only {@link Integer}s.
     */
    public @NotNull List<Integer> asIntegerList() {
        return stream().map(CastingUtil::asInt).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Convert this List to a {@link List} containing only {@link Double}s.
     *
     * @return {@link List} containing only {@link Double}s.
     */
    public @NotNull List<Double> asDoubleList() {
        return stream().map(CastingUtil::asDouble).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Convert this List to a {@link List} containing only {@link Long}s.
     *
     * @return {@link List} containing only {@link Long}s.
     */
    public @NotNull List<Long> asLongList() {
        return stream().map(CastingUtil::asLong).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Convert this List to a {@link List} containing only {@link Boolean}s.
     *
     * @return {@link List} containing only {@link Boolean}s.
     */
    public @NotNull List<Boolean> asBooleanList() {
        return stream().map(CastingUtil::asBoolean).filter(Objects::nonNull).collect(Collectors.toList());
    }

    /**
     * Optionally returns the element at the specified position in this list, cast to a {@link String}.
     *
     * @param index Index of the element to return.
     *
     * @return an {@link Optional} containing the element at the specified position in this list, if found and if it is an instanceof {@link String}, otherwise empty.
     */
    public @NotNull Optional<String> getString(int index) {
        return getOptional(index).map(CastingUtil::asString);
    }

    /**
     * Optionally returns the element at the specified position in this list, cast to an {@link Integer}.
     *
     * @param index Index of the element to return.
     *
     * @return an {@link Optional} containing the element at the specified position in this list, if found and if it is an instanceof {@link Integer}, otherwise empty.
     */
    public @NotNull Optional<Integer> getInt(int index) {
        return getOptional(index).map(CastingUtil::asInt);
    }

    /**
     * Optionally returns the element at the specified position in this list, cast to a {@link Double}.
     *
     * @param index Index of the element to return.
     *
     * @return an {@link Optional} containing the element at the specified position in this list, if found and if it is an instanceof {@link Double}, otherwise empty.
     */
    public @NotNull Optional<Double> getDouble(int index) {
        return getOptional(index).map(CastingUtil::asDouble);
    }

    /**
     * Optionally returns the element at the specified position in this list, cast to a {@link Long}.
     *
     * @param index Index of the element to return.
     *
     * @return an {@link Optional} containing the element at the specified position in this list, if found and if it is an instanceof {@link Long}, otherwise empty.
     */
    public @NotNull Optional<Long> getLong(int index) {
        return getOptional(index).map(CastingUtil::asLong);
    }

    /**
     * Optionally returns the element at the specified position in this list, cast to a {@link Boolean}.
     *
     * @param index Index of the element to return.
     *
     * @return an {@link Optional} containing the element at the specified position in this list, if found and if it is an instanceof {@link Boolean}, otherwise empty.
     */
    public @NotNull Optional<Boolean> getBoolean(int index) {
        return getOptional(index).map(CastingUtil::asBoolean);
    }

    /**
     * Optionally returns the element at the specified position in this list, cast to a {@link CastingList}.
     *
     * @param index Index of the element to return.
     *
     * @return an {@link Optional} containing the element at the specified position in this list, if found and if it is an instanceof {@link CastingList}, otherwise empty.
     */
    public @NotNull Optional<CastingList> getList(int index) {
        return getOptional(index).map(CastingUtil::asList);
    }

}
