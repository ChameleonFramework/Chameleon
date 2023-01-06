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
package dev.hypera.chameleon.events.proxy;

import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.users.ProxyUser;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@link ProxyUser} switch sever event, dispatched whenever a player switches server.
 */
public final class ProxyUserSwitchEvent implements ProxyUserEvent {

    private final @NotNull ProxyUser user;
    private final @Nullable Server from;
    private final @NotNull Server to;

    /**
     * {@link ProxyUserSwitchEvent} constructor.
     *
     * @param user The {@link ProxyUser} that triggered this event.
     * @param from The {@link Server} the user switched from.
     * @param to   The {@link Server} the user switched to.
     */
    @Internal
    public ProxyUserSwitchEvent(@NotNull ProxyUser user, @Nullable Server from, @NotNull Server to) {
        this.user = user;
        this.from = from;
        this.to = to;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ProxyUser getUser() {
        return this.user;
    }

    /**
     * The {@link Server} the user switched from, if available.
     *
     * @return optionally the {@link Server} the user switched from.
     */
    public @NotNull Optional<Server> getFrom() {
        return Optional.ofNullable(this.from);
    }

    /**
     * The {@link Server} the user switched to.
     *
     * @return the {@link Server} the user switched to.
     */
    public @NotNull Server getTo() {
        return this.to;
    }

}
