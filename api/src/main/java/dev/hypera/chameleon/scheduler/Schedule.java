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
package dev.hypera.chameleon.scheduler;

import java.time.Duration;
import org.jetbrains.annotations.ApiStatus.NonExtendable;
import org.jetbrains.annotations.NotNull;

/**
 * Schedule.
 */
@NonExtendable
public interface Schedule {

    /**
     * Create a new schedule that is never executed.
     *
     * @return new schedule.
     */
    static @NotNull Schedule none() {
        return ScheduleImpl.NONE;
    }

    /**
     * Create a new schedule that is executed according to the provided Duration.
     *
     * @param duration Duration.
     *
     * @return new schedule.
     */
    static @NotNull Schedule duration(@NotNull Duration duration) {
        return new ScheduleImpl(duration);
    }

    /**
     * Create a new schedule that is executed every {@code ticks} ticks.
     *
     * @param ticks Ticks between executions.
     *
     * @return new schedule.
     */
    static @NotNull Schedule ticks(long ticks) {
        // A tick occurs every 50 milliseconds.
        return duration(Duration.ofMillis(ticks * 50L));
    }

    /**
     * Create a new schedule that runs every {@code hours} hours.
     *
     * @param hours Hours between runs.
     *
     * @return new schedule.
     */
    static @NotNull Schedule hours(long hours) {
        return duration(Duration.ofHours(hours));
    }

    /**
     * Create a new schedule that runs every {@code minutes} minutes.
     *
     * @param minutes Minutes between runs.
     *
     * @return new schedule.
     */
    static @NotNull Schedule minutes(long minutes) {
        return duration(Duration.ofMinutes(minutes));
    }

    /**
     * Create a new schedule that runs every {@code seconds} seconds.
     *
     * @param seconds Seconds between runs.
     *
     * @return new schedule.
     */
    static @NotNull Schedule seconds(long seconds) {
        return duration(Duration.ofSeconds(seconds));
    }

    /**
     * Create a new schedule that runs every {@code millis} milliseconds.
     *
     * @param millis Milliseconds between runs.
     *
     * @return new schedule.
     */
    static @NotNull Schedule millis(long millis) {
        return duration(Duration.ofMillis(millis));
    }


    /**
     * Convert this schedule to ticks.
     *
     * @return ticks.
     */
    long toTicks();

    /**
     * Convert this schedule to milliseconds.
     *
     * @return milliseconds.
     */
    long toMillis();

    /**
     * Convert this schedule to a Duration.
     *
     * @return Duration.
     */
    @NotNull Duration toDuration();

}
