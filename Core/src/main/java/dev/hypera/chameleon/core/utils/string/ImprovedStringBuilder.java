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

package dev.hypera.chameleon.core.utils.string;

import java.util.function.Predicate;

/**
 * This class simply wraps a StringBuilder, allowing us to add more helpful methods like {@link ImprovedStringBuilder#appendIf(Object, Predicate)}
 */
public class ImprovedStringBuilder {

	private StringBuilder builder = new StringBuilder();

	public ImprovedStringBuilder() {

	}

	public ImprovedStringBuilder(StringBuilder builder) {
		this.builder = builder;
	}


	public ImprovedStringBuilder append(Object obj) {
		builder.append(obj);
		return this;
	}

	public ImprovedStringBuilder appendIf(Object obj, Predicate<Object> predicate) {
		if (predicate.test(obj)) {
			builder.append(obj);
		}
		return this;
	}

	public ImprovedStringBuilder appendIfElse(Object obj, Object other, Predicate<Object> predicate) {
		if (predicate.test(obj)) {
			builder.append(obj);
		} else {
			builder.append(other);
		}
		return this;
	}

	public ImprovedStringBuilder insert(int offset, Object obj) {
		builder.insert(offset, obj);
		return this;
	}

	public int indexOf(String s) {
		return builder.indexOf(s);
	}

	public int indexOf(String s, int fromIndex) {
		return builder.indexOf(s, fromIndex);
	}

	public int lastIndexOf(String s) {
		return builder.lastIndexOf(s);
	}

	public int lastIndexOf(String s, int fromIndex) {
		return builder.lastIndexOf(s, fromIndex);
	}

	public ImprovedStringBuilder reverse() {
		builder.reverse();
		return this;
	}

	@Override
	public String toString() {
		return builder.toString();
	}

}
