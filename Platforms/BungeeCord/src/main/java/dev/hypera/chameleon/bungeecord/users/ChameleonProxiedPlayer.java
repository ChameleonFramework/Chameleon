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

package dev.hypera.chameleon.bungeecord.users;

import dev.hypera.chameleon.bungeecord.BungeeCordChameleon;
import dev.hypera.chameleon.bungeecord.objects.BungeeCordServer;
import dev.hypera.chameleon.core.internal.utils.AudienceWrapper;
import dev.hypera.chameleon.core.objects.Server;
import dev.hypera.chameleon.core.users.ProxyUser;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChameleonProxiedPlayer extends AudienceWrapper implements ProxyUser {

    private final BungeeCordChameleon chameleon;
    private final ProxiedPlayer player;

    @Internal
    public ChameleonProxiedPlayer(BungeeCordChameleon chameleon, ProxiedPlayer player) {
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
        player.setPermission(permission, has);
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
        return player.getLocale();
    }

    @Override
    public int getPing() {
        return player.getPing();
    }

    @Override
    public @Nullable Server getServer() {
        return null == player.getServer() ? null : new BungeeCordServer(chameleon, player.getServer().getInfo());
    }

    @Override
    public boolean isForgeUser() {
        return player.isForgeUser();
    }

    @Override
    public @Nullable Map<String, String> getModList() {
        return player.getModList();
    }

    @Override
    public void chat(@NotNull String message) {
        player.chat(message);
    }

    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        player.sendData(channel, data);
    }

    @Override
    public void connect(@NotNull Server server) {
        player.connect(((BungeeCordServer) server).getServer());
    }

    @Override
    public void connect(@NotNull Server server, @NotNull BiConsumer<Boolean, Throwable> callback) {
        player.connect(((BungeeCordServer) server).getServer(), callback::accept);
    }

}
