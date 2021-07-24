package dev.hypera.chameleon.spigot.users;

import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.spigot.SpigotChameleon;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpigotUserManager {

    public static ChatUser getUser(SpigotChameleon chameleon, CommandSender user) {
        if (user instanceof Player) return new ChameleonPlayer(chameleon, (Player) user);
        else return new ChameleonCommandSender(chameleon, user);
    }

}
