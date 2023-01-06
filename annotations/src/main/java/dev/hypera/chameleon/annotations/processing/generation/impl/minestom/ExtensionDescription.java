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
package dev.hypera.chameleon.annotations.processing.generation.impl.minestom;

import dev.hypera.chameleon.annotations.PlatformDependency;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.Plugin.Platform;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({ "unused", "FieldCanBeLocal" })
class ExtensionDescription {

    private final @NotNull String name;
    private final @NotNull String entrypoint;
    private final @NotNull String version;
    private final @NotNull List<String> authors;
    private final @NotNull List<String> dependencies;

    ExtensionDescription(@NotNull Plugin plugin, @NotNull String main) {
        this.name = plugin.name().isEmpty() ? plugin.id() : plugin.name();
        this.entrypoint = main;
        this.version = plugin.version();
        this.authors = Arrays.asList(plugin.authors());
        this.dependencies = Arrays.stream(plugin.dependencies()).filter(d -> d.platforms().length == 0 || Arrays.asList(d.platforms()).contains(Platform.MINESTOM)).map(PlatformDependency::name).collect(Collectors.toList());
    }

}
