package dev.hypera.chameleon.bungeecord;

import dev.hypera.chameleon.core.objects.internal.utils.ChameleonAudience;
import dev.hypera.chameleon.core.objects.users.ChatUser;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;

public class ChameleonCommandSender extends ChameleonAudience implements ChatUser {

    private final CommandSender sender;

    public ChameleonCommandSender(BungeeAudiences adventure, CommandSender sender) {
        super(adventure.sender(sender));
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

}
