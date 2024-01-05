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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

final class EdgeTests {

    @Test
    void testEquality() {
        Edge<Integer> edge = Edge.of(1, 2);
        assertEquals(edge, edge);
        assertNotEquals(null, edge);
        assertNotEquals(edge, new Object());
        assertEquals(Edge.of(1, 2), Edge.of(1, 2));
        assertEquals(Edge.of("test", "test2"), Edge.of("test", "test2"));
        assertNotEquals(Edge.of(1, 2), Edge.of(1, 3));
        assertNotEquals(Edge.of(2, 3), Edge.of(1, 3));
    }

    @Test
    void testFlip() {
        assertThat(Edge.of(1, 2).flip()).isEqualTo(Edge.of(2, 1));
        assertThat(Edge.of("test", "test2").flip()).isEqualTo(Edge.of("test2", "test"));
    }

}
