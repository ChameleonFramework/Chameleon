package dev.hypera.chameleon.bungeecord.users;

import dev.hypera.chameleon.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ChatUser;
import net.md_5.bungee.api.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class ChameleonCommandSender extends AudienceWrapper implements ChatUser {

    private final @NotNull CommandSender sender;

    @ApiStatus.Internal
    public ChameleonCommandSender(@NotNull BungeeCordChameleon chameleon, @NotNull CommandSender sender) {
        super(chameleon.getAdventure().sender(sender));
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return sender.hasPermission(permission);
    }

}
