/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.annotations;

import dev.hypera.chameleon.ChameleonPluginBootstrap;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

/**
 * Annotation used to describe a plugin.
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface Plugin {

    /**
     * Returns the unique identifier of this plugin.
     *
     * @return plugin ID.
     */
    @NotNull String id();

    /**
     * Returns the human-readable display name of this plugin.
     *
     * @return plugin name.
     */
    @NotNull String name() default "";

    /**
     * Returns the version of this plugin.
     *
     * @return plugin version.
     */
    @NotNull String version();

    /**
     * Returns the license this plugin is distributed under.
     * <p>This is currently only used for Sponge support.</p>
     *
     * @return plugin license.
     */
    @NotNull String license() default "";

    /**
     * Returns a short description of this plugin.
     *
     * @return plugin description.
     */
    @NotNull String description() default "";

    /**
     * Returns the website or download URL of this plugin.
     *
     * @return plugin URL.
     */
    @NotNull String url() default "";

    /**
     * Returns the authors of this plugin.
     *
     * @return plugin authors.
     */
    @NotNull String[] authors() default {};

    /**
     * Returns the dependencies of this plugin.
     *
     * @return plugin dependencies.
     */
    @NotNull Dependency[] dependencies() default {};

    /**
     * Returns the platforms this plugin can run on.
     *
     * <p>This is used internally to decide which platform plugin generators to run.</p>
     *
     * @return the platforms this plugin can run on.
     */
    @NotNull String[] platforms() default {};

    /**
     * Returns the plugin bootstrap to use when bootstrapping Chameleon.
     *
     * <p>The provided bootstrap must have a public constructor with no parameters.</p>
     * <p><strong>If this is not provided, the annotated class must have a public constructor with
     * a single Chameleon parameter.</strong></p>
     *
     * @return plugin bootstrap class.
     */
    @NotNull Class<? extends ChameleonPluginBootstrap> bootstrap() default ChameleonPluginBootstrap.class;

}
