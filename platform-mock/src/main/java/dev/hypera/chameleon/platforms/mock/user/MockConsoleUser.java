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
import dev.hypera.chameleon.users.ChatUser;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Mock console {@link ChatUser} implementation.
 */
public final class MockConsoleUser extends AbstractMockAudience implements ChatUser {

    private final @NotNull MockChameleon chameleon;

    /**
     * {@link MockConsoleUser} constructor.
     *
     * @param chameleon {@link MockChameleon} instance.
     */
    @Internal
    public MockConsoleUser(@NotNull MockChameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return "Console";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(@NotNull String permission) {
        return true;
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

}
