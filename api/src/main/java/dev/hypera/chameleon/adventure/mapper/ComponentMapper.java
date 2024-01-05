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
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure Component mapper.
 */
public final class ComponentMapper implements Mapper<Component> {

    private @Nullable Object gsonComponentSerializerInstance;
    private @Nullable Method gsonComponentSerializerSerializeMethod;
    private @Nullable Method gsonComponentSerializerDeserializeMethod;

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        Class<?> serializerClass = Class.forName(AdventureMapper.ORIGINAL_GSON_COMPONENT_SERIALIZER_CLASS_NAME);
        this.gsonComponentSerializerInstance = serializerClass.getMethod("gson").invoke(null);
        this.gsonComponentSerializerSerializeMethod = serializerClass.getMethod(
            "serialize", Class.forName(AdventureMapper.ORIGINAL_COMPONENT_CLASS_NAME)
        );
        this.gsonComponentSerializerDeserializeMethod = serializerClass.getMethod(
            "deserialize", Object.class
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.gsonComponentSerializerInstance != null &&
            this.gsonComponentSerializerSerializeMethod != null &&
            this.gsonComponentSerializerDeserializeMethod != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull Component component) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("component", component);
        String json = GsonComponentSerializer.gson().serialize(component);
        return Objects.requireNonNull(this.gsonComponentSerializerDeserializeMethod).invoke(
            Objects.requireNonNull(this.gsonComponentSerializerInstance), json
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Component mapBackwards(@NotNull Object component) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("component", component);
        Object json = Objects.requireNonNull(this.gsonComponentSerializerSerializeMethod).invoke(
            Objects.requireNonNull(this.gsonComponentSerializerInstance), component
        );
        return GsonComponentSerializer.gson().deserialize((String) json);
    }

}
