package dev.hypera.chameleon.spigot.users;

import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ServerUser;
import dev.hypera.chameleon.spigot.SpigotChameleon;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

public class ChameleonPlayer extends AudienceWrapper implements ServerUser {

    private final Player player;

    @ApiStatus.Internal
    public ChameleonPlayer(SpigotChameleon chameleon, Player player) {
        super(chameleon.getAdventure().player(player));
        this.player = player;
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

}
