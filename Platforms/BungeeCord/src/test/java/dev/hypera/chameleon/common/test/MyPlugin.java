package dev.hypera.chameleon.common.test;

import dev.hypera.chameleon.common.test.commands.MyCommand;
import dev.hypera.chameleon.core.objects.Chameleon;
import dev.hypera.chameleon.core.objects.Plugin;

public class MyPlugin extends Plugin {

    public MyPlugin(Chameleon chameleon) {
        super(chameleon);
    }

    @Override
    public void onEnable() {
        chameleon.registerCommand(new MyCommand());
    }

    @Override
    public void onDisable() {

    }

}
