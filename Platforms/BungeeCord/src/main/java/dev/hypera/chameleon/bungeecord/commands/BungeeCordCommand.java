package dev.hypera.chameleon.bungeecord.commands;

import dev.hypera.chameleon.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.bungeecord.ChameleonCommandSender;
import dev.hypera.chameleon.core.commands.Command;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.TabExecutor;
import org.jetbrains.annotations.NotNull;

public class BungeeCordCommand extends net.md_5.bungee.api.plugin.Command implements TabExecutor {

    private final @NotNull BungeeCordChameleon chameleon;
    private final @NotNull Command command;

    public BungeeCordCommand(@NotNull BungeeCordChameleon chameleon, @NotNull Command command) {
        super(command.getName(), null, command.getAliases());
        this.chameleon = chameleon;
        this.command = command;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        command.execute(new ChameleonCommandSender(chameleon.getAdventure(), commandSender), strings);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender commandSender, String[] strings) {
        return command.tabComplete(new ChameleonCommandSender(chameleon.getAdventure(), commandSender), strings);
    }

    public @NotNull Command getCommand() {
        return command;
    }

}
