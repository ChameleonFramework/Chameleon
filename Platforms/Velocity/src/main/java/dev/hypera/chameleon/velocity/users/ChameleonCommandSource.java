package dev.hypera.chameleon.velocity.users;

import com.velocitypowered.api.command.CommandSource;
import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ChatUser;
import org.jetbrains.annotations.ApiStatus;

public class ChameleonCommandSource extends AudienceWrapper implements ChatUser {

    private final CommandSource commandSource;

    @ApiStatus.Internal
    public ChameleonCommandSource(CommandSource commandSource) {
        super(commandSource);
        this.commandSource = commandSource;
    }

    @Override
    public boolean hasPermission(String permission) {
        return commandSource.hasPermission(permission);
    }

}
