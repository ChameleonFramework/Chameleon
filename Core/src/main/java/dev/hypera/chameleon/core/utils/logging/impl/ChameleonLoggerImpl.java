/*
 * Chameleon - Cross-platform Minecraft plugin framework
 * Copyright (c) 2021 Joshua Sing <joshua@hypera.dev>
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

package dev.hypera.chameleon.core.utils.logging.impl;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.utils.logging.ChameleonLogger;
import dev.hypera.chameleon.core.utils.string.ImprovedStringBuilder;
import dev.hypera.chameleon.core.utils.string.StringUtils;
import java.io.PrintWriter;
import java.io.StringWriter;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChameleonLoggerImpl implements ChameleonLogger {

	private static final String RESET = "&r";
	private static final String CHAMELEON_PREFIX = "&b[Chameleon]";
	private static final String DEBUG_PREFIX = "&c[DEBUG] ";
	private static final String WARNING_PREFIX = "&e[WARNING] ";
	private static final String ERROR_PREFIX = "&4[ERROR] ";

	private final @NotNull Chameleon chameleon;
	private final boolean isChameleon;
	private boolean debug = false;

	public ChameleonLoggerImpl(@NotNull Class<?> clazz, @NotNull Chameleon chameleon) {
		this.chameleon = chameleon;
		this.isChameleon = clazz.getPackage().getName().startsWith("dev.hypera.chameleon");
	}


	@Override
	public void log(@NotNull String s) {
		ImprovedStringBuilder builder = StringUtils.getImprovedStringBuilder();
		builder.append(String.format(chameleon.getPlugin().getData().getLogPrefix(), chameleon.getPlugin().getData().getName())).append(RESET).appendIf(" " + CHAMELEON_PREFIX + RESET, (str) -> isChameleon).append(" ")
				.append(s);
		chameleon.getConsoleSender().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(builder.toString()));
	}

	@Override
	public void info(@NotNull String s, @Nullable Object... o) {
		log(String.format(s, o));
	}

	@Override
	public void debug(@NotNull String s, @Nullable Object... o) {
		log(DEBUG_PREFIX + String.format(s, o));
	}

	@Override
	public void warn(@NotNull String s, @Nullable Object... o) {
		log(WARNING_PREFIX + String.format(s, o));
	}

	@Override
	public void warn(@NotNull String s, @NotNull Throwable throwable, @Nullable Object... o) {
		warn(s, o);
		warn(getTrace(throwable));
	}

	@Override
	public void error(@NotNull String s, @Nullable Object... o) {
		log(ERROR_PREFIX + String.format(s, o));
	}

	@Override
	public void error(@NotNull String s, @NotNull Throwable throwable, @Nullable Object... o) {
		error(s, o);
		error(getTrace(throwable));
	}

	@Override
	public void setDebugEnabled(boolean enabled) {
		this.debug = enabled;
	}

	@Override
	public boolean isDebugEnabled() {
		return debug;
	}

	private @NotNull String getTrace(@NotNull Throwable throwable) {
		try (StringWriter writer = new StringWriter(); PrintWriter printWriter = new PrintWriter(writer)) {
			throwable.printStackTrace(printWriter);
			return writer.toString();
		} catch (Exception ignored) {
			return throwable.getMessage();
		}
	}

}
