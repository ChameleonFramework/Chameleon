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
package dev.hypera.chameleon.core.managers;

import dev.hypera.chameleon.core.scheduling.Task;
import dev.hypera.chameleon.core.scheduling.TaskImpl;
import org.jetbrains.annotations.NotNull;

/**
 * {@link dev.hypera.chameleon.core.Chameleon} scheduler.
 */
public abstract class Scheduler {

    /**
     * Create new {@link Task.Builder}.
     *
     * @param runnable Task runnable.
     *
     * @return new {@link Task.Builder}.
     */
    public final @NotNull Task.Builder createBuilder(@NotNull Runnable runnable) {
        return new Task.Builder(this::schedule, runnable);
    }

    protected abstract void schedule(@NotNull TaskImpl task);

}
