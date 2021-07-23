package dev.hypera.chameleon.spigot;

import dev.hypera.chameleon.core.objects.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.objects.users.ChatUser;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.command.CommandSender;

public class ChameleonCommandSender extends AudienceWrapper implements ChatUser {

    private final CommandSender sender;

    public ChameleonCommandSender(BukkitAudiences adventure, CommandSender sender) {
        super(adventure.sender(sender));
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

}
