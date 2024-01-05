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
package dev.hypera.chameleon.platform.sponge.event;

import dev.hypera.chameleon.adventure.mapper.AdventureMapper;
import dev.hypera.chameleon.adventure.mapper.ComponentMapper;
import dev.hypera.chameleon.exception.reflection.ChameleonReflectiveException;
import dev.hypera.chameleon.util.Preconditions;
import java.lang.reflect.Method;
import java.util.Objects;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.event.message.PlayerChatEvent;

@Internal
final class EventReflection {

    private final @NotNull ComponentMapper componentMapper;
    private @Nullable Method playerChatEventSetMessageMethod;

    EventReflection(@NotNull ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    void load() {
        Preconditions.checkState(!isLoaded(), "EventReflection has already been loaded");
        try {
            this.playerChatEventSetMessageMethod = PlayerChatEvent.class.getMethod(
                "setMessage", Class.forName(AdventureMapper.ORIGINAL_COMPONENT_CLASS_NAME)
            );
        } catch (ReflectiveOperationException ex) {
            throw new ChameleonReflectiveException(ex);
        }
    }

    boolean isLoaded() {
        return this.componentMapper.isLoaded() &&
            this.playerChatEventSetMessageMethod != null;
    }

    void setPlayerChatEventMessage(@NotNull PlayerChatEvent event, @NotNull Component component) {
        Preconditions.checkState(isLoaded(), "EventReflection has not been loaded");
        try {
            Objects.requireNonNull(this.playerChatEventSetMessageMethod)
                .invoke(event, this.componentMapper.map(component));
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.playerChatEventSetMessageMethod), event, ex
            );
        }
    }

}
