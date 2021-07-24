package dev.hypera.chameleon.core;

import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.users.ChatUser;

import java.lang.reflect.InvocationTargetException;

public abstract class Chameleon {

    protected final Plugin plugin;

    public Chameleon(Class<? extends Plugin> pluginClass) throws InstantiationException {
        try {
            this.plugin = pluginClass.getConstructor(Chameleon.class).newInstance(this);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InstantiationException("Failed to initialise instance of " + pluginClass.getSimpleName());
        }
    }

    public void onEnable() {
        plugin.onEnable();
    }
    public void onDisable() {
        plugin.onDisable();
    }

    public abstract void registerCommand(Command command);

    public abstract ChatUser getConsoleSender();

}
