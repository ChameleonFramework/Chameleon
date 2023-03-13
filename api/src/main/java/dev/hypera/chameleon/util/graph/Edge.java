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
package dev.hypera.chameleon.util.graph;

import org.jetbrains.annotations.NotNull;

/**
 * An edge between two nodes.
 *
 * @param <T> Node type.
 */
public interface Edge<T> {

    /**
     * Create a new edge between {@code source} and {@code target}.
     *
     * @param source Edge source.
     * @param target Edge target.
     * @param <T>    Node type.
     *
     * @return new edge.
     */
    static <T> @NotNull Edge<T> of(@NotNull T source, @NotNull T target) {
        return new EdgeImpl<>(source, target);
    }

    /**
     * Returns the source of this edge.
     *
     * @return edge source.
     */
    @NotNull T source();

    /**
     * Returns the target of this edge.
     *
     * @return edge target.
     */
    @NotNull T target();

    /**
     * Returns a new edge with the sources and targets of this edge, but reversed.
     *
     * @return flipped edge.
     */
    @NotNull Edge<T> flip();

}
