/*
 * Chameleon - Cross-platform Minecraft plugin creation library
 *  Copyright (c) 2021 SLLCoding <luisjk266@gmail.com>
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

package dev.hypera.chameleon.spigot.commands;

import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.commands.CommandManager;
import dev.hypera.chameleon.spigot.SpigotChameleon;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public class SpigotCommandManager extends CommandManager {

    private final SpigotChameleon chameleon;
    private final CommandMap map;

    public SpigotCommandManager(SpigotChameleon chameleon) throws NoSuchFieldException, IllegalAccessException {
        super(chameleon);
        this.chameleon = chameleon;

        Field commandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        commandMap.setAccessible(true);
        this.map = (CommandMap) commandMap.get(Bukkit.getServer());
    }

    @Override
    protected void registerPlatformCommand(@NotNull Command command) {
        map.register(command.getName(), chameleon.getSpigotPlugin().getName(), new SpigotCommand(chameleon, command));
    }

    @Override
    protected void unregisterPlatformCommand(@NotNull Command command) {
        Objects.requireNonNull(map.getCommand(command.getName())).unregister(map);
    }

}
