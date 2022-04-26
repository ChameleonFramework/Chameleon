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
package dev.hypera.chameleon.features.configuration.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import dev.hypera.chameleon.features.configuration.Configuration;
import dev.hypera.chameleon.features.configuration.impl.JsonConfiguration;
import dev.hypera.chameleon.features.configuration.util.CastingList;
import dev.hypera.chameleon.features.configuration.util.CastingMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class JsonTests {

    private static final @NotNull String FILE_NAME = "test.json";
    private static final @NotNull String MODIFIED_FILE_NAME = "test-modified.json";

    @TempDir
    public static Path folder;

    @BeforeAll
    public static void setup() throws IOException {
        Files.copy(Objects.requireNonNull(JsonTests.class.getResourceAsStream("/" + FILE_NAME)), folder.resolve(FILE_NAME));
        Files.copy(Objects.requireNonNull(JsonTests.class.getResourceAsStream("/" + FILE_NAME)), folder.resolve(MODIFIED_FILE_NAME));
    }

    @Test
    public void load() throws IOException {
        Configuration config = new JsonConfiguration(folder, FILE_NAME).load();
        assertEquals("Hello World!", config.getString("string").orElseThrow(IllegalStateException::new));
        assertFalse(config.getInt("string").isPresent());
        assertEquals(42, config.getInt("integer").orElseThrow(IllegalStateException::new));
        assertEquals(69.420, config.getDouble("double").orElseThrow(IllegalStateException::new));
        assertEquals(2028743837545L, config.getLong("long").orElseThrow(IllegalStateException::new));
        assertEquals(false, config.getBoolean("boolean").orElseThrow(IllegalStateException::new));

        assertEquals("yes", config.getString("nested.item").orElseThrow(IllegalStateException::new));

        CastingList list = config.getList("string_list").orElseThrow(IllegalStateException::new);
        assertEquals("item1", list.getString(0).orElseThrow(IllegalStateException::new));
        assertEquals(2, list.getInt(1).orElseThrow(IllegalStateException::new));

        CastingMap map = config.getMap("map").orElseThrow(IllegalStateException::new);
        assertEquals("b", map.getString("a").orElseThrow(IllegalStateException::new));
        assertEquals("d", map.getString("c").orElseThrow(IllegalStateException::new));
    }

    @Test
    public void reload() throws IOException {
        Configuration config = new JsonConfiguration(folder, MODIFIED_FILE_NAME).load();
        assertEquals(false, config.getBoolean("boolean").orElseThrow(IllegalStateException::new));

        Files.copy(Objects.requireNonNull(JsonTests.class.getResourceAsStream("/" + MODIFIED_FILE_NAME)), folder.resolve(MODIFIED_FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
        config.reload();

        assertEquals(true, config.getBoolean("boolean").orElseThrow(IllegalStateException::new));
    }

    @Test
    public void unload() throws IOException {
        Configuration config = new JsonConfiguration(folder, FILE_NAME).load();
        assertEquals(false, config.getBoolean("boolean").orElseThrow(IllegalStateException::new));

        config.unload();
        assertThrows(IllegalStateException.class, () -> config.getBoolean("boolean").orElseThrow(IllegalStateException::new));
    }

}
