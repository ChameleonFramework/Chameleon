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
package dev.hypera.chameleon.platforms.sponge.platform;

import dev.hypera.chameleon.core.platform.server.ServerPlatform;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Platform.Component;
import org.spongepowered.api.Sponge;

/**
 * Sponge {@link ServerPlatform} implementation.
 */
@Internal
public final class SpongePlatform extends ServerPlatform {

    @Override
    public @NotNull String getAPIName() {
        return Sponge.game().platform().container(Component.API).metadata().name().orElse("Sponge");
    }

    @Override
    public @NotNull String getName() {
        return Sponge.game().platform().container(Component.IMPLEMENTATION).metadata().name().orElse("Sponge");
    }

    @Override
    public @NotNull String getVersion() {
        return Sponge.game().platform().container(Component.IMPLEMENTATION).metadata().version().toString() + " (" + Sponge.game().platform().container(Component.API).metadata().version() + ")";
    }

    @Override
    public @NotNull Type getType() {
        return Type.SERVER;
    }

}
