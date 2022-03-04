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

package dev.hypera.chameleon.platforms.bungeecord.managers;

import dev.hypera.chameleon.core.managers.UserManager;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.core.users.User;
import dev.hypera.chameleon.platforms.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.platforms.bungeecord.users.BungeeCordConsoleUser;
import dev.hypera.chameleon.platforms.bungeecord.users.BungeeCordUser;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord user manager
 */
public final class BungeeCordUserManager extends UserManager {

	private final @NotNull BungeeCordChameleon chameleon;

	public BungeeCordUserManager(@NotNull BungeeCordChameleon chameleon) {
		this.chameleon = chameleon;
	}


	@Override
	public @NotNull ChatUser getConsole() {
		return new BungeeCordConsoleUser(chameleon);
	}

	@Override
	public @NotNull Set<User> getPlayers() {
		return ProxyServer.getInstance().getPlayers().stream().map(p -> new BungeeCordUser(chameleon, p)).collect(Collectors.toSet());
	}

	@Override
	public @NotNull User getPlayer(@NotNull UUID uniqueId) {
		ProxiedPlayer player = ProxyServer.getInstance().getPlayer(uniqueId);
		if (null == player) {
			throw new IllegalArgumentException("Cannot find user with id '" + uniqueId + "'");
		} else {
			return new BungeeCordUser(chameleon, player);
		}
	}

}
