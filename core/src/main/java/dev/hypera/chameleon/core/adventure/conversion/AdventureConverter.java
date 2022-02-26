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

package dev.hypera.chameleon.core.adventure.conversion;

import dev.hypera.chameleon.core.adventure.conversion.impl.ComponentConverter;
import dev.hypera.chameleon.core.adventure.conversion.impl.book.BookConverter;
import dev.hypera.chameleon.core.adventure.conversion.impl.bossbar.BossBarConverter;
import dev.hypera.chameleon.core.adventure.conversion.impl.key.KeyConverter;
import dev.hypera.chameleon.core.adventure.conversion.impl.sound.SoundConverter;
import dev.hypera.chameleon.core.adventure.conversion.impl.sound.SoundStopConverter;
import dev.hypera.chameleon.core.adventure.conversion.impl.title.TitleConverter;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

public class AdventureConverter {

	public static final char[] PACKAGE = { 'n', 'e', 't', '.', 'k', 'y', 'o', 'r', 'i', '.', 'a', 'd', 'v', 'e', 'n', 't', 'u', 'r', 'e', '.' };
	private static final @NotNull KeyConverter KEY_CONVERTER = new KeyConverter();
	private static final @NotNull ComponentConverter COMPONENT_CONVERTER = new ComponentConverter();
	private static final @NotNull TitleConverter TITLE_CONVERTER = new TitleConverter();
	private static final @NotNull BossBarConverter BOSS_BAR_CONVERTER = new BossBarConverter();
	private static final @NotNull SoundConverter SOUND_CONVERTER = new SoundConverter();
	private static final @NotNull SoundStopConverter SOUND_STOP_CONVERTER = new SoundStopConverter();
	private static final @NotNull BookConverter BOOK_CONVERTER = new BookConverter();

	public static @NotNull Object convertKey(@NotNull Key key) {
		return KEY_CONVERTER.convert(key);
	}

	public static @NotNull Object convertComponent(@NotNull ComponentLike component) {
		return COMPONENT_CONVERTER.convert(component.asComponent());
	}

	public static @NotNull Object convertTitle(@NotNull Title title) {
		return TITLE_CONVERTER.convert(title);
	}

	public static @NotNull Object convertBossBar(@NotNull BossBar bossBar) {
		return BOSS_BAR_CONVERTER.convert(bossBar);
	}

	public static @NotNull Object convertSound(@NotNull Sound sound) {
		return SOUND_CONVERTER.convert(sound);
	}

	public static @NotNull Object convertSoundStop(@NotNull SoundStop soundStop) {
		return SOUND_STOP_CONVERTER.convert(soundStop);
	}

	public static @NotNull Object convertBook(@NotNull Book book) {
		return BOOK_CONVERTER.convert(book);
	}

}
