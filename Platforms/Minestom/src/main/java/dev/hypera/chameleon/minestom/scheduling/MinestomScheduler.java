/*
 * Chameleon - Cross-platform Minecraft plugin framework
 * Copyright (c) 2021 Joshua Sing <joshua@hypera.dev>
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

package dev.hypera.chameleon.minestom.scheduling;

import dev.hypera.chameleon.core.scheduling.Scheduler;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;
import net.minestom.server.MinecraftServer;

public class MinestomScheduler implements Scheduler {

	@Override
	public void runAsync(Runnable task) {
		MinecraftServer.getSchedulerManager().buildTask(task).build().run();
	}

	@Override
	public void scheduleAsyncTask(Runnable task, long delay, TimeUnit unit) {
		MinecraftServer.getSchedulerManager().buildTask(task).delay(delay, unit.toChronoUnit()).build().run();
	}

	@Override
	public void scheduleRepeatingAsyncTask(Runnable task, long delay, long period, TimeUnit unit) {
		MinecraftServer.getSchedulerManager().buildTask(task).delay(delay, unit.toChronoUnit()).repeat(period, unit.toChronoUnit()).build().run();
	}

}
