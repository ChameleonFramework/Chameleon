package dev.hypera.chameleon.minestom.commands;

import dev.hypera.chameleon.core.objects.commands.Command;
import dev.hypera.chameleon.minestom.ChameleonCommandSender;

public class MinestomCommand extends net.minestom.server.command.builder.Command {

    public MinestomCommand(Command command) {
        super(command.getName(), command.getAliases());
        setDefaultExecutor((sender, context) -> command.execute(new ChameleonCommandSender(sender), context.getInput().replace(context.getCommandName() + " ", "").split(" ")));
    }

}
