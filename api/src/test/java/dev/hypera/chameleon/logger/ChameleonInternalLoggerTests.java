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
package dev.hypera.chameleon.logger;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import dev.hypera.chameleon.util.internal.ChameleonProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class ChameleonInternalLoggerTests {

    private ChameleonLogger underlyingLogger = mock(ChameleonLogger.class);
    private ChameleonLogger logger;

    @BeforeEach
    void setup() {
        ChameleonProperty.DEBUG.set(true);
        this.underlyingLogger = mock(ChameleonLogger.class);
        this.logger = ChameleonInternalLogger.create(this.underlyingLogger);
    }

    @Test
    void testTrace() {
        // Return true when #isTraceEnabled() is called
        doReturn(true).when(this.underlyingLogger).isTraceEnabled();

        // #trace(String) should forward to the underlying logger if #isTraceEnabled() returns true
        this.logger.trace("test");
        verify(this.underlyingLogger, times(1)).isTraceEnabled();
        verify(this.underlyingLogger, times(1)).trace(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test")
        );

        // #trace(String, Object) should forward to the underlying logger if #isTraceEnabled()
        // returns true
        this.logger.trace("hello, {}!", "world");
        verify(this.underlyingLogger, times(2)).isTraceEnabled();
        verify(this.underlyingLogger, times(1)).trace(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), "world"
        );

        // #trace(String, Object, Object) should forward to the underlying logger if
        // #isTraceEnabled() returns true
        this.logger.trace("hello, {}! I'm {}", "world", "steve");
        verify(this.underlyingLogger, times(3)).isTraceEnabled();
        verify(this.underlyingLogger, times(1)).trace(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}! I'm {}"), "world", "steve"
        );

        // #trace(String, Object...) should forward to the underlying logger if #isTraceEnabled()
        // returns true
        this.logger.trace("hello, {}!", new Object[]{ "world" });
        verify(this.underlyingLogger, times(4)).isTraceEnabled();
        verify(this.underlyingLogger, times(1)).trace(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), new Object[]{ "world" }
        );

        // #trace(String, Exception) should forward to the underlying logger if #isTraceEnabled()
        // returns true
        Throwable ex = new IllegalStateException();
        this.logger.trace("test", ex);
        verify(this.underlyingLogger, times(5)).isTraceEnabled();
        verify(this.underlyingLogger, times(1)).trace(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test"), ex
        );
    }

    @Test
    void testTraceNotForwardedWhenDisabled() {
        // Return false when #isTraceEnabled() is called
        doReturn(false).when(this.underlyingLogger).isTraceEnabled();

        // #trace(String) should not forward to the underlying logger if #isTraceEnabled()
        // returns false
        this.logger.trace("test");
        verify(this.underlyingLogger, times(1)).isTraceEnabled();
        verify(this.underlyingLogger, times(0)).trace(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test")
        );

        // #trace(String, Object) should not forward to the underlying logger if #isTraceEnabled()
        // returns false
        this.logger.trace("hello, {}!", "world");
        verify(this.underlyingLogger, times(2)).isTraceEnabled();
        verify(this.underlyingLogger, times(0)).trace(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), "world"
        );

        // #trace(String, Object, Object) should not forward to the underlying logger if
        // #isTraceEnabled() returns false
        this.logger.trace("hello, {}! I'm {}", "world", "steve");
        verify(this.underlyingLogger, times(3)).isTraceEnabled();
        verify(this.underlyingLogger, times(0)).trace(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}! I'm {}"), "world", "steve"
        );

        // #trace(String, Object...) should not forward to the underlying logger if
        // #isTraceEnabled() returns false
        this.logger.trace("hello, {}!", new Object[]{ "world" });
        verify(this.underlyingLogger, times(4)).isTraceEnabled();
        verify(this.underlyingLogger, times(0)).trace(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), new Object[]{ "world" }
        );

        // #trace(String, Exception) should not forward to the underlying logger if
        // #isTraceEnabled() returns false
        Throwable ex = new IllegalStateException();
        this.logger.trace("test", ex);
        verify(this.underlyingLogger, times(5)).isTraceEnabled();
        verify(this.underlyingLogger, times(0)).trace(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test"), ex
        );
    }

    @Test
    void testDebug() {
        // Return true when #isDebugEnabled() is called
        doReturn(true).when(this.underlyingLogger).isDebugEnabled();

        // #debug(String) should forward to the underlying logger if #isDebugEnabled() returns true
        this.logger.debug("test");
        verify(this.underlyingLogger, times(1)).isDebugEnabled();
        verify(this.underlyingLogger, times(1)).debug(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test")
        );

        // #debug(String, Object) should forward to the underlying logger if #isDebugEnabled()
        // returns true
        this.logger.debug("hello, {}!", "world");
        verify(this.underlyingLogger, times(2)).isDebugEnabled();
        verify(this.underlyingLogger, times(1)).debug(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), "world"
        );

        // #debug(String, Object, Object) should forward to the underlying logger if
        // #isDebugEnabled() returns true
        this.logger.debug("hello, {}! I'm {}", "world", "steve");
        verify(this.underlyingLogger, times(3)).isDebugEnabled();
        verify(this.underlyingLogger, times(1)).debug(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}! I'm {}"), "world", "steve"
        );

        // #debug(String, Object...) should forward to the underlying logger if #isDebugEnabled()
        // returns true
        this.logger.debug("hello, {}!", new Object[]{ "world" });
        verify(this.underlyingLogger, times(4)).isDebugEnabled();
        verify(this.underlyingLogger, times(1)).debug(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), new Object[]{ "world" }
        );

        // #debug(String, Exception) should forward to the underlying logger if #isDebugEnabled()
        // returns true
        Throwable ex = new IllegalStateException();
        this.logger.debug("test", ex);
        verify(this.underlyingLogger, times(5)).isDebugEnabled();
        verify(this.underlyingLogger, times(1)).debug(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test"), ex
        );
    }

    @Test
    void testDebugNotForwardedWhenDisabled() {
        // Return false when #isDebugEnabled() is called
        doReturn(false).when(this.underlyingLogger).isDebugEnabled();

        // #debug(String) should not forward to the underlying logger if #isDebugEnabled()
        // returns false
        this.logger.debug("test");
        verify(this.underlyingLogger, times(1)).isDebugEnabled();
        verify(this.underlyingLogger, times(0)).debug(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test")
        );

        // #debug(String, Object) should not forward to the underlying logger if #isDebugEnabled()
        // returns false
        this.logger.debug("hello, {}!", "world");
        verify(this.underlyingLogger, times(2)).isDebugEnabled();
        verify(this.underlyingLogger, times(0)).debug(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), "world"
        );

        // #debug(String, Object, Object) should not forward to the underlying logger if
        // #isDebugEnabled() returns false
        this.logger.debug("hello, {}! I'm {}", "world", "steve");
        verify(this.underlyingLogger, times(3)).isDebugEnabled();
        verify(this.underlyingLogger, times(0)).debug(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}! I'm {}"), "world", "steve"
        );

        // #debug(String, Object...) should not forward to the underlying logger if
        // #isDebugEnabled() returns false
        this.logger.debug("hello, {}!", new Object[]{ "world" });
        verify(this.underlyingLogger, times(4)).isDebugEnabled();
        verify(this.underlyingLogger, times(0)).debug(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), new Object[]{ "world" }
        );

        // #debug(String, Exception) should not forward to the underlying logger if
        // #isDebugEnabled() returns false
        Throwable ex = new IllegalStateException();
        this.logger.debug("test", ex);
        verify(this.underlyingLogger, times(5)).isDebugEnabled();
        verify(this.underlyingLogger, times(0)).debug(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test"), ex
        );
    }

    @Test
    void testInfo() {
        // Return true when #isInfoEnabled() is called
        doReturn(true).when(this.underlyingLogger).isInfoEnabled();

        // #info(String) should forward to the underlying logger if #isInfoEnabled() returns true
        this.logger.info("test");
        verify(this.underlyingLogger, times(1)).isInfoEnabled();
        verify(this.underlyingLogger, times(1)).info(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test")
        );

        // #info(String, Object) should forward to the underlying logger if #isInfoEnabled()
        // returns true
        this.logger.info("hello, {}!", "world");
        verify(this.underlyingLogger, times(2)).isInfoEnabled();
        verify(this.underlyingLogger, times(1)).info(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), "world"
        );

        // #info(String, Object, Object) should forward to the underlying logger if
        // #isInfoEnabled() returns true
        this.logger.info("hello, {}! I'm {}", "world", "steve");
        verify(this.underlyingLogger, times(3)).isInfoEnabled();
        verify(this.underlyingLogger, times(1)).info(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}! I'm {}"), "world", "steve"
        );

        // #info(String, Object...) should forward to the underlying logger if #isInfoEnabled()
        // returns true
        this.logger.info("hello, {}!", new Object[]{ "world" });
        verify(this.underlyingLogger, times(4)).isInfoEnabled();
        verify(this.underlyingLogger, times(1)).info(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), new Object[]{ "world" }
        );

        // #info(String, Exception) should forward to the underlying logger if #isInfoEnabled()
        // returns true
        Throwable ex = new IllegalStateException();
        this.logger.info("test", ex);
        verify(this.underlyingLogger, times(5)).isInfoEnabled();
        verify(this.underlyingLogger, times(1)).info(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test"), ex
        );
    }

    @Test
    void testInfoNotForwardedWhenDisabled() {
        // Return false when #isInfoEnabled() is called
        doReturn(false).when(this.underlyingLogger).isInfoEnabled();

        // #info(String) should not forward to the underlying logger if #isInfoEnabled()
        // returns false
        this.logger.info("test");
        verify(this.underlyingLogger, times(1)).isInfoEnabled();
        verify(this.underlyingLogger, times(0)).info(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test")
        );

        // #info(String, Object) should not forward to the underlying logger if #isInfoEnabled()
        // returns false
        this.logger.info("hello, {}!", "world");
        verify(this.underlyingLogger, times(2)).isInfoEnabled();
        verify(this.underlyingLogger, times(0)).info(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), "world"
        );

        // #info(String, Object, Object) should not forward to the underlying logger if
        // #isInfoEnabled() returns false
        this.logger.info("hello, {}! I'm {}", "world", "steve");
        verify(this.underlyingLogger, times(3)).isInfoEnabled();
        verify(this.underlyingLogger, times(0)).info(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}! I'm {}"), "world", "steve"
        );

        // #info(String, Object...) should not forward to the underlying logger if
        // #isInfoEnabled() returns false
        this.logger.info("hello, {}!", new Object[]{ "world" });
        verify(this.underlyingLogger, times(4)).isInfoEnabled();
        verify(this.underlyingLogger, times(0)).info(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), new Object[]{ "world" }
        );

        // #info(String, Exception) should not forward to the underlying logger if
        // #isInfoEnabled() returns false
        Throwable ex = new IllegalStateException();
        this.logger.info("test", ex);
        verify(this.underlyingLogger, times(5)).isInfoEnabled();
        verify(this.underlyingLogger, times(0)).info(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test"), ex
        );
    }

    @Test
    void testWarn() {
        // Return true when #isWarnEnabled() is called
        doReturn(true).when(this.underlyingLogger).isWarnEnabled();

        // #warn(String) should forward to the underlying logger if #isWarnEnabled() returns true
        this.logger.warn("test");
        verify(this.underlyingLogger, times(1)).isWarnEnabled();
        verify(this.underlyingLogger, times(1)).warn(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test")
        );

        // #warn(String, Object) should forward to the underlying logger if #isWarnEnabled()
        // returns true
        this.logger.warn("hello, {}!", "world");
        verify(this.underlyingLogger, times(2)).isWarnEnabled();
        verify(this.underlyingLogger, times(1)).warn(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), "world"
        );

        // #warn(String, Object, Object) should forward to the underlying logger if
        // #isWarnEnabled() returns true
        this.logger.warn("hello, {}! I'm {}", "world", "steve");
        verify(this.underlyingLogger, times(3)).isWarnEnabled();
        verify(this.underlyingLogger, times(1)).warn(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}! I'm {}"), "world", "steve"
        );

        // #warn(String, Object...) should forward to the underlying logger if #isWarnEnabled()
        // returns true
        this.logger.warn("hello, {}!", new Object[]{ "world" });
        verify(this.underlyingLogger, times(4)).isWarnEnabled();
        verify(this.underlyingLogger, times(1)).warn(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), new Object[]{ "world" }
        );

        // #warn(String, Exception) should forward to the underlying logger if #isWarnEnabled()
        // returns true
        Throwable ex = new IllegalStateException();
        this.logger.warn("test", ex);
        verify(this.underlyingLogger, times(5)).isWarnEnabled();
        verify(this.underlyingLogger, times(1)).warn(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test"), ex
        );
    }

    @Test
    void testWarnNotForwardedWhenDisabled() {
        // Return false when #isWarnEnabled() is called
        doReturn(false).when(this.underlyingLogger).isWarnEnabled();

        // #warn(String) should not forward to the underlying logger if #isWarnEnabled()
        // returns false
        this.logger.warn("test");
        verify(this.underlyingLogger, times(1)).isWarnEnabled();
        verify(this.underlyingLogger, times(0)).warn(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test")
        );

        // #warn(String, Object) should not forward to the underlying logger if #isWarnEnabled()
        // returns false
        this.logger.warn("hello, {}!", "world");
        verify(this.underlyingLogger, times(2)).isWarnEnabled();
        verify(this.underlyingLogger, times(0)).warn(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), "world"
        );

        // #warn(String, Object, Object) should not forward to the underlying logger if
        // #isWarnEnabled() returns false
        this.logger.warn("hello, {}! I'm {}", "world", "steve");
        verify(this.underlyingLogger, times(3)).isWarnEnabled();
        verify(this.underlyingLogger, times(0)).warn(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}! I'm {}"), "world", "steve"
        );

        // #warn(String, Object...) should not forward to the underlying logger if
        // #isWarnEnabled() returns false
        this.logger.warn("hello, {}!", new Object[]{ "world" });
        verify(this.underlyingLogger, times(4)).isWarnEnabled();
        verify(this.underlyingLogger, times(0)).warn(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), new Object[]{ "world" }
        );

        // #warn(String, Exception) should not forward to the underlying logger if
        // #isWarnEnabled() returns false
        Throwable ex = new IllegalStateException();
        this.logger.warn("test", ex);
        verify(this.underlyingLogger, times(5)).isWarnEnabled();
        verify(this.underlyingLogger, times(0)).warn(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test"), ex
        );
    }

    @Test
    void testError() {
        // Return true when #isErrorEnabled() is called
        doReturn(true).when(this.underlyingLogger).isErrorEnabled();

        // #error(String) should forward to the underlying logger if #isErrorEnabled() returns true
        this.logger.error("test");
        verify(this.underlyingLogger, times(1)).isErrorEnabled();
        verify(this.underlyingLogger, times(1)).error(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test")
        );

        // #error(String, Object) should forward to the underlying logger if #isErrorEnabled()
        // returns true
        this.logger.error("hello, {}!", "world");
        verify(this.underlyingLogger, times(2)).isErrorEnabled();
        verify(this.underlyingLogger, times(1)).error(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), "world"
        );

        // #error(String, Object, Object) should forward to the underlying logger if
        // #isErrorEnabled() returns true
        this.logger.error("hello, {}! I'm {}", "world", "steve");
        verify(this.underlyingLogger, times(3)).isErrorEnabled();
        verify(this.underlyingLogger, times(1)).error(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}! I'm {}"), "world", "steve"
        );

        // #error(String, Object...) should forward to the underlying logger if #isErrorEnabled()
        // returns true
        this.logger.error("hello, {}!", new Object[]{ "world" });
        verify(this.underlyingLogger, times(4)).isErrorEnabled();
        verify(this.underlyingLogger, times(1)).error(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), new Object[]{ "world" }
        );

        // #error(String, Exception) should forward to the underlying logger if #isErrorEnabled()
        // returns true
        Throwable ex = new IllegalStateException();
        this.logger.error("test", ex);
        verify(this.underlyingLogger, times(5)).isErrorEnabled();
        verify(this.underlyingLogger, times(1)).error(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test"), ex
        );
    }

    @Test
    void testErrorNotForwardedWhenDisabled() {
        // Return false when #isErrorEnabled() is called
        doReturn(false).when(this.underlyingLogger).isErrorEnabled();

        // #error(String) should not forward to the underlying logger if #isErrorEnabled()
        // returns false
        this.logger.error("test");
        verify(this.underlyingLogger, times(1)).isErrorEnabled();
        verify(this.underlyingLogger, times(0)).error(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test")
        );

        // #error(String, Object) should not forward to the underlying logger if #isErrorEnabled()
        // returns false
        this.logger.error("hello, {}!", "world");
        verify(this.underlyingLogger, times(2)).isErrorEnabled();
        verify(this.underlyingLogger, times(0)).error(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), "world"
        );

        // #error(String, Object, Object) should not forward to the underlying logger if
        // #isErrorEnabled() returns false
        this.logger.error("hello, {}! I'm {}", "world", "steve");
        verify(this.underlyingLogger, times(3)).isErrorEnabled();
        verify(this.underlyingLogger, times(0)).error(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}! I'm {}"), "world", "steve"
        );

        // #error(String, Object...) should not forward to the underlying logger if
        // #isErrorEnabled() returns false
        this.logger.error("hello, {}!", new Object[]{ "world" });
        verify(this.underlyingLogger, times(4)).isErrorEnabled();
        verify(this.underlyingLogger, times(0)).error(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("hello, {}!"), new Object[]{ "world" }
        );

        // #error(String, Exception) should not forward to the underlying logger if
        // #isErrorEnabled() returns false
        Throwable ex = new IllegalStateException();
        this.logger.error("test", ex);
        verify(this.underlyingLogger, times(5)).isErrorEnabled();
        verify(this.underlyingLogger, times(0)).error(
            ChameleonInternalLogger.CHAMELEON_PREFIX.concat("test"), ex
        );
    }

    @Test
    void testIsTraceEnabled() {
        // #isTraceEnabled() should only return true when ChameleonProperty.DEBUG is true and the
        // underlying logger's #isTraceEnabled() method returns true
        doReturn(false).when(this.underlyingLogger).isTraceEnabled();
        assertFalse(this.logger.isTraceEnabled());

        doReturn(true).when(this.underlyingLogger).isTraceEnabled();
        assertTrue(this.logger.isTraceEnabled());

        ChameleonProperty.DEBUG.set(false);
        assertFalse(this.logger.isTraceEnabled());
    }

    @Test
    void testIsDebugEnabled() {
        // #isDebugEnabled() should only return true when ChameleonProperty.DEBUG is true and the
        // underlying logger's #isDebugEnabled() method returns true
        doReturn(false).when(this.underlyingLogger).isDebugEnabled();
        assertFalse(this.logger.isDebugEnabled());

        doReturn(true).when(this.underlyingLogger).isDebugEnabled();
        assertTrue(this.logger.isDebugEnabled());

        ChameleonProperty.DEBUG.set(false);
        assertFalse(this.logger.isDebugEnabled());
    }

    @Test
    void testIsInfoEnabled() {
        // #isInfoEnabled() should only return true when ChameleonProperty.DEBUG is true and the
        // underlying logger's #isInfoEnabled() method returns true
        doReturn(false).when(this.underlyingLogger).isInfoEnabled();
        assertFalse(this.logger.isInfoEnabled());

        doReturn(true).when(this.underlyingLogger).isInfoEnabled();
        assertTrue(this.logger.isInfoEnabled());

        ChameleonProperty.DEBUG.set(false);
        assertFalse(this.logger.isInfoEnabled());
    }

    @Test
    void testIsWarnEnabled() {
        // #isWarnEnabled() should only return true when ChameleonProperty.DEBUG is true and the
        // underlying logger's #isWarnEnabled() method returns true
        doReturn(false).when(this.underlyingLogger).isWarnEnabled();
        assertFalse(this.logger.isWarnEnabled());

        doReturn(true).when(this.underlyingLogger).isWarnEnabled();
        assertTrue(this.logger.isWarnEnabled());

        ChameleonProperty.DEBUG.set(false);
        assertFalse(this.logger.isWarnEnabled());
    }

    @Test
    void testIsErrorEnabled() {
        // #isErrorEnabled() should only return true when ChameleonProperty.LOG_ERRORS is true and
        // the underlying logger's #isErrorEnabled() method returns true
        doReturn(false).when(this.underlyingLogger).isErrorEnabled();
        assertFalse(this.logger.isErrorEnabled());

        doReturn(true).when(this.underlyingLogger).isErrorEnabled();
        assertTrue(this.logger.isErrorEnabled());

        ChameleonProperty.LOG_ERRORS.set(false);
        assertFalse(this.logger.isErrorEnabled());
        ChameleonProperty.LOG_ERRORS.reset();
    }

}
