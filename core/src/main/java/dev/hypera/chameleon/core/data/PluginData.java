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
package dev.hypera.chameleon.core.data;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Plugin data
 */
public class PluginData {

	private final @NotNull String name;
	private final @NotNull String version;
	private final @NotNull String description;
	private final @NotNull String url;
	private final @NotNull List<String> authors;
	private final @NotNull String logPrefix;
	private final @NotNull List<Platform> platforms;

	public PluginData(@NotNull String name, @NotNull String version, @NotNull String description, @NotNull String url, @NotNull List<String> authors, @NotNull String logPrefix, @NotNull List<Platform> platforms) {
		this.name = name;
		this.version = version;
		this.description = description;
		this.url = url;
		this.authors = authors;
		this.logPrefix = logPrefix;
		this.platforms = platforms;
	}

	public @NotNull String getName() {
		return name;
	}

	public @NotNull String getVersion() {
		return version;
	}

	public @NotNull String getDescription() {
		return description;
	}

	public @NotNull String getUrl() {
		return url;
	}

	public @NotNull List<String> getAuthors() {
		return authors;
	}

	public @NotNull String getLogPrefix() {
		return logPrefix;
	}

	public @NotNull List<Platform> getPlatforms() {
		return platforms;
	}

	public enum Platform {
		BUNGEECORD,
		MINESTOM,
		SPIGOT,
		VELOCITY
	}

}
