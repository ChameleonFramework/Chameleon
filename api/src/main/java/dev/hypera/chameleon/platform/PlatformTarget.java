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

import dev.hypera.chameleon.platform.proxy.ProxyPlatform;
import dev.hypera.chameleon.platform.server.ServerPlatform;
import dev.hypera.chameleon.util.Preconditions;
import java.util.function.Predicate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Platform target.
 * <p>This allows you to target a certain platform or restrict a feature to a certain platform.</p>
 */
@FunctionalInterface
public interface PlatformTarget extends Predicate<Platform> {

    /**
     * Create a new platform target that matches all platforms.
     *
     * @return new platform target.
     */
    @Contract(pure = true)
    static @NotNull PlatformTarget all() {
        return p -> true;
    }

    /**
     * Create a new platform target that matches nothing.
     *
     * @return new platform target.
     */
    @Contract(pure = true)
    static @NotNull PlatformTarget none() {
        return p -> false;
    }

    /**
     * Create a new platform target that matches all proxy platforms.
     *
     * @return new platform target.
     */
    @Contract(pure = true)
    static @NotNull PlatformTarget proxy() {
        return ProxyPlatform.class::isInstance;
    }

    /**
     * Create a new platform target that matches all server platforms.
     *
     * @return new platform target.
     */
    @Contract(pure = true)
    static @NotNull PlatformTarget server() {
        return ServerPlatform.class::isInstance;
    }

    /**
     * Create a new platform target that matches Bukkit.
     *
     * @return new platform target.
     */
    @Contract(pure = true)
    static @NotNull PlatformTarget bukkit() {
        return id(Platform.BUKKIT, true);
    }

    /**
     * Create a new platform target that matches BungeeCord.
     *
     * @return new platform target.
     */
    @Contract(pure = true)
    static @NotNull PlatformTarget bungeeCord() {
        return id(Platform.BUNGEECORD, true);
    }

    /**
     * Create a new platform target that matches Nukkit.
     *
     * @return new platform target.
     */
    @Contract(pure = true)
    static @NotNull PlatformTarget nukkit() {
        return id(Platform.NUKKIT, true);
    }

    /**
     * Create a new platform target that matches Sponge.
     *
     * @return new platform target.
     */
    @Contract(pure = true)
    static @NotNull PlatformTarget sponge() {
        return id(Platform.SPONGE, true);
    }

    /**
     * Create a new platform target that matches Sponge.
     *
     * @return new platform target.
     */
    @Contract(pure = true)
    static @NotNull PlatformTarget velocity() {
        return id(Platform.VELOCITY, true);
    }

    /**
     * Create a new platform target that matches platforms with the given {@code id}.
     *
     * @param id Target platform identifier.
     *
     * @return new platform target.
     */
    @Contract(pure = true)
    static @NotNull PlatformTarget id(@NotNull String id) {
        return id(id, false);
    }

    /**
     * Create a new platform target that matches platforms with the given {@code id}.
     *
     * @param id     Target platform identifier.
     * @param strict Whether the id equality should be case-sensitive.
     *
     * @return new platform target.
     */
    @Contract(pure = true)
    static @NotNull PlatformTarget id(@NotNull String id, boolean strict) {
        Preconditions.checkNotNull("id", id);

        if (strict) {
            return p -> p.getId().equals(id);
        } else {
            return p -> p.getId().equalsIgnoreCase(id);
        }
    }

    /**
     * Check if the given platform matches this target.
     *
     * @param platform Platform.
     *
     * @return {@code true} if the given platform matches this target, otherwise {@code false}.
     */
    @Override
    boolean test(@NotNull Platform platform);

    /**
     * Returns a new platform target that matches this target, and the {@code other} target.
     *
     * @param other Other platform target.
     *
     * @return new platform target.
     */
    @Contract(value = "_ -> new", pure = true)
    default @NotNull PlatformTarget and(@NotNull PlatformTarget other) {
        Preconditions.checkNotNull("other", other);
        return p -> test(p) && other.test(p);
    }

    /**
     * Returns a new platform target that matches this target, or the {@code other} target.
     *
     * @param other Other platform target.
     *
     * @return new platform target.
     */
    @Contract(value = "_ -> new", pure = true)
    default @NotNull PlatformTarget or(@NotNull PlatformTarget other) {
        Preconditions.checkNotNull("other", other);
        return p -> test(p) || other.test(p);
    }

    /**
     * Returns a new platform target that negates the result of this target.
     *
     * @return new platform target.
     */
    @Override
    @Contract(value = "-> new", pure = true)
    default @NotNull PlatformTarget negate() {
        return p -> !test(p);
    }

}
