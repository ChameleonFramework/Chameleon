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
import dev.hypera.chameleon.core.exceptions.ChameleonInstantiationException;
import dev.hypera.chameleon.velocity.VelocityChameleon;
import dev.hypera.chameleon.velocity.VelocityPlugin;

@Plugin(id = "chameleonproject", name = "ChameleonProject", version = "1.0-SNAPSHOT")
public class ChameleonProjectVelocity implements VelocityPlugin {

    private final ProxyServer server;
    private final Path dataDirectory;
    private VelocityChameleon chameleon;

    @Inject public ChameleonProjectVelocity(ProxyServer server, @DataDirectory Path dataDirectory) { this.server = server; this.dataDirectory = dataDirectory; }
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        try { chameleon = new VelocityChameleon(ChameleonProject.class, this); chameleon.onEnable(); }
        catch (ChameleonInstantiationException e) { e.printStackTrace(); }
    }
    @Subscribe public void onProxyShutdown(ProxyShutdownEvent event) { chameleon.onDisable(); }
    @Override public @NotNull ProxyServer getServer() { return server; }
    @Override public @NotNull Path getDataDirectory() { return dataDirectory; }

}
```
A full example of a Chameleon plugin supporting Velocity is in [the example project](https://github.com/HyperaOfficial/ChameleonProject/tree/main/Velocity).