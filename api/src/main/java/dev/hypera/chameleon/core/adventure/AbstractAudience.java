/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package dev.hypera.chameleon.core.adventure;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.MessageType;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

/**
 * Implements the Audience methods to prevent duplicate code.
 */
public abstract class AbstractAudience implements Audience {

    private final @NotNull Audience audience;

    /**
     * {@link AbstractAudience} constructor.
     *
     * @param audience Adventure {@link Audience}.
     */
    public AbstractAudience(@NotNull Audience audience) {
        this.audience = audience;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull ComponentLike message) {
        this.audience.sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message) {
        this.audience.sendMessage(source, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message) {
        this.audience.sendMessage(source, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Component message) {
        this.audience.sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identified source, @NotNull Component message) {
        this.audience.sendMessage(source, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message) {
        this.audience.sendMessage(source, message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull ComponentLike message, @NotNull MessageType type) {
        this.audience.sendMessage(message, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message, @NotNull MessageType type) {
        this.audience.sendMessage(source, message, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message, @NotNull MessageType type) {
        this.audience.sendMessage(source, message, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Component message, @NotNull MessageType type) {
        this.audience.sendMessage(message, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {
        this.audience.sendMessage(source, message, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        this.audience.sendMessage(source, message, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendActionBar(@NotNull ComponentLike message) {
        this.audience.sendActionBar(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendActionBar(@NotNull Component message) {
        this.audience.sendActionBar(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListHeader(@NotNull ComponentLike header) {
        this.audience.sendPlayerListHeader(header);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListHeader(@NotNull Component header) {
        this.audience.sendPlayerListHeader(header);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListFooter(@NotNull ComponentLike footer) {
        this.audience.sendPlayerListFooter(footer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListFooter(@NotNull Component footer) {
        this.audience.sendPlayerListFooter(footer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull ComponentLike header, @NotNull ComponentLike footer) {
        this.audience.sendPlayerListHeaderAndFooter(header, footer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
        this.audience.sendPlayerListHeaderAndFooter(header, footer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showTitle(@NotNull Title title) {
        this.audience.showTitle(title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearTitle() {
        this.audience.clearTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetTitle() {
        this.audience.resetTitle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showBossBar(@NotNull BossBar bar) {
        this.audience.showBossBar(bar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideBossBar(@NotNull BossBar bar) {
        this.audience.hideBossBar(bar);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playSound(@NotNull Sound sound) {
        this.audience.playSound(sound);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playSound(@NotNull Sound sound, double x, double y, double z) {
        this.audience.playSound(sound, x, y, z);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopSound(@NotNull SoundStop stop) {
        this.audience.stopSound(stop);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openBook(Book.@NotNull Builder book) {
        this.audience.openBook(book);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openBook(@NotNull Book book) {
        this.audience.openBook(book);
    }

}
