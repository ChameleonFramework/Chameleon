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

import dev.hypera.chameleon.scheduling.TaskImpl.BuilderImpl;
import java.util.function.BooleanSupplier;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Task.
 */
@FunctionalInterface
public interface Task {

    /**
     * Create a new task builder.
     *
     * @param runnable Task runnable.
     *
     * @return new builder.
     */
    static @NotNull Builder builder(@NotNull Runnable runnable) {
        return new BuilderImpl(runnable);
    }

    /**
     * Create a new synchronous task.
     *
     * @param runnable Task runnable.
     *
     * @return new task.
     */
    static @NotNull Task sync(@NotNull Runnable runnable) {
        return builder(runnable).sync().build();
    }

    /**
     * Create a new asynchronous task.
     *
     * @param runnable Task runnable.
     *
     * @return new task.
     */
    static @NotNull Task async(@NotNull Runnable runnable) {
        return builder(runnable).async().build();
    }


    /**
     * Execute this task.
     */
    void run();

    /**
     * Get whether this task should be executed asynchronously.
     *
     * @return asynchronous execution.
     */
    default boolean isAsync() {
        return true;
    }


    /**
     * Task builder.
     */
    @NonExtendable
    interface Builder {

        /**
         * Execute this task synchronously.
         *
         * @return {@code this}.
         */
        @Contract("-> this")
        @NotNull Builder sync();

        /**
         * Execute this task asynchronously.
         *
         * @return {@code this}.
         */
        @Contract("-> this")
        @NotNull Builder async();

        /**
         * Execute this task after a delay.
         *
         * @param delay Delay schedule.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder delay(@NotNull Schedule delay);

        /**
         * Execute this task repeatedly.
         *
         * @param repeat Repeat schedule.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder repeat(@NotNull Schedule repeat);

        /**
         * Cancel this task when this the supplier returns {@code true}.
         *
         * @param cancelWhen Cancel when.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder cancelWhen(@NotNull BooleanSupplier cancelWhen);

        /**
         * Cancel after {@code cancelAfter} executions.
         *
         * @param cancelAfter Cancel after.
         *
         * @return {@code this}.
         */
        @Contract("_ -> this")
        @NotNull Builder cancelAfter(int cancelAfter);

        /**
         * Build.
         *
         * @return new {@link Task} instance.
         */
        @Contract(value = "-> new", pure = true)
        @NotNull Task build();

    }

}
