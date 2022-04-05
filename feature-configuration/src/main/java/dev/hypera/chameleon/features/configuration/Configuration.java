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
package dev.hypera.chameleon.features.configuration;

import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Configuration
 */
public interface Configuration {

	@NotNull Optional<Object> get(@NotNull String path);
	default <T> @NotNull Optional<T> get(@NotNull String path, @NotNull Class<T> type) {
		Optional<Object> o = get(path);
		if (!o.isPresent() || type.isInstance(o.get())) return Optional.empty();
		return Optional.of(type.cast(o.get()));
	}

	default @NotNull Optional<Class<?>> getType(@NotNull String path) {
		return get(path).map(Object::getClass);
	}
	default boolean isType(@NotNull String path, @NotNull Class<?> type) {
		return get(path).filter(type::isInstance).isPresent();
	}

	default @NotNull Optional<String> getString(@NotNull String path) {
		return get(path).map(o -> (String) o);
	}
	default @NotNull Optional<Integer> getInt(@NotNull String path) {
		return get(path).map(o -> {
			if (o instanceof Long) return ((Long) o).intValue();
			return (Integer) o;
		});
	}
	default @NotNull Optional<Double> getDouble(@NotNull String path) {
		return get(path).map(o -> (Double) o);
	}
	default @NotNull Optional<Long> getLong(@NotNull String path) {
		return get(path).map(o -> (Long) o);
	}
	default @NotNull Optional<Boolean> getBoolean(@NotNull String path) {
		return get(path).map(o -> (Boolean) o);
	}
	default @NotNull Optional<List<?>> getList(@NotNull String path) {
		return get(path).map(o -> (List<?>) o);
	}

	@NotNull Path getPath();

}
