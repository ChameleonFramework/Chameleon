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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.hypera.chameleon.TestChameleon;
import dev.hypera.chameleon.exception.instantiation.ChameleonInstantiationException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
final class ExtensionTests<P> {

    private static TestChameleon chameleon;
    private @NotNull ChameleonExtensionFactory<ChameleonExtension<P>> extensionFactory = mock(ChameleonExtensionFactory.class);
    private @NotNull ChameleonExtension<P> extension = mock(ChameleonExtension.class);

    @BeforeAll
    static void init() throws ChameleonInstantiationException {
        chameleon = new TestChameleon();
    }

    @BeforeEach
    void setup() {
        this.extension = mock(ChameleonExtension.class);
        this.extensionFactory = mock(ChameleonExtensionFactory.class);
        when(this.extensionFactory.create(any())).thenReturn(this.extension);
    }

    @Test
    void testExtensionManagerLoad() {
        // Load extension
        P ext = chameleon.getExtensionManager().loadExtension(this.extensionFactory);
        assertEquals(this.extension, ext);

        // Verify that the factory created the extension, and the extension was initialised and loaded.
        verify(this.extensionFactory, times(1)).create(chameleon.getPlatform());
        //verify(ext, times(1)).init(chameleon.getLogger(), chameleon.getEventBus());
        //verify(ext, times(1)).load(chameleon);
        // TODO: fix

        // Verify that the extension can be retrieved from the extension manager.
        assertEquals(ext, chameleon.getExtensionManager().getExtension(this.extension.getClass()).orElse(null));
        assertEquals(ext, chameleon.getExtensionManager().loadExtension(this.extensionFactory));
        assertEquals(1, chameleon.getExtensionManager().getExtensions().size());
    }

    @Test
    void testBootstrapLoad() throws ChameleonInstantiationException {
        // Create a new Chameleon bootstrap with the extension and load it.
        TestChameleon testChameleon = TestChameleon.create().withExtensions(this.extension).load();

        // Verify that the extension was initialised and loaded.
        verify(this.extension, times(1)).init(testChameleon.getLogger(), testChameleon.getEventBus());
        verify(this.extension, times(1)).load(testChameleon);

        // Verify that the extension can be retrieved from the extension manager.
        assertEquals(this.extension, testChameleon.getExtensionManager().getExtension(this.extension.getClass()).orElse(null));
        assertEquals(1, testChameleon.getExtensionManager().getExtensions().size());
    }


}
