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

package dev.hypera.chameleon.platforms.spigot.user;

import dev.hypera.chameleon.core.adventure.AbstractAudience;
import dev.hypera.chameleon.core.platform.server.GameMode;
import dev.hypera.chameleon.core.users.platforms.ServerUser;
import dev.hypera.chameleon.platforms.spigot.SpigotChameleon;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * Spigot user
 */
public class SpigotUser extends AbstractAudience implements ServerUser {

	private final @NotNull SpigotChameleon chameleon;
	private final @NotNull Player player;

	public SpigotUser(@NotNull SpigotChameleon chameleon, @NotNull Player player) {
		super(chameleon.getAdventure().player(player.getUniqueId()));
		this.chameleon = chameleon;
		this.player = player;
	}


	@Override
	public @NotNull String getName() {
		return player.getName();
	}

	@Override
	public @NotNull UUID getUniqueId() {
		return player.getUniqueId();
	}

	@Override
	public int getPing() {
		return player.getPing();
	}

	@Override
	public void chat(@NotNull String message) {
		player.chat(message);
	}

	@Override
	public void sendData(@NotNull String channel, byte[] data) {
		player.sendPluginMessage(chameleon.getSpigotPlugin(), channel, data);
	}

	@Override
	public boolean hasPermission(@NotNull String permission) {
		return player.hasPermission(permission);
	}

	@Override
	public @NotNull GameMode getGameMode() {
		return GameMode.valueOf(player.getGameMode().name());
	}

	@Override
	public void setGameMode(@NotNull GameMode gameMode) {
		player.setGameMode(org.bukkit.GameMode.valueOf(gameMode.name()));
	}

}
