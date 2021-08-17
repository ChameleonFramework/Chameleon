/*
 * Chameleon - Cross-platform Minecraft plugin framework
 * Copyright (c) 2021 Joshua Sing <joshua@hypera.dev>
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

package dev.hypera.chameleon.core.events.mapping;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.annotations.MappedField;
import dev.hypera.chameleon.core.annotations.PlatformSpecific;
import dev.hypera.chameleon.core.annotations.Transform;
import dev.hypera.chameleon.core.transformers.Transformer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import org.jetbrains.annotations.ApiStatus.Internal;

/**
 * Event Mapper
 * Maps platform events to Chameleon events using annotations.
 *
 * @author Joshua Sing <joshua@hypera.dev>
 */
public class EventMapper {

	/**
	 * Map a platform event to a Chameleon event. (Internal)
	 * @param chameleon Chameleon instance.
	 * @param object Platform event.
	 * @param as Chameleon event to map to.
	 * @param <T> Output chameleon event.
	 * @return Mapped chameleon event.
	 * @throws Exception if something goes wrong while mapping.
	 */
	@Internal
	public <T> T map(Chameleon chameleon, Object object, Class<T> as) throws Exception {
		Object output = as.getDeclaredConstructor().newInstance();
		for (Field field : output.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(MappedField.class)) {
				field.setAccessible(true);
				field.set(output, map(chameleon, object, field.getAnnotation(MappedField.class).value(), field));
			}
		}

		return as.cast(output);
	}

	/**
	 * Map a field from a platform event to a Chameleon event.
	 * @param chameleon Chameleon instance.
	 * @param object Platform event.
	 * @param names Platform field/method names.
	 * @param originalField Chameleon event field to be mapped to.
	 * @return Mapped object.
	 * @throws Exception if something goes wrong while mapping.
	 */
	@Internal
	private Object map(Chameleon chameleon, Object object, String[] names, Field originalField) throws Exception {
		Optional<Field> field = Arrays.stream(object.getClass().getDeclaredFields()).filter(f -> {
			f.setAccessible(true);
			return Arrays.stream(names).anyMatch(n -> !n.endsWith("()") && f.getName().equals(n));
		}).findFirst();

		Object mappedObject;

		if (!field.isPresent()) {
			Optional<Method> method = Arrays.stream(object.getClass().getMethods()).filter(m -> {
				m.setAccessible(true);
				return Arrays.stream(names).anyMatch(n -> n.endsWith("()") && m.getName().equals(n.substring(0, n.length() - 2)));
			}).findFirst();

			if (!method.isPresent()) {
				if (originalField.isAnnotationPresent(PlatformSpecific.class)) {
					return null;
				} else {
					throw new NullPointerException("Cannot map field '" + originalField.getName() + "' from names " + Arrays.toString(names));
				}
			} else {
				mappedObject = method.get().invoke(object);
			}
		} else {
			mappedObject = field.get().get(object);
		}

		if (originalField.isAnnotationPresent(Transform.class)) {
			Transform transform = originalField.getAnnotation(Transform.class);
			if (null != transform.data() && transform.data().length > 0) {
				return Transformer.transformWithData(chameleon, mappedObject, transform.value(), transform.data());
			} else {
				return Transformer.transform(chameleon, mappedObject, transform.value());
			}
		} else {
			return mappedObject;
		}
	}

}
