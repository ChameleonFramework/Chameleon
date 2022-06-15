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

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class CastingMap extends LinkedHashMap<Object, Object> {

    private static final long serialVersionUID = -1754140207986794772L;

    public @NotNull Optional<Object> getOptional(@NotNull Object key) {
        return Optional.ofNullable(get(key));
    }

    public @NotNull Optional<Class<?>> getType(@NotNull Object key) {
        return getOptional(key).map(Object::getClass);
    }

    public boolean isType(@NotNull Object key, @NotNull Class<?> type) {
        return getOptional(key).filter(type::isInstance).isPresent();
    }


    public @NotNull Map<String, Object> asStringObjectMap() {
        return entrySet().stream().collect(Collectors.toMap(e -> CastingUtil.asString(e.getKey()), Entry::getValue));
    }

    public @NotNull Map<String, String> asStringMap() {
        return entrySet().stream().collect(Collectors.toMap(e -> CastingUtil.asString(e.getKey()), e -> CastingUtil.asString(e.getValue())));
    }

    public @NotNull Map<Integer, Object> asIntegerObjectMap() {
        return entrySet().stream().map(e -> new SimpleEntry<>(CastingUtil.asInt(e.getKey()), e.getValue())).filter(e -> null != e.getKey() && null != e.getValue()).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    public @NotNull Map<Integer, Integer> asIntegerMap() {
        return entrySet().stream()
            .map(e -> new SimpleEntry<>(CastingUtil.asInt(e.getKey()), CastingUtil.asInt(e.getValue())))
            .filter(e -> null != e.getKey() && null != e.getValue())
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    public @NotNull Map<Double, Object> asDoubleObjectMap() {
        return entrySet().stream().map(e -> new SimpleEntry<>(CastingUtil.asDouble(e.getKey()), e.getValue())).filter(e -> null != e.getKey() && null != e.getValue()).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    public @NotNull Map<Double, Double> asDoubleMap() {
        return entrySet().stream()
            .map(e -> new SimpleEntry<>(CastingUtil.asDouble(e.getKey()), CastingUtil.asDouble(e.getValue())))
            .filter(e -> null != e.getKey() && null != e.getValue())
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    public @NotNull Map<Long, Object> asLongObjectMap() {
        return entrySet().stream().map(e -> new SimpleEntry<>(CastingUtil.asLong(e.getKey()), e.getValue())).filter(e -> null != e.getKey() && null != e.getValue()).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    public @NotNull Map<Long, Long> asLongMap() {
        return entrySet().stream()
            .map(e -> new SimpleEntry<>(CastingUtil.asLong(e.getKey()), CastingUtil.asLong(e.getValue())))
            .filter(e -> null != e.getKey() && null != e.getValue())
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    public @NotNull Map<Boolean, Object> asBooleanObjectMap() {
        return entrySet().stream().map(e -> new SimpleEntry<>(CastingUtil.asBoolean(e.getKey()), e.getValue())).filter(e -> null != e.getKey() && null != e.getValue()).collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }

    public @NotNull Map<Boolean, Boolean> asBooleanMap() {
        return entrySet().stream()
            .map(e -> new SimpleEntry<>(CastingUtil.asBoolean(e.getKey()), CastingUtil.asBoolean(e.getValue())))
            .filter(e -> null != e.getKey() && null != e.getValue())
            .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue));
    }


    public @NotNull Optional<String> getString(@NotNull Object key) {
        return getOptional(key).map(CastingUtil::asString);
    }

    public @NotNull Optional<Integer> getInt(@NotNull Object key) {
        return getOptional(key).map(CastingUtil::asInt);
    }

    public @NotNull Optional<Double> getDouble(@NotNull Object key) {
        return getOptional(key).map(CastingUtil::asDouble);
    }

    public @NotNull Optional<Long> getLong(@NotNull Object key) {
        return getOptional(key).map(CastingUtil::asLong);
    }

    public @NotNull Optional<Boolean> getBoolean(@NotNull Object key) {
        return getOptional(key).map(CastingUtil::asBoolean);
    }

    public @NotNull Optional<CastingList> getList(@NotNull Object key) {
        return getOptional(key).map(CastingUtil::asList);
    }

}
