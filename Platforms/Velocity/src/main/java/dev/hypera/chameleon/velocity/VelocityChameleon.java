package dev.hypera.chameleon.velocity;

import com.velocitypowered.api.command.CommandManager;
import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.Plugin;
import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.velocity.commands.VelocityCommand;

public class VelocityChameleon extends Chameleon {

    private final VelocityPlugin velocityPlugin;

    public VelocityChameleon(Class<? extends Plugin> pluginClass, VelocityPlugin velocityPlugin) throws InstantiationException {
        super(pluginClass);
        this.velocityPlugin = velocityPlugin;
    }

    @Override
    public void registerCommand(Command command) {
        CommandManager commandManager = velocityPlugin.getServer().getCommandManager();
        commandManager.register(commandManager.metaBuilder(command.getName()).aliases(command.getAliases()).build(), new VelocityCommand(command));
    }

    @Override
    public ChatUser getConsoleSender() {
        return new ChameleonCommandSource(velocityPlugin.getServer().getConsoleCommandSource());
    }

}