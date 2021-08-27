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

package dev.hypera.chameleon.spigot;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.Plugin;
import dev.hypera.chameleon.core.commands.CommandManager;
import dev.hypera.chameleon.core.exceptions.ChameleonInstantiationException;
import dev.hypera.chameleon.core.objects.Server;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.spigot.commands.SpigotCommandManager;
import dev.hypera.chameleon.spigot.data.SpigotData;
import dev.hypera.chameleon.spigot.events.SpigotEventHandler;
import dev.hypera.chameleon.spigot.transformers.PlayerChatUserTransformer;
import dev.hypera.chameleon.spigot.transformers.PlayerUUIDTransformer;
import dev.hypera.chameleon.spigot.users.ChameleonCommandSender;
import dev.hypera.chameleon.spigot.users.SpigotUserManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.UUID;

public class SpigotChameleon extends Chameleon {

    private final @NotNull JavaPlugin spigotPlugin;
    private final @NotNull BukkitAudiences adventure;
    private final @NotNull CommandManager commandManager;

    public SpigotChameleon(@NotNull Class<? extends Plugin> pluginClass, @NotNull JavaPlugin spigotPlugin) throws ChameleonInstantiationException {
        super(pluginClass, new SpigotData(),
                new PlayerUUIDTransformer(),
                new PlayerChatUserTransformer()
        );
        try {
            this.spigotPlugin = spigotPlugin;
            this.adventure = BukkitAudiences.create(spigotPlugin);
            this.commandManager = new SpigotCommandManager(this);
        }
        catch (Exception e) {
            throw new ChameleonInstantiationException("Failed to initialise instance of SpigotCommandManager", e);
        }
    }

    public @NotNull JavaPlugin getSpigotPlugin() {
        return spigotPlugin;
    }

    public @NotNull BukkitAudiences getAdventure() {
        return adventure;
    }

    @Override
    public void onEnable() {
        new SpigotEventHandler(this);
        super.onEnable();
    }

    @Override
    public @NotNull Path getDataFolder() {
        return spigotPlugin.getDataFolder().toPath();
    }

    @Override
    public @NotNull CommandManager getCommandManager() {
        return commandManager;
    }

    @Override
    public @NotNull ChatUser getConsoleSender() {
        return new ChameleonCommandSender(this, Bukkit.getConsoleSender());
    }

    @Override
    public @Nullable ChatUser getPlayer(UUID uuid) {
        return SpigotUserManager.getUser(this, uuid);
    }

    @Override
    public @Nullable Server getServer(String name) {
        throw new UnsupportedOperationException("This method is not supported on this platform.");
    }

}
