/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2023 The Chameleon Framework Authors.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.hypera.chameleon;

import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.command.CommandManager;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.event.common.ChameleonDisableEvent;
import dev.hypera.chameleon.event.common.ChameleonEnableEvent;
import dev.hypera.chameleon.event.common.ChameleonLoadEvent;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extension.ExtensionManager;
import dev.hypera.chameleon.extension.ExtensionManagerImpl;
import dev.hypera.chameleon.extension.ExtensionMap;
import dev.hypera.chameleon.logger.ChameleonInternalLogger;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import dev.hypera.chameleon.user.UserManager;
import dev.hypera.chameleon.util.Preconditions;
import java.nio.file.Path;
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
    private final @NotNull EventBus eventBus;
    private final @NotNull ExtensionManager extensionManager;

    @Internal
    protected Chameleon(@NotNull Class<? extends ChameleonPlugin> plugin, @NotNull EventBus eventBus, @NotNull ChameleonLogger logger, @NotNull ExtensionMap extensions) throws ChameleonInstantiationException {
        Preconditions.checkNotNull("plugin", plugin);
        Preconditions.checkNotNull("pluginData", plugin);
        Preconditions.checkNotNull("eventBus", eventBus);
        Preconditions.checkNotNull("logger", logger);
        Preconditions.checkNotNull("extensions", extensions);

        try {
            this.logger = logger;
            this.internalLogger = new ChameleonInternalLogger(logger);
            this.plugin = plugin.getConstructor(Chameleon.class).newInstance(this);
            this.eventBus = eventBus;
            this.extensionManager = new ExtensionManagerImpl(this, extensions);
        } catch (Exception ex) {
            throw new ChameleonInstantiationException("Failed to initialise instance of " + plugin.getCanonicalName(), ex);
        }
    }

    /**
     * Called after Chameleon has been loaded.
     */
    public void onLoad() {
        this.eventBus.dispatch(new ChameleonLoadEvent(this));
        this.plugin.onLoad();
    }

    /**
     * Called when the platform plugin is enabled.
     */
    public void onEnable() {
        this.eventBus.dispatch(new ChameleonEnableEvent(this));
        this.plugin.onEnable();
    }

    /**
     * Called when the platform plugin is disabled.
     */
    public void onDisable() {
        this.eventBus.dispatch(new ChameleonDisableEvent(this));
        this.plugin.onDisable();
    }


    /**
     * Get the plugin.
     *
     * @return the plugin.
     */
    public final @NotNull ChameleonPlugin getPlugin() {
        return this.plugin;
    }

    /**
     * Get the logger instance.
     *
     * @return the logger instance.
     */
    public final @NotNull ChameleonLogger getLogger() {
        return this.logger;
    }

    /**
     * Get an internal logger instance for use by Chameleon.
     * <p>This should <strong>not</strong> be used outside of Chameleon and is only intended to be
     * used for debugging and error reporting by Chameleon.</p>
     *
     * @return the internal logger instance.
     */
    @Internal
    public final @NotNull ChameleonLogger getInternalLogger() {
        return this.internalLogger;
    }

    /**
     * Get the event bus.
     *
     * @return the event bus.
     */
    public final @NotNull EventBus getEventBus() {
        return this.eventBus;
    }

    /**
     * Get the extension manager.
     *
     * @return the extension manager.
     */
    public final @NotNull ExtensionManager getExtensionManager() {
        return this.extensionManager;
    }

    /**
     * Get the audience provider.
     *
     * @return the audience provider.
     */
    public abstract @NotNull ChameleonAudienceProvider getAdventure();

    /**
     * Get the platform.
     *
     * @return the platform.
     */
    public abstract @NotNull Platform getPlatform();

    /**
     * Get the command manager.
     *
     * @return the command manager.
     */
    public abstract @NotNull CommandManager getCommandManager();

    /**
     * Get the plugin manager.
     *
     * @return the plugin manager.
     */
    public abstract @NotNull PluginManager getPluginManager();

    /**
     * Get the user manager.
     *
     * @return the user manager.
     */
    public abstract @NotNull UserManager getUserManager();

    /**
     * Get the scheduler.
     *
     * @return the scheduler.
     */
    public abstract @NotNull Scheduler getScheduler();

    /**
     * Get the plugin data folder.
     *
     * @return plugin data folder.
     */
    public abstract @NotNull Path getDataFolder();


    /**
     * Get the current Chameleon version.
     *
     * @return the current Chameleon version.
     */
    public static @NotNull String getVersion() {
        return VERSION;
    }

    /**
     * Get the current Chameleon commit.
     *
     * @return the current Chameleon commit.
     */
    public static @NotNull String getCommit() {
        return COMMIT;
    }

}
