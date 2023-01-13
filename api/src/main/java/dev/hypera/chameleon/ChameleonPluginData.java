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
package dev.hypera.chameleon;

import dev.hypera.chameleon.util.Preconditions;
import java.util.Collection;
import java.util.Optional;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon plugin data.
 */
public interface ChameleonPluginData {

    /**
     * Create a new Chameleon plugin data instance with only a name and version.
     *
     * @param name    Plugin name.
     * @param version Plugin version.
     *
     * @return new Chameleon plugin data.
     */
    static @NotNull ChameleonPluginData create(@NotNull String name, @NotNull String version) {
        return ChameleonPluginData.builder(name, version).build();
    }

    /**
     * Create a new Chameleon plugin data builder.
     *
     * @param name    Plugin name.
     * @param version Plugin version.
     *
     * @return new Chameleon plugin data builder.
     */
    static @NotNull Builder builder(@NotNull String name, @NotNull String version) {
        Preconditions.checkNotNull("name", name);
        Preconditions.checkNotNull("version", version);
        return new ChameleonPluginDataImpl.BuilderImpl(name, version);
    }

    /**
     * Get name of this plugin.
     *
     * @return plugin name.
     */
    @NotNull String getName();

    /**
     * Get the version of this plugin.
     *
     * @return plugin version.
     */
    @NotNull String getVersion();

    /**
     * Get the description for this plugin.
     *
     * @return an optional containing the description of this plugin, if available, otherwise an
     *     empty optional.
     */
    @NotNull Optional<String> getDescription();

    /**
     * Get the url for this plugin.
     *
     * @return an optional containing the url, if available, otherwise an empty optional.
     */
    @NotNull Optional<String> getUrl();

    /**
     * Get the authors of this plugin.
     *
     * @return plugin authors.
     */
    @NotNull Collection<String> getAuthors();

    /**
     * Plugin data builder.
     */
    interface Builder {

        /**
         * Set the description of this plugin.
         *
         * @param description Plugin description.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder description(@NotNull String description);

        /**
         * Set the url of this plugin.
         *
         * @param url Plugin URL.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder url(@NotNull String url);

        /**
         * Add an author of this plugin.
         *
         * @param author Plugin author.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder author(@NotNull String author);

        /**
         * Add authors of this plugin.
         *
         * @param authors Plugin authors.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder authors(@NotNull Collection<String> authors);

        /**
         * Build a new plugin data.
         *
         * @return plugin data.
         */
        @Contract(value = "-> new", pure = true)
        @NotNull ChameleonPluginData build();

    }

}
