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

import com.velocitypowered.api.proxy.Player;
import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.transformers.ITransformer;
import java.util.UUID;

/**
 * Transforms a player to a UUID.
 */
public class PlayerUUIDTransformer implements ITransformer<Player, UUID> {

	@Override
	public UUID transform(Chameleon chameleon, Player original) {
		return original.getUniqueId();
	}

	@Override
	public UUID transformWithData(Chameleon chameleon, Player original, String... data) {
		return original.getUniqueId();
	}

	@Override
	public Class<Player> getFrom() {
		return Player.class;
	}

	@Override
	public Class<UUID> getTo() {
		return UUID.class;
	}

}
