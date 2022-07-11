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
package dev.hypera.chameleon.platforms.mock.adventure;

import dev.hypera.chameleon.adventure.ChameleonAudienceProvider;
import dev.hypera.chameleon.platforms.mock.managers.MockUserManager;
import dev.hypera.chameleon.users.ChatUser;
import dev.hypera.chameleon.users.platforms.ProxyUser;
import java.util.UUID;
import java.util.function.Predicate;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.flattener.ComponentFlattener;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Mock {@link ChameleonAudienceProvider} implementation.
 */
public final class MockAudienceProvider implements ChameleonAudienceProvider {

    private final @NotNull MockUserManager userManager;

    /**
     * {@link MockAudienceProvider} constructor.
     *
     * @param userManager {@link MockUserManager} instance.
     */
    @Internal
    public MockAudienceProvider(@NotNull MockUserManager userManager) {
        this.userManager = userManager;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience all() {
        return Audience.audience(players(), console());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience console() {
        return this.userManager.getConsole();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience players() {
        return Audience.audience(this.userManager.getPlayers());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience player(@NotNull UUID playerId) {
        return this.userManager.getPlayer(playerId).orElseThrow(() -> new IllegalArgumentException("Cannot find player with id '" + playerId + "'"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience filter(@NotNull Predicate<ChatUser> filter) {
        return all().filterAudience(f -> filter.test((ChatUser) f));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience permission(@NotNull String permission) {
        return filter(p -> p.hasPermission(permission));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience world(@NotNull Key world) {
        return all();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience server(@NotNull String serverName) {
        return filter(p -> !(p instanceof ProxyUser) || ((ProxyUser) p).getServer().map(s -> s.getName().equalsIgnoreCase(serverName)).orElse(false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ComponentFlattener flattener() {
        return ComponentFlattener.basic();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {

    }

}
