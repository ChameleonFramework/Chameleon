package dev.hypera.chameleon.core;

import org.jetbrains.annotations.NotNull;

public abstract class Plugin {

    protected final Chameleon chameleon;

    public Plugin(@NotNull Chameleon chameleon) {
        this.chameleon = chameleon;
    }

    public @NotNull Chameleon getChameleon() {
        return chameleon;
    }

    public abstract void onEnable();
    public abstract void onDisable();

}
