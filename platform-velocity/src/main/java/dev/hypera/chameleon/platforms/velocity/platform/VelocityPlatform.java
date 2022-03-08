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
package dev.hypera.chameleon.platforms.velocity.platform;

import dev.hypera.chameleon.core.platform.proxy.ProxyPlatform;
import dev.hypera.chameleon.core.platform.proxy.Server;
import dev.hypera.chameleon.platforms.velocity.VelocityChameleon;
import dev.hypera.chameleon.platforms.velocity.platform.objects.VelocityServer;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity platform
 */
public final class VelocityPlatform extends ProxyPlatform {

	private final @NotNull VelocityChameleon chameleon;

	public VelocityPlatform(@NotNull VelocityChameleon chameleon) {
		this.chameleon = chameleon;
	}


	@Override
	public @NotNull String getAPIName() {
		return "Velocity";
	}

	@Override
	public @NotNull String getName() {
		return chameleon.getVelocityPlugin().getServer().getVersion().getName();
	}

	@Override
	public @NotNull String getVersion() {
		return chameleon.getVelocityPlugin().getServer().getVersion().getVersion();
	}

	@Override
	public @NotNull Type getType() {
		return Type.PROXY;
	}


	@Override
	public @NotNull Set<Server> getServers() {
		return chameleon.getVelocityPlugin().getServer().getAllServers().stream().map(s -> new VelocityServer(chameleon, s)).collect(Collectors.toSet());
	}

}
