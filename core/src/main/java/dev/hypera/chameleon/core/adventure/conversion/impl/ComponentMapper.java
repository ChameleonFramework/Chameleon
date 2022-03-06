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
package dev.hypera.chameleon.core.adventure.conversion.impl;

import dev.hypera.chameleon.core.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.core.adventure.conversion.IMapper;
import java.lang.reflect.Method;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.jetbrains.annotations.NotNull;

/**
 * Maps shaded to platform net.kyori.adventure.text.Component
 */
public class ComponentMapper implements IMapper<Component> {

	private final @NotNull Method GSON_SERIALIZER_DESERIALIZE;
	private final @NotNull Object GSON_SERIALIZER_INSTANCE;

	public ComponentMapper() {
		try {
			Class<?> serializerClass = Class.forName(new String(AdventureConverter.PACKAGE) + "text.serializer.gson.GsonComponentSerializer");
			GSON_SERIALIZER_DESERIALIZE = serializerClass.getMethod("deserialize", Object.class);
			GSON_SERIALIZER_INSTANCE = serializerClass.getMethod("gson").invoke(null);
		} catch (ReflectiveOperationException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Map Component to the platform version of Adventure
	 *
	 * @param component Component to be mapped
	 * @return Platform Component
	 */
	@Override
	public @NotNull Object map(@NotNull Component component) {
		String json = GsonComponentSerializer.gson().serialize(component);
		try {
			return GSON_SERIALIZER_DESERIALIZE.invoke(GSON_SERIALIZER_INSTANCE, json);
		} catch (ReflectiveOperationException ex) {
			throw new RuntimeException(ex);
		}
	}

}
