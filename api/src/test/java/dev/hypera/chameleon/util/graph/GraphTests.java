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

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.Collection;
import org.junit.jupiter.api.Test;

final class GraphTests {

    @Test
    void testNodesReturnedCollectionMutability() {
        Graph<Integer> graph = Graph.<Integer>directed().build();
        graph.addNode(1);

        Collection<Integer> nodes = graph.nodes();
        assertThrows(UnsupportedOperationException.class, () -> nodes.add(2));
        assertThrows(UnsupportedOperationException.class, () -> nodes.remove(1));
    }

    @Test
    void testEdgesReturnedCollectionMutability() {
        Graph<Integer> graph = Graph.<Integer>directed().build();
        graph.addNode(1);

        Edge<Integer> testEdge = Edge.of(1, 2);
        Collection<Edge<Integer>> edges = graph.edges();
        assertThrows(UnsupportedOperationException.class, () -> edges.add(testEdge));
        assertThrows(UnsupportedOperationException.class, () -> edges.remove(testEdge));
    }

    @Test
    void testAdjacentNodesReturnedCollectionMutability() {
        Graph<Integer> graph = Graph.<Integer>directed().build();
        graph.addNode(1);

        Collection<Integer> adjacentNodes = graph.adjacentNodes(1);
        assertThrows(UnsupportedOperationException.class, () -> adjacentNodes.add(2));
        assertThrows(UnsupportedOperationException.class, () -> adjacentNodes.remove(1));
    }

    @Test
    void testPredecessorsReturnedCollectionMutability() {
        Graph<Integer> graph = Graph.<Integer>directed().build();
        graph.addNode(1);

        Collection<Integer> predecessors = graph.predecessors(1);
        assertThrows(UnsupportedOperationException.class, () -> predecessors.add(2));
        assertThrows(UnsupportedOperationException.class, () -> predecessors.remove(1));
    }

    @Test
    void testSuccessorsReturnedCollectionMutability() {
        Graph<Integer> graph = Graph.<Integer>directed().build();
        graph.addNode(1);

        Collection<Integer> successors = graph.successors(1);
        assertThrows(UnsupportedOperationException.class, () -> successors.add(2));
        assertThrows(UnsupportedOperationException.class, () -> successors.remove(1));
    }

    @Test
    void testPredecessorsOneEdge() {
        Graph<Integer> graph = Graph.<Integer>directed().build();
        graph.putEdge(1, 2);
        assertThat(graph.predecessors(2)).containsExactly(1);
        assertThat(graph.predecessors(1)).isEmpty();
    }

    @Test
    void testSuccessorsOneEdge() {
        Graph<Integer> graph = Graph.<Integer>directed().build();
        graph.putEdge(1, 2);
        assertThat(graph.successors(1)).containsExactly(2);
        assertThat(graph.successors(2)).isEmpty();
    }

    @Test
    void testRemoveNodeRemovesEdges() {
        Graph<Integer> graph = Graph.<Integer>directed().build();
        graph.putEdge(1, 2);
        graph.putEdge(2, 1);
        assertThat(graph.nodes()).containsExactly(1, 2);
        assertThat(graph.edges()).hasSize(2);
        graph.removeNode(1);
        assertThat(graph.nodes()).containsExactly(2);
        assertThat(graph.edges()).hasSize(0);
    }

    @Test
    void testRemoveEdges() {
        Graph<Integer> graph = Graph.<Integer>directed().build();
        graph.putEdge(1, 2);
        assertThat(graph.nodes()).containsExactly(1, 2);
        assertThat(graph.edges()).hasSize(1);
        graph.removeEdge(1, 2);
        assertThat(graph.nodes()).containsExactly(1, 2);
        assertThat(graph.edges()).hasSize(0);
    }

    @Test
    void testPreventsSelfLoops() {
        // Verify that a graph that does not allow self-loops will prevent self-loops.
        Graph<Integer> graph = Graph.<Integer>directed().allowSelfLoops(false).build();
        assertThrowsExactly(IllegalArgumentException.class, () ->
            graph.putEdge(1, 1));

        // Verify that a graph with allow self-loops will actually allow a self-loop.
        Graph<Integer> graph2 = Graph.<Integer>directed().allowSelfLoops().build();
        assertDoesNotThrow(() -> graph2.putEdge(1, 1));
    }

}
