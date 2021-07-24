package dev.hypera.chameleon.spigot;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.Plugin;
import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.spigot.commands.SpigotCommand;
import dev.hypera.chameleon.spigot.users.ChameleonCommandSender;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class SpigotChameleon extends Chameleon {

    private final @NotNull JavaPlugin spigotPlugin;
    private final @NotNull BukkitAudiences adventure;

    public SpigotChameleon(@NotNull Class<? extends Plugin> pluginClass, @NotNull JavaPlugin spigotPlugin) throws InstantiationException {
        super(pluginClass);
        this.spigotPlugin = spigotPlugin;
        this.adventure = BukkitAudiences.create(spigotPlugin);
    }

    public @NotNull JavaPlugin getPlugin() {
        return spigotPlugin;
    }

    public @NotNull BukkitAudiences getAdventure() {
        return adventure;
    }

    @Override
    public void registerCommand(@NotNull Command command) {
        try {
            Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMap.setAccessible(true);
            CommandMap map = (CommandMap) commandMap.get(Bukkit.getServer());
            SpigotCommand spigotCommand = new SpigotCommand(this, command);
            map.register(command.getName(), spigotPlugin.getName(), spigotCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public @NotNull ChatUser getConsoleSender() {
        return new ChameleonCommandSender(this, Bukkit.getConsoleSender());
    }

}
