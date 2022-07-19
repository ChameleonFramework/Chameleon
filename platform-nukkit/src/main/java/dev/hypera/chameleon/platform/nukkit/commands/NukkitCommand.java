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
package dev.hypera.chameleon.platform.nukkit.commands;

import cn.nukkit.command.CommandSender;
import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.commands.Command;
import dev.hypera.chameleon.commands.context.ContextImpl;
import dev.hypera.chameleon.platform.nukkit.users.NukkitUsers;
import java.util.Arrays;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit {@link Command} wrapper.
 */
@Internal
public class NukkitCommand extends cn.nukkit.command.Command {

    private final @NotNull Chameleon chameleon;
    private final @NotNull Command command;

    /**
     * {@link NukkitCommand} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     * @param command   {@link Command} to wrap.
     */
    @Internal
    public NukkitCommand(@NotNull Chameleon chameleon, @NotNull Command command) {
        super(command.getName(), "", null, command.getAliases().toArray(new String[0]));
        this.chameleon = chameleon;
        this.command = command;
    }

    /**
     * Execute the command.
     *
     * @param sender       the {@link CommandSender} who issued this command.
     * @param commandLabel the commandLabel used when issuing this command.
     * @param args         the command arguments.
     */
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length < 1 || this.command.executeSubCommand(new ContextImpl(NukkitUsers.wrap(sender), this.chameleon, Arrays.copyOfRange(args, 1, args.length)), args[0])) {
            this.command.executeCommand(new ContextImpl(NukkitUsers.wrap(sender), this.chameleon, args));
        }

        return true;
    }

}
