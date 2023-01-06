/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2023 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.events.common;

import dev.hypera.chameleon.events.cancellable.Cancellable;
import dev.hypera.chameleon.users.User;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@link User} connect event, dispatched when a user joins the proxy/server.
 */
public final class UserConnectEvent implements UserEvent, Cancellable {

    private final @NotNull User user;
    private boolean cancelled = false;
    private @NotNull Component cancelReason = Component.text("Disconnected");

    /**
     * {@link UserConnectEvent} constructor.
     *
     * @param user The {@link User} that triggered this event.
     */
    @Internal
    public UserConnectEvent(@NotNull User user) {
        this.user = user;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull User getUser() {
        return this.user;
    }

    /**
     * Cancel the event, with a reason.
     *
     * @param reason Disconnect reason.
     */
    public void cancel(@NotNull Component reason) {
        setCancelled(true, reason);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Cancel the event, with a reason.
     *
     * @param cancelled {@code true} if the event is cancelled, otherwise {@code false}.
     * @param reason    Disconnect reason.
     */
    public void setCancelled(boolean cancelled, @Nullable Component reason) {
        this.cancelled = cancelled;
        if (null != reason) {
            this.cancelReason = reason;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    /**
     * Get the reason used when kicking the player if this event is cancelled.
     *
     * @return cancel reason.
     */
    public @NotNull Component getCancelReason() {
        return this.cancelReason;
    }

}
