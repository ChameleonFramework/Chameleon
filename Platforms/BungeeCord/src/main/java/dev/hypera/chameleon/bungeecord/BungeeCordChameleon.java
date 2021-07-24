package dev.hypera.chameleon.bungeecord;

import dev.hypera.chameleon.bungeecord.commands.BungeeCordCommand;
import dev.hypera.chameleon.bungeecord.users.ChameleonCommandSender;
import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.users.ChatUser;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;

public class BungeeCordChameleon extends Chameleon {

    private final Plugin bungeePlugin;
    private final BungeeAudiences adventure;

    public BungeeCordChameleon(Class<? extends dev.hypera.chameleon.core.Plugin> pluginClass, Plugin bungeePlugin) throws InstantiationException {
        super(pluginClass);
        this.bungeePlugin = bungeePlugin;
        this.adventure = BungeeAudiences.create(bungeePlugin);
    }

    public Plugin getPlugin() {
        return bungeePlugin;
    }

    public BungeeAudiences getAdventure() {
        return adventure;
    }

    @Override
    public void registerCommand(Command command) {
        bungeePlugin.getProxy().getPluginManager().registerCommand(bungeePlugin, new BungeeCordCommand(this, command));
    }

    @Override
    public ChatUser getConsoleSender() {
        return new ChameleonCommandSender(this, bungeePlugin.getProxy().getConsole());
    }

}
