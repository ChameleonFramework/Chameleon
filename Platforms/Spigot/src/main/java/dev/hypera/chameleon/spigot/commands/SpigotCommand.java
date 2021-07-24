package dev.hypera.chameleon.spigot.commands;

import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.spigot.SpigotChameleon;
import dev.hypera.chameleon.spigot.users.SpigotUserManager;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class SpigotCommand extends org.bukkit.command.Command {

    private final @NotNull SpigotChameleon chameleon;
    private final @NotNull Command command;

    public SpigotCommand(@NotNull SpigotChameleon chameleon, @NotNull Command command) {
        super(command.getName(), "", "", Arrays.asList(command.getAliases()));
        this.chameleon = chameleon;
        this.command = command;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return command.execute(SpigotUserManager.getUser(chameleon, sender), args);
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return command.tabComplete(SpigotUserManager.getUser(chameleon, sender), args);
    }

}
