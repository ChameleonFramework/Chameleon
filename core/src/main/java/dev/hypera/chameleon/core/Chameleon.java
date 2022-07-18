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
package dev.hypera.chameleon.core;

import dev.hypera.chameleon.core.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.core.data.PluginData;
import dev.hypera.chameleon.core.events.EventManager;
import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.core.exceptions.modules.ChameleonModuleInjectionException;
import dev.hypera.chameleon.core.logging.ChameleonLogger;
import dev.hypera.chameleon.core.logging.impl.InternalChameleonLogger;
import dev.hypera.chameleon.core.managers.CommandManager;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.managers.Scheduler;
import dev.hypera.chameleon.core.managers.UserManager;
import dev.hypera.chameleon.core.modules.ModuleLoader;
import dev.hypera.chameleon.core.platform.Platform;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon.
 */
public abstract class Chameleon {

    private static final @NotNull String VERSION = "@version@";

    private final @NotNull ChameleonLogger logger;
    private final @NotNull ChameleonLogger internalLogger;

    private final @NotNull ChameleonPlugin plugin;
    private final @NotNull PluginData pluginData;
    private final @NotNull EventManager eventMapper = new EventManager(this);
    private final @NotNull ModuleLoader moduleLoader = new ModuleLoader(this);

    @Internal
    protected Chameleon(@NotNull Class<? extends ChameleonPlugin> plugin, @NotNull PluginData pluginData, @NotNull ChameleonLogger logger) throws ChameleonInstantiationException {
        try {
            this.logger = logger;
            this.internalLogger = new InternalChameleonLogger(logger);

            this.plugin = plugin.getConstructor(Chameleon.class).newInstance(this);
            this.pluginData = pluginData;

            for (Field field : plugin.getDeclaredFields()) {
                try {
                    this.moduleLoader.injectModule(field, this.plugin);
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | InstantiationException ex) {
                    throw new ChameleonModuleInjectionException("Failed to inject module for field '" + field.getName() + "' of '" + this.plugin.getClass().getCanonicalName() + "'");
                }
            }
        } catch (Exception ex) {
            throw new ChameleonInstantiationException("Failed to initialise instance of " + plugin.getCanonicalName(), ex);
        }
    }


    /**
     * Called after Chameleon has been loaded.
     */
    public void onLoad() {
        this.plugin.onLoad();
    }

    /**
     * Called when the platform plugin is enabled.
     */
    public void onEnable() {
        this.plugin.onEnable();
    }

    /**
     * Called when the platform plugin is disabled.
     */
    public void onDisable() {
        this.plugin.onDisable();
    }


    /**
     * Get {@link ChameleonPlugin} instance.
     *
     * @return the stored {@link ChameleonPlugin} instance.
     */
    public final @NotNull ChameleonPlugin getPlugin() {
        return this.plugin;
    }

    /**
     * Get {@link PluginData} instance.
     *
     * @return the stored {@link PluginData} instance.
     */
    public final @NotNull PluginData getData() {
        return this.pluginData;
    }

    /**
     * Get {@link ChameleonLogger} instance.
     *
     * @return the stored {@link ChameleonLogger} instance.
     */
    public final @NotNull ChameleonLogger getLogger() {
        return this.logger;
    }

    /**
     * Get internal {@link ChameleonLogger} instance.
     * This is only to be used internally by Chameleon for debugging and error reporting.
     *
     * @return the stored internal {@link ChameleonLogger} instance.
     */
    @Internal
    public final @NotNull ChameleonLogger getInternalLogger() {
        return this.internalLogger;
    }

    /**
     * Get {@link EventManager} instance.
     *
     * @return the stored {@link EventManager} instance.
     */
    public final @NotNull EventManager getEventManager() {
        return this.eventMapper;
    }

    /**
     * Get {@link ModuleLoader} instance.
     *
     * @return the stored {@link ModuleLoader} instance.
     */
    public final @NotNull ModuleLoader getModuleLoader() {
        return this.moduleLoader;
    }


    /**
     * Get Adventure {@link ChameleonAudienceProvider} instance.
     *
     * @return Adventure {@link ChameleonAudienceProvider} instance.
     */
    public abstract @NotNull ChameleonAudienceProvider getAdventure();

    /**
     * Get {@link Platform} instance.
     *
     * @return {@link Platform} instance.
     */
    public abstract @NotNull Platform getPlatform();

    /**
     * Get {@link CommandManager} instance.
     *
     * @return {@link CommandManager} instance.
     */
    public abstract @NotNull CommandManager getCommandManager();

    /**
     * Get {@link PluginManager} instance.
     *
     * @return {@link PluginManager} instance.
     */
    public abstract @NotNull PluginManager getPluginManager();

    /**
     * Get {@link UserManager} instance.
     *
     * @return {@link UserManager} instance.
     */
    public abstract @NotNull UserManager getUserManager();

    /**
     * Get {@link Scheduler} instance.
     *
     * @return {@link Scheduler} instance.
     */
    public abstract @NotNull Scheduler getScheduler();

    /**
     * Get the plugin's data folder.
     *
     * @return plugin data folder as a {@link Path}.
     */
    public abstract @NotNull Path getDataFolder();


    /**
     * Get the current Chameleon version.
     *
     * @return the current {@link Chameleon} version.
     */
    public static @NotNull String getVersion() {
        return VERSION;
    }

}
