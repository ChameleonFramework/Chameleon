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
package dev.hypera.chameleon.util.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class ChameleonPropertyTests {

    @BeforeEach
    void setup() {
        ChameleonProperty.DEBUG.reset();
        ChameleonProperty.LOG_ERRORS.reset();
        System.setProperty("dev.hypera.chameleon.debug", "");
    }

    @Test
    void testName() {
        assertEquals("debug", ChameleonProperty.DEBUG.name());
    }

    @Test
    void testDefaults() {
        // DEBUG property should be false by default
        assertFalse(ChameleonProperty.DEBUG.get());

        // LOG_ERRORS property should be true by default
        assertTrue(ChameleonProperty.LOG_ERRORS.get());
    }

    @Test
    void testSystemProperties() {
        // #get() should read from system properties
        System.setProperty("dev.hypera.chameleon.debug", "true");
        assertTrue(ChameleonProperty.DEBUG.get());

        // #get() should cache the value from system properties
        System.setProperty("dev.hypera.chameleon.debug", "false");
        assertTrue(ChameleonProperty.DEBUG.get());

        // #reset() should reset the cached value
        System.setProperty("dev.hypera.chameleon.debug", "true");
        ChameleonProperty.DEBUG.reset();
        assertTrue(ChameleonProperty.DEBUG.get());
    }

    @Test
    void test() {
        // #set() should change the cached value
        ChameleonProperty.DEBUG.set(true);
        assertTrue(ChameleonProperty.DEBUG.get());
    }

}
