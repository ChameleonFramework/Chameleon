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
package dev.hypera.chameleon.event.proxy;

import dev.hypera.chameleon.platform.proxy.Server;
import dev.hypera.chameleon.user.ProxyUser;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * This event is dispatched once the user has successfully connected to the proxy and has been
 * connected to a server.
 *
 * <p>This is similar to {@link ProxyUserServerConnectedEvent}, however this is only dispatched on
 * the initial server connection after the user has connected to the proxy.</p>
 *
 * <p>{@link ProxyUserServerConnectedEvent} will also be dispatched, however
 * {@link ProxyUserServerConnectedEvent#getPreviousServer()} will return an empty optional for the
 * initial connection, as the user was not previously connected to a server.</p>
 *
 * @see ProxyUserServerConnectedEvent
 */
public final class ProxyUserConnectedEvent implements ProxyUserEvent {

    private final @NotNull ProxyUser user;
    private final @NotNull Server server;

    /**
     * Constructs a ProxyUserConnectedEvent.
     *
     * @param user   User who connected.
     * @param server Server the user connected to.
     */
    @Internal
    public ProxyUserConnectedEvent(@NotNull ProxyUser user, @NotNull Server server) {
        this.user = user;
        this.server = server;
    }

    /**
     * Returns the user who connected.
     *
     * @return connected user.
     */
    @Override
    public @NotNull ProxyUser getUser() {
        return this.user;
    }

    /**
     * Returns the server the user connected to.
     *
     * @return connected server.
     */
    public @NotNull Server getServer() {
        return this.server;
    }

}
