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

@Internal
public class ScheduleImpl {

	static @NotNull Schedule NEXT_TICK = new TickSchedule(1);
	static @NotNull Schedule NONE = () -> Type.NONE;


	@Internal
	public static class DurationSchedule implements Schedule {

		private final @NotNull Duration duration;

		public DurationSchedule(@NotNull Duration duration) {
			this.duration = duration;
		}

		@Override
		public @NotNull Type getType() {
			return Type.DURATION;
		}

		public @NotNull Duration getDuration() {
			return duration;
		}

	}

	@Internal
	public static class TickSchedule implements Schedule {

		private final int ticks;

		public TickSchedule(int ticks) {
			this.ticks = ticks;
		}

		@Override
		public @NotNull Type getType() {
			return Type.TICK;
		}

		public int getTicks() {
			return ticks;
		}

	}

}
