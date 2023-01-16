/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2023 The Chameleon Framework Authors.
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

import java.util.UUID;
import net.kyori.adventure.identity.Identity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

final class IdentityMapperTests {

    private static IdentityMapper identityMapper;

    @BeforeAll
    static void setup() {
        // Create and load an identity mapper
        identityMapper = new IdentityMapper();
        try {
            identityMapper.load();
        } catch (ReflectiveOperationException ignored) {

        }
    }

    @Test
    void load() {
        // Create and load an identity mapper
        IdentityMapper mapper = new IdentityMapper();
        assertDoesNotThrow(mapper::load);
        assertTrue(mapper.isLoaded());

        // Should not load twice.
        assertThrows(IllegalStateException.class, mapper::load);
    }

    @Test
    void map() throws ClassNotFoundException {
        // Create an Identity
        Identity identity = Identity.identity(UUID.randomUUID());

        // Map the identity
        Object mapped = assertDoesNotThrow(() -> identityMapper.map(identity));
        assertNotNull(mapped);
        assertTrue(Class.forName(AdventureMapper.ORIGINAL_IDENTITY_CLASS_NAME).isInstance(mapped));
        assertEquals(identity, mapped);
    }

    @Test
    void mapBackwards() {
        // Create an Identity
        Identity identity = Identity.identity(UUID.randomUUID());

        // Map the identity backwards
        Identity mapped = assertDoesNotThrow(() -> identityMapper.mapBackwards(identity));
        assertNotNull(mapped);
        assertEquals(identity, mapped);
    }

}
