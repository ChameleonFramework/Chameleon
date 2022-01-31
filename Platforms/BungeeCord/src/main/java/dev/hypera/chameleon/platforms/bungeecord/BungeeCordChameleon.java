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
import dev.hypera.chameleon.core.exceptions.ChameleonInstantiationException;
import dev.hypera.chameleon.core.managers.CommandManager;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.managers.Scheduler;
import dev.hypera.chameleon.core.platform.Platform;
import dev.hypera.chameleon.core.wrappers.AudienceProvider;
import dev.hypera.chameleon.platforms.bungeecord.managers.BungeeCordCommandManager;
import dev.hypera.chameleon.platforms.bungeecord.managers.BungeeCordPluginManager;
import dev.hypera.chameleon.platforms.bungeecord.managers.BungeeCordScheduler;
import dev.hypera.chameleon.platforms.bungeecord.platform.BungeeCordPlatform;
import dev.hypera.chameleon.platforms.bungeecord.wrappers.BungeeCordAudienceProvider;
import java.nio.file.Path;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class BungeeCordChameleon extends Chameleon {

	private final @NotNull Plugin plugin;
	private final @NotNull AudienceProvider audienceProvider;
	private final @NotNull BungeeCordPlatform platform = new BungeeCordPlatform(this);
	private final @NotNull BungeeCordCommandManager commandManager = new BungeeCordCommandManager(this);
	private final @NotNull BungeeCordPluginManager pluginManager = new BungeeCordPluginManager();
	private final @NotNull BungeeCordScheduler scheduler = new BungeeCordScheduler(this);

	public BungeeCordChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull Plugin bungeePlugin) throws ChameleonInstantiationException {
		super(chameleonPlugin);
		this.plugin = bungeePlugin;
		this.audienceProvider = new BungeeCordAudienceProvider(BungeeAudiences.create(bungeePlugin));
	}



	@Override
	public @NotNull AudienceProvider getAdventure() {
		return audienceProvider;
	}

	@Override
	public @NotNull Platform getPlatform() {
		return platform;
	}


	/* -- Managers -- */
	@Override
	public @NotNull CommandManager getCommandManager() {
		return commandManager;
	}

	@Override
	public @NotNull PluginManager getPluginManager() {
		return pluginManager;
	}

	@Override
	public @NotNull Scheduler getScheduler() {
		return scheduler;
	}


	/* -- Data -- */
	@Override
	public @NotNull Path getDataFolder() {
		return plugin.getDataFolder().toPath().toAbsolutePath();
	}


	/* -- Platform data -- */
	public @NotNull Plugin getPlugin() {
		return plugin;
	}

}
