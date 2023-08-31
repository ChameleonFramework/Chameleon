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
package dev.hypera.chameleon.annotations.processing.generation.velocity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import dev.hypera.chameleon.annotations.Dependency;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.exception.ChameleonAnnotationException;
import dev.hypera.chameleon.annotations.processing.generation.Generator;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Objects;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.StandardLocation;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity plugin main class and 'velocity-plugin.json' description file generator.
 */
public class VelocityGenerator extends Generator {

    private static final @NotNull String LOGGER_VAR = "logger";
    private static final @NotNull String PROXY_SERVER_VAR = "server";
    private static final @NotNull String DATA_DIRECTORY_VAR = "dataDirectory";
    private static final @NotNull String SET_STATEMENT = "this.$N = $N";
    private static final @NotNull String RETURN_STATEMENT = "return this.$N";

    private static final @NotNull String DESCRIPTION_FILE = "velocity-plugin.json";
    private static final @NotNull Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * Generate Velocity plugin main class and 'velocity-plugin.json' description file.
     *
     * @param data   {@link Plugin} data
     * @param plugin Chameleon plugin main class
     * @param env    Processing environment
     *
     * @throws ChameleonAnnotationException if something goes wrong while creating the files.
     */
    @Override
    public void generate(@NotNull Plugin data, @NotNull TypeElement plugin, @NotNull ProcessingEnvironment env) throws ChameleonAnnotationException {
        ClassName velocityChameleon = clazz("dev.hypera.chameleon.platform.velocity", "VelocityChameleon");
        ClassName proxyServer = clazz("com.velocitypowered.api.proxy", "ProxyServer");
        ClassName logger = clazz("org.slf4j", "Logger");
        ClassName path = clazz("java.nio.file", "Path");

        MethodSpec constructorSpec = MethodSpec.constructorBuilder()
            .addAnnotation(clazz("com.google.inject", "Inject"))
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ParameterSpec.builder(logger, LOGGER_VAR).build())
            .addParameter(ParameterSpec.builder(proxyServer, PROXY_SERVER_VAR).build())
            .addParameter(ParameterSpec.builder(path, DATA_DIRECTORY_VAR)
                .addAnnotation(clazz("com.velocitypowered.api.plugin.annotation", "DataDirectory"))
                .build())
            .addStatement(SET_STATEMENT, PROXY_SERVER_VAR, PROXY_SERVER_VAR)
            .addStatement(SET_STATEMENT, LOGGER_VAR, LOGGER_VAR)
            .addStatement(SET_STATEMENT, DATA_DIRECTORY_VAR, DATA_DIRECTORY_VAR)
            .beginControlFlow("try")
            .addStatement("this.$N = $T.create($T.class, this).load()", CHAMELEON_VAR,
                velocityChameleon, plugin)
            .nextControlFlow("catch ($T ex)", ChameleonInstantiationException.class)
            .addStatement("this.$N.error(\"An error occurred while loading Chameleon\", $N)", LOGGER_VAR, "ex")
            .addStatement("throw new $T($N)", clazz("dev.hypera.chameleon.exception", "ChameleonRuntimeException"), "ex")
            .endControlFlow()
            .build();

        MethodSpec initEventSpec = MethodSpec.methodBuilder("onProxyInitialization")
            .addAnnotation(clazz("com.velocitypowered.api.event", "Subscribe"))
            .addModifiers(Modifier.PUBLIC)
            .addParameter(ParameterSpec.builder(clazz("com.velocitypowered.api.event.proxy", "ProxyInitializeEvent"), "event").build())
            .addStatement("this.$N.onEnable()", CHAMELEON_VAR)
            .build();

        MethodSpec shutdownEventSpec = MethodSpec.methodBuilder("onProxyShutdown")
            .addModifiers(Modifier.PUBLIC)
            .addAnnotation(clazz("com.velocitypowered.api.event", "Subscribe"))
            .addParameter(ParameterSpec.builder(clazz("com.velocitypowered.api.event.proxy", "ProxyShutdownEvent"), "event").build())
            .addStatement("this.$N.onDisable()", CHAMELEON_VAR)
            .build();
        
        MethodSpec getServerSpec = MethodSpec.methodBuilder("getServer")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(proxyServer)
            .addStatement(RETURN_STATEMENT, PROXY_SERVER_VAR)
            .build();

        MethodSpec getLoggerSpec = MethodSpec.methodBuilder("getLogger")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(logger)
            .addStatement(RETURN_STATEMENT, LOGGER_VAR)
            .build();

        MethodSpec getDataDirectorySpec = MethodSpec.methodBuilder("getDataDirectory")
            .addAnnotation(Override.class)
            .addModifiers(Modifier.PUBLIC)
            .returns(path)
            .addStatement(RETURN_STATEMENT, DATA_DIRECTORY_VAR)
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

        for (Dependency dependency : data.dependencies()) {
            AnnotationSpec dependencyAnnotationSpec = AnnotationSpec.builder(clazz("com.velocitypowered.api.plugin", "Dependency"))
                .addMember("id", "$S", dependency.name().toLowerCase(Locale.ROOT))
                .addMember("optional", "$L", dependency.soft()).build();

            pluginAnnotationSpecBuilder.addMember("dependencies", "$L", dependencyAnnotationSpec);
        }

        TypeSpec velocityMainClassSpec = TypeSpec.classBuilder(plugin.getSimpleName() + "Velocity")
            .addAnnotation(pluginAnnotationSpecBuilder.build())
            .addModifiers(Modifier.PUBLIC)
            .addSuperinterface(clazz("dev.hypera.chameleon.platform.velocity", "VelocityPlugin"))
            .addField(FieldSpec.builder(proxyServer, PROXY_SERVER_VAR, Modifier.PRIVATE, Modifier.FINAL).build())
            .addField(FieldSpec.builder(logger, LOGGER_VAR, Modifier.PRIVATE, Modifier.FINAL).build())
            .addField(FieldSpec.builder(path, DATA_DIRECTORY_VAR, Modifier.PRIVATE, Modifier.FINAL).build())
            .addField(FieldSpec.builder(velocityChameleon, CHAMELEON_VAR, Modifier.PRIVATE).build())
            .addMethod(constructorSpec)
            .addMethod(initEventSpec)
            .addMethod(shutdownEventSpec)
            .addMethod(getServerSpec)
            .addMethod(getLoggerSpec)
            .addMethod(getDataDirectorySpec)
            .build();

        String packageName = Objects.requireNonNull((PackageElement) plugin.getEnclosingElement()).getQualifiedName().toString();
        if (packageName.endsWith("core") || packageName.endsWith("common")) {
            packageName = packageName.substring(0, packageName.lastIndexOf("."));
        }
        packageName = packageName + ".platform.velocity";

        try {
            JavaFile.builder(packageName, velocityMainClassSpec).indent(INDENT).build().writeTo(env.getFiler());
            generateDescriptionFile(data, plugin, env, packageName);
        } catch (IOException ex) {
            throw new ChameleonAnnotationException("Failed to write main class or description file", ex);
        }
    }

    private void generateDescriptionFile(@NotNull Plugin data, @NotNull TypeElement plugin, @NotNull ProcessingEnvironment env, @NotNull String packageName) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(env.getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", DESCRIPTION_FILE).toUri()))) {
            GSON.toJson(new SerializedPluginDescription(data, packageName + "." + plugin.getSimpleName() + "Velocity"), writer);
        }
    }

}
