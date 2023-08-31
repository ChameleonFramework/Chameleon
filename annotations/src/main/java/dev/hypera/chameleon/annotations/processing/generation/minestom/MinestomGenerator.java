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
package dev.hypera.chameleon.annotations.processing.generation.minestom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.exception.ChameleonAnnotationException;
import dev.hypera.chameleon.annotations.processing.generation.Generator;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Minestom extension main class and 'extension.json' description file generator.
 */
public class MinestomGenerator extends Generator {

    private static final @NotNull String DESCRIPTION_FILE = "extension.json";
    private static final @NotNull Gson GSON = new GsonBuilder().create();

    /**
     * Generate Minestom extension main class and 'extension.json' description file.
     *
     * @param data   {@link Plugin} data
     * @param plugin Chameleon plugin main class
     * @param env    Processing environment
     *
     * @throws ChameleonAnnotationException if something goes wrong while creating the files.
     */
    @Override
    public void generate(@NotNull Plugin data, @NotNull TypeElement plugin, @Nullable TypeElement bootstrap, @NotNull ProcessingEnvironment env) throws ChameleonAnnotationException {
        MethodSpec constructorSpec = addBootstrap(
            MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC),
            clazz("dev.hypera.chameleon.platform.minestom", "MinestomChameleon"),
            plugin, bootstrap
        ).build();

        MethodSpec initializeSpec = MethodSpec.methodBuilder("initialize")
            .addAnnotation(Override.class).addModifiers(Modifier.PUBLIC)
            .addStatement("this.$N.onEnable()", CHAMELEON_VAR).build();

        MethodSpec terminateSpec = MethodSpec.methodBuilder("terminate")
            .addAnnotation(Override.class).addModifiers(Modifier.PUBLIC)
            .addStatement("this.$N.onDisable()", CHAMELEON_VAR).build();

        TypeSpec minestomMainClassSpec = TypeSpec.classBuilder(plugin.getSimpleName() + "Minestom")
            .addModifiers(Modifier.PUBLIC)
            .superclass(clazz("net.minestom.server.extensions", "Extension"))
            .addField(FieldSpec.builder(clazz("dev.hypera.chameleon.platform.minestom", "MinestomChameleon"), CHAMELEON_VAR, Modifier.PRIVATE).build())
            .addMethod(constructorSpec)
            .addMethod(initializeSpec)
            .addMethod(terminateSpec)
            .build();

        String packageName = Objects.requireNonNull((PackageElement) plugin.getEnclosingElement()).getQualifiedName().toString();
        if (packageName.endsWith("core") || packageName.endsWith("common")) {
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
        }
        packageName = packageName + ".platform.minestom";

        try {
            JavaFile.builder(packageName, minestomMainClassSpec).indent(INDENT).build().writeTo(env.getFiler());
            generateDescriptionFile(data, plugin, env, packageName);
        } catch (IOException ex) {
            throw new ChameleonAnnotationException("Failed to write main class or description file", ex);
        }
    }

    private void generateDescriptionFile(@NotNull Plugin data, @NotNull TypeElement plugin, @NotNull ProcessingEnvironment env, @NotNull String packageName) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(env.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", DESCRIPTION_FILE).toUri()))) {
            GSON.toJson(new ExtensionDescription(data, packageName + "." + plugin.getSimpleName() + "Minestom"), writer);
        }
    }

}
