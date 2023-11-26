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
package dev.hypera.chameleon.annotations.generator;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a templated file.
 */
public abstract class Templated {

    protected Templated() {

    }

    /**
     * Returns the rendered output of the templated file.
     *
     * @return rendered template.
     */
    public @NotNull String render() {
        return getTemplate().evaluate(getVars());
    }

    protected abstract @NotNull com.google.escapevelocity.Template getTemplate();

    protected abstract @NotNull Map<String, Object> getVars();

    protected @NotNull com.google.escapevelocity.Template parsedTemplateForResource(@NotNull String resourceName) {
        try {
            return com.google.escapevelocity.Template.parseFrom(resourceName, this::resourceOpener);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

    private @NotNull Reader resourceOpener(@NotNull String resourceName) {
        InputStream resourceStream = getClass().getResourceAsStream('/' + resourceName);
        if (resourceStream == null) {
            throw new IllegalArgumentException("Cannot find resource: " + resourceName);
        }
        return new InputStreamReader(resourceStream, StandardCharsets.UTF_8);
    }

}
