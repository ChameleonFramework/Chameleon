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
package dev.hypera.chameleon.platform.bungeecord.adventure;

import dev.hypera.chameleon.platform.adventure.PlatformAudienceProvider;
import dev.hypera.chameleon.platform.bungeecord.user.BungeeCordUserManager;
import dev.hypera.chameleon.user.ChatUser;
import dev.hypera.chameleon.util.Preconditions;
import java.util.function.Predicate;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bungeecord.BungeeAudiences;
import net.md_5.bungee.api.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * BungeeCord chameleon audience provider implementation.
 */
@Internal
public final class BungeeCordAudienceProvider extends PlatformAudienceProvider {

    private final @NotNull BungeeCordUserManager userManager;

    /**
     * BungeeCord audience provider constructor.
     *
     * @param userManager User manager implementation.
     */
    @Internal
    public BungeeCordAudienceProvider(@NotNull BungeeCordUserManager userManager) {
        this.userManager = userManager;
    }

    /**
     * Initialises the underlying Adventure audience provider implementation.
     *
     * @param plugin BungeeCord plugin.
     */
    public void init(@NotNull Plugin plugin) {
        this.audienceProvider.set(BungeeAudiences.create(plugin));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Audience filter(@NotNull Predicate<ChatUser> filter) {
        Preconditions.checkNotNull("filter", filter);
        AudienceProvider provider = this.audienceProvider.get();
        if (provider == null) {
            return Audience.empty();
        }
        return ((BungeeAudiences) provider).filter(c -> filter.test(this.userManager.wrap(c)));
    }

}
