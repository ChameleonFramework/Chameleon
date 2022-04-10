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
package dev.hypera.chameleon.platforms.velocity.user;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import dev.hypera.chameleon.core.adventure.AbstractReflectedAudience;
import dev.hypera.chameleon.core.platform.proxy.Server;
import dev.hypera.chameleon.core.users.platforms.ProxyUser;
import dev.hypera.chameleon.platforms.velocity.VelocityChameleon;
import dev.hypera.chameleon.platforms.velocity.platform.objects.VelocityServer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * Velocity user
 */
public class VelocityUser extends AbstractReflectedAudience implements ProxyUser {

	private final @NotNull VelocityChameleon chameleon;
	private final @NotNull Player player;

	public VelocityUser(@NotNull VelocityChameleon chameleon, @NotNull Player player) {
		super(player);
		this.chameleon = chameleon;
		this.player = player;
	}

	@Override
	public @NotNull String getName() {
		return player.getUsername();
	}

	@Override
	public @NotNull UUID getUniqueId() {
		return player.getUniqueId();
	}

	@Override
	public int getPing() {
		return player.getPing() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) player.getPing();
	}

	@Override
	public void chat(@NotNull String message) {
		player.spoofChatInput(message);
	}

	@Override
	public void sendData(@NotNull String channel, byte[] data) {
		player.sendPluginMessage(MinecraftChannelIdentifier.from(channel), data);
	}

	@Override
	public boolean hasPermission(@NotNull String permission) {
		return player.hasPermission(permission);
	}

	@Override
	public @NotNull Optional<Server> getServer() {
		return player.getCurrentServer().map(s -> new VelocityServer(chameleon, s.getServer()));
	}

	@Override
	public void connect(@NotNull Server server) {
		player.createConnectionRequest(((VelocityServer) server).getVelocity()).fireAndForget();
	}

	@Override
	public void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback) {
		player.createConnectionRequest(((VelocityServer) server).getVelocity()).connect().whenComplete((result, ex) -> {
			callback.accept(result.isSuccessful(), ex);
		});
	}

}
