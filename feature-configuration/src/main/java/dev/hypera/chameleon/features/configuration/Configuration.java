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

import dev.hypera.chameleon.features.configuration.util.CastingList;
import dev.hypera.chameleon.features.configuration.util.CastingMap;
import dev.hypera.chameleon.features.configuration.util.CastingUtil;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Configuration
 */
public abstract class Configuration {

	protected final @NotNull Path dataFolder;
	protected final @NotNull String fileName;
	protected final @NotNull Path path;
	protected final boolean copyDefaultFromResources;
	protected boolean loaded = false;

	public Configuration(@NotNull Path dataFolder, @NotNull String fileName) {
		this(dataFolder, fileName, true);
	}

	public Configuration(@NotNull Path dataFolder, @NotNull String fileName, boolean copyDefaultFromResources) {
		this.dataFolder = dataFolder;
		this.fileName = fileName;
		this.path = dataFolder.resolve(fileName);
		this.copyDefaultFromResources = copyDefaultFromResources;
	}

	public abstract @NotNull Configuration load() throws IOException;
	public @NotNull Configuration reload() throws IOException {
		return unload().load();
	}
	public abstract @NotNull Configuration unload();

	public abstract @NotNull Optional<Object> get(@NotNull String path);
	public <T> @NotNull Optional<T> get(@NotNull String path, @NotNull Class<T> type) {
		Optional<Object> o = get(path);
		if (!o.isPresent() || type.isInstance(o.get())) return Optional.empty();
		return Optional.of(type.cast(o.get()));
	}

	public @NotNull Optional<Class<?>> getType(@NotNull String path) {
		return get(path).map(Object::getClass);
	}
	public boolean isType(@NotNull String path, @NotNull Class<?> type) {
		return get(path).filter(type::isInstance).isPresent();
	}

	public @NotNull Optional<String> getString(@NotNull String path) {
		return get(path).map(CastingUtil::asString);
	}
	public @NotNull Optional<Integer> getInt(@NotNull String path) {
		return get(path).map(CastingUtil::asInt);
	}
	public @NotNull Optional<Double> getDouble(@NotNull String path) {
		return get(path).map(CastingUtil::asDouble);
	}
	public @NotNull Optional<Long> getLong(@NotNull String path) {
		return get(path).map(CastingUtil::asLong);
	}
	public @NotNull Optional<Boolean> getBoolean(@NotNull String path) {
		return get(path).map(CastingUtil::asBoolean);
	}
	public @NotNull Optional<CastingList> getList(@NotNull String path) {
		return get(path).map(CastingUtil::asList);
	}
	public @NotNull Optional<CastingMap> getMap(@NotNull String path) {
		return get(path).map(CastingUtil::asMap);
	}

	public @NotNull Path getPath() {
		return path;
	}

}
