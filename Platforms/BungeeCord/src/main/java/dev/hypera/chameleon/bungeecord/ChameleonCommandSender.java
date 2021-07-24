package dev.hypera.chameleon.bungeecord;

import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ChatUser;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ChameleonCommandSender extends AudienceWrapper implements ChatUser {

    private final @NotNull CommandSender sender;

    public ChameleonCommandSender(@NotNull BungeeAudiences adventure, @NotNull CommandSender sender) {
        super(adventure.sender(sender));
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return sender.hasPermission(permission);
    }

}
