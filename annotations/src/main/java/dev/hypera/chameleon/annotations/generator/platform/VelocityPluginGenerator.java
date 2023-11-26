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
import dev.hypera.chameleon.annotations.exception.ChameleonAnnotationException;
import dev.hypera.chameleon.annotations.generator.GeneratedClass;
import dev.hypera.chameleon.annotations.generator.GeneratedResource;
import dev.hypera.chameleon.platform.Platform;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity platform plugin generator.
 * <p>Generates Velocity plugin main class and {@code velocity-plugin.json} file.</p>
 */
final class VelocityPluginGenerator extends PlatformPluginGenerator {

    private static final @NotNull Pattern ID_PATTERN = Pattern.compile("[a-z][a-z\\d-_]{0,63}");
    private static final @NotNull Gson GSON = new GsonBuilder().create();

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<GeneratedClass> generateClasses(@NotNull Context ctx) {
        return Collections.singleton(
            templatedClass(
                ctx.options().generatedFQCN(ctx),
                "platform/velocity-plugin.java.vm",
                mapOf(
                    "plugin", ctx.plugin(),
                    "pluginAuthors", Arrays.asList(ctx.plugin().authors()),
                    "pluginDeps", getDependencies(ctx),
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
        if (!ID_PATTERN.matcher(ctx.plugin().id()).matches()) {
            throw new ChameleonAnnotationException(
                "Validating @Plugin (Velocity): Invalid plugin ID " + ctx.plugin().id() +
                    ": must match " + ID_PATTERN.pattern()
            );
        }

        return Collections.singleton(
            GeneratedResource.of("velocity-plugin.json", GSON.toJson(mapOf(
                "id", ctx.plugin().id(),
                "name", ctx.plugin().name(),
                "version", ctx.plugin().version(),
                "description", ctx.plugin().description(),
                "url", ctx.plugin().url(),
                "authors", Arrays.asList(ctx.plugin().authors()),
                "dependencies", getDependencies(ctx),
                "main", ctx.options().generatedFQCN(ctx)
            )))
        );
    }

    private @NotNull List<Object> getDependencies(@NotNull Context ctx) {
        return Arrays.stream(ctx.plugin().dependencies())
            .filter(d -> d.platforms().length == 0 || Arrays.asList(d.platforms()).contains(Platform.VELOCITY))
            .map(d -> mapOf(
                "name", d.name().toLowerCase(Locale.ROOT),
                "optional", d.optional()
            )).collect(Collectors.toUnmodifiableList());
    }

}
