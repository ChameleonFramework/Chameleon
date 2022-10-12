/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.users;

import java.net.SocketAddress;
import java.util.Optional;
import java.util.UUID;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an in-game player, e.g. a person connected with the Minecraft client.
 */
@NonExtendable
public interface User extends ChatUser {

    /**
     * Get the unique identifier of this user.
     *
     * @return unique identifier.
     */
    @NotNull UUID getId();

    /**
     * Get the address of this user.
     *
     * @return an {@code Optional} containing the address of this user, if available, otherwise an
     *     empty optional.
     */
    @NotNull Optional<SocketAddress> getAddress();

    /**
     * Get the latency in milliseconds between this user and the platform.
     *
     * @return latency between this user and the platform.
     */
    int getLatency();

    /**
     * Send a chat message as this user.
     *
     * @param message Chat message to be sent.
     *
     * @see #chat(Component)
     */
    default void chat(@NotNull String message) {
        chat(Component.text(message));
    }

    /**
     * Spoof a chat message as this user.
     *
     * @param message Chat message to be sent.
     */
    void chat(@NotNull Component message);

    /**
     * Send a plugin message to this user.
     *
     * @param channel Plugin message channel.
     * @param data    Data.
     */
    void sendData(@NotNull String channel, byte[] data);

    /**
     * Disconnect this user from the platform.
     *
     * @param reason Disconnect reason.
     */
    void disconnect(@NotNull Component reason);

}
