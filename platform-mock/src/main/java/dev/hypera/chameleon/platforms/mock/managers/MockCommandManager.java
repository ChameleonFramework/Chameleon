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
import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.commands.Command;
import dev.hypera.chameleon.commands.context.ContextImpl;
import dev.hypera.chameleon.managers.CommandManager;
import dev.hypera.chameleon.platforms.mock.MockChameleon;
import dev.hypera.chameleon.users.ChatUser;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Mock {@link CommandManager} implementation.
 */
public final class MockCommandManager extends CommandManager {

    private final @NotNull Set<Command> commands = new HashSet<>();
    private final @NotNull Chameleon chameleon;

    /**
     * {@link MockCommandManager} constructor.
     *
     * @param chameleon {@link MockChameleon} instance.
     */
    @Internal
    public MockCommandManager(@NotNull MockChameleon chameleon) {
        super(chameleon);
        this.chameleon = chameleon;
    }

    /**
     * Execute a command.
     *
     * @param user {@link ChatUser} to execute the command as.
     * @param name Command name.
     * @param args Command execution arguments.
     *
     * @see dev.hypera.chameleon.platforms.mock.user.MockUser#executeCommand
     */
    @Internal
    public void executeCommand(@NotNull ChatUser user, @NotNull String name, @NotNull String... args) {
        this.commands.stream().filter(c -> c.getName().equalsIgnoreCase(name) || c.getAliases().stream().anyMatch(a -> a.equalsIgnoreCase(name))).findFirst().ifPresent(c -> {
            if (args.length < 1 || c.executeSubCommand(new ContextImpl(user, this.chameleon, Arrays.copyOfRange(args, 1, args.length)), args[0])) {
                c.executeCommand(new ContextImpl(user, this.chameleon, args));
            }
        });
    }

    @Override
    protected void registerCommand(@NotNull Command command) {
        Preconditions.checkArgument(this.commands.stream().noneMatch(c -> c.getName().equalsIgnoreCase(command.getName())), "command with name '" + command.getName() + "' is already registered");
        Preconditions.checkArgument(this.commands.stream().noneMatch(c -> c.getAliases().stream().anyMatch(a -> a.equalsIgnoreCase(command.getName()) || command.getAliases().stream().anyMatch(a::equalsIgnoreCase))), "an alias is already in use");

        this.commands.add(command);
    }

    @Override
    protected void unregisterCommand(@NotNull Command command) {
        Preconditions.checkArgument(
            this.commands.stream()
                .anyMatch(c -> c.getName().equalsIgnoreCase(command.getName()) || c.getAliases().stream().anyMatch(a -> a.equalsIgnoreCase(command.getName()) || command.getAliases().stream().anyMatch(a::equalsIgnoreCase))),
            "command with name '" + command.getName() + "' isn't registered"
        );

        this.commands.remove(command);
    }

}
