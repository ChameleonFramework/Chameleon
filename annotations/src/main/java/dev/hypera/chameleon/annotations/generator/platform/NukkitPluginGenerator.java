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

import dev.hypera.chameleon.annotations.Dependency;
import dev.hypera.chameleon.annotations.exception.ChameleonAnnotationException;
import dev.hypera.chameleon.annotations.generator.GeneratedClass;
import dev.hypera.chameleon.annotations.generator.GeneratedResource;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.util.internal.ChameleonUtil;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit platform plugin generator.
 * <p>Generates Nukkit plugin main class and {@code nukkit.yml} file.</p>
 */
final class NukkitPluginGenerator extends PlatformPluginGenerator {

    private static final @NotNull Collection<String> NUKKIT_API = Collections.singleton("1.0.5");
    private static final @NotNull String[] PROHIBITED_PACKAGES = new String[] { "cn.nukkit." };

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<GeneratedClass> generateClasses(@NotNull Context ctx) {
        return Collections.singleton(
            templatedClass(
                ctx.options().generatedFQCN(ctx),
                "platform/nukkit-plugin.java.vm",
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
        // Validate main class
        String mainClass = ctx.options().generatedFQCN(ctx);
        for (String prohibitedPackage : PROHIBITED_PACKAGES) {
            if (mainClass.startsWith(prohibitedPackage)) {
                throw new ChameleonAnnotationException(
                    "Validating @Plugin (Nukkit): Main class" + mainClass +
                        " must not be within the " + prohibitedPackage + " package"
                );
            }
        }

        return Collections.singleton(
            yamlResource("nukkit.yml",
                "name", ChameleonUtil.firstNonEmpty(ctx.plugin().name(), ctx.plugin().id()),
                "main", mainClass,
                "version", ctx.plugin().version(),
                "api", NUKKIT_API,
                "authors", Arrays.asList(ctx.plugin().authors()),
                "website", ctx.plugin().url(),
                "depend", getDepend(ctx),
                "softdepend", getSoftDepend(ctx),
                "description", ctx.plugin().description()
            )
        );
    }

    private @NotNull List<String> getDepend(@NotNull Context ctx) {
        return Arrays.stream(ctx.plugin().dependencies()).filter(d -> !d.optional())
            .filter(d -> d.platforms().length == 0 || Arrays.asList(d.platforms()).contains(Platform.NUKKIT))
            .map(Dependency::name).collect(Collectors.toUnmodifiableList());
    }

    private @NotNull List<String> getSoftDepend(@NotNull Context ctx) {
        return Arrays.stream(ctx.plugin().dependencies()).filter(Dependency::optional)
            .filter(d -> d.platforms().length == 0 || Arrays.asList(d.platforms()).contains(Platform.NUKKIT))
            .map(Dependency::name).collect(Collectors.toUnmodifiableList());
    }

}
