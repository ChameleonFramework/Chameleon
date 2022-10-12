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
package dev.hypera.chameleon.scheduling;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.hypera.chameleon.scheduling.objects.TestScheduler;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

final class SchedulerTests {

    @Test
    void scheduleConversion() {
        assertEquals(Duration.ofHours(1), Schedule.hours(1).toDuration());
        assertEquals(Duration.ofSeconds(60), Schedule.minutes(1).toDuration());
        assertEquals(1000, Schedule.seconds(1).toMillis());
        assertEquals(100, Schedule.ticks(2).toMillis());

        // We need to round the schedule to nearest for ticks.
        assertEquals(2, Schedule.millis(120).toTicks());
    }

    @Test
    void handlesMissingScheduledTask() {
        AtomicInteger executions = new AtomicInteger(0);

        Task task = Task.builder(executions::getAndIncrement)
            .sync().cancelAfter(1).build();

        task.run();
        assertTrue(((TaskImpl) task).isCancelled());
        assertEquals(1, executions.get());

        task.run();
        assertEquals(1, executions.get());
    }

    @Test
    void cancelsAfter() {
        TestScheduler scheduler = new TestScheduler();
        AtomicInteger executions = new AtomicInteger(0);

        scheduler.schedule(
            Task.builder(executions::getAndIncrement)
                .cancelAfter(1).build()
        );

        scheduler.execute();
        assertEquals(1, executions.get());

        scheduler.execute();
        assertEquals(1, executions.get());
        assertEquals(0, scheduler.getTaskCount());
    }

    @Test
    void cancelsWhen() {
        TestScheduler scheduler = new TestScheduler();
        AtomicInteger executions = new AtomicInteger(0);

        scheduler.schedule(
            Task.builder(executions::getAndIncrement)
                .cancelWhen(() -> executions.get() == 1)
                .build()
        );

        scheduler.execute();
        assertEquals(1, executions.get());

        scheduler.execute();
        assertEquals(1, executions.get());
        assertEquals(0, scheduler.getTaskCount());
    }

}
