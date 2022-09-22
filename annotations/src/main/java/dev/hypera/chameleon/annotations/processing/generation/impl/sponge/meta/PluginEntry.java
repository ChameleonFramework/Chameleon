/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.annotations.processing.generation.impl.sponge.meta;

import dev.hypera.chameleon.annotations.PlatformDependency;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.Plugin.Platform;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Internal
@SuppressWarnings({ "unused", "FieldCanBeLocal" })
final class PluginEntry {

    private static final @NotNull Dependency SPONGE_API_DEPENDENCY = new Dependency("spongeapi", "8.0.0");

    private final @NotNull String id;
    private final @Nullable String name;
    private final @Nullable String description;
    private final @NotNull String entrypoint;
    private final @NotNull String version;
    private final @Nullable Links links;
    private final @NotNull List<Contributor> contributors;
    private final @NotNull List<Dependency> dependencies;

    @Internal
    PluginEntry(@NotNull Plugin plugin, @NotNull String main) {
        this.id = plugin.id();
        this.name = nullEmpty(plugin.name());
        this.description = nullEmpty(plugin.description());
        this.entrypoint = main;
        this.version = plugin.version();
        this.links = new Links(nullEmpty(plugin.url()));
        this.contributors = Arrays.stream(plugin.authors()).map(Contributor::new).collect(Collectors.toList());
        this.dependencies = Arrays.stream(plugin.dependencies()).filter(d -> d.platforms().length == 0 || Arrays.asList(d.platforms()).contains(Platform.SPONGE)).map(Dependency::new).collect(Collectors.toList());
        this.dependencies.add(SPONGE_API_DEPENDENCY);
    }

    @Internal
    private static final class Links {

        private final @Nullable String homepage;

        @Internal
        private Links(@Nullable String url) {
            this.homepage = url;
        }

    }

    @Internal
    private static final class Contributor {

        private final @NotNull String name;

        @Internal
        private Contributor(@NotNull String name) {
            this.name = name;
        }

    }

    @Internal
    private static final class Dependency {

        private final @NotNull String id;
        private final @NotNull String version;

        @Internal
        private Dependency(@NotNull PlatformDependency dependency) {
            if (dependency.version().isEmpty()) {
                throw new IllegalArgumentException("@PlatformDependency must contain version for Sponge support");
            }

            this.id = dependency.name().toLowerCase();
            this.version = dependency.version();
        }

        @Internal
        private Dependency(@NotNull String id, @NotNull String version) {
            this.id = id;
            this.version = version;
        }

    }

    private @Nullable String nullEmpty(@NotNull String s) {
        return s.isEmpty() ? null : s;
    }

}
