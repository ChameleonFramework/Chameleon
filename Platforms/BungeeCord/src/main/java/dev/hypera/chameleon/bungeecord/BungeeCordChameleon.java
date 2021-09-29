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

import dev.hypera.chameleon.bungeecord.commands.BungeeCordCommandManager;
import dev.hypera.chameleon.bungeecord.data.BungeeCordData;
import dev.hypera.chameleon.bungeecord.events.BungeeCordEventHandler;
import dev.hypera.chameleon.bungeecord.managers.BungeeCordPluginManager;
import dev.hypera.chameleon.bungeecord.objects.BungeeCordServer;
import dev.hypera.chameleon.bungeecord.scheduling.BungeeCordScheduler;
import dev.hypera.chameleon.bungeecord.transformers.*;
import dev.hypera.chameleon.bungeecord.users.BungeeCordUserManager;
import dev.hypera.chameleon.bungeecord.users.ChameleonCommandSender;
import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.commands.CommandManager;
import dev.hypera.chameleon.core.exceptions.ChameleonInstantiationException;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.objects.Server;
import dev.hypera.chameleon.core.scheduling.Scheduler;
import dev.hypera.chameleon.core.users.ChatUser;
import java.util.Set;
import java.util.stream.Collectors;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.UUID;

public class BungeeCordChameleon extends Chameleon {

    private final @NotNull Plugin bungeePlugin;
    private final @NotNull BungeeAudiences adventure;
    private final @NotNull CommandManager commandManager;
    private final @NotNull PluginManager pluginManager;
    private final @NotNull Scheduler scheduler;

    public BungeeCordChameleon(@NotNull Class<? extends dev.hypera.chameleon.core.Plugin> pluginClass, @NotNull Plugin bungeePlugin) throws ChameleonInstantiationException {
        super(pluginClass, new BungeeCordData(),
                new ProxiedPlayerUUIDTransformer(),
                new ProxiedPlayerChatUserTransformer(),
                new ProxiedPlayerProxyUserTransformer(),
                new ConnectionChatUserTransformer(),
                new ServerInfoServerTransformer()
        );
        this.bungeePlugin = bungeePlugin;
        this.adventure = BungeeAudiences.create(bungeePlugin);
        this.commandManager = new BungeeCordCommandManager(this);
        this.pluginManager = new BungeeCordPluginManager();
        this.scheduler = new BungeeCordScheduler(bungeePlugin);
    }

    public @NotNull Plugin getBungeeCordPlugin() {
        return bungeePlugin;
    }

    public @NotNull BungeeAudiences getAdventure() {
        return adventure;
    }

    @Override
    public void onEnable() {
        new BungeeCordEventHandler(this);
        super.onEnable();
    }

    @Override
    public @NotNull Path getDataFolder() {
        return bungeePlugin.getDataFolder().toPath();
    }

    @Override
    public @NotNull CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public @NotNull PluginManager getPluginManager() {
        return pluginManager;
    }

    @Override
    public @NotNull Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public @NotNull ChatUser getConsoleSender() {
        return new ChameleonCommandSender(this, bungeePlugin.getProxy().getConsole());
    }

    @Override
    public @Nullable ChatUser getPlayer(UUID uuid) {
        return BungeeCordUserManager.getUser(this, uuid);
    }

    @Override
    public @NotNull Set<ChatUser> getPlayers() {
        return ProxyServer.getInstance().getPlayers().stream().map(p -> BungeeCordUserManager.getUser(this, p)).collect(Collectors.toSet());
    }

    @Override
    public @Nullable Server getServer(String name) {
        ServerInfo info = ProxyServer.getInstance().getServerInfo(name);
        return null == info ? null : new BungeeCordServer(this, info);
    }

    @Override
    public @NotNull Set<Server> getServers() {
        return ProxyServer.getInstance().getServers().values().stream().map(serverInfo -> new BungeeCordServer(this, serverInfo)).collect(Collectors.toSet());
    }

}
