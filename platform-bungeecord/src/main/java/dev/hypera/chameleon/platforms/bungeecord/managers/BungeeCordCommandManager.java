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
package dev.hypera.chameleon.platforms.bungeecord.managers;

import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.managers.CommandManager;
import dev.hypera.chameleon.platforms.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.platforms.bungeecord.commands.BungeeCordCommand;
import java.util.Map.Entry;
import net.md_5.bungee.api.ProxyServer;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord command manager
 */
public final class BungeeCordCommandManager extends CommandManager {

    private final @NotNull BungeeCordChameleon chameleon;

    public BungeeCordCommandManager(@NotNull BungeeCordChameleon chameleon) {
        super(chameleon);
        this.chameleon = chameleon;
    }

    @Override
    protected void registerCommand(@NotNull Command command) {
        ProxyServer.getInstance().getPluginManager().registerCommand(chameleon.getBungeePlugin(), new BungeeCordCommand(chameleon, command));
    }

    @Override
    protected void unregisterCommand(@NotNull Command command) {
        net.md_5.bungee.api.plugin.Command bungeeCommand = ProxyServer.getInstance().getPluginManager().getCommands().stream().filter(c -> c.getKey().equals(command.getName())).map(Entry::getValue).findFirst().orElse(null);
        if (null != bungeeCommand) {
            ProxyServer.getInstance().getPluginManager().unregisterCommand(bungeeCommand);
        } else {
            throw new IllegalArgumentException("Cannot find command with name '" + command.getName() + "'");
        }
    }

}
