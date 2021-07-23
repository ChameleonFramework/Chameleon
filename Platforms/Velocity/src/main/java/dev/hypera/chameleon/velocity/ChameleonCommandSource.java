package dev.hypera.chameleon.velocity;

import com.velocitypowered.api.command.CommandSource;
import dev.hypera.chameleon.core.objects.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.objects.users.ChatUser;

public class ChameleonCommandSource extends AudienceWrapper implements ChatUser {

    private final CommandSource commandSource;

    public ChameleonCommandSource(CommandSource commandSource) {
        super(commandSource);
        this.commandSource = commandSource;
    }

    @Override
    public boolean hasPermission(String permission) {
        return commandSource.hasPermission(permission);
    }

}
