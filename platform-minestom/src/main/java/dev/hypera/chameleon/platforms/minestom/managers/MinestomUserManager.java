/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package dev.hypera.chameleon.platforms.minestom.managers;

import dev.hypera.chameleon.managers.UserManager;
import dev.hypera.chameleon.platforms.minestom.users.MinestomUser;
import dev.hypera.chameleon.platforms.minestom.users.MinestomUsers;
import dev.hypera.chameleon.users.ChatUser;
import dev.hypera.chameleon.users.User;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom {@link UserManager} implementation.
 */
@Internal
public final class MinestomUserManager extends UserManager {

    /**
     * {@link MinestomUserManager} constructor.
     */
    @Internal
    public MinestomUserManager() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChatUser getConsole() {
        return MinestomUsers.console();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<User> getPlayers() {
        return MinecraftServer.getConnectionManager().getOnlinePlayers().stream().map(MinestomUser::new).collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<User> getPlayer(@NotNull UUID uniqueId) {
        return Optional.ofNullable(MinecraftServer.getConnectionManager().getPlayer(uniqueId)).map(MinestomUser::new);
    }

}
