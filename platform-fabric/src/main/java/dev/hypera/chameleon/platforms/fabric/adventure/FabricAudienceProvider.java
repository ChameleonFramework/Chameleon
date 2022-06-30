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
package dev.hypera.chameleon.platforms.fabric.adventure;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.platforms.fabric.user.FabricUsers;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnknownNullability;

import java.util.UUID;
import java.util.function.Predicate;

/**
 * Fabric {@link ChameleonAudienceProvider} implementation.
 */
public final class FabricAudienceProvider implements ChameleonAudienceProvider {

    private final @NotNull Chameleon chameleon;
    private @UnknownNullability FabricServerAudiences adventure;

    /**
     * {@link FabricAudienceProvider} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     */
    @Internal
    public FabricAudienceProvider(@NotNull Chameleon chameleon) {
        this.chameleon = chameleon;
        ServerLifecycleEvents.SERVER_STARTING.register(server -> this.adventure = FabricServerAudiences.of(server));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> this.adventure = null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience filter(@NotNull Predicate<ChatUser> filter) {
        if (this.adventure == null) throw new IllegalStateException("Server is not running");
        return this.adventure.all().filterAudience(c -> filter.test(FabricUsers.wrap(this.chameleon, c)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience all() {
        if (this.adventure == null) throw new IllegalStateException("Server is not running");
        return this.adventure.all();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience console() {
        if (this.adventure == null) throw new IllegalStateException("Server is not running");
        return this.adventure.console();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience players() {
        if (this.adventure == null) throw new IllegalStateException("Server is not running");
        return this.adventure.players();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience player(@NotNull UUID playerId) {
        if (this.adventure == null) throw new IllegalStateException("Server is not running");
        return this.adventure.player(playerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience permission(@NotNull String permission) {
        if (this.adventure == null) throw new IllegalStateException("Server is not running");
        return this.adventure.permission(permission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience world(@NotNull Key world) {
        if (this.adventure == null) throw new IllegalStateException("Server is not running");
        return this.adventure.world(world);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience server(@NotNull String serverName) {
        if (this.adventure == null) throw new IllegalStateException("Server is not running");
        return this.adventure.server(serverName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ComponentFlattener flattener() {
        if (this.adventure == null) throw new IllegalStateException("Server is not running");
        return this.adventure.flattener();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        if (this.adventure == null) throw new IllegalStateException("Server is not running");
        this.adventure.close();
    }

}
