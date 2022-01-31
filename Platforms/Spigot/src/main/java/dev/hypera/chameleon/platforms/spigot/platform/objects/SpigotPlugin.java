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

package dev.hypera.chameleon.platforms.spigot.platform.objects;

import dev.hypera.chameleon.core.platform.objects.PlatformPlugin;
import dev.hypera.chameleon.core.utils.BasicUtil;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpigotPlugin implements PlatformPlugin {

	private final @NotNull Plugin plugin;

	public SpigotPlugin(@NotNull Plugin plugin) {
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
	public @Nullable String getDescription() {
		return plugin.getDescription().getDescription();
	}

	@Override
	public @NotNull Class<?> getMainClass() {
		return plugin.getClass();
	}

	@Override
	public @NotNull List<String> getAuthors() {
		return BasicUtil.getOrDefault(plugin.getDescription().getAuthors(), Collections.emptyList());
	}

	@Override
	public @NotNull Set<String> getDependencies() {
		return new HashSet<>(plugin.getDescription().getDepend());
	}

	@Override
	public @NotNull Set<String> getSoftDependencies() {
		return new HashSet<>(plugin.getDescription().getSoftDepend());
	}

	@Override
	public @NotNull Path getDataFolder() {
		return plugin.getDataFolder().toPath().toAbsolutePath();
	}

	@Override
	public void enable() {
		Bukkit.getPluginManager().enablePlugin(plugin);
	}

	@Override
	public void disable() {
		Bukkit.getPluginManager().disablePlugin(plugin);
	}

}
