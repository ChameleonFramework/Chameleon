/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 The Chameleon Framework Authors.
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
import dev.hypera.chameleon.ChameleonPluginBootstrap;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.logger.ChameleonJavaLogger;
import dev.hypera.chameleon.platform.logger.ChameleonSlf4jLogger;
import dev.hypera.chameleon.platform.util.ReflectionUtil;
import java.lang.invoke.MethodHandle;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit Chameleon bootstrap implementation.
 */
public final class BukkitChameleonBootstrap extends ChameleonBootstrap<BukkitChameleon> {

    private final @NotNull JavaPlugin bukkitPlugin;

    @Internal
    BukkitChameleonBootstrap(@NotNull ChameleonPluginBootstrap pluginBootstrap, @NotNull JavaPlugin bukkitPlugin) {
        super(Platform.BUKKIT, pluginBootstrap, createLogger(bukkitPlugin));
        this.bukkitPlugin = bukkitPlugin;
    }

    @Override
    protected @NotNull BukkitChameleon loadPlatform() {
        return new BukkitChameleon(
            this.pluginBootstrap, this.bukkitPlugin,
            this.eventBus, this.logger, this.extensions
        );
    }

    private static @NotNull ChameleonLogger createLogger(@NotNull JavaPlugin bukkitPlugin) {
        MethodHandle slf4jMethod = ReflectionUtil.getMethod(
            bukkitPlugin.getClass(), "getSLF4JLogger",
            ReflectionUtil.findClass("org.slf4j.Logger")
        );
        if (slf4jMethod != null) {
            try {
                return ChameleonSlf4jLogger.create(slf4jMethod.bindTo(bukkitPlugin).invoke());
            } catch (Throwable ignored) {
                // continue
            }
        }
        return new ChameleonJavaLogger(bukkitPlugin.getLogger());
    }

}
