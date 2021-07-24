package dev.hypera.chameleon.minestom;

import dev.hypera.chameleon.core.objects.Chameleon;
import dev.hypera.chameleon.core.objects.Plugin;
import dev.hypera.chameleon.core.objects.commands.Command;
import dev.hypera.chameleon.core.objects.users.ChatUser;
import dev.hypera.chameleon.minestom.commands.MinestomCommand;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;

public class MinestomChameleon extends Chameleon {

    private final Extension extension;

    public MinestomChameleon(Class<? extends Plugin> pluginClass, Extension extension) throws InstantiationException {
        super(pluginClass);
        this.extension = extension;
    }

    public Extension getExtension() {
        return extension;
    }

    @Override
    public void registerCommand(Command command) {
        MinecraftServer.getCommandManager().register(new MinestomCommand(command));
    }

    @Override
    public ChatUser getConsoleSender() {
        return new ChameleonCommandSender(MinecraftServer.getCommandManager().getConsoleSender());
    }

}
