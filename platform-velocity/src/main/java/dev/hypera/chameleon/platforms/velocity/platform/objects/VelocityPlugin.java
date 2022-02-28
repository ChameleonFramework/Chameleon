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

package dev.hypera.chameleon.platforms.velocity.platform.objects;

import com.velocitypowered.api.plugin.PluginContainer;
import com.velocitypowered.api.plugin.meta.PluginDependency;
import dev.hypera.chameleon.core.platform.objects.PlatformPlugin;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Velocity plugin implementation
 */
public class VelocityPlugin implements PlatformPlugin {

	private final @NotNull PluginContainer plugin;

	public VelocityPlugin(@NotNull PluginContainer plugin) {
		this.plugin = plugin;
	}


	@Override
	public @NotNull String getName() {
		return plugin.getDescription().getName().orElse(plugin.getDescription().getId());
	}

	@Override
	public @NotNull String getVersion() {
		return plugin.getDescription().getVersion().orElse("0.0.0");
	}

	@Override
	public @Nullable String getDescription() {
		return plugin.getDescription().getDescription().orElse(null);
	}

	@Override
	public @NotNull Class<?> getMainClass() {
		return plugin.getInstance().orElseThrow(IllegalStateException::new).getClass();
	}

	@Override
	public @NotNull List<String> getAuthors() {
		return plugin.getDescription().getAuthors();
	}

	@Override
	public @NotNull Set<String> getDependencies() {
		return plugin.getDescription().getDependencies().stream().filter(d -> !d.isOptional()).map(PluginDependency::getId).collect(Collectors.toSet());
	}

	@Override
	public @NotNull Set<String> getSoftDependencies() {
		return plugin.getDescription().getDependencies().stream().filter(PluginDependency::isOptional).map(PluginDependency::getId).collect(Collectors.toSet());
	}

	@Override
	public @NotNull Path getDataFolder() {
		return Paths.get("/");
	}

	@Override
	public void enable() {

	}

	@Override
	public void disable() {

	}

}
