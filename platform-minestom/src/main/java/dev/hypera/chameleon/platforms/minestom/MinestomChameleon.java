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

package dev.hypera.chameleon.platforms.minestom;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.ChameleonPlugin;
import dev.hypera.chameleon.core.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.core.exceptions.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.core.managers.CommandManager;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.managers.Scheduler;
import dev.hypera.chameleon.core.managers.UserManager;
import dev.hypera.chameleon.core.platform.Platform;
import dev.hypera.chameleon.platforms.minestom.adventure.MinestomAudienceProvider;
import dev.hypera.chameleon.platforms.minestom.events.MinestomListener;
import dev.hypera.chameleon.platforms.minestom.managers.MinestomCommandManager;
import dev.hypera.chameleon.platforms.minestom.managers.MinestomPluginManager;
import dev.hypera.chameleon.platforms.minestom.managers.MinestomScheduler;
import dev.hypera.chameleon.platforms.minestom.managers.MinestomUserManager;
import dev.hypera.chameleon.platforms.minestom.platform.MinestomPlatform;
import java.nio.file.Path;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom Chameleon
 */
public final class MinestomChameleon extends Chameleon {

	private final @NotNull Extension extension;
	private final @NotNull MinestomAudienceProvider audienceProvider = new MinestomAudienceProvider();
	private final @NotNull MinestomPlatform platform = new MinestomPlatform();
	private final @NotNull MinestomCommandManager commandManager = new MinestomCommandManager(this);
	private final @NotNull MinestomPluginManager pluginManager = new MinestomPluginManager();
	private final @NotNull MinestomUserManager userManager = new MinestomUserManager();
	private final @NotNull MinestomScheduler scheduler = new MinestomScheduler();

	public MinestomChameleon(@NotNull Class<? extends ChameleonPlugin> chameleonPlugin, @NotNull Extension extension) throws ChameleonInstantiationException {
		super(chameleonPlugin);
		this.extension = extension;
		new MinestomListener(this);
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
		return extension.getDataDirectory();
	}


	public @NotNull Extension getExtension() {
		return extension;
	}

}
