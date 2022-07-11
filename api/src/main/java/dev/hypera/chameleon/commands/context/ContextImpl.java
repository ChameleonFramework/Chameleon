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
package dev.hypera.chameleon.commands.context;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.users.ChatUser;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Context} implementation.
 */
public final class ContextImpl implements Context {

    private final @NotNull ChatUser sender;
    private final @NotNull Chameleon chameleon;
    private final @NotNull String[] args;

    /**
     * {@link ContextImpl} constructor.
     *
     * @param sender Command sender.
     * @param chameleon {@link Chameleon} instance.
     * @param args Command arguments.
     */
    @Internal
    public ContextImpl(@NotNull ChatUser sender, @NotNull Chameleon chameleon, @NotNull String[] args) {
        this.sender = sender;
        this.chameleon = chameleon;
        this.args = args;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChatUser getSender() {
        return this.sender;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Chameleon getChameleon() {
        return this.chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String[] getArgs() {
        return this.args;
    }

}
