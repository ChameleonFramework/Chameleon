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
package dev.hypera.chameleon.annotations.processing.generation;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import dev.hypera.chameleon.ChameleonPluginData;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.exception.ChameleonAnnotationException;
import java.util.Arrays;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Platform main class generator.
 */
@NonExtendable
public abstract class Generator {

    public static final @NotNull String CHAMELEON_VAR = "chameleon";
    public static final @NotNull String INDENT = "    ";

    /**
     * Generate main class and any required files.
     *
     * @param data   Plugin data.
     * @param plugin Chameleon plugin main class.
     * @param env    Processing environment.
     *
     * @throws ChameleonAnnotationException if something goes wrong while generating the files.
     */
    public abstract void generate(@NotNull Plugin data, @NotNull TypeElement plugin, @NotNull ProcessingEnvironment env) throws ChameleonAnnotationException;


    protected @NotNull CodeBlock createPluginData(@NotNull Plugin data) {
        return CodeBlock.builder().add(
            "$T pluginData = $T.builder($S, $S).description($S).url($S).authors($T.asList($L)).build()",
            ChameleonPluginData.class,
            ChameleonPluginData.class,
            data.name().isEmpty() ? data.id() : data.name(),
            data.version(),
            data.description(),
            data.url(),
            Arrays.class,
            data.authors().length > 0 ? '"' + String.join("\",\"", data.authors()) + '"' : ""
        ).build();
    }

    protected @NotNull ParameterizedTypeName generic(@NotNull ClassName clazz, @NotNull TypeName... arguments) {
        return ParameterizedTypeName.get(clazz, arguments);
    }

    protected @NotNull ClassName clazz(@NotNull String p, @NotNull String n) {
        return ClassName.get(p, n);
    }

}
