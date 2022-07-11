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
package dev.hypera.chameleon.annotations.processing;

import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.Plugin.Platform;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

/**
 * Chameleon Annotation Processor.
 */
@SupportedAnnotationTypes("dev.hypera.chameleon.annotations.Plugin")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ChameleonAnnotationProcessor extends AbstractProcessor {

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Plugin.class);

        if (elements.size() >= 1) {
            if (elements.size() > 1) {
                throw new RuntimeException("@Plugin cannot be used more than once");
            }

            Element element = elements.iterator().next();
            if (element.getKind() != ElementKind.CLASS || element.getModifiers().contains(Modifier.ABSTRACT)) {
                throw new RuntimeException("@Plugin cannot be used on abstract classes");
            }

            TypeElement plugin = (TypeElement) element;

            Plugin data = plugin.getAnnotation(Plugin.class);
            Platform[] platforms = data.platforms();
            if (platforms.length < 1) {
                platforms = Platform.values();
            }

            for (Platform platform : platforms) {
                try {
                    platform.getGenerator().getConstructor().newInstance().generate(data, plugin, processingEnv);
                } catch (Exception ex) {
                    throw new RuntimeException("Failed to generate platform data", ex);
                }
            }
        }

        return false;
    }

}
