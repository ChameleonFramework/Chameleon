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
package dev.hypera.chameleon.features.configuration.impl;

import dev.hypera.chameleon.features.configuration.Configuration;
import org.jetbrains.annotations.NotNull;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * JSON configuration implementation
 */
public class JsonConfiguration implements Configuration {


	private Path file;
	private Map<String, Object> config;

	private static final String SEPARATOR = ".";

	@SuppressWarnings("unchecked")
	public JsonConfiguration(@NotNull Path dataFolder, @NotNull String filename, boolean copyDefaultFromResources) {
		try {
			if (!Files.exists(dataFolder)) {
				Files.createDirectories(dataFolder);
			}

			file = dataFolder.resolve(filename);

			if (!Files.exists(file)) {
				if (copyDefaultFromResources) {
					try (InputStream defaultResource = JsonConfiguration.class.getResourceAsStream("/" + filename)) {
						if (null == defaultResource) {
							throw new IllegalStateException("Failed to load resource '" + filename + "'");
						}
						Files.copy(defaultResource, file);
					}
				} else {
					Files.createFile(file);
				}
			}

			BufferedReader reader = Files.newBufferedReader(file);
			config = ((Map<String, Object>) new JSONParser().parse(reader));
			reader.close();
		} catch (IOException | ParseException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public @NotNull Optional<Object> get(@NotNull String path) {
		if (path.contains(SEPARATOR)) {
			List<String> parts = Arrays.asList(path.split("\\" + SEPARATOR));

			if (parts.size() < 2 || !(config.get(parts.get(0)) instanceof Map<?, ?>)) return Optional.empty();

			Map<?, ?> section = (Map<?, ?>) config.get(parts.get(0));
			Object output = null;

			for (int i = 1; i < parts.size(); i++) {
				if (null == section.get(parts.get(i))) break;

				if (i == parts.size() - 1) {
					output = section.get(parts.get(i));
					break;
				}

				if (section.get(parts.get(i)) instanceof Map<?, ?>) {
					section = (Map<?, ?>) section.get(parts.get(i));
					continue;
				}

				break;
			}

			return Optional.ofNullable(output);
		} else return Optional.ofNullable(config.get(path));
	}

	@Override
	public @NotNull Path getPath() {
		return file;
	}

}
