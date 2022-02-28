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

package dev.hypera.chameleon.core.users;

import dev.hypera.chameleon.core.annotations.PlatformSpecific;
import dev.hypera.chameleon.core.platform.Platform.Type;
import dev.hypera.chameleon.core.users.permissions.IPermissionHolder;
import dev.hypera.chameleon.core.users.platforms.ProxyUser;
import dev.hypera.chameleon.core.users.platforms.ServerUser;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

/**
 * User, can be a player or console
 */
public interface ChatUser extends Audience, IPermissionHolder {

	@NotNull String getName();


	default @NotNull User user() {
		if (this instanceof User) {
			return (User) this;
		} else {
			throw new IllegalStateException("Cannot cast to User");
		}
	}

	@PlatformSpecific(Type.PROXY)
	default @NotNull ProxyUser proxy() {
		if (this instanceof ProxyUser) {
			return (ProxyUser) this;
		} else {
			throw new IllegalStateException("Cannot cast to ProxyUser");
		}
	}

	@PlatformSpecific(Type.SERVER)
	default @NotNull ServerUser server() {
		if (this instanceof ServerUser) {
			return (ServerUser) this;
		} else {
			throw new IllegalStateException("Cannot cast to ServerUser");
		}
	}

}
