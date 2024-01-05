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
import net.kyori.adventure.sound.SoundStop;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Adventure SoundStop mapper.
 */
public final class SoundStopMapper implements Mapper<SoundStop> {

    private final @NotNull KeyMapper keyMapper;
    private @Nullable EnumMapper<Sound.Source> sourceMapper;
    private @Nullable Method soundStopAllMethod;
    private @Nullable Method soundStopNamedMethod;
    private @Nullable Method soundStopOnSourceMethod;
    private @Nullable Method soundStopNamedOnSourceMethod;
    private @Nullable Method soundStopSoundMethod;
    private @Nullable Method soundStopSourceMethod;

    SoundStopMapper(@NotNull KeyMapper keyMapper) {
        this.keyMapper = keyMapper;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "mapper has already been loaded");
        Class<?> soundStopClass = Class.forName(AdventureMapper.ORIGINAL_SOUND_STOP_CLASS_NAME);
        Class<?> keyClass = Class.forName(AdventureMapper.ORIGINAL_KEY_CLASS_NAME);
        Class<?> sourceClass = Class.forName(AdventureMapper.ORIGINAL_SOUND_CLASS_NAME.concat("$Source"));
        this.sourceMapper = EnumMapper.createAndLoad(Sound.Source.class, sourceClass);
        this.soundStopAllMethod = soundStopClass.getMethod("all");
        this.soundStopNamedMethod = soundStopClass.getMethod("named", keyClass);
        this.soundStopOnSourceMethod = soundStopClass.getMethod("source", sourceClass);
        this.soundStopNamedOnSourceMethod = soundStopClass.getMethod("namedOnSource", keyClass, sourceClass);
        this.soundStopSoundMethod = soundStopClass.getMethod("sound");
        this.soundStopSourceMethod = soundStopClass.getMethod("source");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoaded() {
        return this.keyMapper.isLoaded() &&
            this.sourceMapper != null && this.sourceMapper.isLoaded() &&
            this.soundStopAllMethod != null && this.soundStopNamedMethod != null &&
            this.soundStopOnSourceMethod != null && this.soundStopNamedOnSourceMethod != null &&
            this.soundStopSoundMethod != null && this.soundStopSourceMethod != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Object map(@NotNull SoundStop soundStop) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("soundStop", soundStop);

        if (soundStop.sound() == null) {
            if (soundStop.source() == null) {
                return Objects.requireNonNull(this.soundStopAllMethod).invoke(null);
            }

            return Objects.requireNonNull(this.soundStopOnSourceMethod).invoke(
                null, Objects.requireNonNull(this.sourceMapper).map(
                    Objects.requireNonNull(soundStop.source())
                )
            );
        }

        if (soundStop.source() == null) {
            return Objects.requireNonNull(this.soundStopNamedMethod).invoke(null, this.keyMapper.map(
                Objects.requireNonNull(soundStop.sound())
            ));
        }

        return Objects.requireNonNull(this.soundStopNamedOnSourceMethod).invoke(null, this.keyMapper.map(
            Objects.requireNonNull(soundStop.sound())
        ), Objects.requireNonNull(this.sourceMapper).map(
            Objects.requireNonNull(soundStop.source())
        ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull SoundStop mapBackwards(@NotNull Object soundStop) throws ReflectiveOperationException {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("soundStop", soundStop);

        Object sound = Objects.requireNonNull(this.soundStopSoundMethod).invoke(soundStop);
        Object source = Objects.requireNonNull(this.soundStopSourceMethod).invoke(soundStop);
        if (sound == null) {
            if (source == null) {
                return SoundStop.all();
            }

            return SoundStop.source(Objects.requireNonNull(this.sourceMapper).mapBackwards(source));
        }

        if (source == null) {
            return SoundStop.named(this.keyMapper.mapBackwards(sound));
        }

        return SoundStop.namedOnSource(
            this.keyMapper.mapBackwards(sound),
            Objects.requireNonNull(this.sourceMapper).mapBackwards(source)
        );
    }

}
