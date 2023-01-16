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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Chameleon plugin data implementation.
 */
@Internal
final class ChameleonPluginDataImpl implements ChameleonPluginData {

    private final @NotNull String name;
    private final @NotNull String version;
    private final @Nullable String description;
    private final @Nullable String url;
    private final @NotNull Collection<String> authors;

    /**
     * Chameleon plugin data implementation constructor.
     *
     * @param name        Plugin name.
     * @param version     Plugin version.
     * @param description Plugin description.
     * @param url         Plugin URL.
     * @param authors     Plugin authors.
     */
    ChameleonPluginDataImpl(@NotNull String name, @NotNull String version, @Nullable String description, @Nullable String url, @NotNull Collection<String> authors) {
        this.name = name;
        this.version = version;
        this.description = description;
        this.url = url;
        this.authors = authors;
    }

    /**
     * Get name of this plugin.
     *
     * @return plugin name.
     */
    @Override
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * Get the version of this plugin.
     *
     * @return plugin version.
     */
    @Override
    public @NotNull String getVersion() {
        return this.version;
    }

    /**
     * Get the description for this plugin.
     *
     * @return an optional containing the description of this plugin, if available, otherwise an
     *     empty optional.
     */
    @Override
    public @NotNull Optional<String> getDescription() {
        return Optional.ofNullable(this.description);
    }

    /**
     * Get the url for this plugin.
     *
     * @return an optional containing the url, if available, otherwise an empty optional.
     */
    @Override
    public @NotNull Optional<String> getUrl() {
        return Optional.ofNullable(this.url);
    }

    /**
     * Get the authors of this plugin.
     *
     * @return plugin authors.
     */
    @Override
    public @NotNull Collection<String> getAuthors() {
        return this.authors;
    }

    /**
     * Chameleon plugin data builder implementation.
     */
    static final class BuilderImpl implements ChameleonPluginData.Builder {

        private final @NotNull String name;
        private final @NotNull String version;
        private @Nullable String description;
        private @Nullable String url;
        private final @NotNull Collection<String> authors = new ArrayList<>();

        /**
         * Chameleon plugin data builder implementation constructor.
         *
         * @param name    Plugin name.
         * @param version Plugin version.
         */
        BuilderImpl(@NotNull String name, @NotNull String version) {
            this.name = name;
            this.version = version;
        }

        /**
         * Set the description of this plugin.
         *
         * @param description Plugin description.
         *
         * @return {@code this}.
         */
        @Override
        public @NotNull Builder description(@NotNull String description) {
            Preconditions.checkNotNull("description", description);
            this.description = description;
            return this;
        }

        /**
         * Set the url of this plugin.
         *
         * @param url Plugin URL.
         *
         * @return {@code this}.
         */
        @Override
        public @NotNull Builder url(@NotNull String url) {
            Preconditions.checkNotNull("url", url);
            this.url = url;
            return this;
        }

        /**
         * Add an author of this plugin.
         *
         * @param author Plugin author.
         *
         * @return {@code this}.
         */
        @Override
        public @NotNull Builder author(@NotNull String author) {
            Preconditions.checkNotNull("author", author);
            this.authors.add(author);
            return this;
        }

        /**
         * Add authors of this plugin.
         *
         * @param authors Plugin authors.
         *
         * @return {@code this}.
         */
        @Override
        public @NotNull Builder authors(@NotNull Collection<String> authors) {
            Preconditions.checkNotNull("authors", authors);
            this.authors.addAll(authors);
            return this;
        }

        /**
         * Build a new plugin data.
         *
         * @return plugin data.
         */
        @Override
        public @NotNull ChameleonPluginData build() {
            return new ChameleonPluginDataImpl(
                this.name, this.version, this.description, this.url, this.authors
            );
        }

    }

}
