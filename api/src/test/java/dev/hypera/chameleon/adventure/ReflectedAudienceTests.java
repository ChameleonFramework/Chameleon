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
package dev.hypera.chameleon.adventure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import dev.hypera.chameleon.TestChameleon;
import dev.hypera.chameleon.adventure.mapper.AdventureMapper;
import dev.hypera.chameleon.adventure.matches.BossBarMatcher;
import dev.hypera.chameleon.adventure.matches.BoundMatcher;
import dev.hypera.chameleon.adventure.matches.SignatureMatcher;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Flag;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.chat.SignedMessage.Signature;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.pointer.Pointer;
import net.kyori.adventure.pointer.Pointers;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.title.TitlePart;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class ReflectedAudienceTests {

    private static AdventureMapper adventureMapper;
    private Audience audience = mock(Audience.class);
    private Audience reflectedAudience;

    @BeforeAll
    static void loadAdventureMapper() throws ChameleonInstantiationException {
        adventureMapper = new AdventureMapper(new TestChameleon());
        assertDoesNotThrow(adventureMapper::load);
    }

    @BeforeEach
    void setup() {
        this.audience = mock(Audience.class);
        this.reflectedAudience = adventureMapper.createReflectedAudience(this.audience);
    }

    @Test
    void sendMessage() {
        // Create message
        Component message = Component.text("test");

        // Make and verify call
        this.reflectedAudience.sendMessage(message);
        verify(this.audience, times(1)).sendMessage(message);
    }

    @Test
    void sendMessageBound() {
        // Create message and bound
        Component message = Component.text("test");
        ChatType.Bound bound = ChatType.CHAT.bind(Component.text("test"));

        // Make and verify call
        this.reflectedAudience.sendMessage(message, bound);
        verify(this.audience, times(1))
            .sendMessage(eq(message), argThat(new BoundMatcher(bound)));
    }

    @Test
    @SuppressWarnings("deprecation")
    void sendMessageIdentifiedType() {
        // Create identity, source and message
        Identity identity = Identity.identity(UUID.randomUUID());
        Identified source = () -> identity;
        Component message = Component.text("test");

        // Make and verify call
        this.reflectedAudience.sendMessage(source, message,
            net.kyori.adventure.audience.MessageType.CHAT);
        verify(this.audience, times(1))
            .sendMessage(identity, message, net.kyori.adventure.audience.MessageType.CHAT);
    }

    @Test
    @SuppressWarnings("deprecation")
    void sendMessageIdentityType() {
        // Create identity and message
        Identity identity = Identity.identity(UUID.randomUUID());
        Component message = Component.text("test");

        // Make and verify call
        this.reflectedAudience.sendMessage(identity, message,
            net.kyori.adventure.audience.MessageType.CHAT);
        verify(this.audience, times(1))
            .sendMessage(identity, message, net.kyori.adventure.audience.MessageType.CHAT);
    }

    @Test
    void deleteMessage() {
        // Create signature
        Signature signature = () -> new byte[] {
            0x01, 0x02, 0x03, 0x04
        };

        // Make and verify call
        this.reflectedAudience.deleteMessage(signature);
        verify(this.audience, times(1))
            .deleteMessage(argThat(new SignatureMatcher(signature)));
    }

    @Test
    void sendActionBar() {
        // Create message
        Component message = Component.text("test");

        // Make and verify call
        this.reflectedAudience.sendActionBar(message);
        verify(this.audience, times(1)).sendActionBar(message);
    }

    @Test
    void sendPlayerListHeader() {
        // Create header
        Component header = Component.text("test");

        // Make and verify call
        this.reflectedAudience.sendPlayerListHeader(header);
        verify(this.audience, times(1)).sendPlayerListHeader(header);
    }

    @Test
    void sendPlayerListFooter() {
        // Create footer
        Component footer = Component.text("test");

        // Make and verify call
        this.reflectedAudience.sendPlayerListFooter(footer);
        verify(this.audience, times(1)).sendPlayerListFooter(footer);
    }

    @Test
    void sendPlayerListHeaderAndFooter() {
        // Create header and footer
        Component header = Component.text("test1");
        Component footer = Component.text("test2");

        // Make and verify call
        this.reflectedAudience.sendPlayerListHeaderAndFooter(header, footer);
        verify(this.audience, times(1))
            .sendPlayerListHeaderAndFooter(header, footer);
    }

    @Test
    @SuppressWarnings("unchecked")
    <T> void sendTitlePart() {
        // Create parts
        Map<TitlePart<?>, Object> parts = new HashMap<>();
        parts.put(TitlePart.TITLE, Component.text("title"));
        parts.put(TitlePart.SUBTITLE, Component.text("sub-title"));
        parts.put(TitlePart.TIMES, Times.times(
            Duration.ofMillis(1), Duration.ofMillis(2), Duration.ofMillis(3)
        ));

        // Make and verify call
        for (Entry<TitlePart<?>, Object> call : parts.entrySet()) {
            this.reflectedAudience.sendTitlePart((TitlePart<T>) call.getKey(), (T) call.getValue());
            verify(this.audience, times(1))
                .sendTitlePart((TitlePart<T>) call.getKey(), (T) call.getValue());
        }

        // Make sure illegal title parts aren't processed
        assertThrows(IllegalArgumentException.class, () ->
            this.reflectedAudience.sendTitlePart(new TitlePart<String>() {}, "illegal"));
    }

    @Test
    void clearTitle() {
        this.reflectedAudience.clearTitle();
        verify(this.audience, times(1)).clearTitle();
    }

    @Test
    void resetTitle() {
        this.reflectedAudience.resetTitle();
        verify(this.audience, times(1)).resetTitle();
    }

    @Test
    void showBossBar() {
        BossBar bossBar = BossBar.bossBar(
            Component.text("chameleon"), 1f,
            BossBar.Color.GREEN, BossBar.Overlay.PROGRESS,
            Collections.singleton(Flag.PLAY_BOSS_MUSIC)
        );
        this.reflectedAudience.showBossBar(bossBar);
        verify(this.audience, times(1))
            .showBossBar(argThat(new BossBarMatcher(bossBar)));
    }

    @Test
    void hideBossBar() {
        BossBar bossBar = BossBar.bossBar(
            Component.text("chameleon"), 1f,
            BossBar.Color.GREEN, BossBar.Overlay.PROGRESS,
            Collections.singleton(Flag.PLAY_BOSS_MUSIC)
        );
        this.reflectedAudience.hideBossBar(bossBar);
        verify(this.audience, times(1))
            .hideBossBar(argThat(new BossBarMatcher(bossBar)));
    }

    @Test
    void playSound() {
        Sound sound = Sound.sound(
            Key.key("chameleon:test"),
            Sound.Source.MASTER, 1f, 1f
        );
        this.reflectedAudience.playSound(sound);
        verify(this.audience, times(1)).playSound(sound);
    }

    @Test
    void playSoundPosition() {
        Sound sound = Sound.sound(
            Key.key("chameleon:test"),
            Sound.Source.MASTER, 1f, 1f
        );
        this.reflectedAudience.playSound(sound, 1, 2, 3);
        verify(this.audience, times(1)).playSound(sound, 1, 2, 3);
    }

    @Test
    void playSoundEmitter() {
        Sound sound = Sound.sound(
            Key.key("chameleon:test"),
            Sound.Source.MASTER, 1f, 1f
        );
        Sound.Emitter emitter = Sound.Emitter.self();
        this.reflectedAudience.playSound(sound, emitter);
        verify(this.audience, times(1)).playSound(sound, emitter);
    }

    @Test
    void stopSound() {
        SoundStop stop = SoundStop.all();
        this.reflectedAudience.stopSound(stop);
        verify(this.audience, times(1)).stopSound(stop);
    }

    @Test
    void openBook() {
        Book book = Book.book(
            Component.text("test"), Component.text("chameleon"),
            Component.text("page1"), Component.text("page2")
        );
        this.reflectedAudience.openBook(book);
        verify(this.audience, times(1)).openBook(book);
    }

    @Test
    void audience() {
        assertEquals(this.reflectedAudience,
            ((ForwardingAudience.Single) this.reflectedAudience).audience());
    }

    @Test
    void get() {
        // Create pointers
        Map<Pointer<?>, Object> pointers = new HashMap<>();
        pointers.put(Pointer.pointer(String.class, Key.key("chameleon:test_basic")), "hi");
        pointers.put(Pointer.pointer(Component.class, Key.key("chameleon:component")),
            Component.text("hi"));
        pointers.put(Pointer.pointer(Key.class, Key.key("chameleon:key")),
            Key.key("chameleon:key"));
        pointers.put(Pointer.pointer(Audience.class, Key.key("chameleon:fail")),
            this.audience);

        // Make and verify call
        for (Entry<Pointer<?>, Object> pointer : pointers.entrySet()) {
            doReturn(Optional.ofNullable(pointer.getValue()))
                .when(this.audience).get(pointer.getKey());

            Optional<?> value = this.reflectedAudience.get(pointer.getKey());
            verify(this.audience, times(1)).get(pointer.getKey());
            assertEquals(pointer.getValue(), value.orElse(null));
        }
    }

    @Test
    void getOrDefault() {
        Pointer<String> pointer = Pointer.pointer(String.class, Key.key("chameleon:test"));
        doReturn(Optional.empty()).when(this.audience).get(pointer);

        String value = this.reflectedAudience.getOrDefault(pointer, "hi");
        verify(this.audience, times(1)).get(pointer);
        assertEquals("hi", value);
    }

    @Test
    void getOrDefaultFrom() {
        Pointer<String> pointer = Pointer.pointer(String.class, Key.key("chameleon:test"));
        doReturn(Optional.empty()).when(this.audience).get(pointer);

        String value = this.reflectedAudience.getOrDefaultFrom(pointer, () -> "hi");
        verify(this.audience, times(1)).get(pointer);
        assertEquals("hi", value);
    }

    @Test
    void pointers() {
        assertEquals(Pointers.empty(), this.reflectedAudience.pointers());
    }

}
