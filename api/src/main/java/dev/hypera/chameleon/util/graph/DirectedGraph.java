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

import dev.hypera.chameleon.util.Preconditions;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

/**
 * Directed graph implementation.
 * <p>This is not the best directed graph implementation, however it is enough for what Chameleon
 * currently needs.</p>
 * <p>If you know more about graphs and want to improve this implementation, or maybe add other
 * graph implementations, please create a pull request :)</p>
 *
 * @param <T> Node type.
 */
final class DirectedGraph<T> implements Graph<T> {

    private final @NotNull Collection<T> nodes = new HashSet<>();
    private final @NotNull Collection<Edge<T>> edges = new HashSet<>();
    private final boolean allowSelfLoops;

    DirectedGraph(boolean allowSelfLoops) {
        this.allowSelfLoops = allowSelfLoops;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<T> nodes() {
        return Collections.unmodifiableCollection(this.nodes);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<Edge<T>> edges() {
        return Collections.unmodifiableCollection(this.edges);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<T> adjacentNodes(@NotNull T node) {
        Preconditions.checkArgument(this.nodes.contains(node),
            "provided node is not a node of this graph"
        );
        return Collections.unmodifiableCollection(Stream.concat(predecessors(node).stream(),
            successors(node).stream()
        ).collect(Collectors.toSet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<T> predecessors(@NotNull T node) {
        Preconditions.checkArgument(this.nodes.contains(node),
            "provided node is not a node of this graph"
        );
        return Collections.unmodifiableCollection(this.edges.parallelStream()
            .filter(e -> e.target().equals(node))
            .map(Edge::source)
            .collect(Collectors.toSet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Collection<T> successors(@NotNull T node) {
        Preconditions.checkArgument(this.nodes.contains(node),
            "provided node is not a node of this graph"
        );
        return Collections.unmodifiableCollection(this.edges.parallelStream()
            .filter(e -> e.source().equals(node))
            .map(Edge::target)
            .collect(Collectors.toSet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addNode(@NotNull T node) {
        return this.nodes.add(node);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeNode(@NotNull T node) {
        boolean modified = this.nodes.remove(node);
        modified |= this.edges.removeIf(e -> e.source().equals(node) || e.target().equals(node));
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean putEdge(@NotNull Edge<T> edge) {
        checkSelfLoops(edge);
        boolean modified = this.nodes.add(edge.source());
        modified |= this.nodes.add(edge.target());
        modified |= this.edges.add(edge);
        return modified;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(@NotNull Edge<T> edge) {
        return this.edges.remove(edge);
    }

    private void checkSelfLoops(@NotNull Edge<T> edge) {
        if (!this.allowSelfLoops) {
            Preconditions.checkArgument(!edge.source().equals(edge.target()),
                "self loops are not allowed"
            );
        }
    }

    static final class BuilderImpl<T> implements Builder<T> {

        private boolean allowSelfLoops = false;

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<T> allowSelfLoops() {
            return allowSelfLoops(true);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder<T> allowSelfLoops(boolean allowSelfLoops) {
            this.allowSelfLoops = allowSelfLoops;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Graph<T> build() {
            return new DirectedGraph<>(this.allowSelfLoops);
        }

    }

}
