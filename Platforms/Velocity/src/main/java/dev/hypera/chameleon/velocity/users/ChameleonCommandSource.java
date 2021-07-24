package dev.hypera.chameleon.velocity.users;

import com.velocitypowered.api.command.CommandSource;
import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ChatUser;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus;

public class ChameleonCommandSource extends AudienceWrapper implements ChatUser {

    private final @NotNull CommandSource commandSource;

    @ApiStatus.Internal
    public ChameleonCommandSource(@NotNull CommandSource commandSource) {
        super(commandSource);
        this.commandSource = commandSource;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return commandSource.hasPermission(permission);
    }

}
