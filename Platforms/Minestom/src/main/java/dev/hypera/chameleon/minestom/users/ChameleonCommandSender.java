package dev.hypera.chameleon.minestom.users;

import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ChatUser;
import net.minestom.server.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;

public class ChameleonCommandSender extends AudienceWrapper implements ChatUser {

    private final CommandSender sender;

    @ApiStatus.Internal
    public ChameleonCommandSender(CommandSender sender) {
        super(sender);
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(String permission) {
        return sender.hasPermission(permission);
    }

}
