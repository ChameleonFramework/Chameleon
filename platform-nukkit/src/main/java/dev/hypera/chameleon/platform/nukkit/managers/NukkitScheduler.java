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
package dev.hypera.chameleon.platform.nukkit.managers;

import cn.nukkit.Server;
import dev.hypera.chameleon.managers.Scheduler;
import dev.hypera.chameleon.platform.nukkit.NukkitChameleon;
import dev.hypera.chameleon.scheduling.Schedule;
import dev.hypera.chameleon.scheduling.Schedule.Type;
import dev.hypera.chameleon.scheduling.ScheduleImpl.DurationSchedule;
import dev.hypera.chameleon.scheduling.ScheduleImpl.TickSchedule;
import dev.hypera.chameleon.scheduling.Task;
import dev.hypera.chameleon.scheduling.TaskImpl;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Nukkit {@link Scheduler} implementation.
 */
@Internal
public class NukkitScheduler extends Scheduler {

    private final @NotNull NukkitChameleon chameleon;

    /**
     * {@link NukkitScheduler} constructor.
     *
     * @param chameleon {@link NukkitChameleon} instance.
     */
    @Internal
    public NukkitScheduler(@NotNull NukkitChameleon chameleon) {
        this.chameleon = chameleon;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void schedule(@NotNull TaskImpl task) {
        if (task.getRepeat().getType().equals(Type.NONE)) {
            if (task.getDelay().getType().equals(Type.NONE)) {
                Server.getInstance().getScheduler().scheduleTask(this.chameleon.getNukkitPlugin(), task.getRunnable(), task.getType().equals(Task.Type.ASYNC));
            } else {
                Server.getInstance().getScheduler().scheduleDelayedTask(this.chameleon.getNukkitPlugin(), task.getRunnable(), convert(task.getDelay()), task.getType().equals(Task.Type.ASYNC));
            }
        } else {
            if (task.getDelay().getType().equals(Type.NONE)) {
                Server.getInstance().getScheduler().scheduleRepeatingTask(this.chameleon.getNukkitPlugin(), task.getRunnable(), convert(task.getRepeat()), task.getType().equals(Task.Type.ASYNC));
            } else {
                Server.getInstance().getScheduler().scheduleDelayedRepeatingTask(this.chameleon.getNukkitPlugin(), task.getRunnable(), convert(task.getDelay()), convert(task.getRepeat()), task.getType().equals(Task.Type.ASYNC));
            }
        }
    }

    private int convert(@NotNull Schedule schedule) {
        if (schedule.getType().equals(Type.NONE)) {
            return 0;
        } else if (schedule.getType().equals(Type.DURATION)) {
            return (int) Math.min(((DurationSchedule) schedule).getDuration().toMillis() / 50, Integer.MAX_VALUE);
        } else if (schedule.getType().equals(Type.TICK)) {
            return ((TickSchedule) schedule).getTicks();
        } else {
            throw new UnsupportedOperationException("Cannot convert scheduler type '" + schedule.getType() + "'");
        }
    }

}
