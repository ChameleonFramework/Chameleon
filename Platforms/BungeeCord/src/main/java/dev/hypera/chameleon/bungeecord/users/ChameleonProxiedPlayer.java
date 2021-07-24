package dev.hypera.chameleon.bungeecord.users;

import dev.hypera.chameleon.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ProxyUser;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.ApiStatus;

public class ChameleonProxiedPlayer extends AudienceWrapper implements ProxyUser {

    private final ProxiedPlayer player;

    @ApiStatus.Internal
    public ChameleonProxiedPlayer(BungeeCordChameleon chameleon, ProxiedPlayer player) {
        super(chameleon.getAdventure().player(player));
        this.player = player;
    }

    @Override
    public boolean hasPermission(String permission) {
        return player.hasPermission(permission);
    }

}
