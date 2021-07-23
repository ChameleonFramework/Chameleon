package dev.hypera.chameleon.core.objects;

import dev.hypera.chameleon.core.objects.commands.Command;
import dev.hypera.chameleon.core.objects.users.ChatUser;

public interface Chameleon {

    void onEnable();
    void onDisable();

    void registerCommand(Command command);

    ChatUser getConsoleSender();

}
