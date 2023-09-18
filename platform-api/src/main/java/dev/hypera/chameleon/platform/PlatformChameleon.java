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

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonPluginBootstrap;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.extension.ExtensionMap;
import dev.hypera.chameleon.logger.ChameleonLogger;
import org.jetbrains.annotations.NotNull;

/**
 * Platform Chameleon provides access to the platform plugin.
 * <p><strong>This class is designed for use inside Chameleon platform implementations only. This
 * API is subject to change and use will not be supported.</strong></p>
 *
 * @param <P> Platform plugin type.
 */
public abstract class PlatformChameleon<P> extends Chameleon {

    protected final @NotNull P plugin;

    protected PlatformChameleon(
        @NotNull ChameleonPluginBootstrap pluginBootstrap,
        @NotNull P platformPlugin,
        @NotNull EventBus eventBus,
        @NotNull ChameleonLogger logger,
        @NotNull ExtensionMap extensions
    ) {
        super(pluginBootstrap, eventBus, logger, extensions);
        this.plugin = platformPlugin;
    }

    /**
     * Returns the underlying platform plugin.
     *
     * @return platform plugin.
     */
    public final @NotNull P getPlatformPlugin() {
        return this.plugin;
    }

}
