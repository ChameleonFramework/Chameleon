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

package dev.hypera.chameleon.platforms.spigot.adventure;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.platforms.spigot.user.SpigotUsers;
import java.util.UUID;
import java.util.function.Predicate;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class SpigotAudienceProvider implements ChameleonAudienceProvider {

	private final @NotNull Chameleon chameleon;
	private final @NotNull BukkitAudiences adventure;

	public SpigotAudienceProvider(@NotNull Chameleon chameleon, @NotNull JavaPlugin plugin) {
		this.chameleon = chameleon;
		this.adventure = BukkitAudiences.create(plugin);
	}

	@Override
	public @NotNull Audience all() {
		return adventure.all();
	}

	@Override
	public @NotNull Audience console() {
		return adventure.console();
	}

	@Override
	public @NotNull Audience players() {
		return adventure.players();
	}

	@Override
	public @NotNull Audience player(@NotNull UUID playerId) {
		return adventure.player(playerId);
	}

	@Override
	public @NotNull Audience filter(@NotNull Predicate<ChatUser> filter) {
		return adventure.filter(c -> filter.test(SpigotUsers.wrap(chameleon, c)));
	}

	@Override
	public @NotNull Audience permission(@NotNull String permission) {
		return adventure.permission(permission);
	}

	@Override
	public @NotNull Audience world(@NotNull Key world) {
		return adventure.world(world);
	}

	@Override
	public @NotNull Audience server(@NotNull String serverName) {
		return adventure.server(serverName);
	}

	@Override
	public @NotNull ComponentFlattener flattener() {
		return adventure.flattener();
	}

	@Override
	public void close() {
		adventure.close();
	}

}
