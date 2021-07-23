package dev.hypera.chameleon.core.objects.internal.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class ChameleonAudience implements Audience { // TODO: Complete this class.

    private final Audience audience;

    public ChameleonAudience(Audience audience) {
        this.audience = audience;
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        audience.sendMessage(source, message, type);
    }

}
