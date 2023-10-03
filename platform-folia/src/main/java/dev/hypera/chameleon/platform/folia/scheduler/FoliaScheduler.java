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
package dev.hypera.chameleon.platform.folia.scheduler;

import dev.hypera.chameleon.scheduler.Schedule;
import dev.hypera.chameleon.scheduler.ScheduledTask;
import dev.hypera.chameleon.scheduler.Scheduler;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Folia scheduler implementation.
 */
@Internal
public final class FoliaScheduler extends Scheduler {

    private final @NotNull JavaPlugin plugin;

    /**
     * Folia scheduler constructor.
     *
     * @param plugin Folia plugin.
     */
    @Internal
    public FoliaScheduler(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    protected @NotNull ScheduledTask scheduleAsyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat) {
        AsyncScheduler scheduler = Bukkit.getAsyncScheduler();
        long period = repeat.toMillis();
        io.papermc.paper.threadedregions.scheduler.ScheduledTask foliaTask = period > 0
            ? scheduler.runAtFixedRate(this.plugin, t -> task.run(), delay.toMillis(), period, TimeUnit.MILLISECONDS)
            : scheduler.runDelayed(this.plugin, t -> task.run(), delay.toMillis(), TimeUnit.MILLISECONDS);
        return foliaTask::cancel;
    }

    @Override
    protected @NotNull ScheduledTask scheduleSyncTask(@NotNull Runnable task, @NotNull Schedule delay, @NotNull Schedule repeat) {
        GlobalRegionScheduler scheduler = Bukkit.getGlobalRegionScheduler();
        long period = repeat.toTicks();
        io.papermc.paper.threadedregions.scheduler.ScheduledTask foliaTask = period > 0
            ? scheduler.runAtFixedRate(this.plugin, t -> task.run(), delay.toTicks(), period)
            : scheduler.runDelayed(this.plugin, t -> task.run(), delay.toTicks());
        return foliaTask::cancel;
    }

}
