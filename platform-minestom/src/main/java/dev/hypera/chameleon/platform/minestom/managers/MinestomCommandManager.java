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
package dev.hypera.chameleon.platform.minestom.managers;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.commands.Command;
import dev.hypera.chameleon.managers.CommandManager;
import dev.hypera.chameleon.platform.minestom.command.MinestomCommand;
import net.minestom.server.MinecraftServer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom {@link CommandManager} implementation.
 */
@Internal
public final class MinestomCommandManager extends CommandManager {

    private final @NotNull Chameleon chameleon;

    /**
     * {@link MinestomCommandManager} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     */
    @Internal
    public MinestomCommandManager(@NotNull Chameleon chameleon) {
        super(chameleon);
        this.chameleon = chameleon;
    }

    @Override
    protected void registerCommand(@NotNull Command command) {
        MinecraftServer.getCommandManager().register(new MinestomCommand(this.chameleon, command));
    }

    @Override
    protected void unregisterCommand(@NotNull Command command) {
        net.minestom.server.command.builder.Command minestomCommand = MinecraftServer.getCommandManager().getCommand(command.getName());
        if (null != minestomCommand) {
            MinecraftServer.getCommandManager().unregister(minestomCommand);
        } else {
            throw new IllegalArgumentException("Cannot find command with name '" + command.getName() + "'");
        }
    }

}
