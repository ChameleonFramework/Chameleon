/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package dev.hypera.chameleon.annotations.processing.generation.impl.sponge;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.processing.generation.Generator;
import dev.hypera.chameleon.annotations.processing.generation.impl.sponge.meta.SerializedPluginMetadata;
import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Sponge plugin main class and 'META-INF/sponge_plugins.json' description file generator.
 */
public class SpongeGenerator extends Generator {

    private static final @NotNull String DESCRIPTION_FILE = "META-INF/sponge_plugins.json";
    private static final @NotNull Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Generate Sponge plugin main class and 'META-INF/sponge_plugins.json' description file.
     *
     * @param data   {@link Plugin} data
     * @param plugin Chameleon plugin main class
     * @param env    Processing environment
     *
     * @throws Exception if something goes wrong while creating the files.
     */
    @Override
    public void generate(@NotNull Plugin data, @NotNull TypeElement plugin, @NotNull ProcessingEnvironment env) throws Exception {
        MethodSpec constructorSpec = MethodSpec.constructorBuilder()
            .addAnnotation(clazz("com.google.inject", "Inject"))
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ParameterSpec.builder(clazz("org.spongepowered.plugin", "PluginContainer"), "pluginContainer").build())
            .addParameter(ParameterSpec.builder(clazz("org.apache.logging.log4j", "Logger"), "logger").build())
            .addStatement("this.$N = $N", "pluginContainer", "pluginContainer")
            .addStatement("this.$N = $N", "logger", "logger")
            .beginControlFlow("try")
            .addStatement(createPluginData(data))
            .addStatement("this.$N = $T.create($T.class, this, $N).load()", "chameleon", clazz("dev.hypera.chameleon.platforms.sponge", "SpongeChameleon"), plugin, "pluginData")
            .nextControlFlow("catch ($T ex)", ChameleonInstantiationException.class)
            .addStatement("$N.printStackTrace()", "ex")
            .endControlFlow()
            .build();

        MethodSpec startingEngineEventSpec = MethodSpec.methodBuilder("onStartingEngineEvent")
            .addAnnotation(clazz("org.spongepowered.api.event", "Listener"))
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ParameterSpec.builder(generic(clazz("org.spongepowered.api.event.lifecycle", "StartingEngineEvent"), clazz("org.spongepowered.api", "Server")), "event").build())
            .addStatement("this.$N.onEnable()", "chameleon")
            .build();

        MethodSpec stoppingEngineEventSpec = MethodSpec.methodBuilder("onStoppingEngineEvent")
            .addAnnotation(clazz("org.spongepowered.api.event", "Listener"))
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ParameterSpec.builder(generic(clazz("org.spongepowered.api.event.lifecycle", "StoppingEngineEvent"), clazz("org.spongepowered.api", "Server")), "event").build())
            .addStatement("this.$N.onDisable()", "chameleon")
            .build();

        MethodSpec getPluginContainerSpec = MethodSpec.methodBuilder("getPluginContainer")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(clazz("org.spongepowered.plugin", "PluginContainer"))
            .addStatement("return this.$N", "pluginContainer")
            .build();

        MethodSpec getLoggerSpec = MethodSpec.methodBuilder("getLogger")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(clazz("org.apache.logging.log4j", "Logger"))
            .addStatement("return this.$N", "logger")
            .build();

        MethodSpec getDataDirectorySpec = MethodSpec.methodBuilder("getDataDirectory")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(clazz("java.nio.file", "Path"))
            .addStatement("return this.$N", "dataDirectory")
            .build();

        TypeSpec spongeMainClassSpec = TypeSpec.classBuilder(plugin.getSimpleName() + "Sponge")
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(clazz("dev.hypera.chameleon.platforms.sponge", "SpongePlugin"))
            .addField(FieldSpec.builder(clazz("org.spongepowered.plugin", "PluginContainer"), "pluginContainer", Modifier.PRIVATE, Modifier.FINAL).build())
            .addField(FieldSpec.builder(clazz("org.apache.logging.log4j", "Logger"), "logger", Modifier.PRIVATE, Modifier.FINAL).build())
            .addField(
                FieldSpec.builder(clazz("java.nio.file", "Path"), "dataDirectory", Modifier.PRIVATE)
                    .addAnnotation(clazz("com.google.inject", "Inject"))
                    .addAnnotation(AnnotationSpec.builder(clazz("org.spongepowered.api.config", "DefaultConfig")).addMember("sharedRoot", "false").build())
                    .build()
            )
            .addField(FieldSpec.builder(clazz("dev.hypera.chameleon.platforms.sponge", "SpongeChameleon"), "chameleon", Modifier.PRIVATE).build())
            .addMethod(constructorSpec)
            .addMethod(startingEngineEventSpec)
            .addMethod(stoppingEngineEventSpec)
            .addMethod(getPluginContainerSpec)
            .addMethod(getLoggerSpec)
            .addMethod(getDataDirectorySpec)
            .build();

        String packageName = ((PackageElement) plugin.getEnclosingElement()).getQualifiedName().toString();
        if (packageName.endsWith("core") || packageName.endsWith("common")) {
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
        }
        packageName = packageName + ".platform.sponge";

        JavaFile.builder(packageName, spongeMainClassSpec).indent(INDENT).build().writeTo(env.getFiler());
        generateDescriptionFile(data, plugin, env, packageName);
    }

    private void generateDescriptionFile(@NotNull Plugin data, @NotNull TypeElement plugin, @NotNull ProcessingEnvironment env, @NotNull String packageName) throws IOException {
        Path path = Paths.get(env.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", DESCRIPTION_FILE).toUri());
        if (!Files.exists(path.getParent())) {
            Files.createDirectories(path.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            GSON.toJson(new SerializedPluginMetadata(data, packageName + "." + plugin.getSimpleName() + "Sponge"), writer);
        }
    }

}
