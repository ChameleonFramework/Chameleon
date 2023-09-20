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
package dev.hypera.chameleon.platform.user;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.hypera.chameleon.meta.MetadataKey;
import dev.hypera.chameleon.platform.objects.ConsoleUserImpl;
import dev.hypera.chameleon.platform.objects.PlatformConsole;
import dev.hypera.chameleon.platform.objects.PlatformPlayer;
import dev.hypera.chameleon.platform.objects.PlatformUserImpl;
import dev.hypera.chameleon.platform.objects.PlatformUserManagerImpl;
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.user.ConsoleUser;
import dev.hypera.chameleon.user.User;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

final class PlatformUserTests {

    private static final @NotNull MetadataKey<UUID> ID = MetadataKey.uuid("id");
    private static final @NotNull MetadataKey<Boolean> TEST = MetadataKey.bool("test");
    private final @NotNull PlatformUserManagerImpl userManager = new PlatformUserManagerImpl();

    @Test
    void testAddUser() {
        UUID id = UUID.randomUUID();
        // User with the id should not exist
        assertFalse(this.userManager.getUserById(id).isPresent());
        // Add a new user with the id
        addUser(id);
        // User with id should exist
        assertTrue(this.userManager.getUserById(id).isPresent());
        assertEquals(id, this.userManager.getUserById(id).orElseThrow().getId());
        // #getUsers should not be empty
        assertFalse(this.userManager.getUsers().isEmpty());
    }

    @Test
    void testRemoveUser() {
        UUID id = UUID.randomUUID();
        // Add a new user with the id
        addUser(id);
        // User should exist
        assertTrue(this.userManager.getUserById(id).isPresent());
        // Remove the user
        this.userManager.removeUser(id);
        // User should no longer exist
        assertFalse(this.userManager.getUserById(id).isPresent());
    }

    @Test
    void testMetadataStorage() {
        UUID id = UUID.randomUUID();
        addUser(id);

        ChatUser user = this.userManager.getUserOrThrow(id);
        // Value for key "id" should not exist
        assertFalse(user.getMetadata(ID).isPresent());
        // Set value for key "id"
        user.setMetadata(ID, id);
        // Metadata value (for key "id") should now exist
        assertTrue(user.getMetadata(ID).isPresent());
        assertEquals(id, user.getMetadata(ID).orElseThrow());

        // Retrieve the user again
        ChatUser user2 = this.userManager.getUserOrThrow(id);
        // Metadata value should have been persisted between the two references
        assertTrue(user2.getMetadata(ID).isPresent());
        assertEquals(id, user2.getMetadata(ID).orElseThrow());

        // Remove the metadata from the first reference to the user
        user.removeMetadata(ID);
        // Metadata value should have been removed
        assertFalse(user.getMetadata(ID).isPresent());
        assertFalse(user2.getMetadata(ID).isPresent());
    }

    @Test
    void testConsoleMetadataStorage() {
        UUID id = UUID.randomUUID();
        // Console should not already have this metadata
        assertFalse(this.userManager.getConsole().getMetadata(ID).isPresent());
        // Set the value for the metadata key
        this.userManager.getConsole().setMetadata(ID, id);
        // Console should not have the metadata with the value set above
        assertTrue(this.userManager.getConsole().getMetadata(ID).isPresent());
        assertEquals(id, this.userManager.getConsole().getMetadata(ID).orElseThrow());
    }

    @Test
    void testGetUserOrThrow() {
        UUID id = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        addUser(id);

        // Retrieving a user that exists should not throw
        assertDoesNotThrow(() -> this.userManager.getUserOrThrow(id));
        // Attempting to retrieve a user that does not exist should throw
        assertThrows(IllegalArgumentException.class, () -> this.userManager.getUserOrThrow(id2));
    }

    @Test
    void testClose() {
        addUser(UUID.randomUUID());
        assertEquals(1, this.userManager.getUsers().size());

        // Close the user manager
        this.userManager.close();
        // Close should remove all users
        assertTrue(this.userManager.getUsers().isEmpty());
    }

    @Test
    void testWrap() {
        UUID id = UUID.randomUUID();
        PlatformPlayer platformPlayer = new PlatformPlayer(id, "player-" + id);
        this.userManager.addUser(id, platformPlayer);
        this.userManager.getUserOrThrow(id).setMetadata(TEST, true);

        // Wrapping a valid platform player when a corresponding user exists should not throw
        User user = assertDoesNotThrow(() -> this.userManager.wrapUser(platformPlayer));
        // The user created earlier should be returned
        assertNotNull(user);
        assertTrue(user.getMetadata(TEST).orElse(false));

        // Attempting to wrap an invalid object should throw
        assertThrows(IllegalArgumentException.class, () -> this.userManager.wrap("Steve"));

        // Attempting to wrap a valid non-player object with wrapUser should throw
        PlatformConsole console = new PlatformConsole();
        assertThrows(IllegalArgumentException.class, () -> this.userManager.wrapUser(console));

        // Non-implemented wrap method should throw UnsupportedOperationException
        PlatformUserManagerImplNoWrap manager = new PlatformUserManagerImplNoWrap();
        assertThrows(UnsupportedOperationException.class, () -> manager.wrap(console));
    }

    private void addUser(@NotNull UUID id) {
        this.userManager.addUser(id, new PlatformPlayer(id, "player-" + id));
    }

    private static final class PlatformUserManagerImplNoWrap extends PlatformUserManager<PlatformPlayer, PlatformUserImpl> {

        /**
         * {@inheritDoc}
         */
        @Override
        protected @NotNull ConsoleUser createConsoleUser() {
            return new ConsoleUserImpl();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected @NotNull PlatformUserImpl createUser(@NotNull PlatformPlayer platformPlayer) {
            return new PlatformUserImpl(platformPlayer);
        }

    }

}
