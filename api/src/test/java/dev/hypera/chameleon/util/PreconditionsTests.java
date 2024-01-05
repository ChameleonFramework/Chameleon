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
package dev.hypera.chameleon.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

final class PreconditionsTests {

    private static final @NotNull Pattern TEST_PATTERN = Pattern.compile("value[0-9]?");

    @Test
    void checkArgument() {
        assertDoesNotThrow(() -> Preconditions.checkArgument(true));
        assertDoesNotThrow(() -> Preconditions.checkArgument(true, "test"));
        assertDoesNotThrow(() -> Preconditions.checkArgument(true, "Hello, %s!", "world"));
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkArgument(false)
        );
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkArgument(false, "test")
        );
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkArgument(false, "Hello, %s!", "world")
        );
    }

    @Test
    void checkState() {
        assertDoesNotThrow(() -> Preconditions.checkState(true));
        assertDoesNotThrow(() -> Preconditions.checkState(true, "test"));
        assertDoesNotThrow(() -> Preconditions.checkState(true, "Hello, %s!", "world"));
        assertThrowsExactly(IllegalStateException.class, () -> Preconditions.checkState(false));
        assertThrowsExactly(IllegalStateException.class,
            () -> Preconditions.checkState(false, "test")
        );
        assertThrowsExactly(IllegalStateException.class,
            () -> Preconditions.checkState(false, "Hello, %s!", "world")
        );
    }

    @Test
    void checkNotNull() {
        assertDoesNotThrow(() -> Preconditions.checkNotNull("value"));
        assertDoesNotThrow(() -> Preconditions.checkNotNull("test", "value"));
        assertDoesNotThrow(() -> Preconditions.checkNotNullState("test", "value"));
        assertThrowsExactly(NullPointerException.class, () -> Preconditions.checkNotNull(null));
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkNotNull("test", null)
        );
        assertThrowsExactly(IllegalStateException.class,
            () -> Preconditions.checkNotNullState("test", null)
        );
    }

    @Test
    void checkNotNullOrEmpty() {
        assertDoesNotThrow(() -> Preconditions.checkNotNullOrEmpty("value"));
        assertDoesNotThrow(() -> Preconditions.checkNotNullOrEmpty("test", "value"));
        assertDoesNotThrow(() -> Preconditions.checkNotNullOrEmpty("test",
            new Object[] { "test" }
        ));
        assertDoesNotThrow(() -> Preconditions.checkNotNullOrEmpty("test", List.of("test")));
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkNotNullOrEmpty(null)
        );
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkNotNullOrEmpty("")
        );
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkNotNullOrEmpty("test", (String) null)
        );
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkNotNullOrEmpty("test", "")
        );
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkNotNullOrEmpty("test", new Object[0])
        );
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkNotNullOrEmpty("test", Collections.emptySet())
        );
    }

    @Test
    void checkNotEmpty() {
        assertDoesNotThrow(() -> Preconditions.checkNotNullOrEmpty("test",
            new Object[] { "test" }
        ));
        assertDoesNotThrow(() -> Preconditions.checkNotNullOrEmpty("test", List.of("test")));
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkNotNullOrEmpty("test", new Object[0])
        );
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkNotNullOrEmpty("test", Collections.emptySet())
        );
    }

    @Test
    void checkNoneNull() {
        assertDoesNotThrow(() -> Preconditions.checkNoneNull("test", Collections.singleton("hi")));
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkNoneNull("test", Collections.singleton(null))
        );
    }

    @Test
    void checkMatches() {
        assertDoesNotThrow(() -> Preconditions.checkMatches("test", TEST_PATTERN, "value"));
        assertDoesNotThrow(() -> Preconditions.checkMatches("test", TEST_PATTERN, "value2"));
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkMatches("test", TEST_PATTERN, null)
        );
        assertThrowsExactly(IllegalArgumentException.class,
            () -> Preconditions.checkMatches("test", TEST_PATTERN, "notvalue")
        );
    }

}
