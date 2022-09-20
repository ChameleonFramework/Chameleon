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
package dev.hypera.chameleon.annotations.processing.generation.impl.velocity;

import dev.hypera.chameleon.annotations.PlatformDependency;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.Plugin.Platform;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({ "unused", "FieldCanBeLocal" })
class SerializedPluginDescription {

    private final @NotNull String id;
    private final @NotNull String name;
    private final @NotNull String version;
    private final @NotNull String description;
    private final @NotNull String url;
    private final @NotNull List<String> authors;
    private final @NotNull List<SerializedDependency> dependencies;
    private final @NotNull String main;

    @Internal
    SerializedPluginDescription(@NotNull Plugin plugin, @NotNull String main) {
        if (!plugin.id().matches("[a-z][a-z\\d-_]{0,63}")) {
            throw new IllegalArgumentException("Invalid plugin id");
        }

        this.id = plugin.id();
        this.name = plugin.name();
        this.version = plugin.version();
        this.description = plugin.description();
        this.url = plugin.url();
        this.authors = Arrays.asList(plugin.authors());
        this.dependencies = Arrays.stream(plugin.dependencies()).filter(d -> d.platforms().length == 0 || Arrays.asList(d.platforms()).contains(Platform.VELOCITY)).map(SerializedDependency::new).collect(Collectors.toList());
        this.main = main;
    }

    @Internal
    private static final class SerializedDependency {

        private final @NotNull String name;
        private final boolean optional;

        private SerializedDependency(@NotNull PlatformDependency dependency) {
            this.name = dependency.name();
            this.optional = dependency.soft();
        }

    }

}
