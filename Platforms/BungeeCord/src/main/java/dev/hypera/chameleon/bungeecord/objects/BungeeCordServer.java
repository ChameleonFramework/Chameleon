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

package dev.hypera.chameleon.bungeecord.objects;

import dev.hypera.chameleon.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.bungeecord.users.BungeeCordUserManager;
import dev.hypera.chameleon.core.objects.Server;
import dev.hypera.chameleon.core.users.ProxyUser;
import java.net.SocketAddress;
import java.util.Set;
import java.util.stream.Collectors;
import net.md_5.bungee.api.config.ServerInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BungeeCordServer implements Server {

	private final BungeeCordChameleon chameleon;
	private final ServerInfo server;

	public BungeeCordServer(BungeeCordChameleon chameleon, ServerInfo server) {
		this.chameleon = chameleon;
		this.server = server;
	}

	@Override
	public @NotNull String getName() {
		return server.getName();
	}

	@Override
	public @NotNull SocketAddress getSocketAddress() {
		return server.getSocketAddress();
	}

	@Override
	public @NotNull Set<ProxyUser> getPlayers() {
		return server.getPlayers().stream().map(p -> (ProxyUser) BungeeCordUserManager.getUser(chameleon, p)).collect(Collectors.toSet());
	}

	@Override
	public @Nullable String getMotd() {
		return server.getMotd();
	}

	@Override
	public void sendData(@NotNull String channel, byte[] data) {
		server.sendData(channel, data);
	}

	public ServerInfo getServer() {
		return server;
	}

}
