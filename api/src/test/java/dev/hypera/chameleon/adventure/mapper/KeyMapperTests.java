/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.adventure.mapper;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

final class KeyMapperTests {

    private static KeyMapper keyMapper;

    @BeforeAll
    static void setup() {
        // Create and load a key mapper
        keyMapper = new KeyMapper();
        try {
            keyMapper.load();
        } catch (ReflectiveOperationException ignored) {

        }
    }

    @Test
    void load() {
        // Create and load a key mapper
        KeyMapper mapper = new KeyMapper();
        assertDoesNotThrow(mapper::load);
        assertTrue(mapper.isLoaded());

        // Should not load twice.
        assertThrows(IllegalStateException.class, mapper::load);
    }

    @Test
    void map() throws ClassNotFoundException {
        // Create a key
        Key key = Key.key("chameleon:test");

        // Map the key
        Object mapped = assertDoesNotThrow(() -> keyMapper.map(key));
        assertNotNull(mapped);
        assertTrue(Class.forName(AdventureMapper.ORIGINAL_KEY_CLASS_NAME).isInstance(mapped));
        assertEquals(key, mapped);
    }

    @Test
    void mapBackwards() {
        // Create a key
        Key key = Key.key("chameleon:test");

        // Map the key backwards
        Key mapped = assertDoesNotThrow(() -> keyMapper.mapBackwards(key));
        assertNotNull(mapped);
        assertEquals(key, mapped);
    }

}
