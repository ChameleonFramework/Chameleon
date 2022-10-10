/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.platform.velocity.commands;

import com.velocitypowered.api.command.SimpleCommand;
import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.command.Command;
import dev.hypera.chameleon.command.context.ContextImpl;
import dev.hypera.chameleon.platform.velocity.user.VelocityUsers;
import java.util.Arrays;
import java.util.List;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity {@link Command} wrapper.
 */
@Internal
public final class VelocityCommand implements SimpleCommand {

    private final @NotNull Chameleon chameleon;
    private final @NotNull Command command;

    /**
     * {@link VelocityCommand} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     * @param command   {@link Command} to be wrapped.
     */
    @Internal
    public VelocityCommand(@NotNull Chameleon chameleon, @NotNull Command command) {
        this.chameleon = chameleon;
        this.command = command;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(@NotNull Invocation invocation) {
        if (invocation.arguments().length < 1 || this.command.executeSubCommand(
            new ContextImpl(VelocityUsers.wrap(this.chameleon, invocation.source()), this.chameleon, Arrays.copyOfRange(invocation.arguments(), 1, invocation.arguments().length)),
            invocation.arguments()[0]
        )) {
            this.command.executeCommand(new ContextImpl(VelocityUsers.wrap(this.chameleon, invocation.source()), this.chameleon, invocation.arguments()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> suggest(@NotNull Invocation invocation) {
        return this.command.tabComplete(new ContextImpl(VelocityUsers.wrap(this.chameleon, invocation.source()), this.chameleon, invocation.arguments()));
    }

}
