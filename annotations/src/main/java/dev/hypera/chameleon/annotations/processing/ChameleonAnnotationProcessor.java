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
package dev.hypera.chameleon.annotations.processing;

import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.annotations.exception.ChameleonAnnotationException;
import dev.hypera.chameleon.annotations.processing.generation.Generator;
import dev.hypera.chameleon.annotations.processing.generation.bukkit.BukkitGenerator;
import dev.hypera.chameleon.annotations.processing.generation.bungeecord.BungeeCordGenerator;
import dev.hypera.chameleon.annotations.processing.generation.minestom.MinestomGenerator;
import dev.hypera.chameleon.annotations.processing.generation.nukkit.NukkitGenerator;
import dev.hypera.chameleon.annotations.processing.generation.sponge.SpongeGenerator;
import dev.hypera.chameleon.annotations.processing.generation.velocity.VelocityGenerator;
import dev.hypera.chameleon.platform.Platform;
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

        if (!elements.isEmpty()) {
            if (elements.size() > 1) {
                throw new ChameleonAnnotationException("@Plugin cannot be used more than once");
            }

            Element element = elements.iterator().next();
            if (element.getKind() != ElementKind.CLASS || element.getModifiers().contains(Modifier.ABSTRACT)) {
                throw new ChameleonAnnotationException("@Plugin cannot be used on abstract classes");
            }

            TypeElement plugin = (TypeElement) element;

            Plugin data = plugin.getAnnotation(Plugin.class);
            for (String platform : data.platforms()) {
                Generator generator;
                switch (platform) {
                    case Platform.BUKKIT:
                        generator = new BukkitGenerator();
                        break;
                    case Platform.BUNGEECORD:
                        generator = new BungeeCordGenerator();
                        break;
                    case Platform.MINESTOM:
                        generator = new MinestomGenerator();
                        break;
                    case Platform.NUKKIT:
                        generator = new NukkitGenerator();
                        break;
                    case Platform.SPONGE:
                        generator = new SpongeGenerator();
                        break;
                    case Platform.VELOCITY:
                        generator = new VelocityGenerator();
                        break;
                    default:
                        throw new IllegalStateException("Invalid platform: " + platform);
                }

                generator.generate(data, plugin, processingEnv);
            }
        }

        return false;
    }

}
