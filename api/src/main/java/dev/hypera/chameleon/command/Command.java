/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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

import dev.hypera.chameleon.command.annotations.CommandHandler;
import dev.hypera.chameleon.command.annotations.Permission;
import dev.hypera.chameleon.command.annotations.SubCommandHandler;
import dev.hypera.chameleon.command.context.Context;
import dev.hypera.chameleon.command.objects.Condition;
import dev.hypera.chameleon.exceptions.command.ChameleonCommandException;
import dev.hypera.chameleon.platform.PlatformTarget;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Abstract command.
 */
public abstract class Command {

    private final @NotNull String name;
    private final @NotNull Set<String> aliases = new HashSet<>();
    private final @NotNull Set<SubCommand> subCommands = new HashSet<>();
    private final @Nullable Permission permission;

    private @NotNull PlatformTarget platform = PlatformTarget.all();
    private @NotNull List<Condition> conditions = new ArrayList<>();
    private @Nullable Component permissionErrorMessage;

    /**
     * {@link Command} constructor.
     *
     * @param names Command aliases.
     */
    protected Command(@NotNull String... names) {
        try {
            if (names.length < 1) {
                if (!getClass().isAnnotationPresent(CommandHandler.class)) {
                    throw new IllegalStateException("Classes extending Command must either be annotated with @CommandHandler or provide names in the constructor");
                }

                names = getClass().getAnnotation(CommandHandler.class).value().split("\\|");
            }

            this.name = names[0];
            this.aliases.addAll(names.length > 1 ? new HashSet<>(Arrays.asList(Arrays.copyOfRange(names, 1, names.length))) : Collections.emptySet());
            this.permission = getClass().isAnnotationPresent(Permission.class) ? getClass().getAnnotation(Permission.class) : null;

            for (Method method : getClass().getDeclaredMethods()) {
                if (method.isAnnotationPresent(SubCommandHandler.class) && method.getParameterCount() == 1 && method.getParameterTypes()[0] == Context.class) {
                    this.subCommands.add(new SubCommand(method.getAnnotation(SubCommandHandler.class).value(), method));
                }
            }
        } catch (Exception ex) {
            throw new ChameleonCommandException("Failed to create command", ex);
        }
    }

    /**
     * Execute the command.
     *
     * @param context Execution {@link Context}.
     */
    public abstract void execute(@NotNull Context context);

    /**
     * Tab complete.
     *
     * @param context Execution {@link Context}.
     *
     * @return Tab complete results.
     */
    public @NotNull List<String> tabComplete(@NotNull Context context) {
        return Collections.emptyList();
    }

    /**
     * Execute the command.
     *
     * @param context Execution {@link Context}.
     */
    @Internal
    public final void executeCommand(@NotNull Context context) {
        if (null != this.permission && !this.permission.value().isEmpty() && !context.getSender().hasPermission(Objects.requireNonNull(this.permission.value()))) {
            if (null != this.permissionErrorMessage) {
                context.getSender().sendMessage(this.permissionErrorMessage);
            }

            return;
        }

        if (!this.conditions.isEmpty()) {
            Optional<Condition> failedCondition = this.conditions.stream().filter(condition -> !condition.test(context)).findFirst();
            if (failedCondition.isPresent()) {
                failedCondition.get().getErrorMessage().ifPresent(errorMessage -> context.getSender().sendMessage(errorMessage));
                return;
            }
        }

        execute(context);
    }

    /**
     * Execute sub command.
     *
     * @param context Execution {@link Context}.
     * @param command Sub-command name.
     *
     * @return {@code true} if a sub-command was found and executed, otherwise {@code false}.
     */
    @Internal
    public final boolean executeSubCommand(@NotNull Context context, @NotNull String command) {
        Optional<SubCommand> subCommand = this.subCommands.stream().filter(c -> c.getNames().stream().anyMatch(n -> command.toLowerCase().matches(n))).findFirst();
        if (subCommand.isPresent()) {
            subCommand.get().execute(context, this);
            return false;
        } else {
            return true;
        }
    }


    /**
     * Get command name.
     *
     * @return Command name.
     */
    public final @NotNull String getName() {
        return this.name;
    }

    /**
     * Get command aliases.
     *
     * @return command aliases.
     */
    public final @NotNull Set<String> getAliases() {
        return this.aliases;
    }

    /**
     * Add command aliases.
     *
     * @param aliases Command aliases.
     */
    protected final void addAliases(@NotNull Collection<String> aliases) {
        this.aliases.addAll(aliases);
    }

    /**
     * Get {@link SubCommand}s.
     *
     * @return {@link SubCommand}s.
     */
    @Internal
    public final @NotNull Set<SubCommand> getSubCommands() {
        return this.subCommands;
    }

    /**
     * Get {@link Permission}.
     *
     * @return command {@link Permission}, or {@code null}.
     */
    protected final @NotNull Optional<Permission> getPermission() {
        return Optional.ofNullable(this.permission);
    }

    /**
     * Get the platform target of this command.
     *
     * @return platform target.
     */
    public final @NotNull PlatformTarget getPlatform() {
        return this.platform;
    }

    /**
     * Set the platform target of this command.
     *
     * @param platform platform target.
     */
    protected final void setPlatform(@NotNull PlatformTarget platform) {
        this.platform = platform;
    }

    /**
     * Get command {@link Condition}s.
     *
     * @return command {@link Condition}s.
     */
    protected final @NotNull List<Condition> getConditions() {
        return this.conditions;
    }

    /**
     * Set command {@link Condition}s.
     *
     * @param conditions Command {@link Condition}s.
     */
    protected final void setConditions(@NotNull Condition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    /**
     * Get command permission error message.
     *
     * @return command permission error message.
     */
    protected final @NotNull Optional<Component> getPermissionErrorMessage() {
        return Optional.ofNullable(this.permissionErrorMessage);
    }

    /**
     * Set command permission error message.
     *
     * @param permissionErrorMessage Command permission error message.
     */
    protected final void setPermissionErrorMessage(@NotNull Component permissionErrorMessage) {
        this.permissionErrorMessage = permissionErrorMessage;
    }

}
