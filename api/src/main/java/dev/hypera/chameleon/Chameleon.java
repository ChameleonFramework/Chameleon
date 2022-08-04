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
package dev.hypera.chameleon;

import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.data.PluginData;
import dev.hypera.chameleon.events.EventManager;
import dev.hypera.chameleon.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extensions.ChameleonExtension;
import dev.hypera.chameleon.logging.ChameleonLogger;
import dev.hypera.chameleon.logging.impl.InternalChameleonLogger;
import dev.hypera.chameleon.managers.CommandManager;
import dev.hypera.chameleon.managers.PluginManager;
import dev.hypera.chameleon.managers.Scheduler;
import dev.hypera.chameleon.managers.UserManager;
import dev.hypera.chameleon.platform.Platform;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon.
 */
public abstract class Chameleon {

    private static final @NotNull String VERSION = "@version@";
    private static final @NotNull String COMMIT = "@commit@";

    private final @NotNull ChameleonLogger logger;
    private final @NotNull ChameleonLogger internalLogger;

    private final @NotNull ChameleonPlugin plugin;
    private final @NotNull PluginData pluginData;
    private final @NotNull Collection<ChameleonExtension<?>> extensions;
    private final @NotNull EventManager eventMapper = new EventManager(this);

    @Internal
    protected Chameleon(@NotNull Class<? extends ChameleonPlugin> plugin, @NotNull Collection<ChameleonExtension<?>> extensions, @NotNull PluginData pluginData, @NotNull ChameleonLogger logger) throws ChameleonInstantiationException {
        try {
            this.logger = logger;
            this.internalLogger = new InternalChameleonLogger(logger);

            this.plugin = plugin.getConstructor(Chameleon.class).newInstance(this);
            this.pluginData = pluginData;
            this.extensions = extensions;
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
        this.extensions.forEach(ChameleonExtension::onEnable);
        this.plugin.onEnable();
    }

    /**
     * Called when the platform plugin is disabled.
     */
    public void onDisable() {
        this.extensions.forEach(ChameleonExtension::onDisable);
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
     * Get a loaded extension.
     *
     * @param clazz {@link ChameleonExtension} implementation class.
     * @param <T> {@link ChameleonExtension} type.
     *
     * @return an optional containing the {@link ChameleonExtension} if found, otherwise empty.
     */
    @SuppressWarnings("unchecked")
    public final <T extends ChameleonExtension<?>> @NotNull Optional<T> getExtension(@NotNull Class<T> clazz) {
        return this.extensions.stream().filter(ext -> ext.getClass().equals(clazz)).findFirst().map(ext -> (T) ext);
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
     * Get internal {@link ChameleonLogger} instance. This is only to be used internally by Chameleon for debugging and error reporting.
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

    /**
     * Get the current Chameleon commit.
     *
     * @return the current {@link Chameleon} commit.
     */
    public static @NotNull String getCommit() {
        return COMMIT;
    }

}
