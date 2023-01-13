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
package dev.hypera.chameleon.adventure.mapper;

import dev.hypera.chameleon.util.Preconditions;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Flag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure BossBar mapper.
 */
public final class BossBarMapper implements Mapper<BossBar> {

    private final @NotNull ComponentMapper componentMapper;
    private @Nullable EnumMapper<BossBar.Color> bossBarColorMapper;
    private @Nullable EnumMapper<BossBar.Overlay> bossBarOverlayMapper;
    private @Nullable EnumMapper<BossBar.Flag> bossBarFlagMapper;
    private @Nullable Method bossBarCreateMethod;
    private @Nullable Method bossBarNameMethod;
    private @Nullable Method bossBarProgressMethod;
    private @Nullable Method bossBarColorMethod;
    private @Nullable Method bossBarOverlayMethod;
    private @Nullable Method bossBarFlagsMethod;

    BossBarMapper(@NotNull ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        Class<?> componentLikeClass = Class.forName(AdventureMapper.ORIGINAL_COMPONENT_LIKE_CLASS_NAME);
        Class<?> bossBarClass = Class.forName(AdventureMapper.ORIGINAL_BOSSBAR_CLASS_NAME);
        Class<?> colorEnum = Class.forName(bossBarClass.getCanonicalName() + "$Color");
        Class<?> overlayEnum = Class.forName(bossBarClass.getCanonicalName() + "$Overlay");
        Class<?> flagEnum = Class.forName(bossBarClass.getCanonicalName() + "$Flag");
        this.bossBarColorMapper = EnumMapper.createAndLoad(BossBar.Color.class, colorEnum);
        this.bossBarOverlayMapper = EnumMapper.createAndLoad(BossBar.Overlay.class, overlayEnum);
        this.bossBarFlagMapper = EnumMapper.createAndLoad(BossBar.Flag.class, flagEnum);
        this.bossBarCreateMethod = bossBarClass.getMethod("bossBar", componentLikeClass, float.class, colorEnum, overlayEnum, Set.class);
        this.bossBarNameMethod = bossBarClass.getMethod("name");
        this.bossBarProgressMethod = bossBarClass.getMethod("progress");
        this.bossBarColorMethod = bossBarClass.getMethod("color");
        this.bossBarOverlayMethod = bossBarClass.getMethod("overlay");
        this.bossBarFlagsMethod = bossBarClass.getMethod("flags");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.componentMapper.isLoaded() &&
            this.bossBarCreateMethod != null && this.bossBarNameMethod != null &&
            this.bossBarProgressMethod != null && this.bossBarColorMethod != null &&
            this.bossBarOverlayMethod != null && this.bossBarFlagsMethod != null &&
            this.bossBarColorMapper != null && this.bossBarColorMapper.isLoaded() &&
            this.bossBarOverlayMapper != null && this.bossBarOverlayMapper.isLoaded() &&
            this.bossBarFlagMapper != null && this.bossBarFlagMapper.isLoaded();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull BossBar bossBar) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("bossBar", bossBar);

        Set<Object> flags = new HashSet<>();
        for (Flag flag : bossBar.flags()) {
            flags.add(Objects.requireNonNull(this.bossBarFlagMapper).map(flag));
        }

        return Objects.requireNonNull(this.bossBarCreateMethod).invoke(null,
            this.componentMapper.map(bossBar.name()),
            bossBar.progress(),
            Objects.requireNonNull(this.bossBarColorMapper).map(bossBar.color()),
            Objects.requireNonNull(this.bossBarOverlayMapper).map(bossBar.overlay()),
            flags
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public @NotNull BossBar mapBackwards(@NotNull Object bossBar) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("bossBar", bossBar);

        Set<Flag> flags = new HashSet<>();
        for (Object flag : (Collection<Object>) Objects.requireNonNull(this.bossBarFlagsMethod).invoke(bossBar)) {
            flags.add(Objects.requireNonNull(this.bossBarFlagMapper).mapBackwards(flag));
        }

        return BossBar.bossBar(
            this.componentMapper.mapBackwards(Objects.requireNonNull(this.bossBarNameMethod).invoke(bossBar)),
            (float) Objects.requireNonNull(this.bossBarProgressMethod).invoke(bossBar),
            Objects.requireNonNull(this.bossBarColorMapper).mapBackwards(Objects.requireNonNull(this.bossBarColorMethod).invoke(bossBar)),
            Objects.requireNonNull(this.bossBarOverlayMapper).mapBackwards(Objects.requireNonNull(this.bossBarOverlayMethod).invoke(bossBar)),
           flags
        );
    }

}
