/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package dev.hypera.chameleon.platform;

import dev.hypera.chameleon.annotations.PlatformSpecific;
import dev.hypera.chameleon.platform.proxy.ProxyPlatform;
import dev.hypera.chameleon.platform.server.ServerPlatform;
import org.jetbrains.annotations.NotNull;

/**
 * Platform.
 */
public abstract class Platform {

    /**
     * Get API name.
     *
     * @return API name.
     */
    public abstract @NotNull String getAPIName();

    /**
     * Get name.
     *
     * @return name.
     */
    public abstract @NotNull String getName();

    /**
     * Get version.
     *
     * @return version.
     */
    public abstract @NotNull String getVersion();

    /**
     * Get {@link Type}.
     *
     * @return {@link Type}.
     */
    public abstract @NotNull Type getType();


    /**
     * Cast this {@link Platform} instance to an {@link ProxyPlatform} instance.
     *
     * @return {@link ProxyPlatform}.
     * @throws IllegalStateException if this {@link Platform} is not an {@link ProxyPlatform}.
     */
    @PlatformSpecific(Type.PROXY)
    public final @NotNull ProxyPlatform proxy() {
        if (this instanceof ProxyPlatform) {
            return (ProxyPlatform) this;
        } else {
            throw new IllegalStateException("Cannot cast to ProxyPlatform");
        }
    }

    /**
     * Cast this {@link Platform} instance to an {@link ServerPlatform} instance.
     *
     * @return {@link ServerPlatform}.
     * @throws IllegalStateException if this {@link Platform} is not an {@link ServerPlatform}.
     */
    @PlatformSpecific(Type.SERVER)
    public final @NotNull ServerPlatform server() {
        if (this instanceof ServerPlatform) {
            return (ServerPlatform) this;
        } else {
            throw new IllegalStateException("Cannot cast to ServerPlatform");
        }
    }

    /**
     * Platform type.
     */
    public enum Type {
        SERVER, PROXY
    }

}
