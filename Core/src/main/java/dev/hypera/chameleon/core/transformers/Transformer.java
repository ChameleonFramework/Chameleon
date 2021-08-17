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

package dev.hypera.chameleon.core.transformers;

import dev.hypera.chameleon.core.Chameleon;
import dev.hypera.chameleon.core.exceptions.TransformFailedException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Transformer
 * Transforms an object into a new object.
 *
 * @author Joshua Sing <joshua@hypera.dev>
 */
public class Transformer {

	private static final Set<ITransformer<?, ?>> registeredTransformers = new HashSet<>();

	/**
	 * Register transformers.
	 * @param transformers Transformers to be registered.
	 */
	public static void register(ITransformer<?, ?>... transformers) {
		for (ITransformer<?, ?> transformer : transformers) {
			register(transformer);
		}
	}

	/**
	 * Register transformers.
	 * @param transformers Transformers to be registered.
	 * @param transformers2 Transformers to be registered.
	 */
	public static void register(ITransformer<?,?>[] transformers, ITransformer<?, ?>... transformers2) {
		register(transformers);
		register(transformers2);
	}

	/**
	 * Register a transformer.
	 * @param transformer Transformer to be registered.
	 * @throws IllegalArgumentException if the transformer has already been registered.
	 */
	public static void register(ITransformer<?, ?> transformer) {
		if (registeredTransformers.contains(transformer)) {
			throw new IllegalArgumentException("Transformer '" + transformer.getClass().getCanonicalName() + "' is already registered");
		}

		registeredTransformers.add(transformer);
	}

	/**
	 * Transform an object.
	 * @param chameleon Chameleon instance.
	 * @param object Object to be transformed.
	 * @param to What the object should be transformed to.
	 * @param <T> Input type.
	 * @param <U> Output type.
	 * @return Transformed object.
	 * @throws TransformFailedException if there is no transformers available for the input and output types.
	 */
	@SuppressWarnings("unchecked")
	public static <T, U> U transform(Chameleon chameleon, T object, Class<U> to) throws TransformFailedException {
		if (to.isInstance(object)) {
			return (U) object;
		}

		Optional<ITransformer<T, U>> transformer = registeredTransformers.stream().filter(t -> (t.getFrom().equals(object.getClass()) || t.getFrom().isAssignableFrom(object.getClass())) && (t.getTo().equals(to) || t.getTo().isAssignableFrom(to))).map(t -> (ITransformer<T, U>) t).findFirst();
		if (!transformer.isPresent()) {
			throw new TransformFailedException("Failed to transform " + object.getClass().getCanonicalName() + " to " + to.getCanonicalName());
		} else {
			return transformer.get().transform(chameleon, object);
		}
	}

	/**
	 * Transform an object.
	 * @param chameleon Chameleon instance.
	 * @param object Object to be transformed.
	 * @param to What the object should be transformed to.
	 * @param data Any data the transformer might need to transform the object.
	 * @param <T> Input type.
	 * @param <U> Output type.
	 * @return Transformed object.
	 * @throws TransformFailedException if there is no transformers available for the input and output types.
	 */
	@SuppressWarnings("unchecked")
	public static <T, U> U transformWithData(Chameleon chameleon, T object, Class<U> to, String... data) throws TransformFailedException {
		if (to.isInstance(object)) {
			return (U) object;
		}

		Optional<ITransformer<T, U>> transformer = registeredTransformers.stream().filter(t -> (t.getFrom().equals(object.getClass()) || t.getFrom().isAssignableFrom(object.getClass())) && (t.getTo().equals(to) || t.getTo().isAssignableFrom(to))).map(t -> (ITransformer<T, U>) t).findFirst();
		if (!transformer.isPresent()) {
			throw new TransformFailedException("Failed to transform " + object.getClass().getCanonicalName() + " to " + to.getCanonicalName());
		} else {
			return transformer.get().transformWithData(chameleon, object, data);
		}
	}

}
