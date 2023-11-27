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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.jetbrains.annotations.NotNull;

/**
 * Plugin Dependency.
 *
 * @see Plugin
 */
@Retention(RetentionPolicy.SOURCE)
@Target({})
public @interface Dependency {

    /**
     * Returns the name or ID of this dependency.
     *
     * @return dependency name or ID.
     */
    @NotNull String name();

    /**
     * Returns the version, or a maven version range, that represents the compatible versions of
     * this dependency.
     * <p><strong>This is required for Sponge support.</strong></p>
     *
     * @return the required version of this dependency.
     */
    @NotNull String version() default "";

    /**
     * Returns whether this dependency is optional for loading the dependant plugin.
     * <p>Defaults to {@code false}, meaning the dependency is not required.</p>
     *
     * @return {@code true} if the dependency is not required when loading the dependant plugin.
     */
    boolean optional() default false;

    /**
     * Returns the platforms this dependency applies to.
     * <p>Defaults to all platforms.</p>
     *
     * @return dependency platforms.
     */
    @NotNull String[] platforms() default {};

}
