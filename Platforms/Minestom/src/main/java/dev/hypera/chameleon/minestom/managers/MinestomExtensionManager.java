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

package dev.hypera.chameleon.minestom.managers;

import dev.hypera.chameleon.core.managers.PluginManager;
import dev.hypera.chameleon.core.objects.PlatformPlugin;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MinestomExtensionManager implements PluginManager {

	@Override
	public @NotNull Set<PlatformPlugin> getPlugins() {
		return MinecraftServer.getExtensionManager().getExtensions().stream().map(e -> new PlatformPlugin(e.getOrigin().getName(), e.getOrigin().getVersion(), Arrays.asList(e.getOrigin()
				.getAuthors()), e.getClass(), e.getDependents().stream().toList(), Collections.emptyList())).collect(Collectors.toSet());
	}

	@Override
	public @Nullable PlatformPlugin getPlugin(@NotNull String name) {
		Extension extension = MinecraftServer.getExtensionManager().getExtension(name);
		if (null == extension) {
			return null;
		}
		return new PlatformPlugin(extension.getOrigin().getName(), extension.getOrigin().getVersion(), Arrays.asList(extension.getOrigin().getAuthors()), extension.getClass(), extension.getDependents().stream().toList(), Collections.emptyList());
	}

	@Override
	public boolean isPluginEnabled(@NotNull String name) {
		return MinecraftServer.getExtensionManager().hasExtension(name);
	}

}
