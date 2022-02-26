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

package dev.hypera.chameleon.core.adventure.conversion.impl.book;

import dev.hypera.chameleon.core.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.core.adventure.conversion.IConverter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import net.kyori.adventure.inventory.Book;
import org.jetbrains.annotations.NotNull;

public class BookConverter implements IConverter<Book> {

	private final @NotNull Method CREATE_METHOD;

	public BookConverter() {
		try {
			Class<?> componentClass = Class.forName(new String(AdventureConverter.PACKAGE) + "text.Component");
			Class<?> bookClass = Class.forName(new String(AdventureConverter.PACKAGE) + "inventory.Book");
			CREATE_METHOD = bookClass.getMethod("book", componentClass, componentClass, Collection.class);
		} catch (ReflectiveOperationException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	@Override
	public Object convert(Book book) {
		try {
			return CREATE_METHOD.invoke(
					null,
					AdventureConverter.convertComponent(book.title()),
					AdventureConverter.convertComponent(book.author()),
					book.pages().stream().map(AdventureConverter::convertComponent).collect(Collectors.toCollection(ArrayList::new))
			);
		} catch (ReflectiveOperationException ex) {
			throw new RuntimeException(ex);
		}
	}

}
