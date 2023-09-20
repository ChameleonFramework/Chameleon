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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import dev.hypera.chameleon.meta.MetadataKey;
import dev.hypera.chameleon.platform.objects.PlatformPlayer;
import dev.hypera.chameleon.platform.objects.PlatformUserManagerImpl;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

final class StandaloneAudienceProviderTests {

    private static final @NotNull MetadataKey<Boolean> TEST = MetadataKey.bool("test");

    private final @NotNull PlatformUserManagerImpl userManager = new PlatformUserManagerImpl();
    private final @NotNull StandaloneAudienceProvider audienceProvider = new StandaloneAudienceProvider(this.userManager);

    @Test
    void testAll() {
        // #all() should return a non-null non-empty Audience
        assertNotNull(this.audienceProvider.all());
        assertNotEquals(Audience.empty(), this.audienceProvider.all());
    }

    @Test
    void testConsole() {
        // #console() should return a non-null non-empty Audience
        assertNotNull(this.audienceProvider.console());
        assertNotEquals(Audience.empty(), this.audienceProvider.console());
    }

    @Test
    void testPlayers() {
        // Add two random users
        this.userManager.addTestUser(UUID.randomUUID());
        this.userManager.addTestUser(UUID.randomUUID());

        // #players() should return the two users added above
        AtomicInteger count = new AtomicInteger();
        this.audienceProvider.players().forEachAudience(a -> count.getAndIncrement());
        assertEquals(2, count.get());
    }

    @Test
    void testPlayer() {
        // Add a random user
        UUID id = UUID.randomUUID();
        this.userManager.addTestUser(id);

        // #player(UUID) with the ID above should return the user added above
        assertNotNull(this.audienceProvider.player(id));
        assertNotEquals(Audience.empty(), this.audienceProvider.player(id));

        // #player(UUID) should return Audience#empty() when the user does not exist
        UUID id2 = UUID.randomUUID();
        assertNotNull(this.audienceProvider.player(id2));
        assertEquals(Audience.empty(), this.audienceProvider.player(id2));
    }

    @Test
    void testFilter() {
        // Add one user with TEST=true and one without
        UUID id = UUID.randomUUID();
        this.userManager.addTestUser(id);
        this.userManager.getUserById(id).orElseThrow().setMetadata(TEST, true);
        this.userManager.addTestUser(UUID.randomUUID());

        // #filter(Predicate<ChatUser>) should return an Audience containing only the ChatUsers that
        // test true in the given Predicate
        AtomicInteger count = new AtomicInteger();
        this.audienceProvider.filter(c -> c.getMetadata(TEST).orElse(false))
            .forEachAudience(a -> count.getAndIncrement());
        assertEquals(1, count.get());
    }

    @Test
    void testPermission() {
        // Add one user that will return true in PermissionHolder#hasPermission(String), and another
        // user that will return false
        this.userManager.addTestUser(UUID.randomUUID());
        this.userManager.addTestUser(new PlatformPlayer(UUID.randomUUID(), "Steve", true));

        // #permission(String) should return an Audience containing only the PermissionHolders that
        // have the given permission (in this case, one user added above and Console)
        AtomicInteger count = new AtomicInteger();
        this.audienceProvider.permission("chameleon.test")
            .forEachAudience(a -> count.getAndIncrement());
        assertEquals(2, count.get());
    }

    @Test
    void testWorld() {
        // #world(Key) should currently return #all()
        // When worlds are implemented for ServerUsers, this behaviour should be changed to filter
        // all online server users correctly.
        assertEquals(this.audienceProvider.all(), this.audienceProvider.world(Key.key("chameleon:test")));
    }

    @Test
    void testServer() {
        // Add random users, one on the server "test", one with a null server, one non-proxy user
        this.userManager.addTestUser(new PlatformPlayer(UUID.randomUUID(), "Bob", false, "test"));
        this.userManager.addTestUser(new PlatformPlayer(UUID.randomUUID(), "Alice", false, "test2"));
        this.userManager.addTestUser(new PlatformPlayer(UUID.randomUUID(), "Mallory", false, "")); // "" -> null
        this.userManager.addTestUser(UUID.randomUUID());

        // #server(String) should return an Audience containing only the ProxyUser that are on a
        // server with the given name (in this case, "test"; only Bob should be returned)
        AtomicInteger count = new AtomicInteger();
        assertDoesNotThrow(() -> this.audienceProvider.server("test"))
            .forEachAudience(a -> count.getAndIncrement());
        assertEquals(1, count.get());
    }

    @Test
    void testFlattener() {
        // #flattener() should return a non-null (normally basic) flattener
        assertNotNull(this.audienceProvider.flattener());
    }

    @Test
    void testClose() {
        // #close() should be no-op, and not throw any exception
        assertDoesNotThrow(this.audienceProvider::close);
    }

}
