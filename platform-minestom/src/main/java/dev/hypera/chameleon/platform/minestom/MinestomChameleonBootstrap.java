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
package dev.hypera.chameleon.platform.minestom;

import dev.hypera.chameleon.ChameleonBootstrap;
import dev.hypera.chameleon.ChameleonPluginBootstrap;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.logger.ChameleonSlf4jLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.util.Preconditions;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

/**
 * Minestom Chameleon bootstrap implementation.
 */
public final class MinestomChameleonBootstrap extends ChameleonBootstrap<MinestomChameleon> {

    private final @NotNull Extension extension;

    @Internal
    MinestomChameleonBootstrap(@NotNull ChameleonPluginBootstrap pluginBootstrap, @NotNull Extension extension) {
        super(Platform.MINESTOM, pluginBootstrap, createLogger(extension));
        this.extension = extension;
    }

    @Override
    protected @NotNull MinestomChameleon loadPlatform() {
        return new MinestomChameleon(
            this.pluginBootstrap, this.extension,
            this.eventBus, this.logger, this.extensions
        );
    }

    private static @NotNull ChameleonLogger createLogger(@NotNull Extension extension) {
        Preconditions.checkNotNull("extension", extension);
        try {
            return new ChameleonSlf4jLogger((Logger) Extension.class.getMethod("getLogger").invoke(extension));
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException(ex);
        }
    }

}
