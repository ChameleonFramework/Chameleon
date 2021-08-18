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

package dev.hypera.chameleon.bungeecord;

import dev.hypera.chameleon.bungeecord.commands.BungeeCordCommand;
import dev.hypera.chameleon.bungeecord.users.ChameleonCommandSender;
import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.users.ChatUser;
import java.nio.file.Path;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class BungeeCordChameleon extends Chameleon {

    private final @NotNull Plugin bungeePlugin;
    private final @NotNull BungeeAudiences adventure;

    public BungeeCordChameleon(@NotNull Class<? extends dev.hypera.chameleon.core.Plugin> pluginClass, @NotNull Plugin bungeePlugin) throws InstantiationException {
        super(pluginClass);
        this.bungeePlugin = bungeePlugin;
        this.adventure = BungeeAudiences.create(bungeePlugin);
    }

    public @NotNull Plugin getPlugin() {
        return bungeePlugin;
    }

    public @NotNull BungeeAudiences getAdventure() {
        return adventure;
    }

    @Override
    public Path getDataFolder() {
        return bungeePlugin.getDataFolder().toPath();
    }

    @Override
    public void registerCommand(@NotNull Command command) {
        bungeePlugin.getProxy().getPluginManager().registerCommand(bungeePlugin, new BungeeCordCommand(this, command));
    }

    @Override
    public @NotNull ChatUser getConsoleSender() {
        return new ChameleonCommandSender(this, bungeePlugin.getProxy().getConsole());
    }

}
