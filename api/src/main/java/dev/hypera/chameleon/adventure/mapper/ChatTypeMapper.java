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
package dev.hypera.chameleon.adventure.mapper;

import dev.hypera.chameleon.util.Preconditions;
import java.lang.reflect.Method;
import java.util.Objects;
import net.kyori.adventure.chat.ChatType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure ChatType mapper.
 */
public final class ChatTypeMapper implements Mapper<ChatType> {

    private final @NotNull KeyMapper keyMapper;
    private @Nullable Method chatTypeCreateMethod;
    private @Nullable Method keyedKeyMethod;

    ChatTypeMapper(@NotNull KeyMapper keyMapper) {
        this.keyMapper = keyMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        Class<?> chatTypeClass = Class.forName(AdventureMapper.ORIGINAL_CHAT_TYPE_CLASS_NAME);
        Class<?> keyedClass = Class.forName(AdventureMapper.ORIGINAL_KEYED_CLASS_NAME);
        this.chatTypeCreateMethod = chatTypeClass.getMethod("chatType", keyedClass);
        this.keyedKeyMethod = keyedClass.getMethod("key");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.keyMapper.isLoaded() &&
            this.chatTypeCreateMethod != null &&
            this.keyedKeyMethod != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull ChatType chatType) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("chatType", chatType);
        return Objects.requireNonNull(this.chatTypeCreateMethod).invoke(null, this.keyMapper.map(chatType.key()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull ChatType mapBackwards(@NotNull Object chatType) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("chatType", chatType);
        return ChatType.chatType(this.keyMapper.mapBackwards(Objects.requireNonNull(this.keyedKeyMethod).invoke(chatType)));
    }

}
