/*
 * Chameleon - Cross-platform Minecraft plugin creation library
 *  Copyright (c) 2021 SLLCoding <luisjk266@gmail.com>
 *  Copyright (c) 2021 Joshua Sing <joshua@hypera.dev>
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

package dev.hypera.chameleon.core.configuration;

import java.io.File;
import java.nio.file.Path;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Configuration {

    @Nullable Class<?> getType(@NotNull String path);
    @Nullable Class<?> getType(@NotNull String path, @Nullable Class<?> def);
    boolean isType(@NotNull String path, @NotNull Class<?> type);
    @Nullable <T> T get(@NotNull String path, @NotNull Class<T> type);
    @Nullable Object get(@NotNull String path);
    @Nullable Object get(@NotNull String path, @Nullable Object def);
    @Nullable String getString(@NotNull String path);
    @NotNull String getString(@NotNull String path, @NotNull String def);
    @Nullable Integer getInt(@NotNull String path);
    int getInt(@NotNull String path, int def);
    @Nullable Double getDouble(@NotNull String path);
    double getDouble(@NotNull String path, double def);
    @Nullable Long getLong(@NotNull String path);
    long getLong(@NotNull String path, long def);
    @Nullable Boolean getBoolean(@NotNull String path);
    boolean getBoolean(@NotNull String path, boolean def);
    @Nullable List<?> getList(@NotNull String path);
    @NotNull List<?> getList(@NotNull String path, @NotNull List<?> def);
    @Deprecated @NotNull File getFile();
    @NotNull Path getPath();

}
