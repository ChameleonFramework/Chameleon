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
package dev.hypera.chameleon.adventure;

import dev.hypera.chameleon.adventure.mapper.AdventureMapper;
import dev.hypera.chameleon.adventure.mapper.EnumMapper;
import dev.hypera.chameleon.exception.reflection.ChameleonReflectiveException;
import dev.hypera.chameleon.util.Preconditions;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.pointer.Pointer;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.title.TitlePart;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Helper class for {@link dev.hypera.chameleon.adventure.ReflectedAudience}.
 */
@Internal
@SuppressWarnings("deprecation")
public final class AudienceReflection {

    private final @NotNull AdventureMapper adventure;
    private final @NotNull AtomicBoolean loaded = new AtomicBoolean(false);
    private @Nullable EnumMapper<net.kyori.adventure.audience.MessageType> messageTypeMapper;
    private @Nullable Method audienceSendMessageMethod;
    private @Nullable Method audienceSendMessageBoundMethod;
    private @Nullable Method audienceDeleteMessageMethod;
    private @Nullable Method audienceSendMessageSourceTypeMethod;
    private @Nullable Method audienceSendActionBarMethod;
    private @Nullable Method audienceClearTitleMethod;
    private @Nullable Method audienceResetTitleMethod;
    private @Nullable Method audienceShowBossBarMethod;
    private @Nullable Method audienceHideBossBarMethod;
    private @Nullable Method audiencePlaySoundMethod;
    private @Nullable Method audiencePlaySoundPositionMethod;
    private @Nullable Method audiencePlaySoundEmitterMethod;
    private @Nullable Method audienceStopSoundMethod;
    private @Nullable Method audienceOpenBookMethod;
    private @Nullable Method audienceSendPlayerListHeader;
    private @Nullable Method audienceSendPlayerListFooter;
    private @Nullable Method audienceSendPlayerListHeaderAndFooter;
    private @Nullable Method audienceSendTitlePart;
    private @Nullable Method soundEmitterSelfMethod;
    private @Nullable Method pointeredGetMethod;

    /**
     * Audience reflection constructor.
     *
     * @param adventure Adventure mapper instance.
     */
    @Internal
    public AudienceReflection(@NotNull AdventureMapper adventure) {
        this.adventure = adventure;
    }

    /**
     * Load all classes and methods.
     *
     * @throws ReflectiveOperationException if something goes wrong.
     */
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!isLoaded(), "AudienceReflection has already been loaded");

        // Audience
        Class<?> audienceClass = Class.forName(AdventureMapper.ORIGINAL_AUDIENCE_CLASS_NAME);
        Class<?> bookClass = Class.forName(AdventureMapper.ORIGINAL_BOOK_CLASS_NAME);
        Class<?> bossBarClass = Class.forName(AdventureMapper.ORIGINAL_BOSSBAR_CLASS_NAME);
        Class<?> boundClass = Class.forName(AdventureMapper.ORIGINAL_CHAT_TYPE_BOUND_CLASS_NAME);
        Class<?> componentClass = Class.forName(AdventureMapper.ORIGINAL_COMPONENT_CLASS_NAME);
        Class<?> identityClass = Class.forName(AdventureMapper.ORIGINAL_IDENTITY_CLASS_NAME);
        Class<?> messageTypeClass = Class.forName(
            AdventureMapper.ORIGINAL_PACKAGE.concat("audience.MessageType")
        );
        Class<?> signatureClass = Class.forName(AdventureMapper.ORIGINAL_SIGNED_MESSAGE_SIGNATURE_CLASS_NAME);
        Class<?> soundClass = Class.forName(AdventureMapper.ORIGINAL_SOUND_CLASS_NAME);
        Class<?> soundEmitterClass = Class.forName(AdventureMapper.ORIGINAL_SOUND_EMITTER_CLASS_NAME);
        Class<?> soundStopClass = Class.forName(AdventureMapper.ORIGINAL_SOUND_STOP_CLASS_NAME);
        Class<?> titlePartClass = Class.forName(AdventureMapper.ORIGINAL_TITLE_PART_CLASS_NAME);

        this.messageTypeMapper = EnumMapper.createAndLoad(net.kyori.adventure.audience.MessageType.class, messageTypeClass);
        this.audienceSendMessageMethod = audienceClass.getMethod("sendMessage", componentClass);
        this.audienceSendMessageBoundMethod = audienceClass.getMethod(
            "sendMessage", componentClass, boundClass
        );
        this.audienceDeleteMessageMethod = audienceClass.getMethod("deleteMessage", signatureClass);
        this.audienceSendMessageSourceTypeMethod = audienceClass.getMethod(
            "sendMessage", identityClass, componentClass, messageTypeClass
        );
        this.audienceSendActionBarMethod = audienceClass.getMethod("sendActionBar", componentClass);
        this.audienceClearTitleMethod = audienceClass.getMethod("clearTitle");
        this.audienceResetTitleMethod = audienceClass.getMethod("resetTitle");
        this.audienceShowBossBarMethod = audienceClass.getMethod("showBossBar", bossBarClass);
        this.audienceHideBossBarMethod = audienceClass.getMethod("hideBossBar", bossBarClass);
        this.audiencePlaySoundMethod = audienceClass.getMethod("playSound", soundClass);
        this.audiencePlaySoundPositionMethod = audienceClass.getMethod(
            "playSound", soundClass, double.class, double.class, double.class
        );
        this.audiencePlaySoundEmitterMethod = audienceClass.getMethod(
            "playSound", soundClass, soundEmitterClass
        );
        this.audienceStopSoundMethod = audienceClass.getMethod("stopSound", soundStopClass);
        this.audienceOpenBookMethod = audienceClass.getMethod("openBook", bookClass);
        this.audienceSendPlayerListHeader = audienceClass.getMethod(
            "sendPlayerListHeader", componentClass
        );
        this.audienceSendPlayerListFooter = audienceClass.getMethod(
            "sendPlayerListFooter", componentClass
        );
        this.audienceSendPlayerListHeaderAndFooter = audienceClass.getMethod(
            "sendPlayerListHeaderAndFooter", componentClass, componentClass
        );
        this.audienceSendTitlePart = audienceClass.getMethod(
            "sendTitlePart", titlePartClass, Object.class
        );

        // SoundEmitter
        this.soundEmitterSelfMethod = soundEmitterClass.getMethod("self");

        // Pointered
        Class<?> pointeredClass = Class.forName(AdventureMapper.ORIGINAL_POINTERED_CLASS_NAME);
        Class<?> pointerClass = Class.forName(AdventureMapper.ORIGINAL_POINTER_CLASS_NAME);
        this.pointeredGetMethod = pointeredClass.getMethod("get", pointerClass);

        this.loaded.set(true);
    }

    /**
     * Get whether {@link #load()} has been called.
     *
     * @return loaded.
     */
    public boolean isLoaded() {
        return this.loaded.get();
    }

    /**
     * Send a message to the given audience using reflection.
     *
     * @param audience  Audience to invoke {@code sendMessage(Component)} on.
     * @param component Component to be mapped and sent to the audience.
     */
    public void sendMessage(@NotNull Object audience, @NotNull Component component) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceSendMessageMethod).invoke(audience,
                this.adventure.getComponentMapper().map(component)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceSendMessageMethod), audience, ex
            );
        }
    }

    /**
     * Send a message with a chat type bound to the given audience using reflection.
     *
     * @param audience      Audience to invoke {@code sendMessage(Component, ChatType.Bound)} on.
     * @param component     Component to be mapped and sent to the audience.
     * @param boundChatType Bound to be mapped and send to the audience alongside the component.
     */
    public void sendMessage(@NotNull Object audience, @NotNull Component component, @NotNull ChatType.Bound boundChatType) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceSendMessageBoundMethod).invoke(audience,
                this.adventure.getComponentMapper().map(component),
                this.adventure.getBoundMapper().map(boundChatType)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceSendMessageBoundMethod), audience, ex
            );
        }
    }

    /**
     * Send a message with a type from a source to the given audience using reflection.
     *
     * @param audience    Audience to invoke {@code sendMessage(Identity, Component, MessageType)}
     *                    on.
     * @param source      Identity to be mapped and sent to the audience.
     * @param component   Component to be mapped and sent to the audience.
     * @param messageType MessageType to be mapped and sent to the audience.
     *
     * @deprecated Deprecated by Adventure.
     */
    @Deprecated
    public void sendMessage(@NotNull Object audience, @NotNull Identity source, @NotNull Component component, @NotNull net.kyori.adventure.audience.MessageType messageType) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceSendMessageSourceTypeMethod).invoke(audience,
                this.adventure.getIdentityMapper().map(source),
                this.adventure.getComponentMapper().map(component),
                Objects.requireNonNull(this.messageTypeMapper).map(messageType)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceSendMessageSourceTypeMethod), audience, ex
            );
        }
    }

    /**
     * Send an action bar message to the given audience using reflection.
     *
     * @param audience  Audience to invoke {@code sendActionBar(Component)} on.
     * @param component Component to be mapped and sent to the audience.
     */
    public void sendActionBar(@NotNull Object audience, @NotNull Component component) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceSendActionBarMethod).invoke(audience,
                this.adventure.getComponentMapper().map(component)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceSendActionBarMethod), audience, ex
            );
        }
    }

    /**
     * Delete a message for the given audience using reflection.
     *
     * @param audience  Audience to invoke {@code deleteMessage(SignedMessage.Signature)} on.
     * @param signature Signature to be mapped and deleted for the audience.
     */
    public void deleteMessage(@NotNull Object audience, @NotNull SignedMessage.Signature signature) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceDeleteMessageMethod).invoke(audience,
                this.adventure.getSignatureMapper().map(signature)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceDeleteMessageMethod), audience, ex
            );
        }
    }

    /**
     * Send a player list header to the given audience using reflection.
     *
     * @param audience Audience to invoke {@code sendPlayerListHeader(Component)} on.
     * @param header   Component to be mapped and sent to the audience.
     */
    public void sendPlayerListHeader(@NotNull Object audience, @NotNull Component header) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceSendPlayerListHeader).invoke(audience,
                this.adventure.getComponentMapper().map(header)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceSendPlayerListHeader), audience, ex
            );
        }
    }

    /**
     * Send a player list footer to the given audience using reflection.
     *
     * @param audience Audience to invoke {@code sendPlayerListFooter(Component)} on.
     * @param footer   Component to be mapped and sent to the audience.
     */
    public void sendPlayerListFooter(@NotNull Object audience, @NotNull Component footer) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceSendPlayerListFooter).invoke(audience,
                this.adventure.getComponentMapper().map(footer)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceSendPlayerListFooter), audience, ex
            );
        }
    }

    /**
     * Send a player list header and footer to the given audience using reflection.
     *
     * @param audience Audience to invoke
     *                 {@code sendPlayerListHeaderAndFooter(Component, Component)} on.
     * @param header   Header Component to be mapped and sent to the audience.
     * @param footer   Footer Component to be mapped and sent to the audience.
     */
    public void sendPlayerListHeaderAndFooter(@NotNull Object audience, @NotNull Component header, @NotNull Component footer) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceSendPlayerListHeaderAndFooter).invoke(audience,
                this.adventure.getComponentMapper().map(header),
                this.adventure.getComponentMapper().map(footer)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceSendPlayerListHeaderAndFooter), audience, ex
            );
        }
    }

    /**
     * Send a title part and value to the given audience using reflection.
     *
     * @param audience Audience to invoke {@code sendTitlePart(TitlePart<T>, T)} on.
     * @param part     TitlePart to be mapped and sent to the audience.
     * @param value    TitlePart value to be mapped to be sent to the audience.
     * @param <T>      TitlePart value type.
     *
     * @throws IllegalStateException if {@code value} is not an instance of Component or
     *                               Title.Times.
     */
    public <T> void sendTitlePart(@NotNull Object audience, @NotNull TitlePart<T> part, @NotNull T value) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");
        Preconditions.checkArgument(
            value instanceof Component || value instanceof Title.Times,
            "unsupported TitlePart value type"
        );

        try {
            Object mappedValue;
            if (value instanceof Component) {
                mappedValue = this.adventure.getComponentMapper().map((Component) value);
            } else {
                mappedValue = this.adventure.getTimesMapper().map((Title.Times) value);
            }

            Objects.requireNonNull(this.audienceSendTitlePart).invoke(audience,
                this.adventure.getTitlePartMapper().map(part),
                mappedValue
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceSendTitlePart), audience, ex
            );
        }
    }

    /**
     * Clear the title for the given audience using reflection.
     *
     * @param audience Audience to invoke {@code clearTitle()} on.
     */
    public void clearTitle(@NotNull Object audience) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceClearTitleMethod).invoke(audience);
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceClearTitleMethod), audience, ex
            );
        }
    }

    /**
     * Reset the title for the given audience using reflection.
     *
     * @param audience Audience to invoke {@code resetTitle()} on.
     */
    public void resetTitle(@NotNull Object audience) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceResetTitleMethod).invoke(audience);
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceResetTitleMethod), audience, ex
            );
        }
    }

    /**
     * Show a boss bar to the given audience using reflection.
     *
     * @param audience Audience to invoke {@code showBossBar(BossBar)} on.
     * @param bar      BossBar to be mapped and shown to the given audience.
     */
    public void showBossBar(@NotNull Object audience, @NotNull BossBar bar) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceShowBossBarMethod).invoke(audience,
                this.adventure.getBossBarMapper().map(bar)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceShowBossBarMethod), audience, ex
            );
        }
    }

    /**
     * Hide a boss bar for the given audience using reflection.
     *
     * @param audience Audience to invoke {@code hideBossBar(BossBar)} on.
     * @param bar      BossBar to be mapped and hidden from the given audience.
     */
    public void hideBossBar(@NotNull Object audience, @NotNull BossBar bar) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceHideBossBarMethod).invoke(audience,
                this.adventure.getBossBarMapper().map(bar)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceHideBossBarMethod), audience, ex
            );
        }
    }

    /**
     * Play a sound to the given audience using reflection.
     *
     * @param audience Audience to invoke {@code playSound(Sound)} on.
     * @param sound    Sound to be mapped and played to the given audience.
     */
    public void playSound(@NotNull Object audience, @NotNull Sound sound) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audiencePlaySoundMethod).invoke(audience,
                this.adventure.getSoundMapper().map(sound)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audiencePlaySoundMethod), audience, ex
            );
        }
    }

    /**
     * Play a sound at a position to the given audience using reflection.
     *
     * @param audience Audience to invoke {@code playSound(Sound, double, double, double)} on.
     * @param sound    Sound to be mapped and played to the given audience at the position.
     * @param x        Position X coordinate.
     * @param y        Position Y coordinate.
     * @param z        Position Z coordinate.
     */
    public void playSound(@NotNull Object audience, @NotNull Sound sound, double x, double y, double z) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audiencePlaySoundPositionMethod).invoke(audience,
                this.adventure.getSoundMapper().map(sound), x, y, z
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audiencePlaySoundPositionMethod), audience, ex
            );
        }
    }

    /**
     * Play a sound from an emitter to the given audience using reflection.
     *
     * @param audience Audience to invoke {@code playSound(Sound, double, double, double)} on.
     * @param sound    Sound to be mapped and played to the given audience from the emitter.
     * @param emitter  Emitter to be mapped and play the sound from.
     *
     * @throws IllegalArgumentException if {@code emitter} is not {@code Sound.Emitter#self()}.
     */
    public void playSound(@NotNull Object audience, @NotNull Sound sound, @NotNull Sound.Emitter emitter) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");
        Preconditions.checkArgument(emitter.equals(Sound.Emitter.self()),
            "unsupported Sound.Emitter type"
        );

        try {
            Objects.requireNonNull(this.audiencePlaySoundEmitterMethod).invoke(audience,
                this.adventure.getSoundMapper().map(sound),
                Objects.requireNonNull(this.soundEmitterSelfMethod).invoke(null)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audiencePlaySoundEmitterMethod), audience, ex
            );
        }
    }

    /**
     * Stop playing a sound to the given audience using reflection.
     *
     * @param audience Audience to invoke {@code stopSound(StopSound)} on.
     * @param stop     SoundStop to be mapped and sent to the audience.
     */
    public void stopSound(@NotNull Object audience, @NotNull SoundStop stop) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceStopSoundMethod).invoke(audience,
                this.adventure.getSoundStopMapper().map(stop)
            );
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceStopSoundMethod), audience, ex
            );
        }
    }

    /**
     * Open a book for the given audience using reflection.
     *
     * @param audience Audience to invoke {@code openBook(Book)} on.
     * @param book     Book to be mapped and opened for the audience.
     */
    public void openBook(@NotNull Object audience, @NotNull Book book) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            Objects.requireNonNull(this.audienceOpenBookMethod).invoke(audience, this.adventure.getBookMapper().map(book));
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.audienceOpenBookMethod), audience, ex
            );
        }
    }

    /**
     * Get a pointer from the given pointered.
     *
     * @param pointered Pointered to invoke {@code getPointer(Pointer)} on.
     * @param pointer   Pointer to be mapped and retrieved from the given pointered.
     * @param <T>       Pointer type.
     *
     * @return an optional containing the pointer value, if found, otherwise an empty optional.
     */
    @SuppressWarnings("unchecked")
    public <T> @NotNull Optional<T> getPointer(@NotNull Object pointered, @NotNull Pointer<T> pointer) {
        Preconditions.checkState(isLoaded(), "AudienceReflection has not been loaded");

        try {
            return (Optional<T>) ((Optional<?>) Objects.requireNonNull(this.pointeredGetMethod).invoke(pointered,
                this.adventure.getPointerMapper().map(pointer)
            )).map(value -> {
                try {
                    if (value.getClass().getCanonicalName().startsWith(AdventureMapper.ORIGINAL_PACKAGE)) {
                        if (Class.forName(AdventureMapper.ORIGINAL_COMPONENT_CLASS_NAME).isInstance(value)) {
                            // Pointer value is a component, map it backwards.
                            return this.adventure.getComponentMapper().mapBackwards(value);
                        }

                        if (Class.forName(AdventureMapper.ORIGINAL_KEY_CLASS_NAME).isInstance(value)) {
                            // Pointer value is a key, map it backwards.
                            return this.adventure.getKeyMapper().mapBackwards(value);
                        }

                        // Pointer value is a platform adventure object, however it has not been mapped.
                        this.adventure.getChameleon().getInternalLogger().warn(
                            "Failed to map Adventure Pointer value back to shaded Adventure object. " +
                                "Please report this to the maintainers of https://github.com/ChameleonFramework/Chameleon so it can be properly mapped. " +
                                "If this is incorrect, you can safely ignore this message or report it to the maintainers of Chameleon so it can be resolved."
                        );
                    }

                    return value;
                } catch (ReflectiveOperationException ex) {
                    throw new ChameleonReflectiveException(ex);
                }
            });
        } catch (ReflectiveOperationException ex) {
            throw ChameleonReflectiveException.createMethodInvocationFailure(
                Objects.requireNonNull(this.pointeredGetMethod), pointered, ex
            );
        }
    }

}
