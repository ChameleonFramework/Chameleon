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
package dev.hypera.chameleon.scheduling;

import java.util.function.Consumer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Task.
 */
public interface Task {

    /**
     * {@link Task} builder.
     */
    class Builder {

        private final @NotNull Consumer<TaskImpl> schedule;
        private final @NotNull Runnable runnable;

        private @NotNull Type type = Type.ASYNC;
        private @NotNull Schedule delay = Schedule.none();
        private @NotNull Schedule repeat = Schedule.none();

        /**
         * {@link Builder} constructor.
         *
         * @param schedule {@link TaskImpl} schedule consumer.
         * @param runnable Task {@link Runnable}.
         */
        @Internal
        public Builder(@NotNull Consumer<TaskImpl> schedule, @NotNull Runnable runnable) {
            this.schedule = schedule;
            this.runnable = runnable;
        }

        /**
         * Set Task {@link Type}.
         *
         * @param type {@link Type}.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        public @NotNull Builder type(@NotNull Type type) {
            this.type = type;
            return this;
        }

        /**
         * Set Task delay.
         *
         * @param delay Task delay {@link Schedule}.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        public @NotNull Builder delay(@NotNull Schedule delay) {
            this.delay = delay;
            return this;
        }

        /**
         * Set Task repeat.
         *
         * @param repeat Task repeat {@link Schedule}.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        public @NotNull Builder repeat(@NotNull Schedule repeat) {
            this.repeat = repeat;
            return this;
        }

        /**
         * Build new {@link Schedule}.
         */
        public void build() {
            this.schedule.accept(new TaskImpl(this.runnable, this.type, this.delay, this.repeat));
        }

    }

    /**
     * Task type.
     */
    enum Type {
        SYNC, ASYNC
    }

}
