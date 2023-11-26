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

import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.PluginGeneratorOptions;
import dev.hypera.chameleon.annotations.generator.GeneratedClass;
import dev.hypera.chameleon.annotations.generator.GeneratedResource;
import dev.hypera.chameleon.annotations.generator.TemplatedClass;
import dev.hypera.chameleon.platform.Platform;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.Yaml;

/**
 * Represents a platform plugin generator.
 *
 * <p>Platform plugin generators create the necessary classes/resources to run on a platform.</p>
 *
 * <p>For example, to create a plugin that will run on Bukkit, we need a "plugin main class" (that
 * extends {@code org.bukkit.plugin.java.JavaPlugin}) and a "plugin description file"
 * ({@code plugin.yml}). A Bukkit platform plugin generator would generate these files with the
 * necessary content to load and run Chameleon on Bukkit.</p>
 */
public abstract class PlatformPluginGenerator {

    private static final @NotNull MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final @NotNull Map<String, Class<? extends PlatformPluginGenerator>> GENERATORS = Map.of(
        Platform.BUKKIT, BukkitPluginGenerator.class,
        Platform.BUNGEECORD, BungeeCordPluginGenerator.class,
        Platform.NUKKIT, NukkitPluginGenerator.class,
        Platform.SPONGE, SpongePluginGenerator.class,
        Platform.VELOCITY, VelocityPluginGenerator.class
    );

    /**
     * Returns a new generator that can generate the required classes/resources for the specified
     * platform.
     *
     * @param platformId Platform ID.
     *
     * @return platform plugin generator.
     * @throws IllegalArgumentException if a generator for the given platformId could not be found.
     */
    public static @NotNull PlatformPluginGenerator create(@NotNull String platformId) {
        Class<? extends PlatformPluginGenerator> generatorClass = GENERATORS.get(platformId);
        if (generatorClass == null) {
            throw new IllegalArgumentException(
                "Cannot find platform plugin generator for the platform '" + platformId + "'");
        }
        return createGenerator(generatorClass);
    }

    /**
     * Returns a new options implementation that returns default values.
     *
     * @return default options.
     */
    public static @NotNull Options createOptions() {
        return new Options() {
        };
    }

    /**
     * Returns a new options implementation that returns values from the given options annotation.
     *
     * @param options Options annotation.
     *
     * @return options from annotation.
     * @see PluginGeneratorOptions
     */
    public static @NotNull Options createOptions(@Nullable PluginGeneratorOptions options) {
        if (options == null) {
            return createOptions();
        }
        return new Options() {

            @Override
            public @NotNull String generatedClassName(@NotNull PlatformPluginGenerator.Context context) {
                return options.generatedClassName()
                    .replace("{name}", context.pluginClass().getSimpleName().toString())
                    .replace("{platform}", context.platformId());
            }

            @Override
            public @NotNull String generatedPackageName(@NotNull PlatformPluginGenerator.Context context) {
                return options.generatedPackageName().replace(
                    "{package}",
                    Objects.requireNonNull((PackageElement) context.pluginClass()
                        .getEnclosingElement()).getQualifiedName().toString()
                ).replace("{platform}", context.platformId().toLowerCase(Locale.ROOT));
            }

        };
    }

    /**
     * Returns a new context containing the given data.
     *
     * @param plugin         Plugin data annotation.
     * @param pluginClass    Chameleon plugin class.
     * @param bootstrapClass Chameleon plugin bootstrap class.
     * @param platformId     Platform ID.
     * @param options        Options.
     *
     * @return new context.
     */
    public static @NotNull Context createContext(@NotNull Plugin plugin, @NotNull TypeElement pluginClass, @Nullable TypeElement bootstrapClass, @NotNull String platformId, @NotNull Options options) {
        return new ContextImpl(plugin, pluginClass, bootstrapClass, platformId, options);
    }

    private static @NotNull PlatformPluginGenerator createGenerator(@NotNull Class<? extends PlatformPluginGenerator> generatorClass) {
        MethodHandle constructor;
        try {
            constructor = LOOKUP.findConstructor(generatorClass, MethodType.methodType(void.class));
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Failed to find constructor for generator " + generatorClass);
        }
        try {
            return (PlatformPluginGenerator) constructor.invoke();
        } catch (Throwable ex) {
            throw new IllegalStateException("Failed to invoke constructor for generator " + generatorClass);
        }
    }

    /**
     * Generate the necessary classes for this platform.
     *
     * @param context Generation context.
     *
     * @return generated classes.
     */
    public abstract @NotNull Collection<GeneratedClass> generateClasses(@NotNull Context context);

    /**
     * Generate the necessary resources for this platform.
     *
     * @param context Generation context.
     *
     * @return generated resources.
     */
    public abstract @NotNull Collection<GeneratedResource> generateResources(@NotNull Context context);

    /**
     * Returns a new templated class.
     *
     * @param fqcn                 Fully-qualified class name.
     * @param templateResourceName Template resource name.
     * @param templateVars         Template variables.
     *
     * @return new templated class.
     */
    protected @NotNull GeneratedClass templatedClass(@NotNull String fqcn, @NotNull String templateResourceName, @NotNull Map<String, Object> templateVars) {
        return TemplatedClass.of(fqcn, templateResourceName, templateVars);
    }

    /**
     * Returns a new YAML generated resource.
     *
     * @param name Resource file name.
     * @param map  YAML variables.
     *
     * @return new generated resource.
     */
    protected @NotNull GeneratedResource yamlResource(@NotNull String name, @NotNull Object... map) {
        return GeneratedResource.of(name, new Yaml().dump(mapOf(map)));
    }

    protected @NotNull Map<String, Object> mapOf(@Nullable Object @Nullable ... map) {
        if (map == null) {
            return Map.of();
        }
        if (map.length % 2 != 0) {
            // map contains key/value pairs, and must be even
            throw new IllegalArgumentException("map must contain an even amount of objects");
        }

        Map<String, Object> content = new LinkedHashMap<>();
        for (int i = 0; i < map.length; i += 2) {
            Object k = map[i];
            if (!(k instanceof String)) {
                throw new IllegalArgumentException("map must contain string keys");
            }
            content.put((String) k, map[i + 1]);
        }
        return content;
    }

    /**
     * Generation options.
     */
    public interface Options {

        /**
         * Returns the generated platform class name format.
         *
         * @param context Context.
         *
         * @return generated platform class name.
         */
        default @NotNull String generatedClassName(@NotNull Context context) {
            return context.pluginClass().getSimpleName().toString().concat(context.platformId());
        }

        /**
         * Returns the package generated platform classes should be placed in.
         *
         * @param context Context.
         *
         * @return platform package name.
         */
        default @NotNull String generatedPackageName(@NotNull Context context) {
            return String.format(
                "%s.platform.%s",
                Objects.requireNonNull((PackageElement) context.pluginClass().getEnclosingElement())
                    .getQualifiedName()
                    .toString(),
                context.platformId().toLowerCase(Locale.ROOT)
            );
        }

        /**
         * Returns the fully-qualified class name for the platform.
         *
         * @param context Context.
         *
         * @return platform fully-qualified class name.
         */
        default @NotNull String generatedFQCN(@NotNull Context context) {
            return generatedPackageName(context) + '.' + generatedClassName(context);
        }

    }

    /**
     * Stores data used for each generation call.
     */
    public interface Context {

        /**
         * Returns the plugin data.
         *
         * @return plugin data.
         */
        @NotNull Plugin plugin();

        /**
         * Returns the plugin class.
         *
         * @return plugin class.
         */
        @NotNull TypeElement pluginClass();

        /**
         * Returns the plugin bootstrap class.
         *
         * @return plugin bootstrap class.
         */
        @Nullable TypeElement bootstrapClass();

        /**
         * Returns the platformId to generate for.
         *
         * @return platform identifier.
         */
        @NotNull String platformId();

        /**
         * Returns the platform plugin generator options.
         *
         * @return generator options.
         */
        @NotNull Options options();

    }

    /**
     * Default implementation of {@link Context}.
     */
    static final class ContextImpl implements Context {

        private final @NotNull Plugin plugin;
        private final @NotNull TypeElement pluginClass;
        private final @Nullable TypeElement bootstrapClass;
        private final @NotNull String platformId;
        private final @NotNull Options options;

        ContextImpl(@NotNull Plugin plugin, @NotNull TypeElement pluginClass, @Nullable TypeElement bootstrapClass, @NotNull String platformId, @NotNull Options options) {
            this.plugin = plugin;
            this.pluginClass = pluginClass;
            this.bootstrapClass = bootstrapClass;
            this.platformId = platformId;
            this.options = options;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Plugin plugin() {
            return this.plugin;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull TypeElement pluginClass() {
            return this.pluginClass;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @Nullable TypeElement bootstrapClass() {
            return this.bootstrapClass;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull String platformId() {
            return this.platformId;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Options options() {
            return this.options;
        }

    }

}
