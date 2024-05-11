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
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit platform plugin generator.
 * <p>Generates Bukkit plugin main class and {@code plugin.yml} file.</p>
 */
final class BukkitPluginGenerator extends PlatformPluginGenerator {

    private static final @NotNull Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z0-9\\s_.-]+$");
    private static final @NotNull String[] PROHIBITED_PACKAGES = new String[] {
        "net.minecraft.",
        "org.bukkit.",
        "io.papermc.paper.",
        "com.destroystokyo.paper.",
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<GeneratedClass> generateClasses(@NotNull Context ctx) {
        return Collections.singleton(
            templatedClass(
                ctx.options().generatedFQCN(ctx),
                "platform/bukkit-plugin.java.vm",
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
        // Validate plugin name
        String pluginName = ChameleonUtil.firstNonEmpty(ctx.plugin().name(), ctx.plugin().id());
        if (!NAME_PATTERN.matcher(pluginName).matches()) {
            throw new ChameleonAnnotationException(
                "Validating @Plugin (Bukkit): Invalid plugin name " +
                    pluginName + ": must match " + NAME_PATTERN.pattern()
            );
        }
        // Validate main class
        String mainClass = ctx.options().generatedFQCN(ctx);
        for (String prohibitedPackage : PROHIBITED_PACKAGES) {
            if (mainClass.startsWith(prohibitedPackage)) {
                throw new ChameleonAnnotationException(
                    "Validating @Plugin (Bukkit): Main class " + mainClass +
                        " must not be within the " + prohibitedPackage + " package"
                );
            }
        }

        return Collections.singleton(
            yamlResource("plugin.yml",
                "name", pluginName,
                "version", ctx.plugin().version(),
                "description", ctx.plugin().description(),
                "authors", Arrays.asList(ctx.plugin().authors()),
                "website", ctx.plugin().url(),
                "main", mainClass,
                "folia-supported", true,
                "depend", getDepend(ctx),
                "softdepend", getSoftDepend(ctx)
            )
        );
    }

    private @NotNull List<String> getDepend(@NotNull Context ctx) {
        return Arrays.stream(ctx.plugin().dependencies()).filter(d -> !d.optional())
            .filter(d -> d.platforms().length == 0 || Arrays.asList(d.platforms()).contains(Platform.BUKKIT))
            .map(Dependency::name).collect(Collectors.toUnmodifiableList());
    }

    private @NotNull List<String> getSoftDepend(@NotNull Context ctx) {
        return Arrays.stream(ctx.plugin().dependencies()).filter(Dependency::optional)
            .filter(d -> d.platforms().length == 0 || Arrays.asList(d.platforms()).contains(Platform.BUKKIT))
            .map(Dependency::name).collect(Collectors.toUnmodifiableList());
    }

}
