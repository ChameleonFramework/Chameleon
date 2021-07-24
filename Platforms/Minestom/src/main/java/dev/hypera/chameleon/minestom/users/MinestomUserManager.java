package dev.hypera.chameleon.minestom.users;

import dev.hypera.chameleon.core.users.ChatUser;
import net.minestom.server.command.CommandSender;
import net.minestom.server.entity.Player;

public class MinestomUserManager {

    public static ChatUser getUser(CommandSender user) {
        if (user instanceof Player) return new ChameleonPlayer((Player) user);
        else return new ChameleonCommandSender(user);
    }

}
