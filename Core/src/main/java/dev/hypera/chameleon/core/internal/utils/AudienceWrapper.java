package dev.hypera.chameleon.core.internal.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.pointer.Pointer;
import net.kyori.adventure.pointer.Pointers;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Optional;
import java.util.function.Supplier;

public class AudienceWrapper implements Audience {

    private final Audience audience;

    public AudienceWrapper(Audience audience) {
        this.audience = audience;
    }

    @Override
    public void sendMessage(@NotNull ComponentLike message) {
        audience.sendMessage(message);
    }

    @Override
    public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message) {
        audience.sendMessage(source, message);
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message) {
        audience.sendMessage(source, message);
    }

    @Override
    public void sendMessage(@NotNull Component message) {
        audience.sendMessage(message);
    }

    @Override
    public void sendMessage(@NotNull Identified source, @NotNull Component message) {
        audience.sendMessage(source, message);
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message) {
        audience.sendMessage(source, message);
    }

    @Override
    public void sendMessage(@NotNull ComponentLike message, @NotNull MessageType type) {
        audience.sendMessage(message, type);
    }

    @Override
    public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message, @NotNull MessageType type) {
        audience.sendMessage(source, message, type);
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message, @NotNull MessageType type) {
        audience.sendMessage(source, message, type);
    }

    @Override
    public void sendMessage(@NotNull Component message, @NotNull MessageType type) {
        audience.sendMessage(message, type);
    }

    @Override
    public void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {
        audience.sendMessage(source, message, type);
    }

    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        audience.sendMessage(source, message, type);
    }

    @Override
    public void sendActionBar(@NotNull ComponentLike message) {
        audience.sendActionBar(message);
    }

    @Override
    public void sendActionBar(@NotNull Component message) {
        audience.sendActionBar(message);
    }

    @Override
    public void sendPlayerListHeader(@NotNull ComponentLike header) {
        audience.sendPlayerListHeader(header);
    }

    @Override
    public void sendPlayerListHeader(@NotNull Component header) {
        audience.sendPlayerListHeader(header);
    }

    @Override
    public void sendPlayerListFooter(@NotNull ComponentLike footer) {
        audience.sendPlayerListFooter(footer);
    }

    @Override
    public void sendPlayerListFooter(@NotNull Component footer) {
        audience.sendPlayerListFooter(footer);
    }

    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull ComponentLike header, @NotNull ComponentLike footer) {
        audience.sendPlayerListHeaderAndFooter(header, footer);
    }

    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
        audience.sendPlayerListHeaderAndFooter(header, footer);
    }

    @Override
    public void showTitle(@NotNull Title title) {
        audience.showTitle(title);
    }

    @Override
    public void clearTitle() {
        audience.clearTitle();
    }

    @Override
    public void resetTitle() {
        audience.resetTitle();
    }

    @Override
    public void showBossBar(@NotNull BossBar bar) {
        audience.showBossBar(bar);
    }

    @Override
    public void hideBossBar(@NotNull BossBar bar) {
        audience.hideBossBar(bar);
    }

    @Override
    public void playSound(@NotNull Sound sound) {
        audience.playSound(sound);
    }

    @Override
    public void playSound(@NotNull Sound sound, double x, double y, double z) {
        audience.playSound(sound, x, y, z);
    }

    @Override
    public void stopSound(@NotNull Sound sound) {
        audience.stopSound(sound);
    }

    @Override
    public void playSound(@NotNull Sound sound, Sound.@NotNull Emitter emitter) {
        audience.playSound(sound, emitter);
    }

    @Override
    public void stopSound(@NotNull SoundStop stop) {
        audience.stopSound(stop);
    }

    @Override
    public void openBook(Book.@NotNull Builder book) {
        audience.openBook(book);
    }

    @Override
    public void openBook(@NotNull Book book) {
        audience.openBook(book);
    }

    @Override
    public @NotNull <T> Optional<T> get(@NotNull Pointer<T> pointer) {
        return audience.get(pointer);
    }

    @Override
    public <T> @Nullable T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T defaultValue) {
        return audience.getOrDefault(pointer, defaultValue);
    }

    @Override
    public <T> @UnknownNullability T getOrDefaultFrom(@NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> defaultValue) {
        return audience.getOrDefaultFrom(pointer, defaultValue);
    }

    @Override
    public @NotNull Pointers pointers() {
        return audience.pointers();
    }

}
