/*
 * Chameleon - Cross-platform Minecraft plugin creation library
 *  Copyright (c) 2021 SLLCoding <luisjk266@gmail.com>
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

package dev.hypera.chameleon.core.commands;

import dev.hypera.chameleon.core.Chameleon;
import org.jetbrains.annotations.NotNull;

public abstract class CommandManager {

    protected final Chameleon chameleon;

    public CommandManager(Chameleon chameleon) {
        this.chameleon = chameleon;
    }

    public void registerCommand(@NotNull Command command) {
        if (command.getPlatform().getPlatformTypes().stream().anyMatch(t -> t.equals(chameleon.getPlatformData().getType()))) {
            registerPlatformCommand(command);
        }
    }

    public void unregisterCommand(@NotNull Command command) {
        if (command.getPlatform().getPlatformTypes().stream().anyMatch(t -> t.equals(chameleon.getPlatformData().getType()))) {
            unregisterPlatformCommand(command);
        }
    }

    protected abstract void registerPlatformCommand(@NotNull Command command);
    protected abstract void unregisterPlatformCommand(@NotNull Command command);

}