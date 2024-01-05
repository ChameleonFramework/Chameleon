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
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.user.ProxyUser;
import dev.hypera.chameleon.user.UserManager;
import dev.hypera.chameleon.util.Preconditions;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.jetbrains.annotations.NotNull;

/**
 * Standalone Chameleon audience provider.
 *
 * <p>This audience provider implementation does not require any platform Adventure implementation,
 * and instead uses Chameleon's {@link UserManager}.</p>
 */
public final class StandaloneAudienceProvider implements ChameleonAudienceProvider, ForwardingAudience {

    private final @NotNull UserManager userManager;

    /**
     * Standalone Chameleon audience provider constructor.
     *
     * @param userManager User manager.
     */
    public StandaloneAudienceProvider(@NotNull UserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Returns the audiences.
     *
     * @return the audiences.
     */
    @Override
    public @NotNull Iterable<? extends Audience> audiences() {
        return Stream.concat(
            Stream.of(this.userManager.getConsole()),
            this.userManager.getUsers().stream()
        ).collect(Collectors.toUnmodifiableSet());
    }

    /**
     * Returns an audience for all online players, including the server's console.
     * <p>The audience is dynamically updated as players join and leave.</p>
     *
     * @return the players' and console audience.
     */
    @Override
    public @NotNull Audience all() {
        return this;
    }

    /**
     * Returns an audience for the server's console.
     *
     * @return the console audience.
     */
    @Override
    public @NotNull Audience console() {
        return this.userManager.getConsole();
    }

    /**
     * Returns an audience for all online players.
     * <p>The audience is dynamically updated as players join and leave.</p>
     *
     * @return the players' audience.
     */
    @Override
    public @NotNull Audience players() {
        return Audience.audience(this.userManager.getUsers());
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
        return this.userManager.getUserById(playerId)
            .map(p -> (Audience) p).orElse(Audience.empty());
    }

    /**
     * Returns an audience based on a filter.
     *
     * @param filter a filter.
     *
     * @return an audience.
     */
    @Override
    public @NotNull Audience filter(@NotNull Predicate<ChatUser> filter) {
        Preconditions.checkNotNull("filter", filter);
        return all().filterAudience(a -> filter.test((ChatUser) a));
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
        return filter(p -> p.hasPermission(permission));
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
        return all();
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
        return filter(p -> p instanceof ProxyUser && ((ProxyUser) p).getConnectedServer()
            .map(s -> s.getName().equals(serverName)).orElse(false));
    }

    /**
     * Return a component flattener that can use game data to resolve extra information about
     * components.
     * <p>This can be used for displaying components, or with serializers including the plain and
     * legacy serializers.</p>
     *
     * @return the flattener.
     */
    @Override
    public @NotNull ComponentFlattener flattener() {
        return ComponentFlattener.basic();
    }

    /**
     * Closes the provider and forces audiences to be empty.
     */
    @Override
    public void close() {
        // Not available.
    }

}
