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
package dev.hypera.chameleon.platforms.mock.managers;

import com.google.common.base.Preconditions;
import dev.hypera.chameleon.managers.UserManager;
import dev.hypera.chameleon.platforms.mock.MockChameleon;
import dev.hypera.chameleon.platforms.mock.platform.MockProxyPlatform;
import dev.hypera.chameleon.platforms.mock.platform.MockServerPlatform;
import dev.hypera.chameleon.platforms.mock.user.MockConsoleUser;
import dev.hypera.chameleon.platforms.mock.user.MockProxyUser;
import dev.hypera.chameleon.platforms.mock.user.MockServerUser;
import dev.hypera.chameleon.platforms.mock.user.MockUser;
import dev.hypera.chameleon.users.User;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * Mock {@link UserManager} implementation.
 */
public class MockUserManager extends UserManager {

    private final @NotNull Set<User> users = new HashSet<>();
    private final @NotNull MockConsoleUser consoleUser;
    private final @NotNull MockChameleon chameleon;

    /**
     * {@link MockUserManager} constructor.
     *
     * @param chameleon {@link MockChameleon} instance.
     */
    public MockUserManager(@NotNull MockChameleon chameleon) {
        this.consoleUser = new MockConsoleUser(chameleon);
        this.chameleon = chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull MockConsoleUser getConsole() {
        return this.consoleUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Set<User> getPlayers() {
        return this.users;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<User> getPlayer(@NotNull UUID uniqueId) {
        return this.users.stream().filter(u -> u.getUniqueId().equals(uniqueId)).findFirst();
    }

    /**
     * Create a new {@link User}.
     *
     * @param id   {@link User} unique id.
     * @param name {@link User} name.
     *
     * @return new {@link User} instance.
     */
    public @NotNull MockUser createUser(@NotNull UUID id, @NotNull String name) {
        Preconditions.checkArgument(this.users.stream().noneMatch(u -> u.getUniqueId().equals(id)), "user with id '" + id + "' already exists");
        Preconditions.checkArgument(name.matches("[a-zA-Z0-9_-]{3,16}"), "name must be between 3 and 16 characters long and can only contain a-zA-Z0-9_-");
        MockUser user;

        if (this.chameleon.getPlatform() instanceof MockProxyPlatform) {
            user = new MockProxyUser(id, name, this.chameleon, this.chameleon.getProxyPlatform().getServers().stream().findAny().orElseThrow(() -> new IllegalStateException("a server must be created before creating a user on a proxy platform")));
        } else if (this.chameleon.getPlatform() instanceof MockServerPlatform) {
            user = new MockServerUser(id, name, this.chameleon);
        } else {
            throw new IllegalStateException("unknown platform type");
        }

        this.users.add(user);
        return user;
    }

    /**
     * Remove a {@link User}.
     *
     * @param id {@link User} unique id.
     */
    public void removeUser(@NotNull UUID id) {
        Preconditions.checkArgument(this.users.stream().anyMatch(u -> u.getUniqueId().equals(id)), "user with id '" + id + "' does not exist");
        this.users.removeIf(u -> u.getUniqueId().equals(id));
    }

}
