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
package dev.hypera.chameleon.platform.bukkit.managers;

import dev.hypera.chameleon.commands.Command;
import dev.hypera.chameleon.managers.CommandManager;
import dev.hypera.chameleon.platform.bukkit.BukkitChameleon;
import dev.hypera.chameleon.platform.bukkit.commands.BukkitCommand;
import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Bukkit {@link CommandManager} implementation.
 */
@Internal
public final class BukkitCommandManager extends CommandManager {

    private final @NotNull BukkitChameleon chameleon;
    private final @Nullable CommandMap commandMap;

    /**
     * {@link BukkitCommandManager} constructor.
     *
     * @param chameleon {@link BukkitChameleon} instance.
     */
    @Internal
    public BukkitCommandManager(@NotNull BukkitChameleon chameleon) {
        super(chameleon);
        this.chameleon = chameleon;

        @Nullable CommandMap map;
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            map = (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            chameleon.getInternalLogger().error("Failed to get Bukkit command map", ex);
            map = null;
        }
        this.commandMap = map;
    }

    @Override
    protected void registerCommand(@NotNull Command command) {
        if (null != this.commandMap) {
            this.commandMap.register(command.getName(), this.chameleon.getPlatformPlugin().getName(), new BukkitCommand(this.chameleon, command));
        }
    }

    @Override
    protected void unregisterCommand(@NotNull Command command) {
        if (null != this.commandMap) {
            org.bukkit.command.Command bukkitCommand = this.commandMap.getCommand(command.getName());
            if (null != bukkitCommand) {
                bukkitCommand.unregister(this.commandMap);
            } else {
                throw new IllegalArgumentException("Cannot find command with name '" + command.getName() + "'");
            }
        }
    }

}
