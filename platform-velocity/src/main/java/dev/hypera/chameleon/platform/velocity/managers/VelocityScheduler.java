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
package dev.hypera.chameleon.platform.velocity.managers;

import dev.hypera.chameleon.managers.Scheduler;
import dev.hypera.chameleon.platform.velocity.VelocityChameleon;
import dev.hypera.chameleon.scheduling.Schedule;
import dev.hypera.chameleon.scheduling.ScheduleImpl.DurationSchedule;
import dev.hypera.chameleon.scheduling.ScheduleImpl.TickSchedule;
import dev.hypera.chameleon.scheduling.TaskImpl;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity {@link Scheduler} implementation.
 */
@Internal
public final class VelocityScheduler extends Scheduler {

    private final @NotNull VelocityChameleon chameleon;

    /**
     * {@link VelocityScheduler} constructor.
     *
     * @param chameleon {@link VelocityChameleon} instance.
     */
    @Internal
    public VelocityScheduler(@NotNull VelocityChameleon chameleon) {
        this.chameleon = chameleon;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected void schedule(@NotNull TaskImpl task) {
        this.chameleon.getVelocityPlugin().getServer().getScheduler().buildTask(this.chameleon.getVelocityPlugin(), task.getRunnable()).delay(convert(task.getDelay()), TimeUnit.MILLISECONDS).repeat(convert(task.getRepeat()), TimeUnit.MILLISECONDS).schedule();
    }


    private long convert(@NotNull Schedule schedule) {
        if (schedule.getType().equals(Schedule.Type.NONE)) {
            return 0;
        } else if (schedule.getType().equals(Schedule.Type.DURATION)) {
            return ((DurationSchedule) schedule).getDuration().toMillis();
        } else if (schedule.getType().equals(Schedule.Type.TICK)) {
            return (long) ((TickSchedule) schedule).getTicks() * 50;
        } else {
            throw new UnsupportedOperationException("Cannot convert scheduler type '" + schedule.getType() + "'");
        }
    }

}
