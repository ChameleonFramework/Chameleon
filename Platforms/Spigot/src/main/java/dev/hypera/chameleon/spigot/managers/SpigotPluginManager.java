/*
 * Chameleon - Cross-platform Minecraft plugin framework
 * Copyright (c) 2021 Joshua Sing <joshua@hypera.dev>
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

package dev.hypera.chameleon.spigot.managers;

import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.objects.PlatformPlugin;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpigotPluginManager implements PluginManager {

	@Override
	public @NotNull Set<PlatformPlugin> getPlugins() {
		return Arrays.stream(Bukkit.getPluginManager().getPlugins()).map(p -> new PlatformPlugin(p.getDescription().getName(), p.getDescription().getVersion(), p.getDescription().getAuthors(), p.getClass(), p.getDescription().getDepend(), p.getDescription().getSoftDepend())).collect(Collectors.toSet());
	}

	@Override
	public @Nullable PlatformPlugin getPlugin(@NotNull String name) {
		Plugin plugin = Bukkit.getPluginManager().getPlugin(name);
		if (null == plugin) {
			return null;
		}
		return new PlatformPlugin(plugin.getDescription().getName(), plugin.getDescription().getVersion(), plugin.getDescription().getAuthors(), plugin.getClass(), plugin.getDescription().getDepend(), plugin.getDescription().getSoftDepend());
	}

	@Override
	public boolean isPluginEnabled(@NotNull String name) {
		return Bukkit.getPluginManager().isPluginEnabled(name);
	}

}
