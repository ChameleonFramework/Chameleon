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
package dev.hypera.chameleon.event;

/**
 * Event subscription priority.
 */
public enum EventSubscriptionPriority {

    /**
     * First, indicates that the subscriber should be called before all other subscribers.
     */
    FIRST,

    /**
     * High priority, indicates that the subscriber should be before {@link #MEDIUM} priority subscribers.
     */
    HIGH,

    /**
     * Medium priority, indicates that the subscriber should be called before {@link #NORMAL} priority subscribers.
     */
    MEDIUM,

    /**
     * Normal priority.
     */
    NORMAL,

    /**
     * Low priority, indicates that the subscriber should be called after {@link #NORMAL} priority subscribers.
     */
    LOW,

    /**
     * Very low priority, indicates that the subscriber should be called after {@link #LOW} priority subscribers.
     */
    VERY_LOW,

    /**
     * Last, indicates that the subscriber should be called after all other subscribers.
     */
    LAST

}
