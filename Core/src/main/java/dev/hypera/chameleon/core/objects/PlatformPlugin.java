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

package dev.hypera.chameleon.core.objects;

import java.util.List;

public class PlatformPlugin {

	private final String name;
	private final String version;
	private final List<String> authors;
	private final Class<?> mainClass;
	private final List<String> dependencies;
	private final List<String> softDependencies;

	public PlatformPlugin(String name, String version, List<String> authors, Class<?> mainClass, List<String> dependencies, List<String> softDependencies) {
		this.name = name;
		this.version = version;
		this.authors = authors;
		this.mainClass = mainClass;
		this.dependencies = dependencies;
		this.softDependencies = softDependencies;
	}

	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public List<String> getAuthors() {
		return authors;
	}

	public Class<?> getMainClass() {
		return mainClass;
	}

	public List<String> getDependencies() {
		return dependencies;
	}

	public List<String> getSoftDependencies() {
		return softDependencies;
	}

}
