/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
package dev.hypera.chameleon.platforms.mock.adventure;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Mock {@link Audience}.
 */
@NonExtendable
public interface MockAudience extends Audience {

    /**
     * Get the next message that was received.
     *
     * @return the next message that was received.
     */
    @Nullable Component nextMessage();

    /**
     * Peek at the next message that was received.
     *
     * @return the next message that was received.
     */
    @Nullable Component peekNextMessage();

    /**
     * Get the next message that was received, as a string.
     *
     * @return the next message that was received, serialized as a legacy section string.
     */
    default @Nullable String nextMessageAsString() {
        Component message = nextMessage();
        if (null == message) {
            return null;
        } else {
            return LegacyComponentSerializer.legacySection().serialize(message);
        }
    }


    /**
     * Assert that {@code expected} is equal to the next message that was received.
     *
     * @param expected message.
     */
    default void assertReceived(@NotNull Component expected) {
        Component received = nextMessage();
        if (null == received) {
            fail("no messages have been received");
        } else {
            assertEquals(expected, received);
        }
    }

    /**
     * Assert that {@code expected} is equal to the next message that was received.
     *
     * @param expected message.
     */
    default void assertReceived(@NotNull String expected) {
        String received = nextMessageAsString();
        if (null == received) {
            fail("no messages have been received");
        } else {
            assertEquals(expected, received);
        }
    }

    /**
     * Assert that no more messages have been received.
     */
    default void assertNoneReceived() {
        if (null != nextMessage()) {
            fail("messages have been received");
        }
    }

}
