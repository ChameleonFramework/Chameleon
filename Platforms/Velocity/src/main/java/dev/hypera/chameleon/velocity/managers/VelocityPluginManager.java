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

package dev.hypera.chameleon.velocity.managers;

import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.meta.PluginDependency;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.objects.PlatformPlugin;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VelocityPluginManager implements PluginManager {

	private final ProxyServer server;

	public VelocityPluginManager(ProxyServer server) {
		this.server = server;
	}

	@Override
	public @NotNull Set<PlatformPlugin> getPlugins() {
		return server.getPluginManager().getPlugins().stream().map(p -> new PlatformPlugin(p.getDescription().getId(), p.getDescription().getVersion().orElse(null), p.getDescription().getAuthors(), p.getClass(), p.getDescription().getDependencies().stream().map(PluginDependency::getId).collect(Collectors.toList()), Collections.emptyList())).collect(Collectors.toSet());
	}

	@Override
	public @Nullable PlatformPlugin getPlugin(String name) {
		PluginContainer plugin = server.getPluginManager().getPlugin(name.toLowerCase()).orElse(null);
		if (null == plugin) {
			return null;
		}
		return new PlatformPlugin(plugin.getDescription().getId(), plugin.getDescription().getVersion().orElse(null), plugin.getDescription().getAuthors(), plugin.getClass(), plugin.getDescription().getDependencies().stream().map(PluginDependency::getId).collect(Collectors.toList()), Collections.emptyList());
	}

	@Override
	public boolean isPluginEnabled(String name) {
		return server.getPluginManager().isLoaded(name);
	}

}
