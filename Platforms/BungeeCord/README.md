# Chameleon for BungeeCord

## How do I support BungeeCord?
To support BungeeCord, you must create an instance of Chameleon in your BungeeCord main class:
```java
package dev.hypera.chameleon.bungeecord.test;

import dev.hypera.chameleon.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.common.test.MyPlugin;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * This class represents the code needed for a Chameleon project to support BungeeCord.
 */
public class MyBungeeCord extends Plugin {

    private BungeeCordChameleon chameleon;

    @Override
    public void onEnable() {
        try { chameleon = new BungeeCordChameleon(MyPlugin.class, this); chameleon.onEnable(); }
        catch (InstantiationException e) { e.printStackTrace(); }
    }

    @Override
    public void onDisable() {
        chameleon.onDisable();
    }

}
```
A full example of a Chameleon plugin supporting BungeeCord is in `src/test`.