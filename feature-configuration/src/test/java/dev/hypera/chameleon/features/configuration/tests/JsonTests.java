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

import dev.hypera.chameleon.features.configuration.impl.JsonConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTests {

    private static final String FILE_NAME = "test.json";

    @TempDir
    public static Path folder;

    @BeforeAll
    public static void setup() {
        try {
            Path file = folder.resolve(FILE_NAME);
            Files.write(file, ("{\n" +
                    "  \"string\": \"Hello World!\",\n" +
                    "  \"integer\": 42,\n" +
                    "  \"double\": 69.420,\n" +
                    "  \"long\": 2028743837545,\n" +
                    "  \"string_list\": [\n" +
                    "    \"item1\",\n" +
                    "\t\"item2\"\n" +
                    "  ]\n" +
                    "}").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void read() {
        JsonConfiguration config = new JsonConfiguration(folder, FILE_NAME, false);
        assertEquals("Hello World!", config.getString("string").orElseThrow(IllegalStateException::new));
        assertEquals(42, config.getInt("integer").orElseThrow(IllegalStateException::new));
        assertEquals(69.420, config.getDouble("double").orElseThrow(IllegalStateException::new));
        assertEquals(2028743837545L, config.getLong("long").orElseThrow(IllegalStateException::new));

        List<?> list = config.getList("string_list").orElseThrow(IllegalStateException::new);
        assertEquals("item1", list.get(0));
        assertEquals("item2", list.get(1));
    }

}
