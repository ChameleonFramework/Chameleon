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
import java.time.Instant;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon.
 */
public abstract class Chameleon {

    private static final @NotNull String VERSION = "@version@";
    private static final @NotNull String GIT_BRANCH = "@gitBranch@";
    private static final @NotNull String GIT_COMMIT_HASH = "@gitCommitHash@";
    private static final @NotNull String BUILD_TIME = "@buildTime@";

    private final @NotNull ChameleonLogger logger;
    private final @NotNull ChameleonLogger internalLogger;

    private final @NotNull ChameleonPlugin plugin;
    private final @NotNull EventBus eventBus;
    private final @NotNull ExtensionManager extensionManager;

    @Internal
    protected Chameleon(
        @NotNull ChameleonPluginBootstrap pluginBootstrap,
        @NotNull EventBus eventBus,
        @NotNull ChameleonLogger logger,
        @NotNull ExtensionMap extensions
    ) {
        Preconditions.checkNotNull("pluginBootstrap", pluginBootstrap);
        Preconditions.checkNotNull("eventBus", eventBus);
        Preconditions.checkNotNull("logger", logger);
        Preconditions.checkNotNull("extensions", extensions);

        this.logger = logger;
        this.internalLogger = new ChameleonInternalLogger(logger);
        this.plugin = pluginBootstrap.createPlugin(this);
        this.eventBus = eventBus;
        this.extensionManager = new ExtensionManagerImpl(this, extensions);
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
     * Returns the plugin.
     *
     * @return plugin.
     */
    public final @NotNull ChameleonPlugin getPlugin() {
        return this.plugin;
    }

    /**
     * Returns the plugin logger.
     *
     * @return plugin logger.
     */
    public final @NotNull ChameleonLogger getLogger() {
        return this.logger;
    }

    /**
     * Returns an internal logger instance for use by Chameleon.
     * <p><strong>This must not be used outside of Chameleon and is only intended to be
     * used for debugging and error reporting by Chameleon.</strong></p>
     *
     * @return the internal logger instance.
     */
    @Internal
    public final @NotNull ChameleonLogger getInternalLogger() {
        return this.internalLogger;
    }

    /**
     * Returns the event bus.
     *
     * @return event bus.
     */
    public final @NotNull EventBus getEventBus() {
        return this.eventBus;
    }

    /**
     * Returns the extension manager.
     *
     * @return extension manager.
     */
    public final @NotNull ExtensionManager getExtensionManager() {
        return this.extensionManager;
    }

    /**
     * Returns the audience provider.
     *
     * @return audience provider.
     */
    public abstract @NotNull ChameleonAudienceProvider getAdventure();

    /**
     * Returns the platform.
     *
     * @return platform.
     */
    public abstract @NotNull Platform getPlatform();

    /**
     * Returns the command manager.
     *
     * @return command manager.
     */
    public abstract @NotNull CommandManager getCommandManager();

    /**
     * Returns the plugin manager.
     *
     * @return plugin manager.
     */
    public abstract @NotNull PluginManager getPluginManager();

    /**
     * Returns the user manager.
     *
     * @return user manager.
     */
    public abstract @NotNull UserManager getUserManager();

    /**
     * Returns the scheduler.
     *
     * @return scheduler.
     */
    public abstract @NotNull Scheduler getScheduler();

    /**
     * Returns the plugin data directory.
     *
     * @return plugin data directory.
     */
    public abstract @NotNull Path getDataDirectory();


    /**
     * Returns the current version of Chameleon.
     *
     * @return current version.
     */
    public static @NotNull String getVersion() {
        return VERSION;
    }

    /**
     * Returns the Git branch Chameleon was built from.
     *
     * @return branch.
     */
    public static @NotNull String getBranch() {
        return GIT_BRANCH;
    }

    /**
     * Returns the SHA-1 hash of the Git commit Chameleon was built from.
     *
     * @return commit hash.
     */
    public static @NotNull String getCommitHash() {
        return GIT_COMMIT_HASH;
    }

    /**
     * Returns the shortened SHA-1 hash of the Git commit Chameleon was built from.
     *
     * @return shortened commit hash.
     */
    public static @NotNull String getShortCommitHash() {
        return GIT_COMMIT_HASH.substring(0, 7);
    }

    /**
     * Returns the time Chameleon was built.
     *
     * @return build time.
     */
    public static @NotNull Instant getBuildTime() {
        return Instant.parse(BUILD_TIME);
    }

}
