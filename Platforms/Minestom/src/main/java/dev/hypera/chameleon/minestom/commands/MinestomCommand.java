package dev.hypera.chameleon.minestom.commands;

import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.minestom.users.MinestomUserManager;
import dev.hypera.chameleon.minestom.ChameleonCommandSender;
import org.jetbrains.annotations.NotNull;

public class MinestomCommand extends net.minestom.server.command.builder.Command {

    public MinestomCommand(@NotNull Command command) {
        super(command.getName(), command.getAliases());
        setDefaultExecutor((sender, context) -> command.execute(MinestomUserManager.getUser(sender), context.getInput().replace(context.getCommandName() + " ", "").split(" ")));
    }

}
