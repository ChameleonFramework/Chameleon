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
package dev.hypera.chameleon.platforms.bungeecord;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.ChameleonPlugin;
import dev.hypera.chameleon.core.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.core.data.PluginData;
import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.core.logging.impl.ChameleonJavaLogger;
import dev.hypera.chameleon.core.managers.CommandManager;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.managers.Scheduler;
import dev.hypera.chameleon.core.managers.UserManager;
import dev.hypera.chameleon.core.platform.Platform;
import dev.hypera.chameleon.platforms.bungeecord.adventure.BungeeCordAudienceProvider;
import dev.hypera.chameleon.platforms.bungeecord.events.BungeeCordListener;
import dev.hypera.chameleon.platforms.bungeecord.managers.BungeeCordCommandManager;
import dev.hypera.chameleon.platforms.bungeecord.managers.BungeeCordPluginManager;
import dev.hypera.chameleon.platforms.bungeecord.managers.BungeeCordScheduler;
import dev.hypera.chameleon.platforms.bungeecord.managers.BungeeCordUserManager;
import dev.hypera.chameleon.platforms.bungeecord.platform.BungeeCordPlatform;
import java.nio.file.Path;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord Chameleon
 */
public final class BungeeCordChameleon extends Chameleon {

	private final @NotNull Plugin plugin;
	private final @NotNull ChameleonAudienceProvider audienceProvider;
	private final @NotNull BungeeCordPlatform platform = new BungeeCordPlatform(this);
	private final @NotNull BungeeCordCommandManager commandManager = new BungeeCordCommandManager(this);
	private final @NotNull BungeeCordPluginManager pluginManager = new BungeeCordPluginManager();
	private final @NotNull BungeeCordUserManager userManager = new BungeeCordUserManager(this);
	private final @NotNull BungeeCordScheduler scheduler = new BungeeCordScheduler(this);

	public BungeeCordChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull Plugin bungeePlugin, @NotNull PluginData pluginData) throws ChameleonInstantiationException {
		super(chameleonPlugin, pluginData, new ChameleonJavaLogger(bungeePlugin.getLogger()));
		this.plugin = bungeePlugin;
		this.audienceProvider = new BungeeCordAudienceProvider(this, bungeePlugin);
		ProxyServer.getInstance().getPluginManager().registerListener(bungeePlugin, new BungeeCordListener(this));
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
		return plugin.getDataFolder().toPath().toAbsolutePath();
	}


	public @NotNull Plugin getBungeePlugin() {
		return plugin;
	}

}
