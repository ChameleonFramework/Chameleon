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
package dev.hypera.chameleon.platforms.bungeecord.adventure;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.platforms.bungeecord.users.BungeeCordUsers;
import dev.hypera.chameleon.users.ChatUser;
import java.util.UUID;
import java.util.function.Predicate;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord {@link ChameleonAudienceProvider} implementation.
 */
@Internal
public final class BungeeCordAudienceProvider implements ChameleonAudienceProvider {

    private final @NotNull Chameleon chameleon;
    private final @NotNull BungeeAudiences adventure;

    /**
     * {@link BungeeCordAudienceProvider} constructor.
     *
     * @param chameleon {@link Chameleon} instance.
     * @param plugin    {@link Plugin} instance.
     */
    @Internal
    public BungeeCordAudienceProvider(@NotNull Chameleon chameleon, @NotNull Plugin plugin) {
        this.chameleon = chameleon;
        this.adventure = BungeeAudiences.create(plugin);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience all() {
        return this.adventure.all();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience console() {
        return this.adventure.console();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience players() {
        return this.adventure.players();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience player(@NotNull UUID playerId) {
        return this.adventure.player(playerId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience filter(@NotNull Predicate<ChatUser> filter) {
        return this.adventure.filter(c -> filter.test(BungeeCordUsers.wrap(this.chameleon, c)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience permission(@NotNull String permission) {
        return this.adventure.permission(permission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience world(@NotNull Key world) {
        return this.adventure.world(world);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience server(@NotNull String serverName) {
        return this.adventure.server(serverName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ComponentFlattener flattener() {
        return this.adventure.flattener();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.adventure.close();
    }

}
