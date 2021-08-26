/*
 * Chameleon - Cross-platform Minecraft plugin creation library
 *  Copyright (c) 2021 SLLCoding <luisjk266@gmail.com>
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

package dev.hypera.chameleon.spigot.users;

import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.users.ServerUser;
import dev.hypera.chameleon.spigot.SpigotChameleon;
import java.util.Locale;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChameleonPlayer extends AudienceWrapper implements ServerUser {

    private final SpigotChameleon chameleon;
    private final Player player;

    @ApiStatus.Internal
    public ChameleonPlayer(SpigotChameleon chameleon, Player player) {
        super(chameleon.getAdventure().player(player));
        this.chameleon = chameleon;
        this.player = player;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return player.hasPermission(permission);
    }

    @Override
    public void setPermission(@NotNull String permission, boolean has) {
        // TODO: Complete this and make it work.
        throw new UnsupportedOperationException("ChameleonPlayer#setPermission(String, boolean) is not implemented yet.");
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public @Nullable Locale getLocale() {
        return Locale.forLanguageTag(player.getLocale());
    }

    @Override
    public int getPing() {
        return player.getPing();
    }

    @Override
    public void chat(@NotNull String message) {
        player.chat(message);
    }

    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        player.sendPluginMessage(chameleon.getSpigotPlugin(), channel, data);
    }

}
