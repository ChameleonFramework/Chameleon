/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2023 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.exception.extension;

import dev.hypera.chameleon.exception.ChameleonRuntimeException;


/**
 * Chameleon extension exception.
 */
public class ChameleonExtensionException extends ChameleonRuntimeException {

    private static final long serialVersionUID = 7922248281838810538L;

    /**
     * Chameleon extension exception constructor.
     */
    public ChameleonExtensionException() {
        super();
    }

    /**
     * Chameleon extension exception constructor.
     *
     * @param message Exception message.
     */
    public ChameleonExtensionException(String message) {
        super(message);
    }

    /**
     * Chameleon extension exception constructor.
     *
     * @param message Exception message.
     * @param cause   Exception cause.
     */
    public ChameleonExtensionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Chameleon extension exception constructor.
     *
     * @param cause Exception cause.
     */
    public ChameleonExtensionException(Throwable cause) {
        super(cause);
    }

    protected ChameleonExtensionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
