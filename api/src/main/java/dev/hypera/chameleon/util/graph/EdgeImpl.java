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
package dev.hypera.chameleon.util.graph;

import java.util.Objects;
import org.jetbrains.annotations.NotNull;

final class EdgeImpl<T> implements Edge<T> {

    private final @NotNull T source;
    private final @NotNull T target;

    EdgeImpl(@NotNull T source, @NotNull T target) {
        this.source = source;
        this.target = target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull T source() {
        return this.source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull T target() {
        return this.target;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Edge<T> flip() {
        return Edge.of(this.target, this.source);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Edge)) {
            return false;
        }

        Edge<?> edge = (Edge<?>) o;
        return this.source.equals(edge.source()) && this.target.equals(edge.target());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.source, this.target);
    }

}
