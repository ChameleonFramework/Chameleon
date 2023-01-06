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
package dev.hypera.chameleon.scheduling;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

/**
 * {@link Task} implementation.
 */
public class TaskImpl implements Task {

    private final @NotNull Runnable runnable;
    private final @NotNull Schedule delay;
    private final @NotNull Schedule repeat;
    private final boolean async;

    private final @NotNull BooleanSupplier cancelWhen;
    private final @Nullable AtomicInteger cancellationCount;

    private boolean cancelled = false;
    private @Nullable ScheduledTask scheduledTask;

    TaskImpl(@NotNull Runnable runnable, @NotNull Schedule delay, @NotNull Schedule repeat, boolean async, @NotNull BooleanSupplier cancelWhen, int cancelAfter) {
        this.runnable = runnable;
        this.delay = delay;
        this.repeat = repeat;
        this.async = async;

        this.cancelWhen = cancelWhen;
        this.cancellationCount = cancelAfter > 0 ? new AtomicInteger(cancelAfter) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run() {
        if (this.cancelled) {
            if (null != this.scheduledTask) {
                this.scheduledTask.cancel();
            }

            return;
        }

        if (this.cancelWhen.getAsBoolean()) {
            this.cancelled = true;
            if (null != this.scheduledTask) {
                this.scheduledTask.cancel();
            }

            return;
        }

        this.runnable.run();

        if (null != this.cancellationCount && this.cancellationCount.decrementAndGet() == 0) {
            this.cancelled = true;
            if (null != this.scheduledTask) {
                this.scheduledTask.cancel();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAsync() {
        return this.async;
    }

    @NotNull Schedule getDelay() {
        return this.delay;
    }

    @NotNull Schedule getRepeat() {
        return this.repeat;
    }

    /**
     * Get whether this task was cancelled.
     *
     * @return cancelled.
     */
    @Internal
    @VisibleForTesting
    public boolean isCancelled() {
        return this.cancelled;
    }

    void setScheduledTask(@NotNull ScheduledTask task) {
        this.scheduledTask = task;
    }

    static final class BuilderImpl implements Builder {

        private final @NotNull Runnable runnable;
        private @NotNull Schedule delay = Schedule.none();
        private @NotNull Schedule repeat = Schedule.none();
        private boolean async = true;

        private @NotNull BooleanSupplier cancelWhen = () -> false;
        private int cancelAfter = -1;

        BuilderImpl(@NotNull Runnable runnable) {
            this.runnable = runnable;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder sync() {
            this.async = false;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder async() {
            this.async = true;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder delay(@NotNull Schedule delay) {
            this.delay = delay;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder repeat(@NotNull Schedule repeat) {
            this.repeat = repeat;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder cancelWhen(@NotNull BooleanSupplier cancelWhen) {
            this.cancelWhen = cancelWhen;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Builder cancelAfter(int cancelAfter) {
            this.cancelAfter = cancelAfter;
            return this;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Task build() {
            return new TaskImpl(this.runnable, this.delay, this.repeat, this.async, this.cancelWhen, this.cancelAfter);
        }

    }

}
