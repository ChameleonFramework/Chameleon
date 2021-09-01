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
import dev.hypera.chameleon.core.users.ChatUser;
import dev.hypera.chameleon.spigot.SpigotChameleon;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval;
import org.jetbrains.annotations.NotNull;

public class ChameleonCommandSender extends AudienceWrapper implements ChatUser {

    private final @NotNull SpigotChameleon chameleon;
    private final @NotNull CommandSender sender;

    @ApiStatus.Internal
    public ChameleonCommandSender(@NotNull SpigotChameleon chameleon, @NotNull CommandSender sender) {
        super(chameleon.getAdventure().sender(sender));
        this.chameleon = chameleon;
        this.sender = sender;
    }

    @Override
    public boolean hasPermission(@NotNull String permission) {
        return sender.hasPermission(permission);
    }

    @Deprecated
    @ScheduledForRemoval(inVersion = "1.0.0")
    @Override
    public void setPermission(@NotNull String permission, boolean has) {
        throw new UnsupportedOperationException("ChameleonCommandSender#setPermission(String, boolean) is not implemented yet.");
    }

    @Override
    public String getName() {
        return null;
    }
}
