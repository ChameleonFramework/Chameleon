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
package dev.hypera.chameleon.platforms.mock.user;


import dev.hypera.chameleon.platforms.mock.MockChameleon;
import dev.hypera.chameleon.platforms.mock.adventure.AbstractMockAudience;
import dev.hypera.chameleon.users.User;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.LinkedTransferQueue;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock {@link User} implementation.
 */
@NonExtendable
public abstract class MockUser extends AbstractMockAudience implements User {

    private final @NotNull UUID id;
    private final @NotNull String name;
    private final @NotNull MockChameleon chameleon;

    private final @NotNull Queue<String> sentMessages = new LinkedTransferQueue<>();
    private final @NotNull Set<String> permissions = new HashSet<>();

    private int ping = 100;

    @Internal
    MockUser(@NotNull UUID id, @NotNull String name, @NotNull MockChameleon chameleon) {
        this.id = id;
        this.name = name;
        this.chameleon = chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull UUID getUniqueId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPing() {
        return this.ping;
    }

    /**
     * Set {@code ping}.
     *
     * @param ping The new ping.
     */
    public void setPing(int ping) {
        this.ping = ping;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void chat(@NotNull String message) {
        this.sentMessages.add(message);
    }

    /**
     * Get the next message that this user sent.
     *
     * @return the next message that was sent.
     * @see #chat(String)
     */
    public @Nullable String nextChatMessage() {
        return this.sentMessages.poll();
    }

    /**
     * Peek at the next message that this user sent.
     *
     * @return the next message that was sent.
     * @see #chat(String)
     */
    public @Nullable String peekNextChatMessage() {
        return this.sentMessages.peek();
    }

    /**
     * Execute a command.
     *
     * @param name Name of the command to be executed.
     * @param args Command execution arguments.
     */
    public void executeCommand(@NotNull String name, @NotNull String... args) {
        this.chameleon.getCommandManager().executeCommand(this, name, args);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(@NotNull String permission) {
        return this.permissions.stream().anyMatch(p -> p.equalsIgnoreCase(permission));
    }

    /**
     * Grant the {@code permission} to this user.
     *
     * @param permission Permission to set.
     */
    public void setPermission(@NotNull String permission) {
        this.permissions.add(permission);
    }

    /**
     * Remove the {@code permission} from this user.
     *
     * @param permission Permission to unset.
     */
    public void unsetPermission(@NotNull String permission) {
        this.permissions.remove(permission);
    }

}
