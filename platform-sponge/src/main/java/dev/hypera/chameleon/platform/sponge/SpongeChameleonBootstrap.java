/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package dev.hypera.chameleon.platform.sponge;

import dev.hypera.chameleon.ChameleonBootstrap;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.data.PluginData;
import dev.hypera.chameleon.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extensions.ChameleonExtension;
import dev.hypera.chameleon.logging.ChameleonLogger;
import dev.hypera.chameleon.logging.impl.ChameleonLog4jLogger;
import dev.hypera.chameleon.platform.sponge.extensions.SpongeChameleonExtension;
import java.util.Collection;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Sponge {@link ChameleonBootstrap} implementation.
 */
public final class SpongeChameleonBootstrap extends ChameleonBootstrap<SpongeChameleon, SpongeChameleonExtension<?, ?>> {

    private final @NotNull Class<? extends ChameleonPlugin> chameleonPlugin;
    private final @NotNull SpongePlugin spongePlugin;
    private final @NotNull PluginData pluginData;

    @Internal
    SpongeChameleonBootstrap(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull SpongePlugin spongePlugin, @NotNull PluginData pluginData) {
        this.chameleonPlugin = chameleonPlugin;
        this.spongePlugin = spongePlugin;
        this.pluginData = pluginData;
    }

    /**
     * {@inheritDoc}
     */
    @Internal
    @Override
    protected @NotNull SpongeChameleon loadInternal(@NotNull Collection<ChameleonExtension<?>> extensions) throws ChameleonInstantiationException {
        return new SpongeChameleon(this.chameleonPlugin, extensions, this.spongePlugin, this.pluginData);
    }

    /**
     * {@inheritDoc}
     */
    @Internal
    @Override
    protected @NotNull ChameleonLogger createLogger() {
        return new ChameleonLog4jLogger(this.spongePlugin.getLogger());
    }

}

