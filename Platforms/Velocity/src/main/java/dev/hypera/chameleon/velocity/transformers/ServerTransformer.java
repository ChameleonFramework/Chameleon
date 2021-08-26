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

package dev.hypera.chameleon.velocity.transformers;

import com.velocitypowered.api.proxy.server.RegisteredServer;
import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.objects.Server;
import dev.hypera.chameleon.core.transformers.ITransformer;
import dev.hypera.chameleon.velocity.objects.VelocityServer;

public class ServerTransformer implements ITransformer<RegisteredServer, Server> {

	@Override
	public Server transform(Chameleon chameleon, RegisteredServer original) {
		return new VelocityServer(original);
	}

	@Override
	public Server transformWithData(Chameleon chameleon, RegisteredServer original, String... data) {
		return new VelocityServer(original);
	}

	@Override
	public Class<RegisteredServer> getFrom() {
		return RegisteredServer.class;
	}

	@Override
	public Class<Server> getTo() {
		return Server.class;
	}

}
