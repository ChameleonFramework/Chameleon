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

import java.util.Arrays;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

final class SoundStopMapperTests {

    private static SoundStopMapper soundStopMapper;

    @BeforeAll
    static void setup() {
        // Create and load a key mapper
        KeyMapper keyMapper = new KeyMapper();
        assertDoesNotThrow(keyMapper::load);

        // Create and load a sound stop mapper
        soundStopMapper = new SoundStopMapper(keyMapper);
        try {
            soundStopMapper.load();
        } catch (ReflectiveOperationException ignored) {

        }
    }

    @Test
    void load() {
        // Create and load a key mapper
        KeyMapper keyMapper = new KeyMapper();
        assertDoesNotThrow(keyMapper::load);

        // Create and load a pointer mapper
        SoundStopMapper mapper = new SoundStopMapper(keyMapper);
        assertDoesNotThrow(mapper::load);
        assertTrue(mapper.isLoaded());

        // Should not load twice.
        assertThrows(IllegalStateException.class, mapper::load);
    }

    @Test
    void map() throws ClassNotFoundException {
        // Create sound stops
        SoundStop stopAll = SoundStop.all();
        SoundStop stopSource = SoundStop.source(Sound.Source.MASTER);
        SoundStop stopNamed = SoundStop.named(Key.key("chameleon:test"));
        SoundStop stopNamedOnSource = SoundStop.namedOnSource(
            Key.key("chameleon:test"), Sound.Source.MASTER
        );

        // Map the sound stops
        for (SoundStop stop : Arrays.asList(stopAll, stopSource, stopNamed, stopNamedOnSource)) {
            Object mapped = assertDoesNotThrow(() -> soundStopMapper.map(stop));
            assertNotNull(mapped);
            assertTrue(Class.forName(AdventureMapper.ORIGINAL_SOUND_STOP_CLASS_NAME).isInstance(mapped));
            assertEquals(stop, mapped);
        }
    }

    @Test
    void mapBackwards() {
        // Create sound stops
        SoundStop stopAll = SoundStop.all();
        SoundStop stopSource = SoundStop.source(Sound.Source.MASTER);
        SoundStop stopNamed = SoundStop.named(Key.key("chameleon:test"));
        SoundStop stopNamedOnSource = SoundStop.namedOnSource(
            Key.key("chameleon:test"), Sound.Source.MASTER
        );

        // Map the sound stops backwards
        for (SoundStop stop : Arrays.asList(stopAll, stopSource, stopNamed, stopNamedOnSource)) {
            SoundStop mapped = assertDoesNotThrow(() -> soundStopMapper.mapBackwards(stop));
            assertNotNull(mapped);
            assertEquals(stop, mapped);
        }
    }

}
