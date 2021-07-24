package dev.hypera.chameleon.velocity;

import com.velocitypowered.api.command.CommandManager;
import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.Plugin;
import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.velocity.commands.VelocityCommand;
import org.jetbrains.annotations.NotNull;

public class VelocityChameleon extends Chameleon {

    private final @NotNull VelocityPlugin velocityPlugin;

    public VelocityChameleon(@NotNull Class<? extends Plugin> pluginClass, @NotNull VelocityPlugin velocityPlugin) throws InstantiationException {
        super(pluginClass);
        this.velocityPlugin = velocityPlugin;
    }

    @Override
    public void registerCommand(@NotNull Command command) {
        CommandManager commandManager = velocityPlugin.getServer().getCommandManager();
        commandManager.register(commandManager.metaBuilder(command.getName()).aliases(command.getAliases()).build(), new VelocityCommand(command));
    }

    @Override
    public @NotNull ChatUser getConsoleSender() {
        return new ChameleonCommandSource(velocityPlugin.getServer().getConsoleCommandSource());
    }

}
