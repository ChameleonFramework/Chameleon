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
package dev.hypera.chameleon;

import dev.hypera.chameleon.data.PluginData;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Chameleon} plugin.
 */
public abstract class ChameleonPlugin {

    protected final @NotNull Chameleon chameleon;

    /**
     * {@link ChameleonPlugin} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     */
    public ChameleonPlugin(@NotNull Chameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * Called after Chameleon has been loaded.
     */
    public void onLoad() {

    }

    /**
     * Called when the platform plugin is enabled.
     */
    public abstract void onEnable();

    /**
     * Called when the platform plugin is disabled.
     */
    public abstract void onDisable();

    /**
     * Get {@link PluginData} instance.
     *
     * @return the stored {@link PluginData} instance.
     */
    public final @NotNull PluginData getData() {
        return this.chameleon.getData();
    }

}
