/*
 * This file is a part of the Chameleon Framework, licensed under the MIT License.
 *
 * Copyright (c) 2021-2022 The Chameleon Framework Authors.
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
package dev.hypera.chameleon.adventure.conversion.impl.bossbar;

import dev.hypera.chameleon.adventure.conversion.AdventureConverter;
import dev.hypera.chameleon.adventure.conversion.IMapper;
import dev.hypera.chameleon.exceptions.ChameleonRuntimeException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;
import net.kyori.adventure.bossbar.BossBar;
import org.jetbrains.annotations.NotNull;

/**
 * Maps shaded to platform {@link BossBar}.
 */
public final class BossBarMapper implements IMapper<BossBar> {

    private static final @NotNull String VALUE_OF = "valueOf";

    private final @NotNull Method createMethod;
    private final @NotNull Method colorValueOf;
    private final @NotNull Method overlayValueOf;
    private final @NotNull Method flagValueOf;

    /**
     * {@link BossBarMapper} constructor.
     */
    public BossBarMapper() {
        try {
            Class<?> componentLikeClass = Class.forName(AdventureConverter.PACKAGE + "text.ComponentLike");
            Class<?> bossBarClass = Class.forName(AdventureConverter.PACKAGE + "bossbar.BossBar");

            Class<?> colorEnum = Class.forName(bossBarClass.getCanonicalName() + "$Color");
            Class<?> overlayEnum = Class.forName(bossBarClass.getCanonicalName() + "$Overlay");
            Class<?> flagEnum = Class.forName(bossBarClass.getCanonicalName() + "$Flag");

            this.createMethod = bossBarClass.getMethod("bossBar", componentLikeClass, float.class, colorEnum, overlayEnum, Set.class);
            this.colorValueOf = colorEnum.getMethod(VALUE_OF, String.class);
            this.overlayValueOf = overlayEnum.getMethod(VALUE_OF, String.class);
            this.flagValueOf = flagEnum.getMethod(VALUE_OF, String.class);
        } catch (ReflectiveOperationException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Map {@link BossBar} to the platform version of Adventure.
     *
     * @param bossBar {@link BossBar} to be mapped.
     *
     * @return Platform instance of {@link BossBar}.
     */
    @Override
    public @NotNull Object map(@NotNull BossBar bossBar) {
        try {
            return this.createMethod.invoke(null,
                AdventureConverter.convertComponent(bossBar.name()),
                bossBar.progress(),
                this.colorValueOf.invoke(null, bossBar.color().name()),
                this.overlayValueOf.invoke(null, bossBar.overlay().name()),
                bossBar.flags().stream().map(f -> {
                    try {
                        return this.flagValueOf.invoke(null, f.name());
                    } catch (ReflectiveOperationException ex) {
                        throw new ChameleonRuntimeException(ex);
                    }
                }).collect(Collectors.toSet())
            );
        } catch (ReflectiveOperationException ex) {
            throw new ChameleonRuntimeException(ex);
        }
    }

}
