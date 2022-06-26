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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;

/**
 * Configuration.
 */
public abstract class Configuration {

    private static final @NotNull String SEPARATOR = ".";

    protected final @NotNull Path dataFolder;
    protected final @NotNull String fileName;
    protected final @NotNull Path path;
    protected final boolean copyDefaultFromResources;
    protected boolean loaded = false;

    /**
     * {@link Configuration} constructor.
     *
     * @param dataFolder Folder to store loaded configuration file in.
     * @param fileName   Configuration file name.
     */
    public Configuration(@NotNull Path dataFolder, @NotNull String fileName) {
        this(dataFolder, fileName, true);
    }

    /**
     * {@link Configuration} constructor.
     *
     * @param dataFolder               Folder to store loaded configuration file in.
     * @param fileName                 Configuration file name.
     * @param copyDefaultFromResources Whether this file should be copied from resources if not already loaded.
     */
    public Configuration(@NotNull Path dataFolder, @NotNull String fileName, boolean copyDefaultFromResources) {
        this.dataFolder = dataFolder;
        this.fileName = fileName;
        this.path = dataFolder.resolve(fileName);
        this.copyDefaultFromResources = copyDefaultFromResources;
    }

    /**
     * Load this {@link Configuration}.
     *
     * @return {@code this}.
     * @throws IOException if something goes wrong while reading the file.
     */
    public abstract @NotNull Configuration load() throws IOException;

    /**
     * Reload this {@link Configuration}.
     *
     * @return {@code this}.
     * @throws IOException if something goes wrong while reading the file.
     */
    public @NotNull Configuration reload() throws IOException {
        return unload().load();
    }

    /**
     * Unload this {@link Configuration}.
     *
     * @return {@code this}.
     */
    public abstract @NotNull Configuration unload();


    /**
     * Get {@link Object} by path.
     *
     * @param path Configuration path.
     *
     * @return {@link Optional} containing the {@link Object} if found, otherwise empty.
     */
    public abstract @NotNull Optional<Object> get(@NotNull String path);

    /**
     * Get {@link Object} by path and cast it to the provided type.
     *
     * @param path Configuration path.
     * @param type Type to cast the found {@link Object} to.
     * @param <T>  Type.
     *
     * @return an {@link Optional} containing the cast {@link Object} if found and if the object is an instanceof {@code type}, otherwise empty.
     */
    public <T> @NotNull Optional<T> get(@NotNull String path, @NotNull Class<T> type) {
        Optional<Object> o = get(path);
        if (!o.isPresent() || !type.isInstance(o.get())) {
            return Optional.empty();
        }
        return Optional.of(type.cast(o.get()));
    }

    /**
     * Get the type of {@link Object} by path.
     *
     * @param path Configuration path.
     *
     * @return an {@link Optional} containing the {@link Object}'s type, if found, otherwise empty.
     */
    public @NotNull Optional<Class<?>> getType(@NotNull String path) {
        return get(path).map(Object::getClass);
    }

    /**
     * Check if an {@link Object} by path is an instance of the given type.
     *
     * @param path Configuration path.
     * @param type Type.
     *
     * @return {@code true} if the object was found and is an instance of {@code type}, otherwise {@code false}.
     */
    public boolean isType(@NotNull String path, @NotNull Class<?> type) {
        return get(path).filter(type::isInstance).isPresent();
    }

    /**
     * Get {@link String} by path.
     *
     * @param path Configuration path.
     *
     * @return an {@link Optional} containing the {@link String}, if found, otherwise empty.
     */
    public @NotNull Optional<String> getString(@NotNull String path) {
        return get(path).map(CastingUtil::asString);
    }

    /**
     * Get {@link Integer} by path.
     *
     * @param path Configuration path.
     *
     * @return an {@link Optional} containing the {@link Integer}, if found, otherwise empty.
     */
    public @NotNull Optional<Integer> getInt(@NotNull String path) {
        return get(path).map(CastingUtil::asInt);
    }

    /**
     * Get {@link Double} by path.
     *
     * @param path Configuration path.
     *
     * @return an {@link Optional} containing the {@link Double}, if found, otherwise empty.
     */
    public @NotNull Optional<Double> getDouble(@NotNull String path) {
        return get(path).map(CastingUtil::asDouble);
    }

    /**
     * Get {@link Long} by path.
     *
     * @param path Configuration path.
     *
     * @return an {@link Optional} containing the {@link Long}, if found, otherwise empty.
     */
    public @NotNull Optional<Long> getLong(@NotNull String path) {
        return get(path).map(CastingUtil::asLong);
    }

    /**
     * Get {@link Boolean} by path.
     *
     * @param path Configuration path.
     *
     * @return an {@link Optional} containing the {@link Boolean}, if found, otherwise empty.
     */
    public @NotNull Optional<Boolean> getBoolean(@NotNull String path) {
        return get(path).map(CastingUtil::asBoolean);
    }

    /**
     * Get {@link CastingList} by path.
     *
     * @param path Configuration path.
     *
     * @return an {@link Optional} containing the {@link CastingList}, if found, otherwise empty.
     */
    public @NotNull Optional<CastingList> getList(@NotNull String path) {
        return get(path).map(CastingUtil::asList);
    }

    /**
     * Get {@link CastingMap} by path.
     *
     * @param path Configuration path.
     *
     * @return an {@link Optional} containing the {@link CastingMap}, if found, otherwise empty.
     */
    public @NotNull Optional<CastingMap> getMap(@NotNull String path) {
        return get(path).map(CastingUtil::asMap);
    }

    /**
     * Get {@link Configuration} file {@link Path}.
     *
     * @return file {@link Path}.
     */
    public @NotNull Path getPath() {
        return this.path;
    }

    protected @NotNull Optional<Object> getObject(@NotNull String path, @NotNull Map<String, Object> map) {
        if (path.contains(SEPARATOR)) {
            List<String> parts = Arrays.asList(path.split("\\" + SEPARATOR));

            if (parts.size() < 2 || !(map.get(parts.get(0)) instanceof Map<?, ?>)) {
                return Optional.empty();
            }

            Map<?, ?> section = (Map<?, ?>) map.get(parts.get(0));
            Object output = null;

            for (int i = 1; i < parts.size(); i++) {
                if (null == section.get(parts.get(i))) {
                    break;
                }

                if (i == parts.size() - 1) {
                    output = section.get(parts.get(i));
                    break;
                }

                if (section.get(parts.get(i)) instanceof Map<?, ?>) {
                    section = (Map<?, ?>) section.get(parts.get(i));
                    continue;
                }

                break;
            }

            return Optional.ofNullable(output);
        } else {
            return Optional.ofNullable(map.get(path));
        }
    }

}
