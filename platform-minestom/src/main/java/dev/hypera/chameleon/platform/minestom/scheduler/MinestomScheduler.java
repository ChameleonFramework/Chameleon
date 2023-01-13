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
package dev.hypera.chameleon.platform.minestom.scheduler;

import dev.hypera.chameleon.scheduler.Schedule;
import dev.hypera.chameleon.scheduler.ScheduledTask;
import dev.hypera.chameleon.scheduler.Scheduler;
import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.ExecutionType;
import net.minestom.server.timer.Task;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom scheduler implementation.
 */
@Internal
public final class MinestomScheduler extends Scheduler {

    @Override
    protected @NotNull ScheduledTask scheduleAsyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat) {
        Task scheduledTask = MinecraftServer.getSchedulerManager().buildTask(task)
            .executionType(ExecutionType.ASYNC).delay(delay.toDuration())
            .repeat(repeat.toDuration()).schedule();

        return scheduledTask::cancel;
    }

    @Override
    protected @NotNull ScheduledTask scheduleSyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat) {
        Task scheduledTask = MinecraftServer.getSchedulerManager().buildTask(task)
            .executionType(ExecutionType.SYNC).delay(delay.toDuration())
            .repeat(repeat.toDuration()).schedule();

        return scheduledTask::cancel;
    }

}
