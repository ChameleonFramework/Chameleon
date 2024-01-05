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
import net.kyori.adventure.chat.ChatType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure Bound mapper.
 */
public final class BoundMapper implements Mapper<ChatType.Bound> {

    private final @NotNull ChatTypeMapper chatTypeMapper;
    private final @NotNull ComponentMapper componentMapper;
    private @Nullable Method chatTypeBindMethod;
    private @Nullable Method boundTypeMethod;
    private @Nullable Method boundNameMethod;
    private @Nullable Method boundTargetMethod;

    BoundMapper(@NotNull ChatTypeMapper chatTypeMapper, @NotNull ComponentMapper componentMapper) {
        this.chatTypeMapper = chatTypeMapper;
        this.componentMapper = componentMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        Class<?> chatTypeClass = Class.forName(AdventureMapper.ORIGINAL_CHAT_TYPE_CLASS_NAME);
        Class<?> boundClass = Class.forName(AdventureMapper.ORIGINAL_CHAT_TYPE_BOUND_CLASS_NAME);
        Class<?> componentLikeClass = Class.forName(AdventureMapper.ORIGINAL_COMPONENT_LIKE_CLASS_NAME);
        this.chatTypeBindMethod = chatTypeClass.getMethod("bind", componentLikeClass, componentLikeClass);
        this.boundTypeMethod = boundClass.getMethod("type");
        this.boundNameMethod = boundClass.getMethod("name");
        this.boundTargetMethod = boundClass.getMethod("target");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.chatTypeMapper.isLoaded() && this.componentMapper.isLoaded() &&
            this.chatTypeBindMethod != null && this.boundTypeMethod != null &&
            this.boundNameMethod != null && this.boundTargetMethod != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull ChatType.Bound bound) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("bound", bound);
        return Objects.requireNonNull(this.chatTypeBindMethod).invoke(
            this.chatTypeMapper.map(bound.type()), this.componentMapper.map(bound.name()),
            bound.target() == null ? null : this.componentMapper.map(bound.target())
        );
    }

    /**
     * {@inheritDoc}
     */
    @NotNull
    @Override
    public ChatType.Bound mapBackwards(@NotNull Object bound) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("bound", bound);
        Object target = Objects.requireNonNull(this.boundTargetMethod).invoke(bound);
        return this.chatTypeMapper.mapBackwards(Objects.requireNonNull(this.boundTypeMethod).invoke(bound)).bind(
            this.componentMapper.mapBackwards(Objects.requireNonNull(this.boundNameMethod).invoke(bound)),
            target == null ? null : this.componentMapper.mapBackwards(target)
        );
    }

}
