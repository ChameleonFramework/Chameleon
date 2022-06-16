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
package dev.hypera.chameleon.platforms.minestom.managers;

import dev.hypera.chameleon.core.managers.Scheduler;
import dev.hypera.chameleon.core.scheduling.Schedule;
import dev.hypera.chameleon.core.scheduling.Schedule.Type;
import dev.hypera.chameleon.core.scheduling.ScheduleImpl.DurationSchedule;
import dev.hypera.chameleon.core.scheduling.ScheduleImpl.TickSchedule;
import dev.hypera.chameleon.core.scheduling.TaskImpl;
import net.minestom.server.MinecraftServer;
import net.minestom.server.timer.ExecutionType;
import net.minestom.server.timer.TaskSchedule;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Minestom {@link Scheduler} implementation.
 */
@Internal
public final class MinestomScheduler extends Scheduler {

    /**
     * {@link MinestomScheduler} constructor.
     */
    @Internal
    public MinestomScheduler() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void schedule(@NotNull TaskImpl task) {
        MinecraftServer.getSchedulerManager().buildTask(task.getRunnable()).executionType(ExecutionType.valueOf(task.getType().name())).delay(convert(task.getDelay(), false)).repeat(convert(task.getRepeat(), true)).schedule();
    }

    private @NotNull TaskSchedule convert(@NotNull Schedule schedule, boolean repeat) {
        if (schedule.getType().equals(Type.NONE)) {
            return repeat ? TaskSchedule.stop() : TaskSchedule.immediate();
        } else if (schedule.getType().equals(Type.DURATION)) {
            return TaskSchedule.duration(((DurationSchedule) schedule).getDuration());
        } else if (schedule.getType().equals(Type.TICK)) {
            return TaskSchedule.tick(((TickSchedule) schedule).getTicks());
        } else {
            throw new UnsupportedOperationException("Cannot convert scheduler type '" + schedule.getType() + "'");
        }
    }

}
