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

package dev.hypera.chameleon.core.adventure.conversion.impl.title;

import dev.hypera.chameleon.core.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.core.adventure.conversion.IConverter;
import java.lang.reflect.Method;
import java.time.Duration;
import net.kyori.adventure.title.Title.Times;
import org.jetbrains.annotations.NotNull;

public class TimesConverter implements IConverter<Times> {

	private final @NotNull Method CREATE_METHOD;

	public TimesConverter() {
		try {
			Class<?> timesClass = Class.forName(new String(AdventureConverter.PACKAGE) + "title.Title$Times");
			CREATE_METHOD = timesClass.getMethod("of", Duration.class, Duration.class, Duration.class);
		} catch (ReflectiveOperationException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	@Override
	public Object convert(Times times) {
		try {
			return CREATE_METHOD.invoke(null, times.fadeIn(), times.stay(), times.fadeOut());
		} catch (ReflectiveOperationException ex) {
			throw new RuntimeException(ex);
		}
	}

}