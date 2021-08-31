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

package dev.hypera.chameleon.core;

import dev.hypera.chameleon.core.annotations.PlatformSpecific;
import dev.hypera.chameleon.core.commands.CommandManager;
import dev.hypera.chameleon.core.data.IPlatformData;
import dev.hypera.chameleon.core.events.dispatch.EventManager;
import dev.hypera.chameleon.core.events.impl.common.UserChatEvent;
import dev.hypera.chameleon.core.events.impl.common.UserJoinEvent;
import dev.hypera.chameleon.core.events.impl.common.UserLeaveEvent;
import dev.hypera.chameleon.core.events.impl.proxy.ProxyUserSwitchEvent;
import dev.hypera.chameleon.core.exceptions.ChameleonInstantiationException;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.objects.Platform;
import dev.hypera.chameleon.core.objects.Server;
import dev.hypera.chameleon.core.transformers.ITransformer;
import dev.hypera.chameleon.core.transformers.Transformer;
import dev.hypera.chameleon.core.transformers.impl.StringComponentTransformer;
import dev.hypera.chameleon.core.transformers.impl.StringUUIDTransformer;
import dev.hypera.chameleon.core.transformers.impl.UUIDChatUserTransformer;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.core.utils.logging.ChameleonLogger;
import dev.hypera.chameleon.core.utils.logging.factory.ChameleonLoggerFactory;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.UUID;

public abstract class Chameleon {

    protected final @NotNull Plugin plugin;
    protected final @NotNull EventManager eventManager;
    private final @NotNull IPlatformData platformData;
    private final @NotNull ChameleonLoggerFactory factory = new ChameleonLoggerFactory(this);

    public Chameleon(@NotNull Class<? extends Plugin> pluginClass, @NotNull IPlatformData platformData, ITransformer<?, ?>... transformers) throws ChameleonInstantiationException {
        try {
            Transformer.register(
                    transformers,
                    new StringUUIDTransformer(),
                    new UUIDChatUserTransformer(),
                    new StringComponentTransformer()
            );

            eventManager = new EventManager(this);
            eventManager.registerEvents(
                    UserChatEvent.class,
                    UserJoinEvent.class,
                    UserLeaveEvent.class,

                    ProxyUserSwitchEvent.class
            );

            this.plugin = pluginClass.getConstructor(Chameleon.class).newInstance(this);
            this.plugin.getData().check();
            this.platformData = platformData;
        } catch (Exception e) {
            throw new ChameleonInstantiationException("Failed to initialise instance of " + pluginClass.getCanonicalName(), e);
        }
    }

    // Status
    public void onEnable() {
        plugin.onEnable();
    }
    public void onDisable() {
        plugin.onDisable();
    }

    // Logging
    public @NotNull ChameleonLogger getLogger(Class<?> clazz) {
        return factory.getLogger(clazz);
    }

    // Data
    public @NotNull Plugin getPlugin() {
        return plugin;
    }
    public @NotNull IPlatformData getPlatformData() {
        return platformData;
    }
    public abstract @NotNull Path getDataFolder();

    // Managers
    public abstract @NotNull CommandManager getCommandManager();
    public @NotNull EventManager getEventManager() {
        return eventManager;
    }
    public abstract @NotNull PluginManager getPluginManager();

    // Objects
    public abstract @NotNull ChatUser getConsoleSender();
    public abstract @Nullable ChatUser getPlayer(UUID uuid);
    public abstract @NotNull Set<ChatUser> getPlayers();
    public abstract @PlatformSpecific(Platform.PROXY) @Nullable Server getServer(String name);

}
