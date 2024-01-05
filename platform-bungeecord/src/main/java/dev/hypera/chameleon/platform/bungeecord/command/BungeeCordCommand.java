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
package dev.hypera.chameleon.platform.bungeecord.command;

import dev.hypera.chameleon.command.Command;
import dev.hypera.chameleon.command.context.ContextImpl;
import dev.hypera.chameleon.platform.bungeecord.BungeeCordChameleon;
import java.util.Arrays;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord command wrapper.
 */
@Internal
public final class BungeeCordCommand extends net.md_5.bungee.api.plugin.Command implements TabExecutor {

    private final @NotNull BungeeCordChameleon chameleon;
    private final @NotNull Command command;

    /**
     * BungeeCord command constructor.
     *
     * @param chameleon Chameleon implementation.
     * @param command   Command to be wrapped.
     */
    @Internal
    public BungeeCordCommand(@NotNull BungeeCordChameleon chameleon, @NotNull Command command) {
        super(command.getName(), null, command.getAliases().toArray(new String[0]));
        this.chameleon = chameleon;
        this.command = command;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 1 || this.command.executeSubCommand(new ContextImpl(
            this.chameleon.getUserManager().wrap(sender), this.chameleon,
            Arrays.copyOfRange(args, 1, args.length)), args[0]
        )) {
            this.command.executeCommand(new ContextImpl(
                this.chameleon.getUserManager().wrap(sender),
                this.chameleon, args
            ));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return this.command.tabComplete(new ContextImpl(
            this.chameleon.getUserManager().wrap(sender),
            this.chameleon, args
        ));
    }

}
