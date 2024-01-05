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
package dev.hypera.chameleon.exception.reflection;

import dev.hypera.chameleon.exception.ChameleonRuntimeException;
import java.lang.reflect.Method;
import java.util.StringJoiner;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Chameleon reflective exception.
 */
public class ChameleonReflectiveException extends ChameleonRuntimeException {

    private static final long serialVersionUID = 6727730791800930118L;

    /**
     * Chameleon reflective exception constructor.
     */
    public ChameleonReflectiveException() {
        super();
    }

    /**
     * Chameleon reflective exception constructor.
     *
     * @param message Exception message.
     */
    public ChameleonReflectiveException(String message) {
        super(message);
    }

    /**
     * Chameleon reflective exception constructor.
     *
     * @param message Exception message.
     * @param cause   Exception cause.
     */
    public ChameleonReflectiveException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Chameleon reflective exception constructor.
     *
     * @param cause Exception cause.
     */
    public ChameleonReflectiveException(Throwable cause) {
        super(cause);
    }

    protected ChameleonReflectiveException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * Create a new Chameleon reflective exception with a template and exception.
     *
     * @param s    Template string.
     * @param ex   Exception.
     * @param args Template arguments.
     *
     * @return new Chameleon reflective exception.
     */
    public static @NotNull ChameleonReflectiveException create(@NotNull String s, @NotNull Throwable ex, @NotNull Object... args) {
        return new ChameleonReflectiveException(String.format(s, args), ex);
    }

    /**
     * Create a failed to call method exception.
     * <p>Example message:
     * {@code Failed to invoke method get(String) on dev.hypera.chameleon.Example}</p>
     *
     * @param method Method.
     * @param obj    Object method was called on.
     * @param ex     Exception thrown.
     *
     * @return new exception.
     */
    @Contract(value = "_, _, _ -> new", pure = true)
    public static @NotNull ChameleonReflectiveException createMethodInvocationFailure(@NotNull Method method, @NotNull Object obj, @NotNull Throwable ex) {
        return create("Failed to invoke method %s on %s",
            ex,
            getMethodSignature(method),
            obj.getClass().getCanonicalName()
        );
    }

    /**
     * Replicates Method#toShortSignature, which is not public.
     *
     * @param method Method to create "short signature" for.
     *
     * @return short signature.
     */
    private static @NotNull String getMethodSignature(@NotNull Method method) {
        StringJoiner joiner = new StringJoiner(",", method.getName() + "(", ")");
        for (Class<?> parameterType : method.getParameterTypes()) {
            joiner.add(parameterType.getTypeName());
        }
        return joiner.toString();
    }

}
