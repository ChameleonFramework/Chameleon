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
package dev.hypera.chameleon.extension;

import dev.hypera.chameleon.exception.extension.ChameleonExtensionException;
import dev.hypera.chameleon.util.Pair;
import dev.hypera.chameleon.util.graph.Graph;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Extension map.
 */
@Internal
public final class ExtensionMap extends ConcurrentHashMap<Class<? extends ChameleonExtension>, Pair<ChameleonPlatformExtension, Collection<ChameleonExtensionDependency>>> {

    private static final long serialVersionUID = 9010417357635273389L;

    /**
     * Get the extension with the given class.
     *
     * @param key Extension class.
     * @param <T> Extension type.
     *
     * @return extension.
     */
    @Contract(value = "_ -> _", pure = true)
    public <T extends ChameleonExtension> Optional<T> getExtension(Class<T> key) {
        return Optional.ofNullable(super.get(key)).map(Pair::first).map(key::cast);
    }

    /**
     * Attempt to sort all extensions to be loaded by Chameleon in dependency order, using a
     * depth-first search.
     *
     * @return sorted extensions.
     * @throws ChameleonExtensionException if a circular dependency was detected.
     */
    public @NotNull List<ChameleonPlatformExtension> loadSort() {
        Graph<Class<? extends ChameleonExtension>> graph = Graph.<Class<? extends ChameleonExtension>>directed().build();
        for (Entry<Class<? extends ChameleonExtension>, Pair<ChameleonPlatformExtension, Collection<ChameleonExtensionDependency>>> ext : entrySet()) {
            // Add this extension to the graph.
            graph.addNode(ext.getKey());

            // Add the dependencies of this extension as edges to this node.
            for (ChameleonExtensionDependency dependency : ext.getValue().second()) {
                // Get the dependency extension class, if present. If the dependency extension class
                // is not present, it might mean that the class is not in the classpath, there is a
                // typo, or the class doesn't exist.
                Class<? extends ChameleonExtension> dependencyClass = dependency.extension().orElse(null);
                if (dependencyClass != null) {
                    if (get(dependencyClass) != null) {
                        graph.putEdge(ext.getKey(), dependencyClass);
                    }
                } else if (dependency.required()) {
                    throw ChameleonExtensionException.create(
                        "%s requires dependencies but some are missing: %s",
                        ext.getKey().getSimpleName(), dependency.name()
                    );
                }
            }
        }

        // None of the extensions have dependencies, therefore we don't need to sort the extensions.
        if (graph.edges().isEmpty()) {
            return values().stream().map(Pair::first).collect(Collectors.toUnmodifiableList());
        }

        // Sort the extensions by preforming a depth-first search.
        List<ChameleonPlatformExtension> sortedExtensions = new ArrayList<>();
        Map<Class<? extends ChameleonExtension>, VisitState> visitedNodes = new HashMap<>();
        for (Class<? extends ChameleonExtension> node : graph.nodes()) {
            visitNode(graph, node, visitedNodes, sortedExtensions, new ArrayDeque<>());
        }
        return sortedExtensions;
    }

    private void visitNode(
        @NotNull Graph<Class<? extends ChameleonExtension>> graph,
        @NotNull Class<? extends ChameleonExtension> node,
        @NotNull Map<Class<? extends ChameleonExtension>, VisitState> visitedNodes,
        @NotNull List<ChameleonPlatformExtension> sortedExtensions,
        @NotNull Deque<Class<? extends ChameleonExtension>> scanStack
    ) {
        VisitState state = visitedNodes.get(node);
        if (state == VisitState.COMPLETE) {
            // This node has already been visited.
            return;
        }

        scanStack.addLast(node);
        if (state == VisitState.PENDING) {
            // Detected a circular dependency.
            throw new ChameleonExtensionException("Detected circular dependencies: " +
                scanStack.parallelStream().map(Class::getSimpleName)
                    .collect(Collectors.joining(" -> "))
            );
        }

        // Visit dependencies of this extension.
        visitedNodes.put(node, VisitState.PENDING);
        for (Class<? extends ChameleonExtension> edge : graph.successors(node)) {
            visitNode(graph, edge, visitedNodes, sortedExtensions, scanStack);
        }

        visitedNodes.put(node, VisitState.COMPLETE);
        scanStack.removeLast();
        sortedExtensions.add(Objects.requireNonNull(get(node)).first());
    }

    private enum VisitState {
        PENDING,
        COMPLETE
    }

}
