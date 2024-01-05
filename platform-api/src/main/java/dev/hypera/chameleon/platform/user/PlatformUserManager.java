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
package dev.hypera.chameleon.platform.user;

import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.user.ConsoleUser;
import dev.hypera.chameleon.user.User;
import dev.hypera.chameleon.user.UserManager;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Platform implementation of {@link UserManager}.
 *
 * @param <P> Platform player type.
 * @param <U> Platform user implementation type.
 */
public abstract class PlatformUserManager<P, U extends PlatformUser<P>> implements UserManager {

    private final @NotNull AtomicReference<ConsoleUser> console = new AtomicReference<>();
    private final @NotNull Map<UUID, U> users = new ConcurrentHashMap<>();

    /**
     * Adds a platform player to the stored users, if absent.
     *
     *
     * @param id User ID.
     * @param p  Platform player.
     */
    protected final void addUser(@NotNull UUID id, @NotNull P p) {
        this.users.computeIfAbsent(id, i -> createUser(p));
    }

    /**
     * Removes a stored user with the given identifier, if present.
     *
     * @param id User ID.
     */
    protected final void removeUser(@NotNull UUID id) {
        this.users.remove(id);
    }

    /**
     * Returns an implementation of console user for the platform.
     *
     * @return console user.
     */
    protected abstract @NotNull ConsoleUser createConsoleUser();

    /**
     * Returns an implementation of user for the given platform player.
     *
     * @param p Platform player.
     *
     * @return user.
     */
    protected abstract @NotNull U createUser(@NotNull P p);

    /**
     * {@inheritDoc}
     */
    @Override
    public final @NotNull ConsoleUser getConsole() {
        ConsoleUser consoleUser = this.console.get();
        if (consoleUser == null) {
            this.console.compareAndSet(null, createConsoleUser());
            return Objects.requireNonNull(this.console.get());
        }
        return consoleUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final @NotNull Collection<U> getUsers() {
        return Collections.unmodifiableCollection(this.users.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final @NotNull Optional<U> getUserById(@NotNull UUID id) {
        return Optional.ofNullable(this.users.get(id));
    }

    /**
     * Closes the user manager and removes any stored objects.
     */
    public void close() {
        this.console.set(null);
        this.users.clear();
    }

    /**
     * Returns a user with the given identifier.
     * <p>This method is designed to be used when the player should always exist.</p>
     *
     * @param id User ID.
     *
     * @return user.
     * @throws IllegalArgumentException if the user is not found.
     */
    @Internal
    protected final @NotNull U getUserOrThrow(@NotNull UUID id) {
        U u = this.users.get(id);
        if (u == null) {
            throw new IllegalArgumentException("cannot find user with id " + id);
        }
        return u;
    }

    /**
     * Returns a chat user representing the given object.
     * <p>Note: Implementations should use {@link #getUserById(UUID)} and {@link #getConsole()} to
     * retrieve cached chat users. New chat users must not be constructed.</p>
     *
     * @param obj Object to return a chat user for.
     *
     * @return chat user representing the given object.
     * @throws IllegalArgumentException      if a chat user representing the given object is not
     *                                       available.
     * @throws UnsupportedOperationException if the current platform has not implemented this
     *                                       method.
     */
    @Internal
    public @NotNull ChatUser wrap(@NotNull Object obj) {
        throw new UnsupportedOperationException("wrap method is not implemented by this platform");
    }

    /**
     * Returns a user representing the given object.
     *
     * @param obj Object to return a user for.
     *
     * @return user representing the given object.
     * @throws IllegalArgumentException      if a user representing the given object is not
     *                                       available.
     * @throws UnsupportedOperationException if the current platform has not implemented
     *                                       {@link #wrap(Object)} or this method.
     */
    @Internal
    public @NotNull User wrapUser(@NotNull Object obj) {
        ChatUser user = wrap(obj);
        if (user instanceof User) {
            return (User) user;
        }
        throw new IllegalArgumentException("cannot return a user representing the given object");
    }

}
