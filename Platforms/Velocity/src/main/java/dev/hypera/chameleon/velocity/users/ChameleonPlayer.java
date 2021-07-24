package dev.hypera.chameleon.velocity.users;

import com.velocitypowered.api.proxy.Player;
import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ProxyUser;
import org.jetbrains.annotations.ApiStatus;

public class ChameleonPlayer extends AudienceWrapper implements ProxyUser {

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
