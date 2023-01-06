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
package dev.hypera.chameleon.platform.bukkit.managers;

import dev.hypera.chameleon.platform.bukkit.BukkitChameleon;
import dev.hypera.chameleon.scheduling.Schedule;
import dev.hypera.chameleon.scheduling.ScheduledTask;
import dev.hypera.chameleon.scheduling.Scheduler;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit {@link Scheduler} implementation.
 */
@Internal
public final class BukkitScheduler extends Scheduler {

    private static final int CRAFT_NO_REPEATING = -1;
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

    @Override
    protected @NotNull ScheduledTask scheduleAsyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimerAsynchronously(
            this.chameleon.getPlatformPlugin(),
            task, delay.toTicks(), repeat.toTicks() < 1 ? CRAFT_NO_REPEATING : repeat.toTicks()
        );

        return bukkitTask::cancel;
    }

    @Override
    protected @NotNull ScheduledTask scheduleSyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat) {
        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskTimer(
            this.chameleon.getPlatformPlugin(),
            task, delay.toTicks(), repeat.toTicks() < 1 ? CRAFT_NO_REPEATING : repeat.toTicks()
        );

        return bukkitTask::cancel;
    }

}
