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

import com.google.escapevelocity.Template;
import java.util.HashMap;
import java.util.Map;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a templated class file.
 */
public final class TemplatedClass extends Templated implements GeneratedClass {

    private final @NotNull String fqcn;
    private final @NotNull String templateResourceName;
    private final @NotNull Map<String, Object> templateVars;

    /**
     * Templated class constructor.
     *
     * @param fqcn                 Fully-qualified class name.
     * @param templateResourceName Template resource name.
     * @param templateVars         Template variables.
     */
    private TemplatedClass(
        @NotNull String fqcn,
        @NotNull String templateResourceName,
        @NotNull Map<String, Object> templateVars
    ) {
        this.fqcn = fqcn;
        this.templateResourceName = templateResourceName;
        this.templateVars = new HashMap<>(templateVars);

        // Add default template variables
        this.templateVars.put("pkg", fqcn.substring(0, fqcn.lastIndexOf('.')));
        this.templateVars.put("className", fqcn.substring(fqcn.lastIndexOf('.') + 1));
    }

    /**
     * Returns a new templated class.
     *
     * @param fqcn                 Fully-qualified class name.
     * @param templateResourceName Template resource name.
     * @param templateVars         Template variables.
     *
     * @return new templated class.
     */
    public static @NotNull TemplatedClass of(
        @NotNull String fqcn,
        @NotNull String templateResourceName,
        @NotNull Map<String, Object> templateVars
    ) {
        return new TemplatedClass(fqcn, templateResourceName, templateVars);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String fqcn() {
        return this.fqcn;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String content() {
        return render();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Template getTemplate() {
        return parsedTemplateForResource(this.templateResourceName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull Map<String, Object> getVars() {
        return this.templateVars;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return this.fqcn;
    }

}
