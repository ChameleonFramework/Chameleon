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

package dev.hypera.chameleon.velocity.users;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ServerConnection;
import com.velocitypowered.api.proxy.messages.ChannelIdentifier;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.util.ModInfo;
import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.objects.Server;
import dev.hypera.chameleon.core.users.ProxyUser;
import dev.hypera.chameleon.velocity.VelocityChameleon;
import dev.hypera.chameleon.velocity.objects.VelocityServer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiConsumer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChameleonPlayer extends AudienceWrapper implements ProxyUser {

    private final Player player;

    @ApiStatus.Internal
    public ChameleonPlayer(Player player) {
        super(player);
        this.player = player;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return player.hasPermission(permission);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "1.0.0")
    @Override
    public void setPermission(@NotNull String permission, boolean has) {
        throw new UnsupportedOperationException("ChameleonPlayer#setPermission(String, boolean) is not implemented yet.");
    }

    @Override
    public String getName() {
        return player.getUsername();
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Override
    public @Nullable Locale getLocale() {
        return player.getPlayerSettings().getLocale();
    }

    @Override
    public int getPing() {
        return (int) player.getPing();
    }

    @Override
    public @Nullable Server getServer() {
        return player.getCurrentServer().map(s -> new VelocityServer(s.getServer())).orElse(null);
    }

    @Override
    public boolean isForgeUser() {
        return player.getModInfo().isPresent();
    }

    @Override
    public @Nullable Map<String, String> getModList() {
        Optional<ModInfo> info = player.getModInfo();
        if (!info.isPresent()) {
            return null;
        } else {
            Map<String, String> mods = new HashMap<>();
            info.get().getMods().forEach(m -> mods.put(m.getId(), m.getVersion()));
            return mods;
        }
    }

    @Override
    public void chat(@NotNull String message) {
        player.spoofChatInput(message);
    }

    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        player.sendPluginMessage(MinecraftChannelIdentifier.from(channel), data);
    }

    @Override
    public void connect(@NotNull Server server) {
        player.createConnectionRequest(((VelocityServer) server).getServer());
    }

    @Override
    public void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback) {
        player.createConnectionRequest(((VelocityServer) server).getServer());
        callback.accept(true, null);
    }

}
