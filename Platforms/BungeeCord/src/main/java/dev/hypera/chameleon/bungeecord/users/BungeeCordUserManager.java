package dev.hypera.chameleon.bungeecord.users;

import dev.hypera.chameleon.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.core.users.ChatUser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class BungeeCordUserManager {

    public static ChatUser getUser(BungeeCordChameleon chameleon, CommandSender user) {
        if (user instanceof ProxiedPlayer) return new ChameleonProxiedPlayer(chameleon, (ProxiedPlayer) user);
        else return new ChameleonCommandSender(chameleon, user);
    }

}
