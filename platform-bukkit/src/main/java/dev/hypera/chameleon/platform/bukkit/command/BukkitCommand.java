/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2023 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.platform.bukkit.command;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.command.Command;
import dev.hypera.chameleon.command.context.ContextImpl;
import dev.hypera.chameleon.platform.bukkit.user.BukkitUserManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit command wrapper.
 */
@Internal
public final class BukkitCommand extends org.bukkit.command.Command {

    private final @NotNull Chameleon chameleon;
    private final @NotNull BukkitUserManager userManager;
    private final @NotNull Command command;

    /**
     * Bukkit command constructor.
     *
     * @param chameleon   Bukkit Chameleon implementation.
     * @param userManager Bukkit user manager implementation.
     * @param command     Command to be wrapped.
     */
    @Internal
    public BukkitCommand(
        @NotNull Chameleon chameleon,
        @NotNull BukkitUserManager userManager,
        @NotNull Command command
    ) {
        super(command.getName(), "", "", new ArrayList<>(command.getAliases()));
        this.chameleon = chameleon;
        this.userManager = userManager;
        this.command = command;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1 || this.command.executeSubCommand(new ContextImpl(
            this.userManager.wrap(sender), this.chameleon,
            Arrays.copyOfRange(args, 1, args.length)), args[0]
        )) {
            this.command.executeCommand(new ContextImpl(
                this.userManager.wrap(sender),
                this.chameleon, args
            ));
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return new ArrayList<>(this.command.tabComplete(new ContextImpl(
            this.userManager.wrap(sender),
            this.chameleon, args
        )));
    }

}
