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
package dev.hypera.chameleon.platform;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.hypera.chameleon.platform.objects.TestProxyPlatform;
import dev.hypera.chameleon.platform.objects.TestServerPlatform;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

final class PlatformTargetTests {

    private static final @NotNull Platform PROXY_PLATFORM = new TestProxyPlatform();
    private static final @NotNull Platform SERVER_PLATFORM = new TestServerPlatform();

    @Test
    void testAll() {
        assertTrue(PlatformTarget.all().test(PROXY_PLATFORM));
        assertTrue(PlatformTarget.all().test(SERVER_PLATFORM));
    }

    @Test
    void testNone() {
        assertFalse(PlatformTarget.none().test(PROXY_PLATFORM));
        assertFalse(PlatformTarget.none().test(SERVER_PLATFORM));
    }

    @Test
    void testProxy() {
        assertTrue(PlatformTarget.proxy().test(PROXY_PLATFORM));
        assertFalse(PlatformTarget.proxy().test(SERVER_PLATFORM));
    }

    @Test
    void testServer() {
        assertFalse(PlatformTarget.server().test(PROXY_PLATFORM));
        assertTrue(PlatformTarget.server().test(SERVER_PLATFORM));
    }

    @Test
    void testIds() {
        assertTrue(PlatformTarget.id("TestProxy").test(PROXY_PLATFORM));
        assertFalse(PlatformTarget.id("TestProxy").test(SERVER_PLATFORM));
        assertTrue(PlatformTarget.id("TestServer").test(SERVER_PLATFORM));
        assertFalse(PlatformTarget.id("TestServer").test(PROXY_PLATFORM));
        assertTrue(PlatformTarget.id("testserver").test(SERVER_PLATFORM));
        assertFalse(PlatformTarget.id("testserver").test(PROXY_PLATFORM));
        assertFalse(PlatformTarget.id("neither").test(SERVER_PLATFORM));
        assertFalse(PlatformTarget.id("neither").test(PROXY_PLATFORM));
    }

}
