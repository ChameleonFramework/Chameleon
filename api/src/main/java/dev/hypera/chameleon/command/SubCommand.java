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
package dev.hypera.chameleon.command;

import dev.hypera.chameleon.command.annotations.Permission;
import dev.hypera.chameleon.command.context.Context;
import dev.hypera.chameleon.exception.command.ChameleonCommandException;
import dev.hypera.chameleon.util.Preconditions;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Sub-command.
 */
@Internal
public final class SubCommand {

    private final @NotNull List<String> names;
    private final @Nullable Permission permission;
    private final @NotNull Method method;

    /**
     * Sub-command constructor.
     *
     * @param names  Command names, separated by '|'.
     * @param method Command method.
     */
    @Internal
    SubCommand(@NotNull String names, @NotNull Method method) {
        this.names = Arrays.asList(names.split("\\|"));
        this.method = method;

        this.permission = method.isAnnotationPresent(Permission.class) ?
            method.getAnnotation(Permission.class) : null;
    }

    /**
     * Get command names.
     *
     * @return command names.
     */
    public @NotNull Collection<String> getNames() {
        return this.names;
    }

    /**
     * Get command name.
     *
     * @return command name.
     */
    public @NotNull String getName() {
        return this.names.get(0);
    }

    /**
     * Get command aliases.
     *
     * @return command aliases.
     */
    public @NotNull Collection<String> getAliases() {
        return this.names.subList(1, this.names.size());
    }

    /**
     * Execute the command.
     *
     * @param context Execution context.
     * @param parent  Parent command.
     */
    public void execute(@NotNull Context context, @NotNull Command parent) {
        Preconditions.checkNotNull("context", context);
        Preconditions.checkNotNull("parent", parent);

        try {
            if (this.permission != null && !this.permission.value().isEmpty() &&
                !context.getSender().hasPermission(this.permission.value())) {
                parent.getPermissionErrorMessage().ifPresent(component ->
                    context.getSender().sendMessage(component));
                return;
            }

            this.method.setAccessible(true);
            this.method.invoke(parent, context);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new ChameleonCommandException("Failed to execute sub-command", ex);
        }
    }

}
