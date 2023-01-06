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
package dev.hypera.chameleon.users;

import dev.hypera.chameleon.platform.proxy.ProxyPlatform;
import dev.hypera.chameleon.platform.proxy.Server;
import java.util.Optional;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an in-game {@link User} on a {@link ProxyPlatform}.
 */
@NonExtendable
public interface ProxyUser extends User {

    /**
     * Get the "sub-server" this user is currently connected to.
     *
     * @return an {@code Optional} containing the {@link Server} the user is currently connected to,
     *     or an empty optional if the user is not connected to a server.
     */
    @NotNull Optional<Server> getServer();

    /**
     * Attempt to switch this user to the given {@link Server}.
     *
     * @param server {@link Server} to switch this user to.
     */
    void connect(@NotNull Server server);

    /**
     * Attempt to switch this user to the given {@link Server} and then run the given callback.
     *
     * @param server   {@link Server} to switch this user to.
     * @param callback Callback to run afterwards.
     */
    void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback);

}
