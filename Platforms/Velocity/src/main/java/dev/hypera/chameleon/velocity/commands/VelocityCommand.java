package dev.hypera.chameleon.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.velocity.users.VelocityUserManager;

import java.util.List;

public class VelocityCommand implements SimpleCommand {

    private final Command command;

    public VelocityCommand(Command command) {
        this.command = command;
    }

    @Override
    public void execute(Invocation invocation) {
        command.execute(VelocityUserManager.getUser(invocation.source()), invocation.arguments());
    }

    @Override
    public List<String> suggest(Invocation invocation) {
        return command.tabComplete(VelocityUserManager.getUser(invocation.source()), invocation.arguments());
    }

}
