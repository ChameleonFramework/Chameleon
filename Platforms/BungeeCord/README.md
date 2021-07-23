# Chameleon for BungeeCord

## How do I support BungeeCord?
To support BungeeCord, you must create an instance of Chameleon in your BungeeCord main class:
```java
package org.example.chameleonproject;

import dev.hypera.chameleon.bungeecord.BungeeCordChameleon;
import net.md_5.bungee.api.plugin.Plugin;

public final class ChameleonProjectBungee extends Plugin {

    private BungeeCordChameleon chameleon;

    @Override
    public void onEnable() {
        try { chameleon = new BungeeCordChameleon(ChameleonProject.class, this); chameleon.onEnable(); }
        catch (InstantiationException e) { e.printStackTrace(); }
    }

    @Override
    public void onDisable() {
        chameleon.onDisable();
    }

}
```
A full example of a Chameleon plugin supporting BungeeCord is in [the example project](https://github.com/HyperaOfficial/ChameleonProject/tree/master/ChameleonProject-Bungee).