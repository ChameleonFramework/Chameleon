/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2024 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.platform.objects;

import java.util.UUID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PlatformPlayer {

    private final @NotNull UUID id;
    private final @NotNull String name;
    private final boolean admin;
    private final @Nullable String serverName;

    public PlatformPlayer(@NotNull UUID id, @NotNull String name) {
        this(id, name, false);
    }

    public PlatformPlayer(@NotNull UUID id, @NotNull String name, boolean admin) {
        this(id, name, admin, null);
    }

    public PlatformPlayer(@NotNull UUID id, @NotNull String name, boolean admin, @Nullable String serverName) {
        this.id = id;
        this.name = name;
        this.admin = admin;
        this.serverName = serverName;
    }

    @NotNull UUID id() {
        return this.id;
    }

    @NotNull String name() {
        return this.name;
    }

    boolean admin() {
        return this.admin;
    }

    @Nullable String serverName() {
        return this.serverName;
    }

}
