package dev.hypera.chameleon.common.test.commands;

import dev.hypera.chameleon.core.objects.commands.Command;
import dev.hypera.chameleon.core.objects.users.ChatUser;
import net.kyori.adventure.text.Component;

import java.util.Collections;
import java.util.List;

public class MyCommand extends Command {

    public MyCommand() {
        super("mycommand");
    }

    @Override
    public boolean execute(ChatUser user, String[] args) {
        user.sendMessage(Component.text("You ran my command!"));
        return true;
    }

    @Override
    public List<String> tabComplete(ChatUser user, String[] args) {
        return Collections.emptyList();
    }

}
