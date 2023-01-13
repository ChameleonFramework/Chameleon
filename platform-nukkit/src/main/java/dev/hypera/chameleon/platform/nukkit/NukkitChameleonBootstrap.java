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
import dev.hypera.chameleon.ChameleonPluginData;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extension.ChameleonExtension;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.nukkit.extension.NukkitChameleonExtension;
import dev.hypera.chameleon.platform.nukkit.logger.ChameleonNukkitLogger;
import java.util.Collection;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit Chameleon bootstrap implementation.
 */
public final class NukkitChameleonBootstrap extends ChameleonBootstrap<NukkitChameleon, NukkitChameleonExtension<?, ?>> {

    private final @NotNull Class<? extends ChameleonPlugin> chameleonPlugin;
    private final @NotNull PluginBase nukkitPlugin;
    private final @NotNull ChameleonPluginData pluginData;

    @Internal
    NukkitChameleonBootstrap(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull PluginBase nukkitPlugin, @NotNull ChameleonPluginData pluginData) {
        this.chameleonPlugin = chameleonPlugin;
        this.nukkitPlugin = nukkitPlugin;
        this.pluginData = pluginData;
    }

    /**
     * {@inheritDoc}
     */
    @Internal
    @Override
    protected @NotNull NukkitChameleon loadInternal(@NotNull Collection<ChameleonExtension<?>> extensions) throws ChameleonInstantiationException {
        return new NukkitChameleon(this.chameleonPlugin, extensions, this.nukkitPlugin, this.pluginData);
    }

    /**
     * {@inheritDoc}
     */
    @Internal
    @Override
    protected @NotNull ChameleonLogger createLogger() {
        return new ChameleonNukkitLogger(this.nukkitPlugin.getLogger());
    }

}
