package dev.hypera.chameleon.velocity;

import com.velocitypowered.api.proxy.ProxyServer;
import org.jetbrains.annotations.NotNull;

public interface VelocityPlugin {

    @NotNull ProxyServer getServer();

}
