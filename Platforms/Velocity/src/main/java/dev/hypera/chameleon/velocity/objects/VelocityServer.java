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

package dev.hypera.chameleon.velocity.objects;

import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import com.velocitypowered.api.proxy.server.ServerInfo;
import dev.hypera.chameleon.core.objects.Server;
import dev.hypera.chameleon.core.users.ProxyUser;
import dev.hypera.chameleon.velocity.VelocityChameleon;
import dev.hypera.chameleon.velocity.users.VelocityUserManager;
import java.net.SocketAddress;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class VelocityServer implements Server {

	private final RegisteredServer server;

	public VelocityServer(RegisteredServer server) {
		this.server = server;
	}

	@Override
	public @NotNull String getName() {
		return server.getServerInfo().getName();
	}

	@Override
	public @NotNull SocketAddress getSocketAddress() {
		return server.getServerInfo().getAddress();
	}

	@Override
	public @NotNull Set<ProxyUser> getPlayers() {
		return server.getPlayersConnected().stream().map(p -> (ProxyUser) VelocityUserManager.getUser(p)).collect(Collectors.toSet());
	}

	@Override
	public @Nullable String getMotd() {
		return null;
	}

	@Override
	public void sendData(@NotNull String channel, byte[] data) {
		server.sendPluginMessage(MinecraftChannelIdentifier.from(channel), data);
	}

	public RegisteredServer getServer() {
		return server;
	}

}
