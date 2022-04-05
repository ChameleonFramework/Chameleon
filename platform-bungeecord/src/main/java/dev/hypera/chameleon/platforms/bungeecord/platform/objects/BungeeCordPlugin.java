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
package dev.hypera.chameleon.platforms.bungeecord.platform.objects;

import dev.hypera.chameleon.core.platform.objects.PlatformPlugin;
import dev.hypera.chameleon.core.utils.BasicUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * BungeeCord plugin implementation
 */
public class BungeeCordPlugin implements PlatformPlugin {

	private final @NotNull Plugin plugin;

	public BungeeCordPlugin(@NotNull Plugin plugin) {
		this.plugin = plugin;
	}


	@Override
	public @NotNull String getName() {
		return BasicUtil.getOrDefault(plugin.getDescription().getName(), "unknown");
	}

	@Override
	public @NotNull String getVersion() {
		return BasicUtil.getOrDefault(plugin.getDescription().getVersion(), "unknown");
	}

	@Override
	public @NotNull Optional<String> getDescription() {
		return Optional.ofNullable(plugin.getDescription().getDescription());
	}

	@Override
	public @NotNull Class<?> getMainClass() {
		return plugin.getClass();
	}

	@Override
	public @NotNull List<String> getAuthors() {
		return null == plugin.getDescription().getAuthor() ? Collections.emptyList() : Collections.singletonList(plugin.getDescription().getAuthor());
	}

	@Override
	public @NotNull Set<String> getDependencies() {
		return plugin.getDescription().getDepends();
	}

	@Override
	public @NotNull Set<String> getSoftDependencies() {
		return plugin.getDescription().getSoftDepends();
	}

	@Override
	public @NotNull Path getDataFolder() {
		return plugin.getDataFolder().toPath().toAbsolutePath();
	}

	@Override
	public void enable() {
		throw new UnsupportedOperationException("Cannot enable plugins on BungeeCord");
	}

	@Override
	public void disable() {
		ProxyServer.getInstance().getPluginManager().unregisterCommands(plugin);
		ProxyServer.getInstance().getPluginManager().unregisterListeners(plugin);
		plugin.onDisable();
	}

}
