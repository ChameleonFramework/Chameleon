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
package dev.hypera.chameleon.command;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.util.Preconditions;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon command manager.
 */
@NonExtendable
public abstract class CommandManager {

    private final @NotNull Chameleon chameleon;

    /**
     * Command manager constructor.
     *
     * @param chameleon Chameleon implementation.
     */
    @Internal
    protected CommandManager(@NotNull Chameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * Register the given command.
     *
     * @param command Command to be registered.
     */
    public void register(@NotNull Command command) {
        Preconditions.checkNotNull("command", command);
        if (command.getPlatform().test(this.chameleon.getPlatform())) {
            registerCommand(command);
        }
    }

    /**
     * Unregister the given command.
     *
     * @param command Command to be unregistered.
     */
    public void unregister(@NotNull Command command) {
        Preconditions.checkNotNull("command", command);
        if (command.getPlatform().test(this.chameleon.getPlatform())) {
            unregisterCommand(command);
        }
    }

    @Internal
    protected abstract void registerCommand(@NotNull Command command);

    @Internal
    protected abstract void unregisterCommand(@NotNull Command command);

}
