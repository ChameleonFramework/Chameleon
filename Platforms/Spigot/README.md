# Chameleon for Spigot

## How do I support Spigot?
To support Spigot, you must create an instance of Chameleon in your Spigot main class:
```java
package org.example.chameleonproject;

import dev.hypera.chameleon.spigot.SpigotChameleon;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChameleonProjectSpigot extends JavaPlugin {

    private SpigotChameleon chameleon;

    @Override
    public void onEnable() {
        try { chameleon = new SpigotChameleon(ChameleonProject.class, this); chameleon.onEnable(); }
        catch (InstantiationException e) { e.printStackTrace(); }
    }

    @Override
    public void onDisable() {
        chameleon.onDisable();
    }

}
```
A full example of a Chameleon plugin supporting Spigot is in [the example project](https://github.com/HyperaOfficial/ChameleonProject/tree/master/ChameleonProject-Spigot).