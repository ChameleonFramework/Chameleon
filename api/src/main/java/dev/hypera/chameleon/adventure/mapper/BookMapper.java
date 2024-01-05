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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Book mapper.
 */
public final class BookMapper implements Mapper<Book> {

    private final @NotNull ComponentMapper componentMapper;
    private @Nullable Method bookCreateMethod;
    private @Nullable Method bookTitleMethod;
    private @Nullable Method bookAuthorMethod;
    private @Nullable Method bookPagesMethod;

    BookMapper(@NotNull ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        Class<?> bookClass = Class.forName(AdventureMapper.ORIGINAL_BOOK_CLASS_NAME);
        Class<?> componentClass = Class.forName(AdventureMapper.ORIGINAL_COMPONENT_CLASS_NAME);
        this.bookCreateMethod = bookClass.getMethod("book", componentClass, componentClass, Collection.class);
        this.bookTitleMethod = bookClass.getMethod("title");
        this.bookAuthorMethod = bookClass.getMethod("author");
        this.bookPagesMethod = bookClass.getMethod("pages");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.componentMapper.isLoaded() &&
            this.bookCreateMethod != null && this.bookTitleMethod != null &&
            this.bookAuthorMethod != null && this.bookPagesMethod != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull Book book) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("book", book);

        Collection<Object> pages = new ArrayList<>();
        for (Component page : book.pages()) {
            pages.add(this.componentMapper.map(page));
        }

        return Objects.requireNonNull(this.bookCreateMethod).invoke(
            null, this.componentMapper.map(book.title()),
            this.componentMapper.map(book.author()), pages
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NotNull Book mapBackwards(@NotNull Object book) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("book", book);

        Collection<Component> pages = new ArrayList<>();
        for (Object page : (Collection<Object>) Objects.requireNonNull(this.bookPagesMethod).invoke(book)) {
            pages.add(this.componentMapper.mapBackwards(page));
        }

        return Book.book(
            this.componentMapper.mapBackwards(Objects.requireNonNull(this.bookTitleMethod).invoke(book)),
            this.componentMapper.mapBackwards(Objects.requireNonNull(this.bookAuthorMethod).invoke(book)),
            pages
        );
    }

}
