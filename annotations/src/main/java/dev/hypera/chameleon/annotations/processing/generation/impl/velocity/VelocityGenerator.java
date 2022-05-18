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
package dev.hypera.chameleon.annotations.processing.generation.impl.velocity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import dev.hypera.chameleon.annotations.PlatformDependency;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.Plugin.Platform;
import dev.hypera.chameleon.annotations.processing.generation.Generator;
import dev.hypera.chameleon.core.data.PluginData;
import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import org.jetbrains.annotations.NotNull;

public class VelocityGenerator extends Generator {

    private static final @NotNull String DESCRIPTION_FILE = "velocity-plugin.json";
    private static final @NotNull Gson GSON = new GsonBuilder().create();

    public void generate(@NotNull Plugin data, @NotNull TypeElement plugin, @NotNull ProcessingEnvironment env) throws Exception {
        MethodSpec constructorSpec = MethodSpec.constructorBuilder()
                .addAnnotation(clazz("com.google.inject", "Inject"))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(clazz("org.slf4j", "Logger"), "logger").build())
                .addParameter(ParameterSpec.builder(clazz("com.velocitypowered.api.proxy", "ProxyServer"), "server").build())
                .addParameter(ParameterSpec.builder(clazz("java.nio.file", "Path"), "dataDirectory").addAnnotation(clazz("com.velocitypowered.api.plugin.annotation", "DataDirectory")).build())
                .addStatement("this.$N = $N", "server", "server")
                .addStatement("this.$N = $N", "logger", "logger")
                .addStatement("this.$N = $N", "dataDirectory", "dataDirectory")
                .build();

        Platform[] platforms = data.platforms().length > 0 ? data.platforms() : Platform.values();

        MethodSpec initEventSpec = MethodSpec.methodBuilder("onProxyInitialization")
                .addAnnotation(clazz("com.velocitypowered.api.event", "Subscribe"))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(ParameterSpec.builder(clazz("com.velocitypowered.api.event.proxy", "ProxyInitializeEvent"), "event").build())
                .beginControlFlow("try")
                .addStatement(CodeBlock.builder().add(
                        "$T pluginData = new $T($S, $S, $S, $S, $T.asList($L), $S, $T.asList($L))",
                        PluginData.class, PluginData.class, data.name(), data.version(), data.description(), data.url(),
                        Arrays.class, data.authors().length > 0 ? '"' + String.join("\",\"", data.authors()) + '"' : "", data.logPrefix(),
                        Arrays.class, CodeBlock.builder().add(Arrays.stream(platforms).map(p -> "$1T." + p.name()).collect(Collectors.joining(", ")), PluginData.Platform.class).build()
                ).build())
                .addStatement("this.$N = new $T($T.class, this, pluginData)", "chameleon", clazz("dev.hypera.chameleon.platforms.velocity", "VelocityChameleon"), plugin)
                .addStatement("this.$N.onEnable()", "chameleon")
                .nextControlFlow("catch ($T ex)", ChameleonInstantiationException.class)
                .addStatement("$N.printStackTrace()", "ex")
                .endControlFlow()
                .build();

        MethodSpec shutdownEventSpec = MethodSpec.methodBuilder("onProxyShutdown")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(clazz("com.velocitypowered.api.event", "Subscribe"))
                .addParameter(ParameterSpec.builder(clazz("com.velocitypowered.api.event.proxy", "ProxyShutdownEvent"), "event").build())
                .addStatement("this.$N.onDisable()", "chameleon")
                .build();

        MethodSpec getServerSpec = MethodSpec.methodBuilder("getServer")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(clazz("com.velocitypowered.api.proxy", "ProxyServer"))
                .addStatement("return this.$N", "server")
                .build();

        MethodSpec getLoggerSpec = MethodSpec.methodBuilder("getLogger")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(clazz("org.slf4j", "Logger"))
            .addStatement("return this.$N", "logger")
            .build();


        MethodSpec getDataDirectorySpec = MethodSpec.methodBuilder("getDataDirectory")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(clazz("java.nio.file", "Path"))
                .addStatement("return this.$N", "dataDirectory")
                .build();

        AnnotationSpec.Builder pluginAnnotationSpecBuilder = AnnotationSpec.builder(clazz("com.velocitypowered.api.plugin", "Plugin"))
                .addMember("id", "$S", data.id())
                .addMember("name", "$S", data.name())
                .addMember("version", "$S", data.version())
                .addMember("description", "$S", data.description())
                .addMember("url", "$S", data.url());

        for (String author : data.authors()) {
            pluginAnnotationSpecBuilder.addMember("authors", "$S", author);
        }

        for (PlatformDependency dependency : data.dependencies()) {
            AnnotationSpec dependencyAnnotationSpec = AnnotationSpec.builder(clazz("com.velocitypowered.api.plugin", "Dependency"))
                    .addMember("id", "$S", dependency.name().toLowerCase())
                    .addMember("optional", "$L", dependency.soft())
                    .build();

            pluginAnnotationSpecBuilder.addMember("authors", "$L", dependencyAnnotationSpec);
        }

        TypeSpec velocityMainClassSpec = TypeSpec.classBuilder(plugin.getSimpleName() + "Velocity")
                .addAnnotation(pluginAnnotationSpecBuilder.build())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(clazz("dev.hypera.chameleon.platforms.velocity", "VelocityPlugin"))
                .addField(FieldSpec.builder(
                        clazz("com.velocitypowered.api.proxy", "ProxyServer"),
                        "server",
                        Modifier.PRIVATE, Modifier.FINAL
                ).build())
                .addField(FieldSpec.builder(
                    clazz("org.slf4j", "Logger"),
                    "logger",
                    Modifier.PRIVATE, Modifier.FINAL
                ).build())
                .addField(FieldSpec.builder(
                        clazz("java.nio.file", "Path"),
                        "dataDirectory",
                        Modifier.PRIVATE, Modifier.FINAL
                ).build())
                .addField(FieldSpec.builder(
                        clazz("dev.hypera.chameleon.platforms.velocity", "VelocityChameleon"),
                        "chameleon",
                        Modifier.PRIVATE
                ).build())
                .addMethod(constructorSpec)
                .addMethod(initEventSpec)
                .addMethod(shutdownEventSpec)
                .addMethod(getServerSpec)
                .addMethod(getLoggerSpec)
                .addMethod(getDataDirectorySpec)
                .build();

        String packageName = ((PackageElement) plugin.getEnclosingElement()).getQualifiedName().toString();
        if (packageName.endsWith("core") || packageName.endsWith("common")) {
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
        }
        packageName = packageName + ".platform.velocity";

        JavaFile.builder(packageName, velocityMainClassSpec).indent(INDENT).build().writeTo(env.getFiler());
        generateDescriptionFile(data, plugin, env, packageName);
    }

    private void generateDescriptionFile(@NotNull Plugin data, @NotNull TypeElement plugin, @NotNull ProcessingEnvironment env, @NotNull String packageName) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(env.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", DESCRIPTION_FILE).toUri()))) {
            GSON.toJson(new SerializedPluginDescription(
                    data.id(), data.name(), data.version(), data.description(),
                    data.url(), Arrays.asList(data.authors()), Arrays.asList(data.dependencies()),
                    packageName + "." + plugin.getSimpleName() + "Velocity"
            ), writer);
        }
    }

}
