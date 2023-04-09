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
package dev.hypera.chameleon.platform.bukkit.platform;

import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.server.GameMode;
import dev.hypera.chameleon.platform.server.ServerPlatform;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit server platform implementation.
 */
@Internal
public final class BukkitPlatform implements ServerPlatform {

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getId() {
        return Platform.BUKKIT;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return Bukkit.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getVersion() {
        return Bukkit.getVersion();
    }


    /**
     * Converts a chameleon {@link GameMode GameMode} to the corresponding bukkit {@link org.bukkit.GameMode org.bukkit.GameMode}.
     *
     * @param gameMode the chameleon {@link GameMode GameMode}
     * @return the corresponding bukkit {@link org.bukkit.GameMode org.bukkit.GameMode}
     */
    public static org.bukkit.@NotNull GameMode convertGameModeToBukkit(@NotNull GameMode gameMode) {
        switch (gameMode) {
            case CREATIVE:
                return org.bukkit.GameMode.CREATIVE;
            case ADVENTURE:
                return org.bukkit.GameMode.ADVENTURE;
            case SPECTATOR:
                return org.bukkit.GameMode.SPECTATOR;
            default:
                return org.bukkit.GameMode.SURVIVAL;
        }
    }

    /**
     * Converts a bukkit {@link org.bukkit.GameMode org.bukkit.GameMode} to the corresponding chameleon {@link GameMode GameMode}.
     *
     * @param gameMode the bukkit {@link org.bukkit.GameMode org.bukkit.GameMode}
     * @return the corresponding chameleon {@link GameMode GameMode}
     */
    public static @NotNull GameMode convertGameModeToChameleon(org.bukkit.@NotNull GameMode gameMode) {
        switch (gameMode) {
            case CREATIVE:
                return GameMode.CREATIVE;
            case ADVENTURE:
                return GameMode.ADVENTURE;
            case SPECTATOR:
                return GameMode.SPECTATOR;
            default:
                return GameMode.SURVIVAL;
        }
    }
}
