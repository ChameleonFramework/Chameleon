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

package dev.hypera.chameleon.bungeecord.commands;

import dev.hypera.chameleon.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.commands.CommandManager;
import java.util.Map.Entry;
import net.md_5.bungee.api.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class BungeeCordCommandManager extends CommandManager {

    private final BungeeCordChameleon chameleon;
    private final PluginManager pluginManager;

    public BungeeCordCommandManager(BungeeCordChameleon chameleon) {
        super(chameleon);
        this.chameleon = chameleon;
        this.pluginManager = chameleon.getBungeeCordPlugin().getProxy().getPluginManager();
    }

    @Override
    protected void registerPlatformCommand(@NotNull Command command) {
        pluginManager.registerCommand(chameleon.getBungeeCordPlugin(), new BungeeCordCommand(chameleon, command));
    }

    @Override
    protected void unregisterPlatformCommand(@NotNull Command command) {
        pluginManager.unregisterCommand(pluginManager.getCommands().stream().filter(c -> c.getKey().equals(command.getName())).map(Entry::getValue).findFirst().orElseThrow(() -> new IllegalArgumentException("Failed to find command with name '" + command.getName() + "'")));
    }

}
