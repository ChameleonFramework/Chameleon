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
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * This event is dispatched once a user connected to the proxy has successfully connected to a
 * server.
 *
 * @see ProxyUserConnectedEvent
 */
public final class ProxyUserServerConnectedEvent implements ProxyUserEvent {

    private final @NotNull ProxyUser user;
    private final @NotNull Server server;
    private final @Nullable Server previousServer;

    /**
     * Constructs a ProxyUserServerConnectedEvent.
     *
     * @param user           The user that was connected to the server.
     * @param server         The server the user has connected to.
     * @param previousServer The server the user was previously connected to, {@code null} if none.
     */
    @Internal
    public ProxyUserServerConnectedEvent(@NotNull ProxyUser user, @NotNull Server server, @Nullable Server previousServer) {
        this.user = user;
        this.server = server;
        this.previousServer = previousServer;
    }


    /**
     * Returns the user who switched between the servers.
     *
     * @return the user who switched server.
     */
    @Override
    public @NotNull ProxyUser getUser() {
        return this.user;
    }

    /**
     * Returns the server the user has connected to.
     *
     * @return the connected server.
     */
    public @NotNull Server getServer() {
        return this.server;
    }

    /**
     * Returns the server the user was previously connected to.
     *
     * <p>This will return an empty optional if the user was not previously connected to a server,
     * such as on the initial server connection after the user has connected to the proxy.</p>
     *
     * @return an optional containing the previously connected server, if available, otherwise an
     *     empty optional.
     */
    public @NotNull Optional<Server> getPreviousServer() {
        return Optional.ofNullable(this.previousServer);
    }

}
