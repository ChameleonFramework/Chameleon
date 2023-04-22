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
package dev.hypera.chameleon.event.server;

import dev.hypera.chameleon.platform.server.GameMode;
import dev.hypera.chameleon.user.ServerUser;
import org.jetbrains.annotations.NotNull;

/**
 * Server user gamemode change event, dispatched when a users gamemode changes.
 * <p>Note that this {@code ServerUserEvent} is <strong>not</strong> supported on Minestom.</p>
 */
public final class ServerUserGameModeChangeEvent implements ServerUserEvent {

    private final @NotNull ServerUser user;
    private final @NotNull GameMode newGameMode;

    /**
     * Server user gamemode change event constructor.
     *
     * @param user        The server user whose gamemode was changed.
     * @param newGameMode The new gamemode the user changed to.
     */
    public ServerUserGameModeChangeEvent(@NotNull ServerUser user, @NotNull GameMode newGameMode) {
        this.user = user;
        this.newGameMode = newGameMode;
    }

    /**
     * Get the server user whose gamemode was changed.
     *
     * @return the server user whose gamemode was changed.
     */
    @Override
    public @NotNull ServerUser getUser() {
        return this.user;
    }

    /**
     * Get the new gamemode the user will be changed to.
     *
     * @return new gamemmode.
     */
    public @NotNull GameMode getNewGameMode() {
        return this.newGameMode;
    }
}
