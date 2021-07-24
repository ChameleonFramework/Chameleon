package dev.hypera.chameleon.core.commands;

import dev.hypera.chameleon.core.users.ChatUser;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class Command {

    private final @NotNull String name;
    private final @NotNull String[] aliases;

    public Command(@NotNull String name, @NotNull String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public Command(@NotNull String name) {
        this(name, new String[0]);
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String[] getAliases() {
        return aliases;
    }

    public abstract boolean execute(@NotNull ChatUser user, @NotNull String[] args);
    public abstract List<String> tabComplete(@NotNull ChatUser user, @NotNull String[] args);

}
