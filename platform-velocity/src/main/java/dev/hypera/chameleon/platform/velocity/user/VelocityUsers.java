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
package dev.hypera.chameleon.platform.velocity.user;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.platform.velocity.VelocityChameleon;
import dev.hypera.chameleon.users.ChatUser;
import dev.hypera.chameleon.users.User;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity {@link User} utilities.
 */
@Internal
public final class VelocityUsers {

    @Internal
    private VelocityUsers() {

    }

    /**
     * Wrap provided {@link CommandSource}.
     *
     * @param chameleon {@link Chameleon} instance.
     * @param source    {@link CommandSource} to wrap.
     *
     * @return {@link ChatUser}.
     */
    public static @NotNull ChatUser wrap(@NotNull Chameleon chameleon, @NotNull CommandSource source) {
        if (source instanceof Player) {
            return new VelocityUser((VelocityChameleon) chameleon, (Player) source);
        } else {
            return new VelocityConsoleUser((VelocityChameleon) chameleon);
        }
    }

}
