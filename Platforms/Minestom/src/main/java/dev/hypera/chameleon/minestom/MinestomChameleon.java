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

package dev.hypera.chameleon.minestom;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.Plugin;
import dev.hypera.chameleon.core.commands.CommandManager;
import dev.hypera.chameleon.core.exceptions.ChameleonInstantiationException;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.objects.Server;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.minestom.commands.MinestomCommandManager;
import dev.hypera.chameleon.minestom.data.MinestomData;
import dev.hypera.chameleon.minestom.events.MinestomEventHandler;
import dev.hypera.chameleon.minestom.managers.MinestomExtensionManager;
import dev.hypera.chameleon.minestom.transformers.PlayerChatUserTransformer;
import dev.hypera.chameleon.minestom.transformers.PlayerUUIDTransformer;
import dev.hypera.chameleon.minestom.users.ChameleonCommandSender;
import dev.hypera.chameleon.minestom.users.MinestomUserManager;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.UUID;

public class MinestomChameleon extends Chameleon {

    private final @NotNull Extension extension;
    private final @NotNull CommandManager commandManager;
    private final @NotNull PluginManager pluginManager;

    public MinestomChameleon(@NotNull Class<? extends Plugin> pluginClass, @NotNull Extension extension) throws ChameleonInstantiationException {
        super(pluginClass, new MinestomData(),
                new PlayerUUIDTransformer(),
                new PlayerChatUserTransformer()
        );
        this.extension = extension;
        this.commandManager = new MinestomCommandManager(this);
        this.pluginManager = new MinestomExtensionManager();
    }

    public @NotNull Extension getExtension() {
        return extension;
    }

    @Override
    public void onEnable() {
        new MinestomEventHandler(this);
        super.onEnable();
    }

    @Override
    public @NotNull Path getDataFolder() {
        return extension.getDataDirectory();
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
        return new ChameleonCommandSender(MinecraftServer.getCommandManager().getConsoleSender());
    }

    @Override
    public @Nullable ChatUser getPlayer(UUID uuid) {
        return MinestomUserManager.getUser(uuid);
    }

    @Override
    public @Nullable Server getServer(String name) {
        throw new UnsupportedOperationException("This method is not supported on this platform.");
    }

}
