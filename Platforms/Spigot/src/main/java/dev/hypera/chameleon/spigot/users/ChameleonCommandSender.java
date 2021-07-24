package dev.hypera.chameleon.spigot.users;

import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.spigot.SpigotChameleon;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public class ChameleonCommandSender extends AudienceWrapper implements ChatUser {

    private final @NotNull CommandSender sender;

    @ApiStatus.Internal
    public ChameleonCommandSender(@NotNull SpigotChameleon chameleon, @NotNull CommandSender sender) {
        super(chameleon.getAdventure().sender(sender));
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return sender.hasPermission(permission);
    }

}
