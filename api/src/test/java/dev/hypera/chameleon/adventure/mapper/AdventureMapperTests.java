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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.hypera.chameleon.DummyChameleon;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

final class AdventureMapperTests {

    private static AdventureMapper mapper;

    @BeforeAll
    static void setup() throws ChameleonInstantiationException {
        mapper = new AdventureMapper(new DummyChameleon());
    }

    @Test
    void testLoad() {
        // Load the mapper and make sure it cannot be loaded again.
        assertDoesNotThrow(mapper::load);
        assertTrue(mapper.isLoaded());
        assertThrows(IllegalStateException.class, mapper::load);

        // Make sure all mappers aren't null.
        assertNotNull(mapper.getChameleon());
        assertNotNull(mapper.getComponentMapper());
        assertNotNull(mapper.getBookMapper());
        assertNotNull(mapper.getBossBarMapper());
        assertNotNull(mapper.getKeyMapper());
        assertNotNull(mapper.getChatTypeMapper());
        assertNotNull(mapper.getBoundMapper());
        assertNotNull(mapper.getIdentityMapper());
        assertNotNull(mapper.getPointerMapper());
        assertNotNull(mapper.getSignatureMapper());
        assertNotNull(mapper.getSoundMapper());
        assertNotNull(mapper.getSoundStopMapper());
        assertNotNull(mapper.getTimesMapper());
        assertNotNull(mapper.getTitlePartMapper());
    }

}
