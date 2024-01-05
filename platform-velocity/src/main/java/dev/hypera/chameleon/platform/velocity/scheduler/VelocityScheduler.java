/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.platform.velocity.scheduler;

import dev.hypera.chameleon.platform.velocity.VelocityChameleon;
import dev.hypera.chameleon.scheduler.Schedule;
import dev.hypera.chameleon.scheduler.ScheduledTask;
import dev.hypera.chameleon.scheduler.Scheduler;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity scheduler implementation.
 */
@Internal
public final class VelocityScheduler extends Scheduler {

    private final @NotNull VelocityChameleon chameleon;

    /**
     * Velocity scheduler constructor.
     *
     * @param chameleon Velocity Chameleon implementation.
     */
    @Internal
    public VelocityScheduler(@NotNull VelocityChameleon chameleon) {
        this.chameleon = chameleon;
    }

    @Override
    protected @NotNull ScheduledTask scheduleAsyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat) {
        com.velocitypowered.api.scheduler.ScheduledTask scheduledTask = this.chameleon.getPlatformPlugin().getServer().getScheduler()
            .buildTask(this.chameleon.getPlatformPlugin(), task)
            .delay(delay.toMillis(), TimeUnit.MILLISECONDS)
            .repeat(delay.toMillis(), TimeUnit.MILLISECONDS)
            .schedule();

        return scheduledTask::cancel;
    }

    @Override
    protected @NotNull ScheduledTask scheduleSyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat) {
        // Velocity does not support synchronous tasks.
        return scheduleAsyncTask(task, delay, repeat);
    }

}
