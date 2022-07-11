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
package dev.hypera.chameleon.platforms.mock.user;

import dev.hypera.chameleon.annotations.PlatformSpecific;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.server.GameMode;
import dev.hypera.chameleon.platforms.mock.MockChameleon;
import dev.hypera.chameleon.users.platforms.ServerUser;
import java.util.UUID;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Mock {@link ServerUser} implementation.
 */
@PlatformSpecific(Platform.Type.SERVER)
public final class MockServerUser extends MockUser implements ServerUser {

    private @NotNull GameMode gameMode = GameMode.SURVIVAL;

    /**
     * {@link MockServerUser} constructor.
     *
     * @param id        Unique id.
     * @param name      Name.
     * @param chameleon {@link MockChameleon} instance.
     */
    @Internal
    public MockServerUser(@NotNull UUID id, @NotNull String name, @NotNull MockChameleon chameleon) {
        super(id, name, chameleon);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull GameMode getGameMode() {
        return this.gameMode;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameMode(@NotNull GameMode gameMode) {
        this.gameMode = gameMode;
    }

}
