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
package dev.hypera.chameleon.core.scheduling;

import dev.hypera.chameleon.core.scheduling.Schedule.Type;
import java.time.Duration;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Schedule} implementations.
 */
@Internal
public final class ScheduleImpl {

    static @NotNull Schedule NEXT_TICK = new TickSchedule(1);
    static @NotNull Schedule NONE = () -> Type.NONE;

    private ScheduleImpl() {

    }

    /**
     * {@link Schedule} {@link Duration} implementation.
     *
     * @see Schedule#duration(Duration)
     */
    @Internal
    public final static class DurationSchedule implements Schedule {

        private final @NotNull Duration duration;

        /**
         * {@link DurationSchedule} constructor.
         *
         * @param duration {@link Duration} between runs.
         */
        public DurationSchedule(@NotNull Duration duration) {
            this.duration = duration;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Type getType() {
            return Type.DURATION;
        }

        /**
         * Get {@link Duration}.
         *
         * @return {@link Duration}.
         */
        public @NotNull Duration getDuration() {
            return this.duration;
        }

    }

    /**
     * {@link Schedule} tick implementation.
     *
     * @see Schedule#tick(int)
     */
    @Internal
    public final static class TickSchedule implements Schedule {

        private final int ticks;

        /**
         * {@link TickSchedule} constructor.
         *
         * @param ticks Ticks between runs.
         */
        public TickSchedule(int ticks) {
            this.ticks = ticks;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public @NotNull Type getType() {
            return Type.TICK;
        }

        /**
         * Get ticks.
         *
         * @return ticks.
         */
        public int getTicks() {
            return this.ticks;
        }

    }

}
