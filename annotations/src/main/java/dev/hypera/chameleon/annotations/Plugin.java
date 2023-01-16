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
package dev.hypera.chameleon.annotations;

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
     * The plugin's unique identifier.
     *
     * @return the plugin's ID.
     */
    @NotNull String id();

    /**
     * The plugin's human-readable name.
     *
     * @return the plugin's name, or an empty string.
     */
    @NotNull String name() default "";

    /**
     * The plugin's version.
     *
     * @return the plugin's version.
     */
    @NotNull String version();

    /**
     * The plugin's description, generally a short explaination of what the plugin is used for.
     *
     * @return the plugin's description, or an empty string.
     */
    @NotNull String description() default "";

    /**
     * The plugin's website or download URL.
     *
     * @return the plugin's url, or an empty string.
     */
    @NotNull String url() default "";

    /**
     * The plugin's author(s).
     *
     * @return the plugin's authors.
     */
    @NotNull String[] authors() default {};

    /**
     * The plugin's dependencies.
     *
     * @return the plugin's dependencies.
     */
    @NotNull Dependency[] dependencies() default {};

    /**
     * The platforms this plugin can run on.
     * <p>This is used by Chameleon Annotations to determine what generators should be run.</p>
     * <p>Defaults to all platforms.</p>
     *
     * @return the platforms this plugin can run on.
     */
    @NotNull String[] platforms() default {};

}
