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
package dev.hypera.chameleon.platforms.velocity.managers;

import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.managers.CommandManager;
import dev.hypera.chameleon.platforms.velocity.VelocityChameleon;
import dev.hypera.chameleon.platforms.velocity.commands.VelocityCommand;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity command manager
 */
public final class VelocityCommandManager extends CommandManager {

	private final @NotNull VelocityChameleon chameleon;

	public VelocityCommandManager(@NotNull VelocityChameleon chameleon) {
		super(chameleon);
		this.chameleon = chameleon;
	}

	@Override
	protected void registerCommand(@NotNull Command command) {
		chameleon.getVelocityPlugin().getServer().getCommandManager().register(
				chameleon.getVelocityPlugin().getServer().getCommandManager().metaBuilder(command.getName())
						.aliases(command.getAliases().toArray(new String[0]))
						.plugin(chameleon.getVelocityPlugin())
						.build(),
				new VelocityCommand(chameleon, command)
		);
	}

	@Override
	protected void unregisterCommand(@NotNull Command command) {
		chameleon.getVelocityPlugin().getServer().getCommandManager().unregister(command.getName());
	}

}
