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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.hypera.chameleon.adventure.matches.BoundMatcher;
import java.util.Arrays;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.text.Component;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

final class BoundMapperTests {

    private static BoundMapper boundMapper;

    @BeforeAll
    static void setup() {
        // Create and load a key mapper
        KeyMapper keyMapper = new KeyMapper();
        assertDoesNotThrow(keyMapper::load);

        // Create and load a chat type mapper
        ChatTypeMapper chatTypeMapper = new ChatTypeMapper(keyMapper);
        assertDoesNotThrow(chatTypeMapper::load);

        // Create and load a component mapper
        ComponentMapper componentMapper = new ComponentMapper();
        assertDoesNotThrow(componentMapper::load);

        // Create and load a bound mapper
        boundMapper = new BoundMapper(chatTypeMapper, componentMapper);
        try {
            boundMapper.load();
        } catch (ReflectiveOperationException ignored) {

        }
    }

    @Test
    void load() {
        // Create and load a key mapper
        KeyMapper keyMapper = new KeyMapper();
        assertDoesNotThrow(keyMapper::load);

        // Create and load a chat type mapper
        ChatTypeMapper chatTypeMapper = new ChatTypeMapper(keyMapper);
        assertDoesNotThrow(chatTypeMapper::load);

        // Create and load a component mapper
        ComponentMapper componentMapper = new ComponentMapper();
        assertDoesNotThrow(componentMapper::load);

        // Create and load a boss bar mapper
        BoundMapper mapper = new BoundMapper(chatTypeMapper, componentMapper);
        assertDoesNotThrow(mapper::load);
        assertTrue(mapper.isLoaded());

        // Should not load twice.
        assertThrows(IllegalStateException.class, mapper::load);
    }

    @Test
    void map() throws ClassNotFoundException {
        // Create bounds
        ChatType.Bound chatBound = ChatType.CHAT.bind(
            Component.text("test"), Component.text("chameleon")
        );
        ChatType.Bound sayBound = ChatType.SAY_COMMAND.bind(
            Component.text("test")
        );

        // Map the bounds
        for (ChatType.Bound bound : Arrays.asList(chatBound, sayBound)) {
            Object mapped = assertDoesNotThrow(() -> boundMapper.map(bound));
            assertNotNull(mapped);
            assertTrue(Class.forName(AdventureMapper.ORIGINAL_CHAT_TYPE_BOUND_CLASS_NAME).isInstance(mapped));
            new BoundMatcher(bound).matches((ChatType.Bound) mapped);
        }
    }

    @Test
    void mapBackwards() {
        // Create bounds
        ChatType.Bound chatBound = ChatType.CHAT.bind(
            Component.text("test"), Component.text("chameleon")
        );
        ChatType.Bound sayBound = ChatType.SAY_COMMAND.bind(
            Component.text("test")
        );

        // Map the bounds backwards
        for (ChatType.Bound bound : Arrays.asList(chatBound, sayBound)) {
            ChatType.Bound mapped = assertDoesNotThrow(() -> boundMapper.mapBackwards(bound));
            assertNotNull(mapped);
            new BoundMatcher(bound).matches(mapped);
        }
    }

}
