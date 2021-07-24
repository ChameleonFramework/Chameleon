package dev.hypera.chameleon.minestom;

import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ChatUser;
import net.minestom.server.command.CommandSender;

public class ChameleonCommandSender extends AudienceWrapper implements ChatUser {

    private final CommandSender sender;

    public ChameleonCommandSender(CommandSender sender) {
        super(sender);
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

}
