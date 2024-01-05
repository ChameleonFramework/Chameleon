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

import dev.hypera.chameleon.util.graph.DirectedGraph.BuilderImpl;
import java.util.Collection;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Graph.
 *
 * @param <T> Node type.
 */
public interface Graph<T> {

    /**
     * Create a new directed graph builder.
     *
     * @param <T> Graph node type.
     *
     * @return new builder.
     */
    @Contract(value = "-> new", pure = true)
    static <T> @NotNull Builder<T> directed() {
        return new DirectedGraph.BuilderImpl<>();
    }

    /**
     * Returns all nodes in this graph.
     *
     * @return graph nodes.
     */
    @Contract(value = "-> _", pure = true)
    @NotNull Collection<T> nodes();

    /**
     * Returns all edges in this graph.
     *
     * @return graph edges.
     */
    @Contract(value = "-> _", pure = true)
    @NotNull Collection<Edge<T>> edges();

    /**
     * Returns the nodes which have an edge with {@code node} in this graph.
     * <p>This is equivalent to the union of {@link #predecessors(Object)} and
     * {@link #successors(Object)}</p>
     *
     * @param node Node.
     *
     * @return adjacent nodes to {@code node}.
     */
    @Contract(value = "_ -> _", pure = true)
    @NotNull Collection<T> adjacentNodes(@NotNull T node);

    /**
     * Returns the nodes in this graph that have an edge with {@code node} as a target.
     * <p>In an undirected graph this is equivalent to {@link #adjacentNodes(Object)}.</p>
     *
     * @param node Node to get predecessors.
     *
     * @return predecessors to {@code node}.
     */
    @Contract(value = "_ -> _", pure = true)
    @NotNull Collection<T> predecessors(@NotNull T node);

    /**
     * Returns the nodes in this graph that have an edge with {@code node} as a source.
     * <p>In an undirected graph this is equivalent to {@link #adjacentNodes(Object)}.</p>
     *
     * @param node Node to get successors of.
     *
     * @return successors to {@code node}.
     */
    @Contract(value = "_ -> _", pure = true)
    @NotNull Collection<T> successors(@NotNull T node);

    /**
     * Adds {@code node} to this graph, if it is not already present.
     *
     * @param node Node to be added.
     *
     * @return {@code true} if the graph changed as a result of this call.
     */
    @Contract("_ -> _")
    boolean addNode(@NotNull T node);

    /**
     * Removes {@code node} from this graph, if it is present.
     * <p>All edges connecting to {@code node} will also be removed.</p>
     *
     * @param node Node to be removed.
     *
     * @return {@code true} if the graph changed as a result of this call.
     */
    @Contract("_ -> _")
    boolean removeNode(@NotNull T node);

    /**
     * Adds an edge connecting {@code source} and {@code target}, if not already present.
     * <p>If this graph is directed, the edge will also be directed; otherwise it will be
     * undirected.</p>
     *
     * @param source Source node.
     * @param target Target node.
     *
     * @return {@code true} if the graph changed as a result of this call.
     * @throws IllegalArgumentException if this graph does not allow self-loops and this edge is a
     *                                  self-loop.
     */
    @Contract("_, _ -> _")
    default boolean putEdge(@NotNull T source, @NotNull T target) {
        return putEdge(Edge.of(source, target));
    }

    /**
     * Adds an edge connecting {@link Edge#source()} and {@link Edge#target()}, if not already
     * present.
     * <p>If this graph is directed, the edge will also be directed; otherwise it will be
     * undirected.</p>
     *
     * @param edge Edge to be added.
     *
     * @return {@code true} if the graph changed as a result of this call.
     * @throws IllegalArgumentException if this graph does not allow self-loops and this edge is a
     *                                  self-loop.
     */
    @Contract("_ -> _")
    boolean putEdge(@NotNull Edge<T> edge);

    /**
     * Removes the edge connecting {@code source} and {@code target}, if present.
     *
     * @param source Source node.
     * @param target Target node.
     *
     * @return {@code true} if the graph changed as a result of this call.
     */
    @Contract("_, _ -> _")
    default boolean removeEdge(@NotNull T source, @NotNull T target) {
        return removeEdge(Edge.of(source, target));
    }

    /**
     * Removes the edge connecting {@link Edge#source()} and {@link Edge#target()}, if present.
     *
     * @param edge Edge to be removed.
     *
     * @return {@code true} if the graph changed as a result of this call.
     */
    @Contract("_ -> _")
    boolean removeEdge(@NotNull Edge<T> edge);

    /**
     * Graph builder.
     *
     * @param <T> Node type.
     */
    @NonExtendable
    interface Builder<T> {

        /**
         * Allow self-loops.
         *
         * @return {@code this}.
         */
        @Contract("-> this")
        @NotNull Builder<T> allowSelfLoops();

        /**
         * Sets whether this graph should allow self-loops.
         *
         * <p>Attempting to add a self-loop to a graph with self-loops not allowed will cause an
         * IllegalArgumentException to be thrown.</p>
         *
         * <p>Defaults to {@code false}.</p>
         *
         * @param allowSelfLoops Whether self-loops should be allowed.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder<T> allowSelfLoops(boolean allowSelfLoops);

        /**
         * Build graph.
         *
         * @return new graph.
         */
        @Contract(value = "-> new", pure = true)
        @NotNull Graph<T> build();

    }

}
