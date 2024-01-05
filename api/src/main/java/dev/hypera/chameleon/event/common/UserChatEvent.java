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
package dev.hypera.chameleon.event.common;

import dev.hypera.chameleon.event.AbstractCancellable;
import dev.hypera.chameleon.user.User;
import org.jetbrains.annotations.NotNull;

/**
 * User chat event, dispatched when a player sends a chat message.
 */
public final class UserChatEvent extends AbstractCancellable implements UserEvent {

    private final @NotNull User user;
    private @NotNull String message;
    private final boolean cancellable;
    private final boolean modifiable;

    /**
     * User chat event constructor.
     *
     * @param user        User that sent the message.
     * @param message     Message that the user attempted to send.
     * @param cancelled   Whether this event is cancelled.
     * @param cancellable Whether this event can be cancelled on this platform.
     * @param modifiable  Whether this event can be modified on this platform.
     */
    public UserChatEvent(@NotNull User user, @NotNull String message, boolean cancelled, boolean cancellable, boolean modifiable) {
        super(cancelled);
        this.user = user;
        this.message = message;
        this.cancellable = cancellable;
        this.modifiable = modifiable;
    }

    /**
     * Returns the user who sent this message.
     *
     * @return message sender.
     */
    @Override
    public @NotNull User getUser() {
        return this.user;
    }

    /**
     * Returns the message that was sent.
     *
     * @return message.
     */
    public @NotNull String getMessage() {
        return this.message;
    }

    /**
     * Changes the chat message.
     * <p>Due to changes in Minecraft 1.19.1+, some platforms no longer support modifying the chat
     * message. If the current platform does not support changing the chat message, no changes will
     * be made.</p>
     *
     * @param message New message.
     */
    public void setMessage(@NotNull String message) {
        this.message = message;
    }

    /**
     * Returns whether this event can be cancelled.
     * <p>Due to changes in Minecraft 1.19.1+, some platforms no longer support cancelling signed
     * chat packets. If the current platform does not support cancelling this event, this will
     * return {@code false}.</p>
     *
     * <p>If this method returns {@code false}, but the event is cancelled, the event will not be
     * cancelled on the platform to prevent problems from occurring.</p>
     *
     * @return {@code true} if this chat event can be cancelled on the underlying platform,
     *     otherwise {@code false}.
     */
    public boolean isCancellable() {
        return this.cancellable;
    }

    /**
     * Returns whether this event can be modified.
     * <p>Due to changes in Minecraft 1.19.1+, some platforms no longer support modifying the chat
     * message. If the current platform does not support changing the chat message for this event,
     * this will return {@code false}.</p>
     *
     * <p>If this method returns {@code false}, but the chat message is modified, the event's chat
     * message will not be changed on the platform to prevent problems from occurring.</p>
     *
     * @return {@code true} if this chat event can be modified on the underlying platform, otherwise
     *     {@code false}.
     */
    public boolean isModifiable() {
        return this.modifiable;
    }

}
