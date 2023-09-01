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
package dev.hypera.chameleon.platform.bukkit.adventure;

import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.platform.bukkit.user.BukkitUserManager;
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.util.Preconditions;
import java.util.UUID;
import java.util.function.Predicate;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Bukkit chameleon audience provider implementation.
 */
@Internal
public final class BukkitAudienceProvider implements ChameleonAudienceProvider {

    private final @NotNull BukkitUserManager userManager;
    private final @NotNull BukkitAudiences adventure;

    /**
     * Bukkit audience provider constructor.
     *
     * @param userManager Bukkit user manager implementation.
     * @param plugin      Plugin instance.
     */
    @Internal
    public BukkitAudienceProvider(@NotNull BukkitUserManager userManager, @NotNull JavaPlugin plugin) {
        this.userManager = userManager;
        this.adventure = BukkitAudiences.create(plugin);
    }

    /**
     * Gets an audience for all online players, including the server's console.
     * <p>The audience is dynamically updated as players join and leave.</p>
     *
     * @return the players' and console audience.
     */
    @Override
    public @NotNull Audience all() {
        return this.adventure.all();
    }

    /**
     * Gets an audience for the server's console.
     *
     * @return the console audience.
     */
    @Override
    public @NotNull Audience console() {
        return this.adventure.console();
    }

    /**
     * Gets an audience for all online players.
     * <p>The audience is dynamically updated as players join and leave.</p>
     *
     * @return the players' audience.
     */
    @Override
    public @NotNull Audience players() {
        return this.adventure.players();
    }

    /**
     * Gets an audience for an individual player.
     * <p>If the player is not online, messages are silently dropped.</p>
     *
     * @param playerId a player uuid.
     *
     * @return a player audience.
     */
    @Override
    public @NotNull Audience player(@NotNull UUID playerId) {
        Preconditions.checkNotNull("playerId", playerId);
        return this.adventure.player(playerId);
    }

    /**
     * Creates an audience based on a filter.
     *
     * @param filter a filter.
     *
     * @return an audience.
     */
    @Override
    public @NotNull Audience filter(@NotNull Predicate<ChatUser> filter) {
        Preconditions.checkNotNull("filter", filter);
        return this.adventure.filter(c -> filter.test(this.userManager.wrap(c)));
    }

    /**
     * Gets or creates an audience containing all viewers with the provided permission.
     * <p>The audience is dynamically updated as permissions change.</p>
     *
     * @param permission the permission to filter sending to.
     *
     * @return a permissible audience.
     */
    @Override
    public @NotNull Audience permission(@NotNull String permission) {
        Preconditions.checkNotNull("permission", permission);
        return this.adventure.permission(permission);
    }

    /**
     * Gets an audience for online players in a world, including the server's console.
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
        return this.adventure.world(world);
    }

    /**
     * Gets an audience for online players on a server, including the server's console.
     * <p>If the platform is not a proxy, the audience defaults to everyone.</p>
     *
     * @param serverName a server name.
     *
     * @return a server's audience.
     */
    @Override
    public @NotNull Audience server(@NotNull String serverName) {
        Preconditions.checkNotNull("serverName", serverName);
        return this.adventure.server(serverName);
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
        return this.adventure.flattener();
    }

    /**
     * Closes the provider and forces audiences to be empty.
     */
    @Override
    public void close() {
        this.adventure.close();
    }

}
