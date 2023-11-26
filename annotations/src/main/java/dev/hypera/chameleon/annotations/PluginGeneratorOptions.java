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
 * Platform plugin generator options.
 *
 * @see dev.hypera.chameleon.annotations.generator.platform.PlatformPluginGenerator
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface PluginGeneratorOptions {

    /**
     * Returns the generated platform class name format.
     * <p>Defaults to {@code {name}{platform}}, e.g. {@code ChameleonExampleBukkit}.</p>
     *
     * <p>The following placeholders will be replaced in this string:</p>
     * <ul>
     *   <li>{@code {name}} - Name of the ChameleonPlugin class, e.g. {@code ChameleonExample}.</li>
     *   <li>{@code {platform}} - Platform identifier, e.g. {@code Bukkit}, {@code Velocity}.</li>
     * </ul>
     *
     * @return generated platform class name.
     */
    @NotNull String generatedClassName() default "{name}{platform}";

    /**
     * Returns the package generated platform classes should be placed in.
     * <p>Defaults to {@code {package}.platform.{platform}}, e.g.
     * {@code com.example.platform.bukkit}</p>
     *
     * <p>The following placeholders will be replaced in this string:</p>
     * <ul>
     *   <li>{@code {package}} - Name of the package the ChameleonPlugin is in.</li>
     *   <li>{@code {platform}} - Platform identifier, e.g. {@code bukkit}, {@code velocity}.</li>
     * </ul>
     *
     * @return platform package name.
     */
    @NotNull String generatedPackageName() default "{package}.platform.{platform}";

}
