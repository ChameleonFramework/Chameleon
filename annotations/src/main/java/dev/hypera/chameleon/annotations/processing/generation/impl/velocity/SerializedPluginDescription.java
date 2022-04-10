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
package dev.hypera.chameleon.annotations.processing.generation.impl.velocity;

import dev.hypera.chameleon.annotations.PlatformDependency;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({ "unused", "FieldCanBeLocal" })
public class SerializedPluginDescription {

    private final @NotNull String id;
    private final @NotNull String name;
    private final @NotNull String version;
    private final @NotNull String description;
    private final @NotNull String url;
    private final @NotNull List<String> authors;
    private final @NotNull List<SerializedDependency> dependencies;
    private final @NotNull String main;

    public SerializedPluginDescription(@NotNull String id, @NotNull String name, @NotNull String version, @NotNull String description, @NotNull String url, @NotNull List<String> authors, @NotNull List<PlatformDependency> dependencies, @NotNull String main) {
        if (!id.matches("[a-z][a-z0-9-_]{0,63}")) {
            throw new IllegalArgumentException("Invalid plugin id");
        }

        this.id = id;
        this.name = name;
        this.version = version;
        this.description = description;
        this.url = url;
        this.authors = authors;
        this.dependencies = dependencies.stream().map(d -> new SerializedDependency(d.name(), d.soft()))
                .collect(Collectors.toList());
        this.main = main;
    }


    public static class SerializedDependency {

        private final @NotNull String name;
        private final boolean optional;

        public SerializedDependency(@NotNull String name, boolean optional) {
            this.name = name;
            this.optional = optional;
        }

    }

}
