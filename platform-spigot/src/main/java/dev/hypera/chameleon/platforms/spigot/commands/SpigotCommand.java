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
package dev.hypera.chameleon.platforms.spigot.commands;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.commands.context.ContextImpl;
import dev.hypera.chameleon.platforms.spigot.user.SpigotUsers;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Spigot {@link Command} wrapper.
 */
@Internal
public final class SpigotCommand extends org.bukkit.command.Command {

    private final @NotNull Chameleon chameleon;
    private final @NotNull Command command;

    /**
     * {@link SpigotCommand} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     * @param command   {@link Command} to be wrapped.
     */
    @Internal
    public SpigotCommand(@NotNull Chameleon chameleon, @NotNull Command command) {
        super(command.getName(), "", "", new ArrayList<>(command.getAliases()));
        this.chameleon = chameleon;
        this.command = command;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1 || this.command.executeSubCommand(new ContextImpl(SpigotUsers.wrap(this.chameleon, sender), this.chameleon, Arrays.copyOfRange(args, 1, args.length)), args[0])) {
            this.command.executeCommand(new ContextImpl(SpigotUsers.wrap(this.chameleon, sender), this.chameleon, args));
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return this.command.tabComplete(new ContextImpl(SpigotUsers.wrap(this.chameleon, sender), this.chameleon, args));
    }

}
