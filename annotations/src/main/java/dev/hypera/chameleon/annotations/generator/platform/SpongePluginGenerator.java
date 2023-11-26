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
package dev.hypera.chameleon.annotations.generator.platform;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.hypera.chameleon.annotations.Dependency;
import dev.hypera.chameleon.annotations.generator.GeneratedClass;
import dev.hypera.chameleon.annotations.generator.GeneratedResource;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.util.internal.ChameleonUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Sponge platform plugin generator.
 * <p>Generates Sponge plugin main class and {@code META-INF/sponge_plugins.json} file.</p>
 */
final class SpongePluginGenerator extends PlatformPluginGenerator {

    private static final @NotNull String SPONGE_API_VERSION = "8.0.0";
    private static final @NotNull Gson GSON = new GsonBuilder().create();

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<GeneratedClass> generateClasses(@NotNull Context ctx) {
        return Collections.singleton(
            templatedClass(
                ctx.options().generatedFQCN(ctx),
                "platform/sponge-plugin.java.vm",
                mapOf(
                    "pluginClass", ctx.pluginClass().getQualifiedName().toString(),
                    "bootstrapClass", Optional.ofNullable(ctx.bootstrapClass())
                        .map(b -> b.getQualifiedName().toString()).orElse("")
                )
            )
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<GeneratedResource> generateResources(@NotNull Context ctx) {
        return Collections.singleton(
            GeneratedResource.of("META-INF/sponge_plugins.json", GSON.toJson(mapOf(
                "loader", mapOf(
                    "name", "java_plain",
                    "version", "1.0"
                ),
                "license", ChameleonUtil.firstNonEmpty(ctx.plugin().license(), "unknown"),
                "global", mapOf(
                    "version", ctx.plugin().version(),
                    "links", mapOf(
                        "homepage", ChameleonUtil.nullifyEmpty(ctx.plugin().url())
                    ),
                    "contributors", Arrays.stream(ctx.plugin().authors()).map(authorName -> mapOf(
                        "name", authorName
                    )),
                    "dependencies", getDependencies(ctx)
                ),
                "plugins", Collections.singleton(
                    mapOf(
                        "id", ctx.plugin().id(),
                        "name", ChameleonUtil.nullifyEmpty(ctx.plugin().name()),
                        "description", ChameleonUtil.nullifyEmpty(ctx.plugin().description()),
                        "entrypoint", ctx.options().generatedFQCN(ctx)
                    )
                )
            )))
        );
    }

    private @NotNull List<Object> getDependencies(@NotNull Context ctx) {
        // Validate dependencies
        for (Dependency d : ctx.plugin().dependencies()) {
            if (d.platforms().length > 0 && !Arrays.asList(d.platforms()).contains(Platform.SPONGE)) {
                continue;
            }
            if (d.version().isEmpty()) {
                throw new IllegalStateException("@Dependency must contain version to support Sponge");
            }
        }

        // Convert dependencies to Sponge format
        List<Object> dependencies = Arrays.stream(ctx.plugin().dependencies())
            .filter(d -> d.platforms().length == 0 || Arrays.asList(d.platforms()).contains(Platform.SPONGE))
            .map(d -> mapOf(
                "id", d.name().toLowerCase(Locale.ROOT),
                "version", d.version(),
                "optional", d.optional()
            )).collect(Collectors.toList());

        // Add Sponge API dependency
        dependencies.add(mapOf(
            "id", "spongeapi",
            "version", SPONGE_API_VERSION
        ));
        return dependencies;
    }

}
