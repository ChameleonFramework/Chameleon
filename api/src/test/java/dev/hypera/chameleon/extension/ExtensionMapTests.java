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
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import dev.hypera.chameleon.exception.extension.ChameleonExtensionException;
import dev.hypera.chameleon.extension.objects.Test2Extension;
import dev.hypera.chameleon.extension.objects.Test2ExtensionImpl;
import dev.hypera.chameleon.extension.objects.TestCircularDetection1Extension;
import dev.hypera.chameleon.extension.objects.TestCircularDetection1ExtensionImpl;
import dev.hypera.chameleon.extension.objects.TestCircularDetection2Extension;
import dev.hypera.chameleon.extension.objects.TestCircularDetection2ExtensionImpl;
import dev.hypera.chameleon.extension.objects.TestExtension;
import dev.hypera.chameleon.extension.objects.TestExtensionImpl;
import dev.hypera.chameleon.util.Pair;
import java.util.Arrays;
import java.util.Collections;
import org.junit.jupiter.api.Test;

final class ExtensionMapTests {

    @Test
    void testLoadSort() {
        // Create extension map and extensions.
        ExtensionMap extensionMap = new ExtensionMap();
        TestExtensionImpl testExtensionImpl = new TestExtensionImpl();
        Test2ExtensionImpl test2ExtensionImpl = new Test2ExtensionImpl();
        extensionMap.put(Test2Extension.class, Pair.of(test2ExtensionImpl, Arrays.asList(
            ChameleonExtensionDependency.required("Test", TestExtension.class),
            ChameleonExtensionDependency.optional(TestCircularDetection1Extension.class),
            ChameleonExtensionDependency.optional(TestCircularDetection2Extension.class.getCanonicalName()),
            ChameleonExtensionDependency.optional("dev.hypera.chameleon.nonexistant.NonexistantExtension")
        )));
        extensionMap.put(TestExtension.class, Pair.of(testExtensionImpl, Collections.emptyList()));

        // Verify that the extension map contains the expected extensions.
        assertThat(extensionMap).hasSize(2);

        // Verify that the extensions are sorted in the correct order.
        assertThat(extensionMap.loadSort())
            .containsExactly(testExtensionImpl, test2ExtensionImpl)
            .inOrder();
    }

    @Test
    void testLoadSortCircular() {
        // Create extension map and extensions.
        ExtensionMap extensionMap = new ExtensionMap();
        TestCircularDetection1ExtensionImpl test1Impl = new TestCircularDetection1ExtensionImpl();
        TestCircularDetection2ExtensionImpl test2Impl = new TestCircularDetection2ExtensionImpl();
        extensionMap.put(TestCircularDetection1Extension.class, Pair.of(test1Impl, Collections.singleton(
            ChameleonExtensionDependency.required(TestCircularDetection2Extension.class)
        )));
        extensionMap.put(TestCircularDetection2Extension.class, Pair.of(test2Impl, Collections.singleton(
            ChameleonExtensionDependency.required(TestCircularDetection1Extension.class)
        )));

        // Verify that the extension map contains the expected extensions.
        assertThat(extensionMap).hasSize(2);

        // Verify that the circular dependencies are detected.
        ChameleonExtensionException ex = assertThrowsExactly(
            ChameleonExtensionException.class, extensionMap::loadSort);
        assertThat(ex).hasMessageThat().contains("Detected circular dependencies");
    }

}
