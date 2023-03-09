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

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.event.EventBus;
import dev.hypera.chameleon.exception.extension.ChameleonExtensionException;
import dev.hypera.chameleon.logger.ChameleonLogger;
import org.jetbrains.annotations.NotNull;

/**
 * Extension.
 *
 * @param <P> Extension platform.
 */
public interface ChameleonExtension<P> {

    /**
     * Extension init.
     *
     * <p>This method will be called when the Extension is initialised by Chameleon, either before
     * Chameleon is constructed, or when EventManager#loadExtension is called.</p>
     *
     * @param logger   Logger.
     * @param eventBus Event bus.
     */
    void init(@NotNull ChameleonLogger logger, @NotNull EventBus eventBus) throws ChameleonExtensionException;

    /**
     * Extension load.
     *
     * <p>This method will be called when Chameleon has finished loading, or when
     * EventManager#loadExtension is called after Chameleon has loaded.</p>
     *
     * <p>If your extension is platform dependant, then you can cast {@code chameleon} to the
     * platform Chameleon implementation, e.g. BukkitChameleon, BungeeCordChameleon, etc.</p>
     *
     * @param chameleon Chameleon instance.
     */
    void load(@NotNull Chameleon chameleon);

    /**
     * Get the extension's loaded platform.
     *
     * @return extension platform.
     */
    @NotNull P getPlatform();

}
