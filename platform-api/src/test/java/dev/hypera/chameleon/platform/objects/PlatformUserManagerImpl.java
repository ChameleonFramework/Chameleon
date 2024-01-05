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
package dev.hypera.chameleon.platform.objects;

import dev.hypera.chameleon.platform.user.PlatformUserManager;
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.user.ConsoleUser;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public final class PlatformUserManagerImpl extends PlatformUserManager<PlatformPlayer, PlatformUserImpl> {

    public void addTestUser(@NotNull UUID id) {
        addTestUser(new PlatformPlayer(id, "player-" + id));
    }

    public void addTestUser(@NotNull PlatformPlayer player) {
        addUser(player.id(), player);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull ConsoleUser createConsoleUser() {
        return new ConsoleUserImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull PlatformUserImpl createUser(@NotNull PlatformPlayer p) {
        if (p.serverName() != null) {
            return new PlatformProxyUserImpl(p);
        }
        return new PlatformUserImpl(p);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChatUser wrap(@NotNull Object obj) {
        if (obj instanceof PlatformPlayer) {
            return getUserOrThrow(((PlatformPlayer) obj).id());
        }
        if (obj instanceof PlatformConsole) {
            return getConsole();
        }
        throw new IllegalArgumentException("cannot return chat user representing given object");
    }

}
