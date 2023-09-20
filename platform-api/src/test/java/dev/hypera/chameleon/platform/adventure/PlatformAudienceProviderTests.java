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
package dev.hypera.chameleon.platform.adventure;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import dev.hypera.chameleon.platform.objects.PlatformConsole;
import dev.hypera.chameleon.platform.objects.PlatformPlayer;
import dev.hypera.chameleon.platform.objects.PlatformUserImpl;
import dev.hypera.chameleon.user.ChatUser;
import java.util.UUID;
import java.util.function.Predicate;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class PlatformAudienceProviderTests {

    private final @NotNull PlatformAudienceProviderImpl audienceProvider = new PlatformAudienceProviderImpl();
    private AudienceProvider underlyingProvider = mock(AudienceProvider.class);

    @BeforeEach
    void setup() {
        this.audienceProvider.setAudienceProvider(null); // Reset
        this.underlyingProvider = mock(AudienceProvider.class);
    }

    @Test
    void testAll() {
        // #all() should return Audience#empty() when the underlying audience provider
        // is unavailable
        assertEquals(Audience.empty(), this.audienceProvider.all());

        // Set underlying audience provider
        PlatformUserImpl user = PlatformUserImpl.random();
        doReturn(user).when(this.underlyingProvider).all();
        this.audienceProvider.setAudienceProvider(this.underlyingProvider);

        // #all() should call the underlying audience provider and forward the returned value
        assertEquals(user, this.audienceProvider.all());
        verify(this.underlyingProvider, times(1)).all();
    }

    @Test
    void testConsole() {
        // #console() should return Audience#empty() when the underlying audience provider
        // is unavailable
        assertEquals(Audience.empty(), this.audienceProvider.console());

        // Set underlying audience provider
        PlatformConsole console = new PlatformConsole();
        doReturn(console).when(this.underlyingProvider).console();
        this.audienceProvider.setAudienceProvider(this.underlyingProvider);

        // #console() should call the underlying audience provider and forward the returned value
        assertEquals(console, this.audienceProvider.console());
        verify(this.underlyingProvider, times(1)).console();
    }

    @Test
    void testPlayers() {
        // #players() should return Audience#empty() when the underlying audience provider
        // is unavailable
        assertEquals(Audience.empty(), this.audienceProvider.players());

        // Set underlying audience provider
        PlatformUserImpl user = PlatformUserImpl.random();
        doReturn(user).when(this.underlyingProvider).players();
        this.audienceProvider.setAudienceProvider(this.underlyingProvider);

        // #players() should call the underlying audience provider and forward the returned value
        assertEquals(user, this.audienceProvider.players());
        verify(this.underlyingProvider, times(1)).players();
    }

    @Test
    void testPlayer() {
        UUID id = UUID.randomUUID();

        // #player(UUID) should return Audience#empty() when the underlying audience provider
        // is unavailable
        assertEquals(Audience.empty(), this.audienceProvider.player(id));

        // Set underlying audience provider
        PlatformUserImpl user = new PlatformUserImpl(new PlatformPlayer(id, "Steve"));
        doReturn(user).when(this.underlyingProvider).player(id);
        this.audienceProvider.setAudienceProvider(this.underlyingProvider);

        // #player(UUID) should call the underlying audience provider and forward the returned value
        assertEquals(user, this.audienceProvider.player(id));
        verify(this.underlyingProvider, times(1)).player(id);
    }

    @Test
    void testPermission() {
        // #permission(String) should return Audience#empty() when the underlying audience
        // provider is unavailable
        assertEquals(Audience.empty(), this.audienceProvider.permission("chameleon.test"));

        // Set underlying audience provider
        PlatformUserImpl user = PlatformUserImpl.random();
        doReturn(user).when(this.underlyingProvider).permission("chameleon.test");
        this.audienceProvider.setAudienceProvider(this.underlyingProvider);

        // #permission(String) should call the underlying audience provider and forward the
        // returned value
        assertEquals(user, this.audienceProvider.permission("chameleon.test"));
        verify(this.underlyingProvider, times(1))
            .permission("chameleon.test");
    }

    @Test
    void testWorld() {
        Key key = Key.key("chameleon:test");

        // #world(Key) should return Audience#empty() when the underlying audience provider
        // is unavailable
        assertEquals(Audience.empty(), this.audienceProvider.world(key));

        // Set underlying audience provider
        PlatformUserImpl user = PlatformUserImpl.random();
        doReturn(user).when(this.underlyingProvider).world(key);
        this.audienceProvider.setAudienceProvider(this.underlyingProvider);

        // #world(Key) should call the underlying audience provider and forward the returned value
        assertEquals(user, this.audienceProvider.world(key));
        verify(this.underlyingProvider, times(1)).world(key);
    }

    @Test
    void testServer() {
        // #server(String) should return Audience#empty() when the underlying audience provider
        // is unavailable
        assertEquals(Audience.empty(), this.audienceProvider.server("test"));

        // Set underlying audience provider
        PlatformUserImpl user = PlatformUserImpl.random();
        doReturn(user).when(this.underlyingProvider).server("test");
        this.audienceProvider.setAudienceProvider(this.underlyingProvider);

        // #server(String) should call the underlying audience provider and forward the
        // returned value
        assertEquals(user, this.audienceProvider.server("test"));
        verify(this.underlyingProvider, times(1)).server("test");
    }

    @Test
    void testFlattener() {
        // #flattener() should return ComponentFlattener#basic() when the underlying audience
        // provider is unavailable
        assertEquals(ComponentFlattener.basic(), this.audienceProvider.flattener());

        // Set underlying audience provider
        doReturn(ComponentFlattener.textOnly()).when(this.underlyingProvider).flattener();
        this.audienceProvider.setAudienceProvider(this.underlyingProvider);

        // #flattener() should call the underlying audience provider and forward the returned value
        assertEquals(ComponentFlattener.textOnly(), this.audienceProvider.flattener());
        verify(this.underlyingProvider, times(1)).flattener();
    }

    @Test
    void testClose() {
        // #close() should be no-op when the underlying audience provider is unavailable
        assertDoesNotThrow(this.audienceProvider::close);

        // Set underlying audience provider
        this.audienceProvider.setAudienceProvider(this.underlyingProvider);

        // #close() should call the underlying audience provider
        this.audienceProvider.close();
        verify(this.underlyingProvider, times(1)).close();
    }

    private static final class PlatformAudienceProviderImpl extends PlatformAudienceProvider {

        void setAudienceProvider(@Nullable AudienceProvider audienceProvider) {
            this.audienceProvider.set(audienceProvider);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Audience filter(@NotNull Predicate<ChatUser> filter) {
            return all();
        }

    }

}
