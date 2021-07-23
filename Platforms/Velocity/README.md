# Chameleon for Velocity

## How do I support Velocity?
To support Velocity, you must create an instance of Chameleon in your Velocity main class and make it implement `VelocityServer`:
```java
package org.example.chameleonproject;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.hypera.chameleon.velocity.VelocityChameleon;
import dev.hypera.chameleon.velocity.VelocityPlugin;

@Plugin(id = "chameleonproject", name = "ChameleonProject", version = "1.0-SNAPSHOT")
public class ChameleonProjectVelocity implements VelocityPlugin {

    private final ProxyServer server;
    private final VelocityChameleon chameleon = new VelocityChameleon(ChameleonProject.class, this);

    @Inject public ChameleonProjectVelocity(ProxyServer server) throws InstantiationException { this.server = server; }
    @Subscribe public void onProxyInitialization(ProxyInitializeEvent event) { chameleon.onEnable(); }
    @Subscribe public void onProxyShutdown(ProxyShutdownEvent event) { chameleon.onDisable(); }
    @Override public ProxyServer getServer() { return server; }

}
```
A full example of a Chameleon plugin supporting Velocity is in [the example project](https://github.com/HyperaOfficial/ChameleonProject/tree/master/ChameleonProject-Velocity).