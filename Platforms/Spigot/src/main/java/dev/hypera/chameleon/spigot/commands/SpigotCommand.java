package dev.hypera.chameleon.spigot.commands;

import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.spigot.ChameleonCommandSender;
import dev.hypera.chameleon.spigot.SpigotChameleon;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class SpigotCommand extends org.bukkit.command.Command {

    private final SpigotChameleon chameleon;
    private final Command command;

    public SpigotCommand(SpigotChameleon chameleon, Command command) {
        super(command.getName(), "", "", Arrays.asList(command.getAliases()));
        this.chameleon = chameleon;
        this.command = command;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        return command.execute(new ChameleonCommandSender(chameleon.getAdventure(), sender), args);
    }

    @NotNull
    @Override
    public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return command.tabComplete(new ChameleonCommandSender(chameleon.getAdventure(), sender), args);
    }

}
