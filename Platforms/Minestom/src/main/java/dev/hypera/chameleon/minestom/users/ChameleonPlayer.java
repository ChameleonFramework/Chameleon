package dev.hypera.chameleon.minestom.users;

import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ServerUser;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.ApiStatus;

public class ChameleonPlayer extends AudienceWrapper implements ServerUser {

    private final Player player;

    @ApiStatus.Internal
    public ChameleonPlayer(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

}
