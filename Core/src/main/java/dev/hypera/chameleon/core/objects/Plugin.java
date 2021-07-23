package dev.hypera.chameleon.core.objects;

public abstract class Plugin {

    protected final Chameleon chameleon;

    public Plugin(Chameleon chameleon) {
        this.chameleon = chameleon;
    }

    public Chameleon getChameleon() {
        return chameleon;
    }

    public abstract void onEnable();
    public abstract void onDisable();

}
