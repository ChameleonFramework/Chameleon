# Chameleon for Minestom

## How do I support Minestom?
To support Minestom, you must create an instance of Chameleon in your Minestom main class:
```java
package org.example.chameleonproject;

import dev.hypera.chameleon.minestom.MinestomChameleon;
import net.minestom.server.extensions.Extension;

public class ChameleonProjectMinestom extends Extension {

    private MinestomChameleon chameleon;

    @Override
    public void initialize() {
        try { chameleon = new MinestomChameleon(ChameleonProject.class, this); chameleon.onEnable(); }
        catch (ChameleonInstantiationException e) { e.printStackTrace(); }
    }

    @Override
    public void terminate() {
        chameleon.onDisable();
    }

}
```
A full example of a Chameleon plugin supporting Minestom is in [the example project](https://github.com/HyperaOfficial/ChameleonProject/tree/main/Minestom).