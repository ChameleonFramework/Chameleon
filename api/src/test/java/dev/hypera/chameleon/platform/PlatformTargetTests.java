/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import dev.hypera.chameleon.platform.objects.TestProxyPlatform;
import dev.hypera.chameleon.platform.objects.TestServerPlatform;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

final class PlatformTargetTests {

    private static final @NotNull Platform PROXY_PLATFORM = new TestProxyPlatform();
    private static final @NotNull Platform SERVER_PLATFORM = new TestServerPlatform();

    @Test
    void matchesAll() {
        assertTrue(PlatformTarget.all().matches(PROXY_PLATFORM));
        assertTrue(PlatformTarget.all().matches(SERVER_PLATFORM));
    }

    @Test
    void matchesNone() {
        assertFalse(PlatformTarget.none().matches(PROXY_PLATFORM));
        assertFalse(PlatformTarget.none().matches(SERVER_PLATFORM));
    }

    @Test
    void matchesProxy() {
        assertTrue(PlatformTarget.proxy().matches(PROXY_PLATFORM));
        assertFalse(PlatformTarget.proxy().matches(SERVER_PLATFORM));
    }

    @Test
    void matchesServer() {
        assertFalse(PlatformTarget.server().matches(PROXY_PLATFORM));
        assertTrue(PlatformTarget.server().matches(SERVER_PLATFORM));
    }

    @ParameterizedTest
    @MethodSource("getIdParams")
    void matchesId(@NotNull String id, boolean proxy, boolean server) {
        assertEquals(proxy, PlatformTarget.id(id).matches(PROXY_PLATFORM));
        assertEquals(proxy, Platform.target(id).matches(PROXY_PLATFORM));
        assertEquals(server, PlatformTarget.id(id).matches(SERVER_PLATFORM));
        assertEquals(server, Platform.target(id).matches(SERVER_PLATFORM));
    }

    @Test
    void equality() {
        // id string matches
        assertEquals(Platform.target("test").getId(), "test");

        // #equals matches
        PlatformTarget target = PlatformTarget.id("abc");
        assertEquals(target, target);
        assertEquals(Platform.target("test"), PlatformTarget.id("test"));
        assertEquals(PlatformTarget.id("test"), Platform.target("test"));
        assertNotEquals(PlatformTarget.id("test"), PlatformTarget.id("test2"));
        assertNotEquals(PlatformTarget.id("test"), null);
        assertNotEquals(PlatformTarget.id("test2"), new Object());

        // hashcode matches
        assertEquals(Platform.target("test").hashCode(), PlatformTarget.id("test").hashCode());
    }

    private static @NotNull Stream<Arguments> getIdParams() {
        return Stream.of(
            arguments("TestProxy", true, false),
            arguments("TestServer", false, true),
            arguments("testserver", false, true),
            arguments("Neither", false, false)
        );
    }

}
