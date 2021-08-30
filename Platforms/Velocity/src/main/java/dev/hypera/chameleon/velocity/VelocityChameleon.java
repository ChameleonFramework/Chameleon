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

package dev.hypera.chameleon.velocity;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.Plugin;
import dev.hypera.chameleon.core.commands.CommandManager;
import dev.hypera.chameleon.core.exceptions.ChameleonInstantiationException;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.objects.Server;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.velocity.commands.VelocityCommandManager;
import dev.hypera.chameleon.velocity.data.VelocityData;
import dev.hypera.chameleon.velocity.events.VelocityEventHandler;
import dev.hypera.chameleon.velocity.managers.VelocityPluginManager;
import dev.hypera.chameleon.velocity.objects.VelocityServer;
import dev.hypera.chameleon.velocity.transformers.*;
import dev.hypera.chameleon.velocity.users.ChameleonCommandSource;
import dev.hypera.chameleon.velocity.users.VelocityUserManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.UUID;

public class VelocityChameleon extends Chameleon {

    private final @NotNull VelocityPlugin velocityPlugin;
    private final @NotNull CommandManager commandManager;
    private final @NotNull PluginManager pluginManager;

    public VelocityChameleon(@NotNull Class<? extends Plugin> pluginClass, @NotNull VelocityPlugin velocityPlugin) throws ChameleonInstantiationException {
        super(pluginClass, new VelocityData(velocityPlugin.getServer()),
                new PlayerUUIDTransformer(),
                new PlayerChatUserTransformer(),
                new PlayerProxyUserTransformer(),
                new ResultBooleanTransformer(),
                new ServerTransformer()
        );
        this.velocityPlugin = velocityPlugin;
        this.commandManager = new VelocityCommandManager(this);
        this.pluginManager = new VelocityPluginManager(velocityPlugin.getServer());
    }

    public @NotNull VelocityPlugin getVelocityPlugin() {
        return velocityPlugin;
    }

    @Override
    public void onEnable() {
        new VelocityEventHandler(this);
        super.onEnable();
    }

    @Override
    public @NotNull Path getDataFolder() {
        return velocityPlugin.getDataDirectory();
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
    public @NotNull ChatUser getConsoleSender() {
        return new ChameleonCommandSource(velocityPlugin.getServer().getConsoleCommandSource());
    }

    @Override
    public @Nullable ChatUser getPlayer(UUID uuid) {
        return VelocityUserManager.getUser(this, uuid);
    }

    @Override
    public @Nullable Server getServer(String name) {
        return getVelocityPlugin().getServer().getServer(name).map(VelocityServer::new).orElse(null);
    }

}
