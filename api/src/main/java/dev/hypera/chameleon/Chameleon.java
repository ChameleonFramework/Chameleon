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
import dev.hypera.chameleon.event.EventBusImpl;
import dev.hypera.chameleon.exception.extension.ChameleonExtensionException;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extension.ChameleonExtension;
import dev.hypera.chameleon.extension.ChameleonPlatformExtension;
import dev.hypera.chameleon.extension.annotations.PostLoadable;
import dev.hypera.chameleon.logger.ChameleonInternalLogger;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PluginManager;
import dev.hypera.chameleon.scheduler.Scheduler;
import dev.hypera.chameleon.user.UserManager;
import dev.hypera.chameleon.util.ChameleonUtil;
import dev.hypera.chameleon.util.Preconditions;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Arrays;
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
    private final @NotNull ChameleonPluginData pluginData;
    private final @NotNull Collection<ChameleonExtension<?>> extensions;
    private final @NotNull EventBus eventBus;

    private boolean enabled = false;

    @Internal
    protected Chameleon(@NotNull Class<? extends ChameleonPlugin> plugin, @NotNull Collection<ChameleonExtension<?>> extensions, @NotNull ChameleonPluginData pluginData, @NotNull ChameleonLogger logger) throws ChameleonInstantiationException {
        Preconditions.checkNotNull("plugin", plugin);
        Preconditions.checkNotNull("extensions", extensions);
        Preconditions.checkNotNull("pluginData", plugin);
        Preconditions.checkNotNull("logger", logger);

        try {
            this.logger = logger;
            this.internalLogger = new ChameleonInternalLogger(logger);
            this.plugin = plugin.getConstructor(Chameleon.class).newInstance(this);
            this.pluginData = pluginData;
            this.extensions = extensions;
            this.eventBus = new EventBusImpl(this.internalLogger);
        } catch (Exception ex) {
            throw new ChameleonInstantiationException(
                "Failed to initialise instance of " + plugin.getCanonicalName(), ex
            );
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
        this.enabled = true;
    }

    /**
     * Called when the platform plugin is disabled.
     */
    public void onDisable() {
        this.extensions.forEach(ChameleonExtension::onDisable);
        this.plugin.onDisable();
        this.enabled = false;
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
     * Get the plugin data.
     *
     * @return the plugin data.
     */
    public final @NotNull ChameleonPluginData getData() {
        return this.pluginData;
    }

    /**
     * Get a loaded extension.
     *
     * @param extension Chameleon extension implementation class.
     * @param <T>       Chameleon extension type.
     *
     * @return an optional containing the extension, if found, otherwise an empty optional.
     */
    public final <T extends ChameleonExtension<?>> @NotNull Optional<T> getExtension(@NotNull Class<T> extension) {
        return this.extensions.stream()
            .filter(ext -> ext.getClass().equals(extension))
            .findFirst()
            .map(extension::cast);
    }

    /**
     * Load and return an extension.
     *
     * @param extension Extension implementation class.
     * @param <T>       Extension type.
     * @param <C>       Chameleon type.
     *
     * @return the loaded extension.
     */
    @SuppressWarnings("unchecked")
    public final <T extends ChameleonExtension<?>, C extends Chameleon> @NotNull T loadExtension(@NotNull Class<T> extension) {
        if (!extension.isAnnotationPresent(PostLoadable.class)) {
            throw new IllegalArgumentException("extension cannot be post loaded");
        }

        if (this.extensions.stream().anyMatch(extension::isInstance)) {
            throw new IllegalArgumentException("extension has already been loaded");
        }

        PostLoadable extensionAnnotation = extension.getAnnotation(PostLoadable.class);
        Constructor<?>[] platformExtensionConstructors = Arrays.stream(extensionAnnotation.value())
            .filter(p -> ChameleonUtil.getGenericTypeAsClass(p, 2).isAssignableFrom(getClass()))
            .findFirst()
            .map(Class::getConstructors)
            .orElse(new Constructor<?>[0]);

        if (platformExtensionConstructors.length < 1 || Arrays.stream(platformExtensionConstructors)
            .noneMatch(c -> c.getParameterCount() == 0)) {
            throw new IllegalArgumentException("cannot load platform extension: invalid constructor");
        }

        Constructor<?> constructor = Arrays.stream(platformExtensionConstructors)
            .filter(c -> c.getParameterCount() == 0)
            .findFirst()
            .orElseThrow(IllegalStateException::new);
        try {
            ChameleonPlatformExtension<T, ?, C> platformExtension = (ChameleonPlatformExtension<T, ?, C>) constructor.newInstance();
            platformExtension.onLoad((C) ChameleonUtil.getGenericTypeAsClass(
                platformExtension.getClass(),
                2
            ).cast(this));
            platformExtension.getExtension().onLoad(this);

            if (this.enabled) {
                platformExtension.getExtension().onEnable();
            }

            this.extensions.add(platformExtension.getExtension());
            return platformExtension.getExtension();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
            throw new ChameleonExtensionException(ex);
        }
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
