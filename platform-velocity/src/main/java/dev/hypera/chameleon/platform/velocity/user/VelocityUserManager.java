/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2023 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.platform.velocity.user;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import dev.hypera.chameleon.platform.user.PlatformUserManager;
import dev.hypera.chameleon.platform.velocity.VelocityChameleon;
import dev.hypera.chameleon.user.ConsoleUser;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Velocity user manager implementation.
 */
public final class VelocityUserManager extends PlatformUserManager<Player, VelocityUser> {

    private final @NotNull VelocityChameleon chameleon;
    private final @NotNull PlayerReflection playerReflection;

    /**
     * Velocity user manager constructor.
     *
     * @param chameleon Velocity Chameleon implementation.
     */
    @Internal
    public VelocityUserManager(@NotNull VelocityChameleon chameleon) {
        this.chameleon = chameleon;
        this.playerReflection = new PlayerReflection(this.chameleon.getAdventureMapper().getComponentMapper());
    }

    /**
     * Load reflection utilities.
     */
    public void load() {
        this.playerReflection.load();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull ConsoleUser createConsoleUser() {
        CommandSource consoleSource = this.chameleon.getPlatformPlugin().getServer().getConsoleCommandSource();
        return new VelocityConsoleUser(consoleSource,
            this.chameleon.getAdventureMapper().createReflectedAudience(consoleSource));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected @NotNull VelocityUser createUser(@NotNull Player player) {
        return new VelocityUser(
            this.chameleon, player,
            this.chameleon.getAdventureMapper().createReflectedAudience(player),
            this.playerReflection
        );
    }

}
