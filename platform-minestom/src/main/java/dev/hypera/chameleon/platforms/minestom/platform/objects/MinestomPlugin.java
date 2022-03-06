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
package dev.hypera.chameleon.platforms.minestom.platform.objects;

import dev.hypera.chameleon.core.platform.objects.PlatformPlugin;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minestom.server.MinecraftServer;
import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Minestom plugin implementation
 */
public class MinestomPlugin implements PlatformPlugin {

	private final @NotNull Extension extension;

	public MinestomPlugin(@NotNull Extension extension) {
		this.extension = extension;
	}

	@Override
	public @NotNull String getName() {
		return extension.getOrigin().getName();
	}

	@Override
	public @NotNull String getVersion() {
		return extension.getOrigin().getVersion();
	}

	@Override
	public @Nullable String getDescription() {
		return null;
	}

	@Override
	public @NotNull Class<?> getMainClass() {
		return extension.getClass();
	}

	@Override
	public @NotNull List<String> getAuthors() {
		return Arrays.asList(extension.getOrigin().getAuthors());
	}

	@Override
	public @NotNull Set<String> getDependencies() {
		return new HashSet<>(Arrays.asList(extension.getOrigin().getDependencies()));
	}

	@Override
	public @NotNull Set<String> getSoftDependencies() {
		return Collections.emptySet();
	}

	@Override
	public @NotNull Path getDataFolder() {
		return extension.getDataDirectory();
	}

	@Override
	public void enable() {
		throw new UnsupportedOperationException("Minestom extensions cannot be enabled");
	}

	@Override
	public void disable() {
		throw new UnsupportedOperationException("Minestom extensions cannot be disabled");
	}

}
