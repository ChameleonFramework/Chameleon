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
import dev.hypera.chameleon.core.events.EventManager;
import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.core.logging.ChameleonLogger;
import dev.hypera.chameleon.core.logging.factory.ChameleonLoggerFactory;
import dev.hypera.chameleon.core.managers.CommandManager;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.managers.Scheduler;
import dev.hypera.chameleon.core.managers.UserManager;
import dev.hypera.chameleon.core.platform.Platform;
import java.nio.file.Path;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

public abstract class Chameleon {

	private static final @NotNull String VERSION = "0.4.0-SNAPSHOT";
	private final @NotNull ChameleonPlugin plugin;
	private final @NotNull ChameleonLoggerFactory loggerFactory = new ChameleonLoggerFactory(this);
	private final @NotNull EventManager eventMapper = new EventManager(this);

	public Chameleon(@NotNull Class<? extends ChameleonPlugin> plugin) throws ChameleonInstantiationException {
		try {
			this.plugin = plugin.getConstructor(Chameleon.class).newInstance(this);
		} catch (Exception ex) {
			throw new ChameleonInstantiationException("Failed to initialise instance of " + plugin.getCanonicalName(), ex);
		}
	}

	/* -- Status -- */
	public final void onEnable() {
		plugin.onEnable();
	}

	public final void onDisable() {
		plugin.onDisable();
	}



	public final @NotNull ChameleonPlugin getPlugin() {
		return plugin;
	}

	public final @NotNull ChameleonLogger getLogger() {
		return loggerFactory.getLogger();
	}

	public final @Internal @NotNull ChameleonLogger getInternalLogger() {
		return loggerFactory.getInternalLogger();
	}

	public @NotNull EventManager getEventManager() {
		return eventMapper;
	}

	public abstract @NotNull ChameleonAudienceProvider getAdventure();
	public abstract @NotNull Platform getPlatform();
	public abstract @NotNull CommandManager getCommandManager();
	public abstract @NotNull PluginManager getPluginManager();
	public abstract @NotNull UserManager getUserManager();
	public abstract @NotNull Scheduler getScheduler();
	public abstract @NotNull Path getDataFolder();



	public static @NotNull String getVersion() {
		return VERSION;
	}

}
