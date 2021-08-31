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

package dev.hypera.chameleon.bungeecord.managers;

import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.objects.PlatformPlugin;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BungeeCordPluginManager implements PluginManager {

	@Override
	public @NotNull Set<PlatformPlugin> getPlugins() {
		return ProxyServer.getInstance().getPluginManager().getPlugins().stream().map(p -> new PlatformPlugin(p.getDescription().getName(), p.getDescription().getVersion(), Collections.singletonList(p.getDescription().getAuthor()), p.getClass(), new ArrayList<>(p.getDescription().getDepends()), new ArrayList<>(p.getDescription().getSoftDepends()))).collect(Collectors.toSet());
	}

	@Override
	public @Nullable PlatformPlugin getPlugin(String name) {
		Plugin plugin = ProxyServer.getInstance().getPluginManager().getPlugin(name);
		if (null == plugin) {
			return null;
		}
		return new PlatformPlugin(plugin.getDescription().getName(), plugin.getDescription().getVersion(), Collections.singletonList(plugin.getDescription().getAuthor()), plugin.getClass(), new ArrayList<>(plugin.getDescription().getDepends()), new ArrayList<>(plugin.getDescription().getSoftDepends()));
	}

	@Override
	public boolean isPluginEnabled(String name) {
		return null != ProxyServer.getInstance().getPluginManager().getPlugin(name);
	}

}
