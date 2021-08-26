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

package dev.hypera.chameleon.core.objects;

import dev.hypera.chameleon.core.data.IPlatformData.PlatformType;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.core.users.ProxyUser;
import dev.hypera.chameleon.core.users.ServerUser;
import java.util.Arrays;
import java.util.List;

public enum Platform {

	SERVER(ServerUser.class, PlatformType.SERVER),
	PROXY(ProxyUser.class, PlatformType.PROXY),
	ALL(ChatUser.class, PlatformType.SERVER, PlatformType.PROXY);

	private final Class<? extends ChatUser> userClass;
	private final List<PlatformType> platforms;

	Platform(Class<? extends ChatUser> userClass, PlatformType... platformTypes) {
		this.userClass = userClass;
		this.platforms = Arrays.asList(platformTypes);
	}

	public Class<? extends ChatUser> getUserClass() {
		return userClass;
	}

	public List<PlatformType> getPlatformTypes() {
		return platforms;
	}

}
