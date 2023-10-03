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
package dev.hypera.chameleon.platform.sponge.user;

import dev.hypera.chameleon.platform.sponge.SpongeChameleon;
import dev.hypera.chameleon.platform.user.PlatformUserManager;
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.user.ConsoleUser;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.service.permission.Subject;

/**
 * Sponge user manager implementation.
 */
public final class SpongeUserManager extends PlatformUserManager<ServerPlayer, SpongeUser> {

    private final @NotNull SpongeChameleon chameleon;
    private final @NotNull PlayerReflection playerReflection;
    private final @NotNull SpongeUserManager.Listener listener = new SpongeUserManager.Listener();

    /**
     * Sponge user manager constructor.
     *
     * @param chameleon Sponge Chameleon implementation.
     */
    @Internal
    public SpongeUserManager(@NotNull SpongeChameleon chameleon) {
        this.chameleon = chameleon;
        this.playerReflection = new PlayerReflection(this.chameleon.getAdventureMapper()
            .getComponentMapper());
    }

    /**
     * Registers the platform listeners.
     */
    public void registerListeners() {
        Sponge.eventManager().registerListeners(
            this.chameleon.getPlatformPlugin().getPluginContainer(),
            this.listener
        );
    }

    /**
     * Unregisters the platform listeners.
     */
    public void unregisterListeners() {
        Sponge.eventManager().unregisterListeners(this.listener);
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
        return new SpongeConsoleUser(this.chameleon.getAdventureMapper()
            .createReflectedAudience(Sponge.game().systemSubject()));
    }

    /**
     * Returns an implementation of user for the given platform user.
     *
     * @param player Platform player.
     *
     * @return user.
     */
    @Override
    protected @NotNull SpongeUser createUser(@NotNull ServerPlayer player) {
        return new SpongeUser(player, this.chameleon.getAdventureMapper()
            .createReflectedAudience(player), this.playerReflection);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChatUser wrap(@NotNull Object obj) {
        if (obj instanceof ServerPlayer) {
            return getUserOrThrow(((ServerPlayer) obj).uniqueId());
        }
        if (obj instanceof Subject) {
            return getConsole();
        }
        throw new IllegalArgumentException("cannot return a chat user representing the given object");
    }

    /**
     * Sponge platform listener.
     */
    @Internal
    @SuppressWarnings("unused")
    private final class Listener {

        @org.spongepowered.api.event.Listener(order = Order.EARLY)
        public void onJoinEvent(@NotNull ServerSideConnectionEvent.Join event) {
            addUser(event.player().uniqueId(), event.player());
        }

        @org.spongepowered.api.event.Listener(order = Order.LATE)
        public void onDisconnectEvent(@NotNull ServerSideConnectionEvent.Disconnect event) {
            removeUser(event.player().uniqueId());
        }

    }

}
