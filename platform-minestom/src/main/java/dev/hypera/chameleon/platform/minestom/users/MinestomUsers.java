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
package dev.hypera.chameleon.platform.minestom.users;

import dev.hypera.chameleon.users.ChatUser;
import dev.hypera.chameleon.users.User;
import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom {@link User} utilities.
 */
@Internal
public final class MinestomUsers {

    private static final @NotNull MinestomConsoleUser CONSOLE = new MinestomConsoleUser();

    @Internal
    private MinestomUsers() {

    }

    /**
     * Wrap provided {@link CommandSender}.
     *
     * @param sender {@link CommandSender} to wrap.
     *
     * @return {@link ChatUser}.
     */
    public static @NotNull ChatUser wrap(@NotNull CommandSender sender) {
        if (sender instanceof Player) {
            return new MinestomUser((Player) sender);
        } else {
            return CONSOLE;
        }
    }

    /**
     * Get console {@link ChatUser}.
     *
     * @return console {@link ChatUser}.
     */
    public static @NotNull ChatUser console() {
        return CONSOLE;
    }

}
