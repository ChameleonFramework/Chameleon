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
package dev.hypera.chameleon.platform.sponge.managers;

import dev.hypera.chameleon.platform.sponge.SpongeChameleon;
import dev.hypera.chameleon.scheduling.Schedule;
import dev.hypera.chameleon.scheduling.ScheduledTask;
import dev.hypera.chameleon.scheduling.Scheduler;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

/**
 * Sponge {@link Scheduler} implementation.
 */
public class SpongeScheduler extends Scheduler {

    private final @NotNull SpongeChameleon chameleon;

    /**
     * {@link SpongeScheduler} constructor.
     *
     * @param chameleon {@link SpongeChameleon} instance.
     */
    @Internal
    public SpongeScheduler(@NotNull SpongeChameleon chameleon) {
        this.chameleon = chameleon;
    }

    @Override
    protected @NotNull ScheduledTask scheduleAsyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat) {
        org.spongepowered.api.scheduler.ScheduledTask scheduledTask = Sponge.asyncScheduler().submit(
            Task.builder().execute(task)
                .delay(delay.toMillis(), TimeUnit.MILLISECONDS)
                .interval(repeat.toMillis(), TimeUnit.MILLISECONDS)
                .plugin(this.chameleon.getPlatformPlugin().getPluginContainer())
                .build()
        );

        return scheduledTask::cancel;
    }

    @Override
    protected @NotNull ScheduledTask scheduleSyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat) {
        // Sponge does not support synchronous tasks.
        return scheduleAsyncTask(task, delay, repeat);
    }

}
