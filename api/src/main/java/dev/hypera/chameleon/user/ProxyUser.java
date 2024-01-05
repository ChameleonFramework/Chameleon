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

import dev.hypera.chameleon.platform.proxy.Server;
import java.util.Optional;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a connected Minecraft player on a proxy.
 */
@NonExtendable
public interface ProxyUser extends User {

    /**
     * Get the server this user is currently connected to.
     *
     * @return an optional containing the server the user is currently connected to,
     *     or an empty optional if the user is not connected to a server.
     */
    @NotNull Optional<Server> getConnectedServer();

    /**
     * Attempt to switch this user to the given server.
     *
     * @param server Server to switch this user to.
     */
    void connect(@NotNull Server server);

    /**
     * Attempt to switch this user to the given server and then run the given callback.
     *
     * @param server   Server to switch this user to.
     * @param callback Callback to run afterwards.
     */
    void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback);

}
