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

package dev.hypera.chameleon.core.commands;

import dev.hypera.chameleon.core.commands.context.Context;
import dev.hypera.chameleon.core.exceptions.command.ChameleonCommandException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Sub command
 */
@Internal
final class SubCommand {

	private final @NotNull Set<String> names;
	private final @NotNull Method method;

	SubCommand(@NotNull String names, @NotNull Method method) {
		this.names = new HashSet<>(Arrays.asList(names.split("\\|")));
		this.method = method;
	}

	public @NotNull Set<String> getNames() {
		return names;
	}

	public void execute(@NotNull Context context, @NotNull Command parent) {
		try {
			if (!method.isAccessible()) {
				method.setAccessible(true);
			}

			method.invoke(parent, context);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new ChameleonCommandException("Failed to execute sub-command", ex);
		}
	}

}
