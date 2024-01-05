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
package dev.hypera.chameleon.platform.velocity.user;

import com.velocitypowered.api.command.CommandSource;
import dev.hypera.chameleon.adventure.ReflectedAudience;
import dev.hypera.chameleon.platform.user.PlatformChatUser;
import dev.hypera.chameleon.user.ConsoleUser;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity console user implementation.
 */
@Internal
public final class VelocityConsoleUser extends PlatformChatUser implements ConsoleUser, ForwardingAudience.Single {

    private final @NotNull CommandSource console;
    private final @NotNull ReflectedAudience audience;

    /**
     * Velocity console user constructor.
     *
     * @param console  Velocity console command source.
     * @param audience Reflected audience instance.
     */
    @Internal
    VelocityConsoleUser(@NotNull CommandSource console, @NotNull ReflectedAudience audience) {
        this.console = console;
        this.audience = audience;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(@NotNull String permission) {
        return this.console.hasPermission(permission);
    }

    /**
     * Gets the audience.
     *
     * @return the audience.
     */
    @Override
    public @NotNull Audience audience() {
        return this.audience;
    }

}
