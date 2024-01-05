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
import java.util.Objects;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure Sound mapper.
 */
public final class SoundMapper implements Mapper<Sound> {

    private final @NotNull KeyMapper keyMapper;
    private @Nullable EnumMapper<Sound.Source> sourceMapper;
    private @Nullable Method soundCreateMethod;
    private @Nullable Method soundNameMethod;
    private @Nullable Method soundSourceMethod;
    private @Nullable Method soundVolumeMethod;
    private @Nullable Method soundPitchMethod;

    SoundMapper(@NotNull KeyMapper keyMapper) {
        this.keyMapper = keyMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        Class<?> soundClass = Class.forName(AdventureMapper.ORIGINAL_SOUND_CLASS_NAME);
        Class<?> keyClass = Class.forName(AdventureMapper.ORIGINAL_KEY_CLASS_NAME);
        Class<?> sourceClass = Class.forName(soundClass.getCanonicalName() + "$Source");
        this.sourceMapper = EnumMapper.createAndLoad(Sound.Source.class, sourceClass);
        this.soundCreateMethod = soundClass.getMethod("sound", keyClass, sourceClass, float.class, float.class);
        this.soundNameMethod = soundClass.getMethod("name");
        this.soundSourceMethod = soundClass.getMethod("source");
        this.soundVolumeMethod = soundClass.getMethod("volume");
        this.soundPitchMethod = soundClass.getMethod("pitch");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.keyMapper.isLoaded() &&
            this.sourceMapper != null && this.sourceMapper.isLoaded() &&
            this.soundCreateMethod != null && this.soundNameMethod != null &&
            this.soundSourceMethod != null && this.soundVolumeMethod != null &&
            this.soundPitchMethod != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull Sound sound) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("sound", sound);
        return Objects.requireNonNull(this.soundCreateMethod).invoke(
            null, this.keyMapper.map(sound.name()),
            Objects.requireNonNull(this.sourceMapper).map(sound.source()),
            sound.volume(), sound.pitch()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Sound mapBackwards(@NotNull Object sound) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("sound", sound);
        return Sound.sound(
            this.keyMapper.mapBackwards(Objects.requireNonNull(this.soundNameMethod).invoke(sound)),
            Objects.requireNonNull(this.sourceMapper).mapBackwards(
                Objects.requireNonNull(this.soundSourceMethod).invoke(sound)),
            (float) Objects.requireNonNull(this.soundVolumeMethod).invoke(sound),
            (float) Objects.requireNonNull(this.soundPitchMethod).invoke(sound)
        );
    }

}
