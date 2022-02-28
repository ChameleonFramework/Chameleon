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

package dev.hypera.chameleon.core.adventure.conversion.impl.sound;

import dev.hypera.chameleon.core.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.core.adventure.conversion.IMapper;
import java.lang.reflect.Method;
import java.util.Objects;
import net.kyori.adventure.sound.SoundStop;
import org.jetbrains.annotations.NotNull;

/**
 * Maps shaded to platform net.kyori.adventure.sound.SoundStop
 */
public class SoundStopMapper implements IMapper<SoundStop> {

	private final @NotNull Method ALL_METHOD;
	private final @NotNull Method CREATE_METHOD;

	public SoundStopMapper() {
		try {
			Class<?> soundStopClass = Class.forName(new String(AdventureConverter.PACKAGE) + "sound.SoundStop");
			Class<?> keyClass = Class.forName(new String(AdventureConverter.PACKAGE) + "key.Key");
			ALL_METHOD = soundStopClass.getMethod("all");
			CREATE_METHOD = soundStopClass.getMethod("named", keyClass);
		} catch (ReflectiveOperationException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Map SoundStop to the platform version of Adventure
	 *
	 * @param soundStop SoundStop to be mapped
	 * @return Platform SoundStop
	 */
	@Override
	public @NotNull Object map(@NotNull SoundStop soundStop) {
		try {
			return null == soundStop.sound() ? ALL_METHOD.invoke(null) : CREATE_METHOD.invoke(null, AdventureConverter.convertKey(Objects.requireNonNull(soundStop.sound())));
		} catch (ReflectiveOperationException ex) {
			throw new RuntimeException(ex);
		}
	}

}
