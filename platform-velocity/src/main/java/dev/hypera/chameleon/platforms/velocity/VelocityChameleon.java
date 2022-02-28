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

package dev.hypera.chameleon.platforms.velocity;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.ChameleonPlugin;
import dev.hypera.chameleon.core.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.core.managers.CommandManager;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.managers.Scheduler;
import dev.hypera.chameleon.core.managers.UserManager;
import dev.hypera.chameleon.core.platform.Platform;
import dev.hypera.chameleon.platforms.velocity.adventure.VelocityAudienceProvider;
import dev.hypera.chameleon.platforms.velocity.listeners.VelocityListener;
import dev.hypera.chameleon.platforms.velocity.managers.VelocityCommandManager;
import dev.hypera.chameleon.platforms.velocity.managers.VelocityPluginManager;
import dev.hypera.chameleon.platforms.velocity.managers.VelocityScheduler;
import dev.hypera.chameleon.platforms.velocity.managers.VelocityUserManager;
import dev.hypera.chameleon.platforms.velocity.platform.VelocityPlatform;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity Chameleon
 */
public class VelocityChameleon extends Chameleon {

	private final @NotNull VelocityPlugin plugin;
	private final @NotNull VelocityAudienceProvider audienceProvider = new VelocityAudienceProvider(this);
	private final @NotNull VelocityPlatform platform = new VelocityPlatform(this);
	private final @NotNull VelocityCommandManager commandManager = new VelocityCommandManager(this);
	private final @NotNull VelocityPluginManager pluginManager = new VelocityPluginManager(this);
	private final @NotNull VelocityUserManager userManager = new VelocityUserManager(this);
	private final @NotNull VelocityScheduler scheduler = new VelocityScheduler(this);

	public VelocityChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull VelocityPlugin velocityPlugin) throws ChameleonInstantiationException {
		super(chameleonPlugin);
		this.plugin = velocityPlugin;
		this.plugin.getServer().getEventManager().register(plugin, new VelocityListener(this));
	}

	@Override
	public @NotNull ChameleonAudienceProvider getAdventure() {
		return audienceProvider;
	}

	@Override
	public @NotNull Platform getPlatform() {
		return platform;
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
	public @NotNull UserManager getUserManager() {
		return userManager;
	}

	@Override
	public @NotNull Scheduler getScheduler() {
		return scheduler;
	}


	@Override
	public @NotNull Path getDataFolder() {
		return plugin.getDataDirectory();
	}


	public @NotNull VelocityPlugin getVelocityPlugin() {
		return plugin;
	}

}
