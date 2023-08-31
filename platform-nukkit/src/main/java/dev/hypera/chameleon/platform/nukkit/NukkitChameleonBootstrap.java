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
package dev.hypera.chameleon.platform.nukkit;

import cn.nukkit.plugin.PluginBase;
import dev.hypera.chameleon.ChameleonBootstrap;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.nukkit.logger.ChameleonNukkitLogger;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit Chameleon bootstrap implementation.
 */
final class NukkitChameleonBootstrap extends ChameleonBootstrap<NukkitChameleon> {

    private final @NotNull Class<? extends ChameleonPlugin> chameleonPlugin;
    private final @NotNull PluginBase nukkitPlugin;

    @Internal
    NukkitChameleonBootstrap(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull PluginBase nukkitPlugin) {
        super(new ChameleonNukkitLogger(nukkitPlugin.getLogger()), Platform.NUKKIT);
        this.chameleonPlugin = chameleonPlugin;
        this.nukkitPlugin = nukkitPlugin;
    }

    @Override
    protected @NotNull NukkitChameleon loadInternal() throws ChameleonInstantiationException {
        return new NukkitChameleon(
            this.chameleonPlugin, this.nukkitPlugin,
            this.eventBus, this.logger, this.extensions
        );
    }

}
