package dev.hypera.chameleon.bungeecord;

import dev.hypera.chameleon.bungeecord.commands.BungeeCordCommand;
import dev.hypera.chameleon.core.objects.Chameleon;
import dev.hypera.chameleon.core.objects.commands.Command;
import dev.hypera.chameleon.core.objects.users.ChatUser;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;

public abstract class BungeeCordChameleon implements Chameleon {

    private final Plugin plugin;
    private final BungeeAudiences adventure;

    public BungeeCordChameleon(Plugin plugin) {
        this.plugin = plugin;
        this.adventure = BungeeAudiences.create(plugin);
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public BungeeAudiences getAdventure() {
        return adventure;
    }

    @Override
    public void registerCommand(Command command) {
        plugin.getProxy().getPluginManager().registerCommand(plugin, new BungeeCordCommand(this, command));
    }

    @Override
    public ChatUser getConsoleSender() {
        return new ChameleonCommandSender(adventure, plugin.getProxy().getConsole());
    }

}
