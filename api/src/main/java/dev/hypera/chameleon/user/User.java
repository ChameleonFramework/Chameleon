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
package dev.hypera.chameleon.user;

import java.net.SocketAddress;
import java.util.Optional;
import java.util.UUID;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a connected Minecraft player.
 */
@NonExtendable
public interface User extends ChatUser, Identified {

    /**
     * Get the unique id representing this user.
     *
     * @return user unique id.
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

    /**
     * Gets the identity.
     *
     * @return the identity
     */
    @Override
    default @NotNull Identity identity() {
        return Identity.identity(getId());
    }

}
