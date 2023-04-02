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
package dev.hypera.chameleon.platform.bukkit;

import dev.hypera.chameleon.ChameleonBootstrap;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.ChameleonPluginData;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.logger.ChameleonJavaLogger;
import dev.hypera.chameleon.platform.Platform;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit Chameleon bootstrap implementation.
 */
public final class BukkitChameleonBootstrap extends ChameleonBootstrap<BukkitChameleon> {

    private final @NotNull Class<? extends ChameleonPlugin> chameleonPlugin;
    private final @NotNull JavaPlugin bukkitPlugin;
    private final @NotNull ChameleonPluginData pluginData;

    @Internal
    BukkitChameleonBootstrap(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull JavaPlugin bukkitPlugin, @NotNull ChameleonPluginData pluginData) {
        super(new ChameleonJavaLogger(bukkitPlugin.getLogger()), Platform.BUKKIT);
        this.chameleonPlugin = chameleonPlugin;
        this.bukkitPlugin = bukkitPlugin;
        this.pluginData = pluginData;
    }

    @Override
    protected @NotNull BukkitChameleon loadInternal() throws ChameleonInstantiationException {
        return new BukkitChameleon(
            this.chameleonPlugin, this.bukkitPlugin, this.pluginData,
            this.eventBus, this.logger, this.extensions
        );
    }

}
