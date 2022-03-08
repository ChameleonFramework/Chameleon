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

import java.util.function.Consumer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Task {

	class Builder {

		private final @NotNull Consumer<TaskImpl> schedule;
		private final @NotNull Runnable runnable;

		private @NotNull Type type = Type.ASYNC;
		private @NotNull Schedule delay = Schedule.none();
		private @NotNull Schedule repeat = Schedule.none();

		@Internal
		public Builder(@NotNull Consumer<TaskImpl> schedule, @NotNull Runnable runnable) {
			this.schedule = schedule;
			this.runnable = runnable;
		}

		@Contract("_ -> this")
		public @NotNull Builder type(@NotNull Type type) {
			this.type = type;
			return this;
		}

		@Contract("_ -> this")
		public @NotNull Builder delay(@NotNull Schedule delay) {
			this.delay = delay;
			return this;
		}

		@Contract("_ -> this")
		public @NotNull Builder repeat(@NotNull Schedule repeat) {
			this.repeat = repeat;
			return this;
		}

		public void build() {
			schedule.accept(new TaskImpl(runnable, type, delay, repeat));
		}

	}

	enum Type {
		SYNC, ASYNC
	}

}
