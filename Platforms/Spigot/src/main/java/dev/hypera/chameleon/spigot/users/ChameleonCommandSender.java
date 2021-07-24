package dev.hypera.chameleon.spigot.users;

import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.spigot.SpigotChameleon;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;

public class ChameleonCommandSender extends AudienceWrapper implements ChatUser {

    private final CommandSender sender;

    @ApiStatus.Internal
    public ChameleonCommandSender(SpigotChameleon chameleon, CommandSender sender) {
        super(chameleon.getAdventure().sender(sender));
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

}
