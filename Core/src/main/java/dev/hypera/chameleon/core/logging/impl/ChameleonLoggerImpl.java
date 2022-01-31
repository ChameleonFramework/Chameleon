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

package dev.hypera.chameleon.core.logging.impl;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.logging.ChameleonLogger;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChameleonLoggerImpl implements ChameleonLogger {

	private static final @NotNull String DEBUG_PREFIX = "&c[DEBUG] ";
	private static final @NotNull String WARNING_PREFIX = "&e[WARNING] ";
	private static final @NotNull String ERROR_PREFIX = "&4[ERROR] ";

	private final @NotNull Chameleon chameleon;
	private final boolean debug;

	public ChameleonLoggerImpl(@NotNull Chameleon chameleon, boolean debug) {
		this.chameleon = chameleon;
		this.debug = debug;
	}

	private void log(@NotNull String s) {
		chameleon.getConsole().sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(
				String.format(chameleon.getPlugin().getData().getLogPrefix(), chameleon.getPlugin().getData().getName()) + " " + s
		));
	}

	@Override
	public void info(@NotNull String s, @Nullable Object... o) {
		log(String.format(s, o));
	}

	@Override
	public void debug(@NotNull String s, @Nullable Object... o) {
		if (debug) {
			log(DEBUG_PREFIX + String.format(s, o));
		}
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
		log(String.format(s, o));
	}

	@Override
	public void error(@NotNull String s, @NotNull Throwable throwable, @Nullable Object... o) {
		error(s, o);
		error(getTrace(throwable));
	}

	private @NotNull String getTrace(@NotNull Throwable throwable) {
		try (StringWriter writer = new StringWriter(); PrintWriter printWriter = new PrintWriter(writer)) {
			throwable.printStackTrace(printWriter);
			return writer.toString();
		} catch (IOException ignored) {
			return throwable.getMessage();
		}
	}

}
