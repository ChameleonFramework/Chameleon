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
package dev.hypera.example.commands;

import dev.hypera.chameleon.commands.Command;
import dev.hypera.chameleon.commands.annotations.CommandHandler;
import dev.hypera.chameleon.commands.annotations.Permission;
import dev.hypera.chameleon.commands.annotations.SubCommandHandler;
import dev.hypera.chameleon.commands.context.Context;
import dev.hypera.chameleon.commands.objects.Condition;
import dev.hypera.chameleon.commands.objects.Platform;
import dev.hypera.chameleon.users.User;
import java.util.Collections;
import java.util.List;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

/**
 * Example command.
 */
@Permission("example.command")
@CommandHandler("example|ex")
public class ExampleCommand extends Command {

    /**
     * Example command.
     */
    public ExampleCommand() {
        setPermissionErrorMessage(Component.text("No permission.", NamedTextColor.RED));
        setConditions(Condition.of(c -> c.getSender() instanceof User, Component.text("This command can only be used in-game.", NamedTextColor.RED)));
        setPlatform(Platform.ALL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Context context) {
        context.getSender().sendMessage(Component.text("Hello, world!"));
    }

    /**
     * Sub-command test.
     *
     * @param context context
     */
    @Permission("example.test")
    @SubCommandHandler("sub|test")
    public void sub(@NotNull Context context) {
        context.getSender().sendMessage(Component.text("Hello, " + (context.getArgs().length > 0 ? context.getArgs()[0] : context.getSender().getName()) + "!"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull List<String> tabComplete(@NotNull Context context) {
        return Collections.singletonList("tabcomplete");
    }

}
