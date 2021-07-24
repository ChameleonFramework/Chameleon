package dev.hypera.chameleon.velocity.users;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import dev.hypera.chameleon.core.users.ChatUser;

public class VelocityUserManager {

    public static ChatUser getUser(CommandSource user) {
        if (user instanceof Player) return new ChameleonPlayer((Player) user);
        else return new ChameleonCommandSource(user);
    }

}
