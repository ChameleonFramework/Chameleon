/*
 * Chameleon - Cross-platform Minecraft plugin creation library
 *  Copyright (c) 2021 SLLCoding <luisjk266@gmail.com>
 *  Copyright (c) 2021 Joshua Sing <joshua@hypera.dev>
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

package dev.hypera.chameleon.core.configuration;

import dev.hypera.chameleon.core.Chameleon;
import java.io.BufferedReader;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class JsonConfiguration implements Configuration {

	private Path file;
	private Map<String, Object> config;

	private static final String SEPARATOR = ".";

	@SuppressWarnings("unchecked")
	public JsonConfiguration(Chameleon chameleon, String filename, boolean copyDefaultFromResources) {
		try {
			Path dataFolder = chameleon.getDataFolder();
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
	public @Nullable Class<?> getType(@NotNull String path) {
		Object obj = get(path);
		return (null == obj ? null : obj.getClass());
	}

	@Override
	public @Nullable Class<?> getType(@NotNull String path, @Nullable Class<?> def) {
		Object obj = get(path);
		return (null == obj ? def : obj.getClass());
	}

	@Override
	public boolean isType(@NotNull String path, @NotNull Class<?> type) {
		return type.isInstance(get(path));
	}

	@Override
	public <T> @Nullable T get(@NotNull String path, @NotNull Class<T> type) {
		return type.cast(get(path));
	}

	@Override
	public @Nullable Object get(@NotNull String path) {
		return get(path, (Object) null);
	}

	@Override
	public @Nullable Object get(@NotNull String path, @Nullable Object def) {
		if (path.contains(SEPARATOR)) {
			List<String> parts = Arrays.asList(path.split("\\" + SEPARATOR));

			if (parts.size() < 2 || !(config.get(parts.get(0)) instanceof Map<?, ?>)) {
				return def;
			}

			Map<?, ?> section = (Map<?, ?>) config.get(parts.get(0));
			Object output = null;

			for (int i = 1; i < parts.size(); i++) {
				if (null == section.get(parts.get(i))) {
					break;
				}

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

			return null == output ? def : output;
		} else {
			return config.getOrDefault(path, def);
		}
	}

	@Override
	public @Nullable String getString(@NotNull String path) {
		return (String) get(path);
	}

	@Override
	public @NotNull String getString(@NotNull String path, @NotNull String def) {
		return (String) get(path, def);
	}

	@Override
	public @Nullable Integer getInt(@NotNull String path) {
		return (Integer) get(path);
	}

	@Override
	public int getInt(@NotNull String path, int def) {
		return (int) get(path, def);
	}

	@Override
	public @Nullable Double getDouble(@NotNull String path) {
		return (Double) get(path);
	}

	@Override
	public double getDouble(@NotNull String path, double def) {
		return (double) get(path, def);
	}

	@Override
	public @Nullable Long getLong(@NotNull String path) {
		return (Long) get(path);
	}

	@Override
	public long getLong(@NotNull String path, long def) {
		return (long) get(path, def);
	}

	@Override
	public @Nullable Boolean getBoolean(@NotNull String path) {
		return (Boolean) get(path);
	}

	@Override
	public boolean getBoolean(@NotNull String path, boolean def) {
		return (boolean) get(path, def);
	}

	@Override
	public @Nullable List<?> getList(@NotNull String path) {
		return (List<?>) get(path);
	}

	@Override
	public @NotNull List<?> getList(@NotNull String path, @NotNull List<?> def) {
		return (List<?>) get(path, def);
	}

	@Override
	@Deprecated
	public @NotNull File getFile() {
		return file.toFile();
	}

	@Override
	public @NotNull Path getPath() {
		return file;
	}

}
