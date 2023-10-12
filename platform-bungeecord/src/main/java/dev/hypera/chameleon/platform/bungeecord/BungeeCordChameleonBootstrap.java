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
package dev.hypera.chameleon.platform.bungeecord;

import dev.hypera.chameleon.ChameleonBootstrap;
import dev.hypera.chameleon.ChameleonPluginBootstrap;
import dev.hypera.chameleon.platform.logger.ChameleonJavaLogger;
import dev.hypera.chameleon.platform.Platform;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord Chameleon bootstrap implementation.
 */
public final class BungeeCordChameleonBootstrap extends ChameleonBootstrap<BungeeCordChameleon> {

    private final @NotNull Plugin bungeePlugin;

    @Internal
    BungeeCordChameleonBootstrap(@NotNull ChameleonPluginBootstrap pluginBootstrap, @NotNull Plugin bungeePlugin) {
        super(Platform.BUNGEECORD, pluginBootstrap, new ChameleonJavaLogger(bungeePlugin.getLogger()));
        this.bungeePlugin = bungeePlugin;
    }

    @Override
    protected @NotNull BungeeCordChameleon loadPlatform() {
        return new BungeeCordChameleon(
            this.pluginBootstrap, this.bungeePlugin,
            this.eventBus, this.logger, this.extensions
        );
    }

}
