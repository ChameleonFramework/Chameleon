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
package dev.hypera.chameleon.extensions;

import dev.hypera.chameleon.Chameleon;
import org.jetbrains.annotations.NotNull;

/**
 * Extension.
 *
 * @param <T> {@link CustomPlatformExtension} type.
 */
public abstract class ChameleonExtension<T extends CustomPlatformExtension> {

    protected final @NotNull T platform;

    /**
     * {@link ChameleonExtension} constructor.
     *
     * @param platform {@link CustomPlatformExtension} instance.
     */
    public ChameleonExtension(@NotNull T platform) {
        this.platform = platform;
    }

    /**
     * Called before Chameleon is loaded.
     */
    public void onPreLoad() {

    }

    /**
     * Called after Chameleon has loaded.
     *
     * @param chameleon Initialised {@link Chameleon} instance.
     */
    public void onLoad(@NotNull Chameleon chameleon) {

    }

    /**
     * Called when the platform plugin is enabled.
     */
    public void onEnable() {

    }

    /**
     * Called when the platform plugin is disabled.
     */
    public void onDisable() {

    }

}
