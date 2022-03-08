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

import dev.hypera.chameleon.core.commands.annotations.Permission;
import dev.hypera.chameleon.core.commands.context.Context;
import dev.hypera.chameleon.core.exceptions.command.ChameleonCommandException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sub command
 */
@Internal
public final class SubCommand {

	private final @NotNull List<String> names;
	private final @Nullable Permission permission;
	private final @NotNull Method method;

	SubCommand(@NotNull String names, @NotNull Method method) {
		this.names = Arrays.asList(names.split("\\|"));
		this.method = method;

		this.permission = method.isAnnotationPresent(Permission.class) ? method.getAnnotation(Permission.class) : null;
	}

	public @NotNull List<String> getNames() {
		return names;
	}

	public @NotNull String getName() {
		return names.get(0);
	}

	public @NotNull List<String> getAliases() {
		return names.subList(1, names.size());
	}

	public void execute(@NotNull Context context, @NotNull Command parent) {
		try {
			if (null != permission && !permission.value().isEmpty() && !context.getSender().hasPermission(permission.value())) {
				if (null != parent.getPermissionErrorMessage()) {
					context.getSender().sendMessage(parent.getPermissionErrorMessage());
				}
				return;
			}

			if (!method.isAccessible()) {
				method.setAccessible(true);
			}

			method.invoke(parent, context);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
			throw new ChameleonCommandException("Failed to execute sub-command", ex);
		}
	}

}
