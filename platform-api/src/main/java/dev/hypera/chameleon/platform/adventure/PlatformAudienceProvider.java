/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.platform.adventure;

import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.util.Preconditions;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.jetbrains.annotations.NotNull;

/**
 * Platform audience provider implementation.
 *
 * <p>Important: This implementation supports loading the underlying audience provider at a later
 * time (e.g. after the plugin has been enabled). This implementation will act as a no-op audience
 * provider, which only returns {@code Audience.empty()} until {@code audienceProvider} has been
 * set.</p>
 *
 * <p>If Adventure does not support the underlying platform, or is directly supported by the
 * platform but requires mapping, see {@link StandaloneAudienceProvider}.</p>
 */
public abstract class PlatformAudienceProvider implements ChameleonAudienceProvider {

    protected final @NotNull AtomicReference<AudienceProvider> audienceProvider = new AtomicReference<>();

    /**
     * Gets an audience for all online players, including the server's console.
     * <p>The audience is dynamically updated as players join and leave.</p>
     *
     * @return the players' and console audience.
     */
    @Override
    public @NotNull Audience all() {
        AudienceProvider provider = this.audienceProvider.get();
        if (provider == null) {
            return Audience.empty();
        }
        return provider.all();
    }

    /**
     * Returns an audience for the server's console.
     *
     * @return the console audience.
     */
    @Override
    public @NotNull Audience console() {
        AudienceProvider provider = this.audienceProvider.get();
        if (provider == null) {
            return Audience.empty();
        }
        return provider.console();
    }

    /**
     * Returns an audience for all online players.
     * <p>The audience is dynamically updated as players join and leave.</p>
     *
     * @return the players' audience.
     */
    @Override
    public @NotNull Audience players() {
        AudienceProvider provider = this.audienceProvider.get();
        if (provider == null) {
            return Audience.empty();
        }
        return provider.players();
    }

    /**
     * Returns an audience for an individual player.
     * <p>If the player is not online, messages are silently dropped.</p>
     *
     * @param playerId a player uuid.
     *
     * @return a player audience.
     */
    @Override
    public @NotNull Audience player(@NotNull UUID playerId) {
        Preconditions.checkNotNull("playerId", playerId);
        AudienceProvider provider = this.audienceProvider.get();
        if (provider == null) {
            return Audience.empty();
        }
        return provider.player(playerId);
    }

    /**
     * Returns or creates an audience containing all viewers with the provided permission.
     * <p>The audience is dynamically updated as permissions change.</p>
     *
     * @param permission the permission to filter sending to.
     *
     * @return a permissible audience.
     */
    @Override
    public @NotNull Audience permission(@NotNull String permission) {
        Preconditions.checkNotNull("permission", permission);
        AudienceProvider provider = this.audienceProvider.get();
        if (provider == null) {
            return Audience.empty();
        }
        return provider.permission(permission);
    }

    /**
     * Returns an audience for online players in a world, including the server's console.
     * <p>The audience is dynamically updated as players join and leave.</p>
     *
     * <p>World identifiers were introduced in Minecraft 1.16. On older game instances, worlds will
     * be assigned the key {@code minecraft:<world name>}</p>
     *
     * @param world identifier for a world.
     *
     * @return the world's audience.
     */
    @Override
    public @NotNull Audience world(@NotNull Key world) {
        Preconditions.checkNotNull("world", world);
        AudienceProvider provider = this.audienceProvider.get();
        if (provider == null) {
            return Audience.empty();
        }
        return provider.world(world);
    }

    /**
     * Returns an audience for online players on a server, including the server's console.
     * <p>If the platform is not a proxy, the audience defaults to everyone.</p>
     *
     * @param serverName a server name.
     *
     * @return a server's audience.
     */
    @Override
    public @NotNull Audience server(@NotNull String serverName) {
        Preconditions.checkNotNull("serverName", serverName);
        AudienceProvider provider = this.audienceProvider.get();
        if (provider == null) {
            return Audience.empty();
        }
        return provider.server(serverName);
    }

    /**
     * Returns a component flattener that can use game data to resolve extra information about
     * components.
     * <p>This can be used for displaying components, or with serializers including the plain and
     * legacy serializers.</p>
     *
     * @return the flattener.
     */
    @Override
    public @NotNull ComponentFlattener flattener() {
        AudienceProvider provider = this.audienceProvider.get();
        if (provider == null) {
            return ComponentFlattener.basic();
        }
        return provider.flattener();
    }

    /**
     * Closes the provider and forces audiences to be empty.
     */
    @Override
    public void close() {
        AudienceProvider provider = this.audienceProvider.get();
        if (provider != null) {
            provider.close();
        }
    }

}
