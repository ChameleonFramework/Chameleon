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
package dev.hypera.chameleon.platforms.spigot.managers;

import dev.hypera.chameleon.core.managers.Scheduler;
import dev.hypera.chameleon.platforms.spigot.SpigotChameleon;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

/**
 * Spigot scheduler
 */
public final class SpigotScheduler extends Scheduler {

	private final @NotNull SpigotChameleon chameleon;

	public SpigotScheduler(@NotNull SpigotChameleon chameleon) {
		this.chameleon = chameleon;
	}


	@Override
	public void schedule(@NotNull Runnable runnable) {
		Bukkit.getScheduler().runTaskAsynchronously(chameleon.getSpigotPlugin(), runnable);
	}

	@Override
	public void schedule(@NotNull Runnable runnable, long delay, @NotNull TimeUnit unit) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(chameleon.getSpigotPlugin(), runnable, unit.toSeconds(delay) * 20);
	}

	@Override
	public void scheduleRepeating(@NotNull Runnable runnable, long delay, long period, @NotNull TimeUnit unit) {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(chameleon.getSpigotPlugin(), () -> schedule(runnable), unit.toSeconds(delay) * 20, unit.toSeconds(period) * 20);
	}

}
