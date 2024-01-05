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
package dev.hypera.chameleon.meta;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.google.common.testing.EqualsTester;
import java.util.UUID;
import net.kyori.adventure.key.Key;
import org.junit.jupiter.api.Test;

final class MetadataKeyTests {

    @Test
    void testOfType() {
        // #string(*) should return a key with the type String
        assertEquals(String.class, MetadataKey.string("chameleon:test").type());
        assertEquals(String.class, MetadataKey.string("chameleon", "test").type());

        // #bool(*) should return a key with the type Boolean
        assertEquals(Boolean.class, MetadataKey.bool("chameleon:test").type());
        assertEquals(Boolean.class, MetadataKey.bool("chameleon", "test").type());

        // #integer(*) should return a key with the type Integer
        assertEquals(Integer.class, MetadataKey.integer("chameleon:test").type());
        assertEquals(Integer.class, MetadataKey.integer("chameleon", "test").type());

        // #uuid(*) should return a key with the type UUID
        assertEquals(UUID.class, MetadataKey.uuid("chameleon:test").type());
        assertEquals(UUID.class, MetadataKey.uuid("chameleon", "test").type());
    }

    @Test
    void testOfValueOnly() {
        MetadataKey<String> key = MetadataKey.of(String.class, "test");
        assertEquals(Key.MINECRAFT_NAMESPACE, key.namespace());
        assertEquals("test", key.value());
    }

    @Test
    void testOfString() {
        MetadataKey<String> key = MetadataKey.of(String.class, "chameleon:test");
        assertEquals("chameleon", key.namespace());
        assertEquals("test", key.value());
    }

    @Test
    @SuppressWarnings("PatternValidation")
    void testOfStringInvalid() {
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string(""));
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("!"));
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("!:test"));
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("Chameleon:test"));
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("chameleon:Test"));
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("a/b:testing"));
    }

    @Test
    void testOfNamespaceAndValue() {
        MetadataKey<String> key = MetadataKey.of(String.class, "chameleon", "test");
        assertEquals("chameleon", key.namespace());
        assertEquals("test", key.value());
    }

    @Test
    @SuppressWarnings("PatternValidation")
    void testOfNamespaceAndValueInvalid() {
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("", ""));
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("", "test"));
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("!", "test"));
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("Chameleon", "test"));
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("chameleon", ""));
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("chameleon", "!"));
        assertThrows(IllegalArgumentException.class, () -> MetadataKey.string("chameleon", "Test"));
    }

    @Test
    void testAsString() {
        assertEquals("minecraft:test", MetadataKey.string("test").asString());
        assertEquals("chameleon:test", MetadataKey.bool("chameleon:test").asString());
        assertEquals("minecraft:test<String>", MetadataKey.string("test").toString());
    }

    @Test
    void testEquality() {
        new EqualsTester().addEqualityGroup(
            // Default 'minecraft' namespace
            MetadataKey.of(String.class, "test"),
            MetadataKey.of(String.class, "minecraft:test"),
            MetadataKey.of(String.class, "minecraft", "test"),
            MetadataKey.string("test"),
            MetadataKey.string("minecraft:test"),
            MetadataKey.string("minecraft", "test")
        ).addEqualityGroup(
            // Custom 'chameleon' namespace
            MetadataKey.of(Integer.class, "chameleon:test"),
            MetadataKey.of(Integer.class, "chameleon", "test"),
            MetadataKey.integer("chameleon:test"),
            MetadataKey.integer("chameleon", "test")
        ).addEqualityGroup(
            // Custom 'chameleon' namespace, different key value
            MetadataKey.of(Integer.class, "chameleon:test2"),
            MetadataKey.of(Integer.class, "chameleon", "test2"),
            MetadataKey.integer("chameleon:test2"),
            MetadataKey.integer("chameleon", "test2")
        ).addEqualityGroup(
            // Same as above, different key type
            MetadataKey.of(String.class, "chameleon:test2"),
            MetadataKey.of(String.class, "chameleon", "test2"),
            MetadataKey.string("chameleon:test2"),
            MetadataKey.string("chameleon", "test2")
        ).testEquals();
    }

}
