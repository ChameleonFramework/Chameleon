package dev.hypera.chameleon.minestom;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.Plugin;
import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.minestom.commands.MinestomCommand;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull;

public class MinestomChameleon extends Chameleon {

    private final @NotNull Extension extension;

    public MinestomChameleon(@NotNull Class<? extends Plugin> pluginClass, @NotNull Extension extension) throws InstantiationException {
        super(pluginClass);
        this.extension = extension;
    }

    public @NotNull Extension getExtension() {
        return extension;
    }

    @Override
    public void registerCommand(@NotNull Command command) {
        MinecraftServer.getCommandManager().register(new MinestomCommand(command));
    }

    @Override
    public @NotNull ChatUser getConsoleSender() {
        return new ChameleonCommandSender(MinecraftServer.getCommandManager().getConsoleSender());
    }

}
