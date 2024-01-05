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
package dev.hypera.chameleon.adventure.mapper;

import dev.hypera.chameleon.util.Preconditions;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.UUID;
import net.kyori.adventure.identity.Identity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure Identity mapper.
 */
public final class IdentityMapper implements Mapper<Identity> {

    private @Nullable Method identityCreateMethod;
    private @Nullable Method identityUuidMethod;

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        Class<?> identityClass = Class.forName(AdventureMapper.ORIGINAL_IDENTITY_CLASS_NAME);
        this.identityCreateMethod = identityClass.getMethod("identity", UUID.class);
        this.identityUuidMethod = identityClass.getMethod("uuid");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.identityUuidMethod != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull Identity identity) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("identity", identity);
        return Objects.requireNonNull(this.identityCreateMethod).invoke(null, identity.uuid());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Identity mapBackwards(@NotNull Object identity) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("identity", identity);
        return Identity.identity((UUID) Objects.requireNonNull(this.identityUuidMethod).invoke(identity));
    }

}
