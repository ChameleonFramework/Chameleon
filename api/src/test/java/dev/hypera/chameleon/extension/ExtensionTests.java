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
package dev.hypera.chameleon.extension;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth8.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import dev.hypera.chameleon.TestChameleon;
import dev.hypera.chameleon.exception.extension.ChameleonExtensionException;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import dev.hypera.chameleon.extension.objects.Test2Extension;
import dev.hypera.chameleon.extension.objects.Test2ExtensionImpl;
import dev.hypera.chameleon.extension.objects.TestExtension;
import dev.hypera.chameleon.extension.objects.TestExtensionFactory;
import dev.hypera.chameleon.extension.objects.TestExtensionImpl;
import dev.hypera.chameleon.extension.objects.TestRequiredDependencyEmptyExtension;
import java.util.Collections;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class ExtensionTests {

    private TestChameleon chameleon;
    private @NotNull TestExtensionFactory<TestExtension> factory = spy(TestExtension.create(spy(new TestExtensionImpl())));

    @BeforeEach
    void setup() throws ChameleonInstantiationException {
        this.chameleon = new TestChameleon();
        this.factory = spy(TestExtension.create(spy(new TestExtensionImpl())));
    }

    @Test
    void testDependencies() {
        assertTrue(ChameleonExtensionDependency.required("test-required").required());
        assertFalse(ChameleonExtensionDependency.required("test-required").optional());
        assertFalse(ChameleonExtensionDependency.optional("test-optional").required());
        assertTrue(ChameleonExtensionDependency.optional("test-optional").optional());
    }

    @Test
    void testExtensionManagerLoad() {
        // Load extension
        TestExtension ext = this.chameleon.getExtensionManager().loadExtension(this.factory);
        assertThat(ext.greet("Chameleon")).isEqualTo("こんにちは、Chameleon!");

        // Verify that the factory created the extension, and the extension was initialised and loaded.
        verify(this.factory, times(1)).create(TestChameleon.PLATFORM_ID);
        verify((ChameleonPlatformExtension) ext, times(1))
            .init(this.chameleon.getLogger(), this.chameleon.getEventBus());
        verify((ChameleonPlatformExtension) ext, times(1)).load(this.chameleon);

        // Verify that the extension can be retrieved from the extension manager.
        assertThat(this.chameleon.getExtensionManager().getExtensions()).hasSize(1);
        assertThat(this.chameleon.getExtensionManager().getExtension(TestExtension.class)).hasValue(ext);
        assertThat(
            this.chameleon.getExtensionManager()
                .loadExtension(TestExtension.create(new TestExtensionImpl()))
        ).isEqualTo(ext);
    }

    @Test
    void testBootstrapLoad() throws ChameleonInstantiationException {
        // Create a new Chameleon bootstrap with the extension and load it.
        TestChameleon testChameleon = TestChameleon.create().withExtension(this.factory).load();

        // Verify that the extension was initialised and loaded.
        assertThat(testChameleon.getExtensionManager().getExtensions()).hasSize(1);
        TestExtension ext = testChameleon.getExtensionManager().getExtension(TestExtension.class)
            .orElseGet(() -> fail("extension missing"));
        verify((ChameleonPlatformExtension) ext, times(1))
            .init(testChameleon.getLogger(), testChameleon.getEventBus());
        verify((ChameleonPlatformExtension) ext, times(1)).load(testChameleon);

        // Verify that attempting to load the extension again will just return the already loaded ext.
        assertThat(
            testChameleon.getExtensionManager()
                .loadExtension(TestExtension.create(new TestExtensionImpl()))
        ).isEqualTo(ext);
    }

    @Test
    void testExtensionDependency() {
        // Load first extension.
        TestExtension ext = this.chameleon.getExtensionManager().loadExtension(this.factory);
        assertThat(ext.greet("Chameleon")).isEqualTo("こんにちは、Chameleon!");

        // Load second extension which depends on the first extension.
        Test2Extension ext2 = this.chameleon.getExtensionManager()
            .loadExtension(Test2Extension.create(new Test2ExtensionImpl()));
        assertThat(ext2.greet("Chameleon")).isEqualTo("こんにちは、Chameleon!");

        // Verify that the first extension was called by the second extension.
        verify(ext, times(2)).greet("Chameleon");
    }

    @Test
    void testExtensionMissingDependencyFails() {
        ChameleonExtensionException ex = assertThrowsExactly(ChameleonExtensionException.class, () ->
            this.chameleon.getExtensionManager().loadExtension(Test2Extension.create(
                new Test2ExtensionImpl(), Collections.singleton(
                    ChameleonExtensionDependency.required("Test", TestExtension.class)
                ))));
        assertThat(ex).hasMessageThat().isEqualTo(
            "Test2Extension requires dependencies but some are missing: Test"
        );
    }

    @Test
    void testFactoryReturnsInvalidFails() {
        // Attempt to load the extension using the extension manager.
        ChameleonExtensionException ex = assertThrowsExactly(ChameleonExtensionException.class, () ->
            this.chameleon.getExtensionManager().loadExtension(TestExtension.create(new Test2ExtensionImpl())));
        assertThat(ex).hasMessageThat().isEqualTo(
            "Cannot load TestExtension: not assignable from Test2ExtensionImpl"
        );

        // Create a new Chameleon bootstrap with the extension and attempt to load it.
        ex = assertThrowsExactly(ChameleonExtensionException.class, () ->
            TestChameleon.create().withExtension(
                TestExtension.create(new Test2ExtensionImpl())
            ).load());
        assertThat(ex).hasMessageThat().isEqualTo(
            "Cannot load TestExtension: not assignable from Test2ExtensionImpl"
        );
    }

    @Test
    void testExtensionRequiredDependencyEmptyFails() {
        // Attempt to load the extension using the extension manager.
        ChameleonExtensionException ex = assertThrowsExactly(ChameleonExtensionException.class, () ->
            this.chameleon.getExtensionManager().loadExtension(
                TestExtension.create(new TestRequiredDependencyEmptyExtension(), Collections.singleton(
                    ChameleonExtensionDependency.required("dev.hypera.chameleon.nonexistant.NonexistantExtension")
                ))
            ));
        assertThat(ex).hasMessageThat().isEqualTo(
            "TestExtension requires dependencies but some are missing: " +
                "dev.hypera.chameleon.nonexistant.NonexistantExtension"
        );

        // Create a new Chameleon bootstrap with the extension and attempt to load it.
        ex = assertThrowsExactly(ChameleonExtensionException.class, () ->
            TestChameleon.create().withExtension(
                TestExtension.create(new TestRequiredDependencyEmptyExtension(), Collections.singleton(
                    ChameleonExtensionDependency.required("dev.hypera.chameleon.nonexistant.NonexistantExtension")
                ))
            ).load());
        assertThat(ex).hasMessageThat().isEqualTo(
            "TestExtension requires dependencies but some are missing: " +
                "dev.hypera.chameleon.nonexistant.NonexistantExtension"
        );
    }

}
