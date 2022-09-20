/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.platform.minestom.command;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.commands.Command;
import dev.hypera.chameleon.commands.context.ContextImpl;
import dev.hypera.chameleon.platform.minestom.users.MinestomUsers;
import java.util.Arrays;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom {@link Command} wrapper.
 */
@Internal
public class MinestomCommand extends net.minestom.server.command.builder.Command {

    /**
     * {@link MinestomCommand} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     * @param command   {@link Command} to be wrapped.
     */
    @Internal
    public MinestomCommand(@NotNull Chameleon chameleon, @NotNull Command command) {
        super(command.getName(), command.getAliases().toArray(new String[0]));

        setDefaultExecutor((sender, context) -> {
            String[] args = context.getInput().replace(context.getCommandName(), "").trim().split(" ");
            if (args.length < 1 || command.executeSubCommand(new ContextImpl(MinestomUsers.wrap(sender), chameleon, Arrays.copyOfRange(args, 1, args.length)), args[0])) {
                command.executeCommand(new ContextImpl(MinestomUsers.wrap(sender), chameleon, args));
            }
        });
    }

}
