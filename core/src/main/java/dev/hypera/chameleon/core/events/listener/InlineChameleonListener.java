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

package dev.hypera.chameleon.core.events.listener;

import dev.hypera.chameleon.core.events.ChameleonEvent;
import dev.hypera.chameleon.core.events.listener.annotations.EventHandler;
import java.util.function.Consumer;
import org.jetbrains.annotations.NotNull;

public class InlineChameleonListener<T extends ChameleonEvent> implements ChameleonListener {

	private final @NotNull Class<T> type;
	private final @NotNull Consumer<T> listener;

	public InlineChameleonListener(@NotNull Class<T> type, @NotNull Consumer<T> listener) {
		this.type = type;
		this.listener = listener;
	}


	@EventHandler
	public void onEvent(@NotNull ChameleonEvent event) {
		if (type.isInstance(event)) {
			listener.accept(type.cast(event));
		}
	}

}
