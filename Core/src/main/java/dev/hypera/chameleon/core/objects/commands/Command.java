package dev.hypera.chameleon.core.objects.commands;

import dev.hypera.chameleon.core.objects.users.ChatUser;

import java.util.List;

public abstract class Command {

    private final String name;
    private final String[] aliases;

    public Command(String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public Command(String name) {
        this(name, new String[0]);
    }

    public String getName() {
        return name;
    }

    public String[] getAliases() {
        return aliases;
    }

    public abstract boolean execute(ChatUser user, String[] args);
    public abstract List<String> tabComplete(ChatUser user, String[] args);

}
