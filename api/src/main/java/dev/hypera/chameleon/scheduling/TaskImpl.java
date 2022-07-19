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
package dev.hypera.chameleon.scheduling;

import org.jetbrains.annotations.NotNull;

/**
 * {@link Task} implementation.
 */
public class TaskImpl implements Task {

    private final @NotNull Runnable runnable;
    private final @NotNull Type type;
    private final @NotNull Schedule delay;
    private final @NotNull Schedule repeat;

    TaskImpl(@NotNull Runnable runnable, @NotNull Type type, @NotNull Schedule delay, @NotNull Schedule repeat) {
        this.runnable = runnable;
        this.type = type;
        this.delay = delay;
        this.repeat = repeat;
    }

    /**
     * Get Task {@link Runnable}.
     *
     * @return Task {@link Runnable}.
     */
    public @NotNull Runnable getRunnable() {
        return this.runnable;
    }

    /**
     * Get {@link Task.Type}.
     *
     * @return {@link Task.Type}.
     */
    public @NotNull Type getType() {
        return this.type;
    }

    /**
     * Get Task delay.
     *
     * @return Task delay {@link Schedule}.
     */
    public @NotNull Schedule getDelay() {
        return this.delay;
    }

    /**
     * Get Task repeat.
     *
     * @return Task repeat {@link Schedule}.
     */
    public @NotNull Schedule getRepeat() {
        return this.repeat;
    }

}
