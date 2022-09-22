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
package dev.hypera.chameleon.events.server;

import dev.hypera.chameleon.annotations.PlatformSpecific;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.users.platforms.ServerUser;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@link ServerUser} kick event, dispatched when a user is kicked from the server.
 */
@PlatformSpecific(Platform.Type.SERVER)
public final class ServerUserKickEvent implements ServerUserEvent {

    private final @NotNull ServerUser user;
    private final @NotNull Component reason;

    /**
     * {@link ServerUserKickEvent} constructor.
     *
     * @param user   The {@link ServerUser} that triggered this event.
     * @param reason The reason for this event being triggered.
     */
    @Internal
    public ServerUserKickEvent(@NotNull ServerUser user, @Nullable Component reason) {
        this.user = user;
        this.reason = null != reason ? reason : Component.text("Disconnected");
    }

    /**
     * {@inheritDoc}
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
