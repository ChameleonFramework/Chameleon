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

import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Scheduler.
 */
@NonExtendable
public abstract class Scheduler {

    /**
     * Submit a task to be scheduled.
     *
     * @param task Task.
     *
     * @return scheduled task.
     */
    public final @NotNull ScheduledTask schedule(@NotNull Task task) {
        Schedule delay = task instanceof TaskImpl ? ((TaskImpl) task).getDelay() : Schedule.none();
        Schedule repeat = task instanceof TaskImpl ? ((TaskImpl) task).getRepeat() : Schedule.none();

        ScheduledTask scheduledTask;
        if (task.isAsync()) {
            scheduledTask = scheduleAsyncTask(task::run, delay, repeat);
        } else {
            scheduledTask = scheduleSyncTask(task::run, delay, repeat);
        }

        if (task instanceof TaskImpl) {
            ((TaskImpl) task).setScheduledTask(scheduledTask);
        }

        return scheduledTask;
    }

    protected abstract @NotNull ScheduledTask scheduleAsyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat);

    protected abstract @NotNull ScheduledTask scheduleSyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat);

}
