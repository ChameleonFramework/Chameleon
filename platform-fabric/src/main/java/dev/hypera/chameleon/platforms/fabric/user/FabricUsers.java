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
package dev.hypera.chameleon.platforms.fabric.user;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.users.ChatUser;
import net.kyori.adventure.audience.Audience;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Fabric {@link dev.hypera.chameleon.core.users.User} utilities.
 */
@Internal
public final class FabricUsers {

    @Internal
    private FabricUsers() {

    }

    /**
     * Wrap provided {@link CommandSource}.
     *
     * @param chameleon {@link Chameleon} instance.
     * @param audience    {@link Audience} to wrap.
     *
     * @return {@link ChatUser}.
     */
    public static @NotNull ChatUser wrap(@NotNull Chameleon chameleon, @NotNull Audience audience) {
        if (audience instanceof ServerPlayerEntity) {
            return new FabricUser((ServerPlayerEntity) audience);
        } else if (audience instanceof ServerCommandSource) {
            return new FabricConsoleUser((ServerCommandSource) audience);
        }
        throw new IllegalArgumentException("Unsupported CommandSource type: " + audience.getClass().getName());
    }

}
