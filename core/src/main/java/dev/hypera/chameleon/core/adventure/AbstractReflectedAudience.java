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

import dev.hypera.chameleon.core.adventure.conversion.AdventureConverter;
import java.lang.reflect.Method;
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
 * Implements the Audience methods and uses Reflection to map objects to the non-shaded versions of themselves.
 */
public abstract class AbstractReflectedAudience implements Audience {

    private final static @NotNull Class<?> AUDIENCE_CLASS;
    private final static @NotNull Method SEND_MESSAGE;
    private final static @NotNull Method SEND_ACTION_BAR;
    private final static @NotNull Method SEND_PLAYER_LIST_HEADER;
    private final static @NotNull Method SEND_PLAYER_LIST_FOOTER;
    private final static @NotNull Method SEND_PLAYER_LIST_HEADER_FOOTER;
    private final static @NotNull Method SHOW_TITLE;
    private final static @NotNull Method CLEAR_TITLE;
    private final static @NotNull Method RESET_TITLE;
    private final static @NotNull Method SHOW_BOSS_BAR;
    private final static @NotNull Method HIDE_BOSS_BAR;
    private final static @NotNull Method PLAY_SOUND;
    private final static @NotNull Method PLAY_SOUND_LOCATION;
    private final static @NotNull Method STOP_SOUND;
    private final static @NotNull Method OPEN_BOOK;
    private final @NotNull Object audience;

    static {
        try {
            Class<?> componentClass = Class.forName(AdventureConverter.PACKAGE + "text.Component");
            Class<?> titleClass = Class.forName(AdventureConverter.PACKAGE + "title.Title");
            Class<?> bossBarClass = Class.forName(AdventureConverter.PACKAGE + "bossbar.BossBar");
            Class<?> soundClass = Class.forName(AdventureConverter.PACKAGE + "sound.Sound");
            Class<?> soundStopClass = Class.forName(AdventureConverter.PACKAGE + "sound.SoundStop");
            Class<?> bookClass = Class.forName(AdventureConverter.PACKAGE + "inventory.Book");
            AUDIENCE_CLASS = Class.forName(AdventureConverter.PACKAGE + "audience.Audience");

            SEND_MESSAGE = AUDIENCE_CLASS.getMethod("sendMessage", componentClass);
            SEND_ACTION_BAR = AUDIENCE_CLASS.getMethod("sendActionBar", componentClass);

            SEND_PLAYER_LIST_HEADER = AUDIENCE_CLASS.getMethod("sendPlayerListHeader", componentClass);
            SEND_PLAYER_LIST_FOOTER = AUDIENCE_CLASS.getMethod("sendPlayerListFooter", componentClass);
            SEND_PLAYER_LIST_HEADER_FOOTER = AUDIENCE_CLASS.getMethod("sendPlayerListHeaderAndFooter", componentClass, componentClass);

            SHOW_TITLE = AUDIENCE_CLASS.getMethod("showTitle", titleClass);
            CLEAR_TITLE = AUDIENCE_CLASS.getMethod("clearTitle");
            RESET_TITLE = AUDIENCE_CLASS.getMethod("resetTitle");

            SHOW_BOSS_BAR = AUDIENCE_CLASS.getMethod("showBossBar", bossBarClass);
            HIDE_BOSS_BAR = AUDIENCE_CLASS.getMethod("hideBossBar", bossBarClass);

            PLAY_SOUND = AUDIENCE_CLASS.getMethod("playSound", soundClass);
            PLAY_SOUND_LOCATION = AUDIENCE_CLASS.getMethod("playSound", soundClass, double.class, double.class, double.class);
            STOP_SOUND = AUDIENCE_CLASS.getMethod("stopSound", soundStopClass);

            OPEN_BOOK = AUDIENCE_CLASS.getMethod("openBook", bookClass);
        } catch (Exception ex) {
            throw new IllegalStateException("Failed to initialise AbstractReflectedAudience", ex);
        }
    }

    /**
     * {@link AbstractReflectedAudience} constructor.
     *
     * @param audience Adventure platform {@link Audience}.
     */
    public AbstractReflectedAudience(@NotNull Object audience) {
        if (!AUDIENCE_CLASS.isInstance(audience)) {
            throw new IllegalArgumentException("'audience' is not instance of " + AdventureConverter.PACKAGE + "audience.Audience");
        }

        this.audience = audience;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull ComponentLike message) {
        try {
            SEND_MESSAGE.invoke(this.audience, AdventureConverter.convertComponent(message));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message) {
        sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message) {
        sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Component message) {
        try {
            SEND_MESSAGE.invoke(this.audience, AdventureConverter.convertComponent(message));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identified source, @NotNull Component message) {
        sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message) {
        sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull ComponentLike message, @NotNull MessageType type) {
        sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identified source, @NotNull ComponentLike message, @NotNull MessageType type) {
        sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identity source, @NotNull ComponentLike message, @NotNull MessageType type) {
        sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Component message, @NotNull MessageType type) {
        sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull MessageType type) {
        sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull MessageType type) {
        sendMessage(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendActionBar(@NotNull ComponentLike message) {
        try {
            SEND_ACTION_BAR.invoke(this.audience, AdventureConverter.convertComponent(message));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendActionBar(@NotNull Component message) {
        try {
            SEND_ACTION_BAR.invoke(this.audience, AdventureConverter.convertComponent(message));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListHeader(@NotNull ComponentLike header) {
        try {
            SEND_PLAYER_LIST_HEADER.invoke(this.audience, AdventureConverter.convertComponent(header));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListHeader(@NotNull Component header) {
        try {
            SEND_PLAYER_LIST_HEADER.invoke(this.audience, AdventureConverter.convertComponent(header));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListFooter(@NotNull ComponentLike footer) {
        try {
            SEND_PLAYER_LIST_FOOTER.invoke(this.audience, AdventureConverter.convertComponent(footer));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListFooter(@NotNull Component footer) {
        try {
            SEND_PLAYER_LIST_FOOTER.invoke(this.audience, AdventureConverter.convertComponent(footer));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull ComponentLike header, @NotNull ComponentLike footer) {
        try {
            SEND_PLAYER_LIST_HEADER_FOOTER.invoke(this.audience, AdventureConverter.convertComponent(header), AdventureConverter.convertComponent(footer));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
        try {
            SEND_PLAYER_LIST_HEADER_FOOTER.invoke(this.audience, AdventureConverter.convertComponent(header), AdventureConverter.convertComponent(footer));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showTitle(@NotNull Title title) {
        try {
            SHOW_TITLE.invoke(this.audience, AdventureConverter.convertTitle(title));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clearTitle() {
        try {
            CLEAR_TITLE.invoke(this.audience);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetTitle() {
        try {
            RESET_TITLE.invoke(this.audience);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void showBossBar(@NotNull BossBar bar) {
        try {
            SHOW_BOSS_BAR.invoke(this.audience, AdventureConverter.convertBossBar(bar));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void hideBossBar(@NotNull BossBar bar) {
        try {
            HIDE_BOSS_BAR.invoke(this.audience, AdventureConverter.convertBossBar(bar));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playSound(@NotNull Sound sound) {
        try {
            PLAY_SOUND.invoke(this.audience, AdventureConverter.convertSound(sound));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void playSound(@NotNull Sound sound, double x, double y, double z) {
        try {
            PLAY_SOUND_LOCATION.invoke(this.audience, AdventureConverter.convertSound(sound), x, y, z);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stopSound(@NotNull SoundStop stop) {
        try {
            STOP_SOUND.invoke(this.audience, AdventureConverter.convertSoundStop(stop));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openBook(Book.@NotNull Builder book) {
        try {
            OPEN_BOOK.invoke(this.audience, AdventureConverter.convertBook(book.build()));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openBook(@NotNull Book book) {
        try {
            OPEN_BOOK.invoke(this.audience, AdventureConverter.convertBook(book));
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

}
