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

import dev.hypera.chameleon.platform.proxy.ProxyPlatform;
import dev.hypera.chameleon.platform.server.ServerPlatform;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Platform} target.
 * <p>This allows you to target a certain platform or restrict a feature to a certain platform.</p>
 */
public interface PlatformTarget {

    /**
     * Create a new platform target that matches all platforms.
     *
     * @return new platform target.
     */
    static @NotNull PlatformTarget all() {
        return new PlatformTargetImpl("all", p -> true);
    }

    /**
     * Create a new platform target that matches nothing.
     *
     * @return new platform target.
     */
    static @NotNull PlatformTarget none() {
        return new PlatformTargetImpl("none", p -> false);
    }

    /**
     * Create a new platform target that matches all proxy platforms.
     *
     * @return new platform target.
     */
    static @NotNull PlatformTarget proxy() {
        return new PlatformTargetImpl("proxy", p -> p instanceof ProxyPlatform);
    }

    /**
     * Create a new platform target that matches all server platforms.
     *
     * @return new platform target.
     */
    static @NotNull PlatformTarget server() {
        return new PlatformTargetImpl("server", p -> p instanceof ServerPlatform);
    }

    /**
     * Create a new platform target that matches platforms with the given {@code id}.
     *
     * @param id Target platform identifier.
     *
     * @return new platform target.
     */
    static @NotNull PlatformTarget id(@NotNull String id) {
        return new PlatformTargetImpl(id, p -> p.getId().equalsIgnoreCase(id));
    }

    /**
     * Get the target platform identifier.
     *
     * @return target identifier.
     */
    @NotNull String getId();

    /**
     * Check if the given platform matches this target.
     *
     * @param platform Platform.
     *
     * @return {@code true} if the given platform matches this target, otherwise {@code false}.
     */
    boolean matches(@NotNull Platform platform);

}
