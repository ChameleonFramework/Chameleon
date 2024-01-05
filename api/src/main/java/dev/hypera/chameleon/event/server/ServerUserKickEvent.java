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
package dev.hypera.chameleon.event.server;

import dev.hypera.chameleon.user.ServerUser;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Server user kick event, dispatched when a user is kicked from the server.
 */
public final class ServerUserKickEvent implements ServerUserEvent {

    private final @NotNull ServerUser user;
    private final @NotNull Component reason;

    /**
     * Server user kick event constructor.
     *
     * @param user   The server user who was kicked.
     * @param reason The reason for the user being kicked.
     */
    @Internal
    public ServerUserKickEvent(@NotNull ServerUser user, @Nullable Component reason) {
        this.user = user;
        this.reason = reason != null ? reason : Component.text("Disconnected");
    }

    /**
     * Get the server user who was kicked.
     *
     * @return the server user who was kicked.
     */
    @Override
    public @NotNull ServerUser getUser() {
        return this.user;
    }

    /**
     * Get the reason for this disconnection.
     *
     * @return disconnect reason, defaults to {@code Disconnected}.
     */
    public @NotNull Component getReason() {
        return this.reason;
    }

}
