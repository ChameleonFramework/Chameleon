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
package dev.hypera.chameleon.platform.bukkit.managers;

import dev.hypera.chameleon.managers.Scheduler;
import dev.hypera.chameleon.platform.bukkit.BukkitChameleon;
import dev.hypera.chameleon.scheduling.Schedule;
import dev.hypera.chameleon.scheduling.ScheduleImpl.DurationSchedule;
import dev.hypera.chameleon.scheduling.ScheduleImpl.TickSchedule;
import dev.hypera.chameleon.scheduling.Task;
import dev.hypera.chameleon.scheduling.TaskImpl;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit {@link Scheduler} implementation.
 */
@Internal
public final class BukkitScheduler extends Scheduler {

    private final @NotNull BukkitChameleon chameleon;

    /**
     * {@link BukkitScheduler} constructor.
     *
     * @param chameleon {@link BukkitChameleon} instance.
     */
    @Internal
    public BukkitScheduler(@NotNull BukkitChameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void schedule(@NotNull TaskImpl task) {
        if (task.getRepeat().getType().equals(Schedule.Type.NONE)) {
            if (task.getDelay().getType().equals(Schedule.Type.NONE)) {
                if (task.getType().equals(Task.Type.ASYNC)) {
                    Bukkit.getScheduler().runTaskAsynchronously(this.chameleon.getPlatformPlugin(), task.getRunnable());
                } else {
                    Bukkit.getScheduler().runTask(this.chameleon.getPlatformPlugin(), task.getRunnable());
                }
            } else {
                if (task.getType().equals(Task.Type.ASYNC)) {
                    Bukkit.getScheduler().runTaskLaterAsynchronously(this.chameleon.getPlatformPlugin(), task.getRunnable(), convert(task.getDelay()));
                } else {
                    Bukkit.getScheduler().runTaskLater(this.chameleon.getPlatformPlugin(), task.getRunnable(), convert(task.getDelay()));
                }
            }
        } else {
            Bukkit.getScheduler().scheduleSyncRepeatingTask(this.chameleon.getPlatformPlugin(),
                task.getType().equals(Task.Type.ASYNC) ? () -> Bukkit.getScheduler().runTaskAsynchronously(this.chameleon.getPlatformPlugin(), task.getRunnable()) : task.getRunnable(),
                convert(task.getDelay()),
                convert(task.getRepeat())
            );
        }
    }

    private long convert(@NotNull Schedule schedule) {
        if (schedule.getType().equals(Schedule.Type.NONE)) {
            return 0;
        } else if (schedule.getType().equals(Schedule.Type.DURATION)) {
            return ((DurationSchedule) schedule).getDuration().toMillis() / 50;
        } else if (schedule.getType().equals(Schedule.Type.TICK)) {
            return ((TickSchedule) schedule).getTicks();
        } else {
            throw new UnsupportedOperationException("Cannot convert scheduler type '" + schedule.getType() + "'");
        }
    }

}
