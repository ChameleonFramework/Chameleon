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

package dev.hypera.chameleon.core.data;

import org.jetbrains.annotations.NotNull;

public class PluginData {

	private String name;
	private String version;
	private String author;
	private String logPrefix = "[%s]";


	public static PluginData builder() {
		return new PluginData();
	}


	public PluginData name(@NotNull String name) {
		this.name = name;
		return this;
	}

	public PluginData version(@NotNull String version) {
		this.version = version;
		return this;
	}

	public PluginData author(@NotNull String author) {
		this.author = author;
		return this;
	}

	public PluginData logPrefix(@NotNull String logPrefix) {
		this.logPrefix = logPrefix;
		return this;
	}

	public PluginData check() {
		if (null == name || null == version || null == author || null == logPrefix) {
			throw new IllegalStateException("Plugin data is missing");
		}

		return this;
	}


	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String getAuthor() {
		return author;
	}

	public String getLogPrefix() {
		return logPrefix;
	}

}
