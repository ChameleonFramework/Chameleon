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
package dev.hypera.chameleon.annotations.processor;

import com.google.auto.service.AutoService;
import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonPluginBootstrap;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.PluginGeneratorOptions;
import dev.hypera.chameleon.annotations.exception.ChameleonAnnotationException;
import dev.hypera.chameleon.annotations.generator.GeneratedClass;
import dev.hypera.chameleon.annotations.generator.GeneratedResource;
import dev.hypera.chameleon.annotations.generator.platform.PlatformPluginGenerator;
import dev.hypera.chameleon.util.internal.ChameleonUtil;
import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Chameleon {@link Plugin} annotation processor.
 */
@AutoService(Processor.class)
public final class PluginAnnotationProcessor extends AbstractProcessor {

    public static final @NotNull String CHAMELEON_PLUGIN_ANNOTATION = Plugin.class.getCanonicalName();

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Plugin.class);
        if (elements.isEmpty()) {
            return false;
        }
        if (elements.size() > 1) {
            throw new ChameleonAnnotationException("@Plugin cannot be used more than once");
        }

        Element element = elements.iterator().next();
        if (element.getKind() != ElementKind.CLASS || element.getModifiers().contains(Modifier.ABSTRACT)) {
            throw new ChameleonAnnotationException("@Plugin cannot be used on abstract classes");
        }

        TypeElement pluginClass = (TypeElement) element;
        Plugin pluginData = pluginClass.getAnnotation(Plugin.class);
        validatePluginAnnotation(pluginData);

        TypeElement bootstrapClass = retrieveBootstrap(pluginClass);
        PlatformPluginGenerator.Options options = PlatformPluginGenerator.createOptions(
            pluginClass.getAnnotation(PluginGeneratorOptions.class)
        );

        for (String platformId : pluginData.platforms()) {
            PlatformPluginGenerator.Context ctx = PlatformPluginGenerator.createContext(
                pluginData, pluginClass, bootstrapClass, platformId, options
            );
            generatePlatformPlugin(ctx);
        }
        return false;
    }

    private void validatePluginAnnotation(@NotNull Plugin plugin) {
        if (ChameleonUtil.isNullOrEmpty(plugin.id())) {
            throw new ChameleonAnnotationException("Validating @Plugin: Plugin ID is required");
        }
        if (ChameleonUtil.isNullOrEmpty(plugin.version())) {
            throw new ChameleonAnnotationException("Validating @Plugin: Plugin version is required");
        }
    }

    private void generatePlatformPlugin(@NotNull PlatformPluginGenerator.Context ctx) {
        PlatformPluginGenerator generator = PlatformPluginGenerator.create(ctx.platformId());

        // Generate classes
        for (GeneratedClass clazz : generator.generateClasses(ctx)) {
            try {
                JavaFileObject javaFile = this.processingEnv.getFiler()
                    .createSourceFile(clazz.fqcn(), ctx.pluginClass());
                try (Writer out = javaFile.openWriter()) {
                    out.write(clazz.content());
                }
            } catch (IOException ex) {
                throw new IllegalStateException("Failed to save generated class: " + clazz.fqcn(), ex);
            }
        }

        // Generate resources
        for (GeneratedResource resource : generator.generateResources(ctx)) {
            try {
                FileObject resourceFile = this.processingEnv.getFiler()
                    .createResource(StandardLocation.SOURCE_OUTPUT, "", resource.name());
                try (Writer out = resourceFile.openWriter()) {
                    out.write(resource.content());
                }
            } catch (IOException ex) {
                throw new IllegalStateException("Failed to save generated resource: " + resource.name(), ex);
            }
        }
    }

    private @Nullable TypeElement retrieveBootstrap(@NotNull TypeElement plugin) {
        TypeMirror bootstrapMirror = getBootstrapFromAnnotation(plugin);
        TypeElement bootstrapElement = bootstrapMirror != null ?
            (TypeElement) this.processingEnv.getTypeUtils().asElement(bootstrapMirror) : null;
        if (bootstrapElement != null && !bootstrapElement.toString().equals(ChameleonPluginBootstrap.class.getName())) {
            return bootstrapElement;
        }
        checkDefaultBootstrapConstructorPresent(plugin);
        return null;
    }

    private void checkDefaultBootstrapConstructorPresent(@NotNull TypeElement plugin) {
        // A plugin bootstrap class was not provided, make sure there is a public
        // constructor that has a single Chameleon parameter.
        Optional<ExecutableElement> constructor = plugin.getEnclosedElements()
            .parallelStream()
            .filter(e -> e.getKind() == ElementKind.CONSTRUCTOR && e.getModifiers().contains(Modifier.PUBLIC))
            .map(e -> (ExecutableElement) e)
            .filter(e -> e.getParameters().size() == 1)
            .filter(e -> this.processingEnv.getTypeUtils().asElement(e.getParameters().get(0).asType()).toString().equals(Chameleon.class.getName()))
            .findAny();
        if (constructor.isEmpty()) {
            throw new ChameleonAnnotationException(
                "Validating @Plugin: Custom plugin bootstrap not provided: " +
                    "ChameleonPlugin implementation must have constructor with only a " +
                    "Chameleon parameter to use the default bootstrap"
            );
        }
    }

    /**
     * Returns the bootstrap class type mirror from the Plugin annotation.
     *
     * @param typeElement Type element to read annotation on.
     *
     * @return bootstrap type mirror, if found, otherwise {@code null}.
     */
    private @Nullable TypeMirror getBootstrapFromAnnotation(@NotNull TypeElement typeElement) {
        AnnotationMirror mirror = getPluginAnnotationMirror(typeElement);
        if (mirror == null) {
            return null;
        }
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : mirror.getElementValues().entrySet()) {
            if (entry.getKey().getSimpleName().toString().equals("bootstrap")) {
                return (TypeMirror) entry.getValue().getValue();
            }
        }
        return null;
    }

    private @Nullable AnnotationMirror getPluginAnnotationMirror(@NotNull TypeElement typeElement) {
        for (AnnotationMirror mirror : typeElement.getAnnotationMirrors()) {
            if (mirror.getAnnotationType().toString().equals(Plugin.class.getName())) {
                return mirror;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(CHAMELEON_PLUGIN_ANNOTATION);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

}
