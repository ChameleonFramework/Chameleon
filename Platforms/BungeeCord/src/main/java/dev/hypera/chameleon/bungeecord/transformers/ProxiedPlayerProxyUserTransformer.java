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

package dev.hypera.chameleon.bungeecord.transformers;

import dev.hypera.chameleon.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.bungeecord.users.BungeeCordUserManager;
import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.transformers.ITransformer;
import dev.hypera.chameleon.core.users.ProxyUser;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class ProxiedPlayerProxyUserTransformer implements ITransformer<ProxiedPlayer, ProxyUser> {

	@Override
	public ProxyUser transform(Chameleon chameleon, ProxiedPlayer original) {
		return (ProxyUser) BungeeCordUserManager.getUser((BungeeCordChameleon) chameleon, original);
	}

	@Override
	public ProxyUser transformWithData(Chameleon chameleon, ProxiedPlayer original, String... data) {
		return (ProxyUser) BungeeCordUserManager.getUser((BungeeCordChameleon) chameleon, original);
	}

	@Override
	public Class<ProxiedPlayer> getFrom() {
		return ProxiedPlayer.class;
	}

	@Override
	public Class<ProxyUser> getTo() {
		return ProxyUser.class;
	}

}
