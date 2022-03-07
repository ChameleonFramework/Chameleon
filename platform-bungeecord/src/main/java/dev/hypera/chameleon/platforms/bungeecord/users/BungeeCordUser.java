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
package dev.hypera.chameleon.platforms.bungeecord.users;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.adventure.AbstractAudience;
import dev.hypera.chameleon.core.platform.proxy.Server;
import dev.hypera.chameleon.core.users.platforms.ProxyUser;
import dev.hypera.chameleon.platforms.bungeecord.platform.objects.BungeeCordServer;
import java.util.UUID;
import java.util.function.BiConsumer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * BungeeCord user
 */
public class BungeeCordUser extends AbstractAudience implements ProxyUser {

	private final @NotNull Chameleon chameleon;
	private final @NotNull ProxiedPlayer player;

	public BungeeCordUser(@NotNull Chameleon chameleon, @NotNull ProxiedPlayer player) {
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
		player.sendData(channel, data);
	}

	@Override
	public boolean hasPermission(@NotNull String permission) {
		return player.hasPermission(permission);
	}

	@Override
	public @Nullable Server getServer() {
		return new BungeeCordServer(chameleon, player.getServer().getInfo());
	}

	@Override
	public void connect(@NotNull Server server) {
		player.connect(((BungeeCordServer) server).getBungeeCord());
	}

	@Override
	public void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback) {
		player.connect(((BungeeCordServer) server).getBungeeCord(), callback::accept);
	}

}
