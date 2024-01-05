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
package dev.hypera.chameleon.util.logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import dev.hypera.chameleon.exception.ChameleonException;
import org.junit.jupiter.api.Test;

final class MessageFormatterTest {

    @Test
    void testNullPattern() {
        assertEquals(null, MessageFormatter.basicFormat(null, 1));
    }

    @Test
    void testNullArgs() {
        assertEquals("Hi, null", MessageFormatter.basicFormat("Hi, {}", (Object) null));
        assertEquals("Hi, null. My name is null.",
            MessageFormatter.basicFormat("Hi, {}. My name is {}.", null, null)
        );
        assertEquals("arg1 is 1, arg2 is null",
            MessageFormatter.basicFormat("arg1 is {}, arg2 is {}", 1, null)
        );
        assertEquals("arg1 is null, arg2 is 2",
            MessageFormatter.basicFormat("arg1 is {}, arg2 is {}", null, 2)
        );

        assertEquals("arg1 is null, arg2 is null, arg3 is null",
            MessageFormatter.basicFormat("arg1 is {}, arg2 is {}, arg3 is {}",
                (Object[]) new Integer[] { null, null, null }
            )
        );
        assertEquals("arg1 is 1, arg2 is null, arg3 is null",
            MessageFormatter.basicFormat("arg1 is {}, arg2 is {}, arg3 is {}",
                (Object[]) new Integer[] { 1, null, null }
            )
        );
        assertEquals("arg1 is null, arg2 is 2, arg3 is 3",
            MessageFormatter.basicFormat("arg1 is {}, arg2 is {}, arg3 is {}",
                (Object[]) new Integer[] { null, 2, 3 }
            )
        );
        assertEquals("arg1 is null, arg2 is null, arg3 is 3",
            MessageFormatter.basicFormat("arg1 is {}, arg2 is {}, arg3 is {}",
                (Object[]) new Integer[] { null, null, 3 }
            )
        );
    }

    @Test
    void testNullArray() {
        assertEquals("test", MessageFormatter.basicFormat("test", (Object[]) null));
        assertEquals("test {}", MessageFormatter.basicFormat("test {}", (Object[]) null));
        assertEquals("test {} {}", MessageFormatter.basicFormat("test {} {}", (Object[]) null));
        assertEquals("test {} {} {}",
            MessageFormatter.basicFormat("test {} {} {}", (Object[]) null)
        );
    }

    @Test
    void testFormatOneArg() {
        assertEquals("Hello, world!", MessageFormatter.basicFormat("Hello, {}!", "world"));
        assertEquals("3 is greater than 2",
            MessageFormatter.basicFormat("{} is greater than 2", 3)
        );

        // Invalid placeholders
        assertEquals("Hi", MessageFormatter.basicFormat("Hi", 1));
        assertEquals("Test, {", MessageFormatter.basicFormat("Test, {", 1));
        assertEquals("Hello, {invalid", MessageFormatter.basicFormat("Hello, {invalid", 1));
        assertEquals("{invalid", MessageFormatter.basicFormat("{invalid", 1));

        // Double bracket placeholders
        assertEquals("Hi, {test} Steve", MessageFormatter.basicFormat("Hi, {test} {}", "Steve"));
        assertEquals("My name is {Bob}.", MessageFormatter.basicFormat("My name is {{}}.", "Bob"));

        // Escaped placeholders
        assertEquals("Escaped {} test", MessageFormatter.basicFormat("Escaped \\{} test", 1));
        assertEquals("{}Escaped", MessageFormatter.basicFormat("\\{}Escaped", 1));
        assertEquals("Check the C:\\Users\\ directory",
            MessageFormatter.basicFormat("Check the C:\\\\{}\\ directory", "Users")
        );
    }

    @Test
    void testFormatTwoArgs() {
        assertEquals("1 is less than 2", MessageFormatter.basicFormat("{} is less than {}", 1, 2));
        assertEquals("2 is more than 1", MessageFormatter.basicFormat("{} is more than {}", 2, 1));
        assertEquals("Hi, Alice. My name is Bob.",
            MessageFormatter.basicFormat("Hi, {}. My name is {}.", "Alice", "Bob")
        );
        assertEquals("12", MessageFormatter.basicFormat("{}{}", 1, 2));

        // Invalid placeholders
        assertEquals("Hi, Alice. {invalid",
            MessageFormatter.basicFormat("Hi, {}. {invalid", "Alice", "oops")
        );
        assertEquals("Testing formatting, \\{",
            MessageFormatter.basicFormat("Testing {}, \\{", "formatting")
        );

        // Escaped placeholders
        assertEquals("5 > {}", MessageFormatter.basicFormat("{} > \\{}", 5, 2));
        assertEquals("{} > 2", MessageFormatter.basicFormat("\\{} > {}", 2, 1));
    }

    @Test
    void testFormatExceptionInToString() {
        Object throwingToString = new Object() {
            @Override
            public String toString() {
                throw new RuntimeException("test");
            }
        };
        assertEquals("test [FAILED toString()]",
            MessageFormatter.basicFormat("test {}", throwingToString)
        );
    }

    @Test
    void testFormatArrayArgs() {
        // Integer[]
        assertEquals("a[1, 2]", MessageFormatter.basicFormat("{}{}", "a", new Integer[] { 1, 2 }));

        // boolean[]
        assertEquals("a[true, false]",
            MessageFormatter.basicFormat("{}{}", "a", new boolean[] { true, false })
        );

        // byte[]
        assertEquals("a[1, 2]", MessageFormatter.basicFormat("{}{}", "a", new byte[] { 1, 2 }));

        // char[]
        assertEquals("a[b, c]", MessageFormatter.basicFormat("{}{}", "a", new char[] { 'b', 'c' }));

        // short[]
        assertEquals("a[1, 2]", MessageFormatter.basicFormat("{}{}", "a", new short[] { 1, 2 }));

        // int[]
        assertEquals("a[1, 2]", MessageFormatter.basicFormat("{}{}", "a", new int[] { 1, 2 }));

        // long[]
        assertEquals("a[1, 2]", MessageFormatter.basicFormat("{}{}", "a", new long[] { 1, 2 }));

        // float[]
        assertEquals("a[1.0, 2.0]",
            MessageFormatter.basicFormat("{}{}", "a", new float[] { 1, 2 })
        );

        // double[]
        assertEquals("a[1.0, 2.0]",
            MessageFormatter.basicFormat("{}{}", "a", new double[] { 1, 2 })
        );
    }

    @Test
    void testFormatMultiDimensionalArrayArgs() {
        int[][] ints = new int[][] { { 1, 2 }, { 3, 4 } };
        assertEquals("a[[1, 2], [3, 4]]", MessageFormatter.basicFormat("{}{}", "a", ints));

        float[][] floats = new float[][] { { 1, 2 }, { 3, 4 } };
        assertEquals("a[[1.0, 2.0], [3.0, 4.0]]",
            MessageFormatter.basicFormat("{}{}", "a", floats)
        );

        Object[][] objects = new Object[][] { { 1, 2 }, { 3, 4 } };
        assertEquals("a[[1, 2], [3, 4]]", MessageFormatter.basicFormat("{}{}", "a", objects));

        Object[][][] threeDimensional = new Object[][][] { objects, objects };
        assertEquals("a[[[1, 2], [3, 4]], [[1, 2], [3, 4]]]",
            MessageFormatter.basicFormat("{}{}", "a", threeDimensional)
        );
    }

    @Test
    void testFormatCyclicArrayArgs() {
        Object[] selfCycle = new Object[1];
        selfCycle[0] = selfCycle;
        assertEquals("a[[...]]", MessageFormatter.basicFormat("{}{}", "a", selfCycle));

        Object[] complexCycle = new Object[2];
        complexCycle[0] = 1;
        complexCycle[1] = new Object[] { 2, new Object[] { 3, complexCycle } };
        assertEquals("a[1, [2, [3, [...]]]]",
            MessageFormatter.basicFormat("{}{}", "a", complexCycle)
        );
    }

    @Test
    void testThrowable() {
        FormattedMessage m;
        Throwable t = new ChameleonException("test");

        m = MessageFormatter.format("Lorem {} dolor {} {}", "ipsum", "sit", "amit", t);
        assertEquals("Lorem ipsum dolor sit amit", m.message());
        assertEquals(t, m.throwable());

        m = MessageFormatter.format("{}{}{}", 1, 2, 3, t);
        assertEquals("123", m.message());
        assertEquals(t, m.throwable());

        m = MessageFormatter.format("Hi, {}. I'm {}.", "Alice", "Bob", 123, t);
        assertEquals("Hi, Alice. I'm Bob.", m.message());
        assertEquals(t, m.throwable());

        m = MessageFormatter.format("{}{}{}{}", 1, 2, 3, t);
        assertEquals("123{}", m.message());
        assertEquals(t, m.throwable());

        m = MessageFormatter.format("Something went wrong while {}", "testing", t);
        assertEquals("Something went wrong while testing", m.message());
        assertEquals(t, m.throwable());

        m = MessageFormatter.format("2+2={}", new Object[] { 5 }, t);
        assertEquals("2+2=5", m.message());
        assertEquals(t, m.throwable());

        m = MessageFormatter.format("{}", (Object[]) null);
        assertEquals("{}", m.message());
        assertNull(m.throwable());

        m = MessageFormatter.format("{}");
        assertEquals("{}", m.message());
        assertNull(m.throwable());

        m = MessageFormatter.format("{}", t);
        assertEquals("{}", m.message());
        assertEquals(t, m.throwable());

        m = MessageFormatter.format("{}", (Object) null, t);
        assertEquals("null", m.message());
        assertEquals(t, m.throwable());

        m = MessageFormatter.format("Hello, {}!", "world");
        assertEquals("Hello, world!", m.message());
        assertNull(m.throwable());
    }

}
