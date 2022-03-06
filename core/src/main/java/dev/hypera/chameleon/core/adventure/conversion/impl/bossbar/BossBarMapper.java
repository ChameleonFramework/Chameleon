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
package dev.hypera.chameleon.core.adventure.conversion.impl.bossbar;

import dev.hypera.chameleon.core.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.core.adventure.conversion.IMapper;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;
import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;

/**
 * Maps shaded to platform net.kyori.adventure.bossbar.BossBar
 */
public class BossBarMapper implements IMapper<BossBar> {

	private final @NotNull Method CREATE_METHOD;
	private final @NotNull Method COLOR_VALUE_OF;
	private final @NotNull Method OVERLAY_VALUE_OF;
	private final @NotNull Method FLAG_VALUE_OF;

	public BossBarMapper() {
		try {
			Class<?> componentLikeClass = Class.forName(new String(AdventureConverter.PACKAGE) + "text.ComponentLike");
			Class<?> bossBarClass = Class.forName(new String(AdventureConverter.PACKAGE) + "bossbar.BossBar");

			Class<?> colorEnum = Class.forName(bossBarClass.getCanonicalName() + "$Color");
			Class<?> overlayEnum = Class.forName(bossBarClass.getCanonicalName() + "$Overlay");
			Class<?> flagEnum = Class.forName(bossBarClass.getCanonicalName() + "$Flag");

			CREATE_METHOD = bossBarClass.getMethod("bossBar", componentLikeClass, float.class, colorEnum, overlayEnum, Set.class);
			COLOR_VALUE_OF = colorEnum.getMethod("valueOf", String.class);
			OVERLAY_VALUE_OF = overlayEnum.getMethod("valueOf", String.class);
			FLAG_VALUE_OF = flagEnum.getMethod("valueOf", String.class);
		} catch (ReflectiveOperationException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Map BossBar to the platform version of Adventure
	 *
	 * @param bossBar BossBar to be mapped
	 * @return Platform BossBar
	 */
	@Override
	public @NotNull Object map(@NotNull BossBar bossBar) {
		try {
			return CREATE_METHOD.invoke(
					null,
					AdventureConverter.convertComponent(bossBar.name()),
					bossBar.progress(),
					COLOR_VALUE_OF.invoke(null, bossBar.color().name()),
					OVERLAY_VALUE_OF.invoke(null, bossBar.overlay().name()),
					bossBar.flags().stream().map(f -> {
						try {
							return FLAG_VALUE_OF.invoke(null, f.name());
						} catch (ReflectiveOperationException ex) {
							throw new RuntimeException(ex);
						}
					}).collect(Collectors.toSet())
			);
		} catch (ReflectiveOperationException ex) {
			throw new RuntimeException(ex);
		}
	}

}
