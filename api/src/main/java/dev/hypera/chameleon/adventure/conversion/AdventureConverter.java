/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2023 The Chameleon Framework Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.hypera.chameleon.adventure.conversion;

import dev.hypera.chameleon.adventure.conversion.mapping.BookMapper;
import dev.hypera.chameleon.adventure.conversion.mapping.BossBarMapper;
import dev.hypera.chameleon.adventure.conversion.mapping.ComponentMapper;
import dev.hypera.chameleon.adventure.conversion.mapping.KeyMapper;
import dev.hypera.chameleon.adventure.conversion.mapping.SoundMapper;
import dev.hypera.chameleon.adventure.conversion.mapping.SoundStopMapper;
import dev.hypera.chameleon.adventure.conversion.mapping.TitleMapper;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Because Velocity and Minestom provide Adventure, we cannot use the shaded/relocated version of adventure without there being problems. To get around this we convert the shaded Adventure objects to the platform ones using reflection.
 *
 * <p>
 * Adapted from Lucko's AdventureCompat class in LuckPerms for use in Chameleon.
 * </p>
 */
@Internal
public final class AdventureConverter {

    public static final String PACKAGE = "net.ky".concat("ori.adventure.");

    private static final @NotNull KeyMapper KEY_CONVERTER = new KeyMapper();
    private static final @NotNull ComponentMapper COMPONENT_CONVERTER = new ComponentMapper();
    private static final @NotNull TitleMapper TITLE_CONVERTER = new TitleMapper();
    private static final @NotNull BossBarMapper BOSS_BAR_CONVERTER = new BossBarMapper();
    private static final @NotNull SoundMapper SOUND_CONVERTER = new SoundMapper();
    private static final @NotNull SoundStopMapper SOUND_STOP_CONVERTER = new SoundStopMapper();
    private static final @NotNull BookMapper BOOK_CONVERTER = new BookMapper();

    private AdventureConverter() {

    }

    /**
     * Convert {@link Key} to a platform instance.
     *
     * @param key {@link Key} to be converted.
     *
     * @return Platform instance of {@link Key}.
     */
    public static @NotNull Object convertKey(@NotNull Key key) {
        return KEY_CONVERTER.map(key);
    }

    /**
     * Convert {@link ComponentLike} to a platform instance.
     *
     * @param component {@link ComponentLike} to be converted.
     *
     * @return Platform instance of {@link ComponentLike}.
     */
    public static @NotNull Object convertComponent(@NotNull ComponentLike component) {
        return COMPONENT_CONVERTER.map(component.asComponent());
    }

    /**
     * Convert a platform instance of {@link Component} to a shaded one.
     *
     * @param component Platform {@link Component} to be converted.
     *
     * @return Shaded instance of {@link Component}.
     */
    public static @NotNull Component convertComponentBack(@NotNull Object component) {
        return COMPONENT_CONVERTER.mapBackwards(component);
    }

    /**
     * Convert {@link Title} to a platform instance.
     *
     * @param title {@link Title} to be converted.
     *
     * @return Platform instance of {@link Title}.
     */
    public static @NotNull Object convertTitle(@NotNull Title title) {
        return TITLE_CONVERTER.map(title);
    }

    /**
     * Convert {@link BossBar} to a platform instance.
     *
     * @param bossBar {@link BossBar} to be converted.
     *
     * @return Platform instance of {@link BossBar}.
     */
    public static @NotNull Object convertBossBar(@NotNull BossBar bossBar) {
        return BOSS_BAR_CONVERTER.map(bossBar);
    }

    /**
     * Convert {@link Sound} to a platform instance.
     *
     * @param sound {@link Sound} to be converted.
     *
     * @return Platform instance of {@link Sound}.
     */
    public static @NotNull Object convertSound(@NotNull Sound sound) {
        return SOUND_CONVERTER.map(sound);
    }

    /**
     * Convert {@link SoundStop} to a platform instance.
     *
     * @param soundStop {@link SoundStop} to be converted.
     *
     * @return Platform instance of {@link SoundStop}.
     */
    public static @NotNull Object convertSoundStop(@NotNull SoundStop soundStop) {
        return SOUND_STOP_CONVERTER.map(soundStop);
    }

    /**
     * Convert {@link Book} to a platform instance.
     *
     * @param book {@link Book} to be converted.
     *
     * @return Platform instance of {@link Book}.
     */
    public static @NotNull Object convertBook(@NotNull Book book) {
        return BOOK_CONVERTER.map(book);
    }

}
