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
package dev.hypera.chameleon.core.events.impl.common;

import dev.hypera.chameleon.core.events.cancellable.AbstractCancellable;
import dev.hypera.chameleon.core.users.User;
import org.jetbrains.annotations.NotNull;

/**
 * Dispatched when a user sends a chat message
 */
public class UserChatEvent extends AbstractCancellable implements UserEvent {

	private final @NotNull User user;
	private final @NotNull String message;

	public UserChatEvent(@NotNull User user, @NotNull String message) {
		this.user = user;
		this.message = message;
	}


	public @NotNull User getUser() {
		return user;
	}

	public @NotNull String getMessage() {
		return message;
	}

}
