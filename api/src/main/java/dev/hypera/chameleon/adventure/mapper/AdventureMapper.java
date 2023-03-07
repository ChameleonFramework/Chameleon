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

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.adventure.AudienceReflection;
import dev.hypera.chameleon.adventure.ReflectedAudience;
import dev.hypera.chameleon.exception.reflection.ChameleonReflectiveException;
import dev.hypera.chameleon.util.Preconditions;
import java.util.concurrent.atomic.AtomicBoolean;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

/**
 * Adventure mapper.
 *
 * <p>Because Minestom, Sponge and Velocity natively use Adventure, we cannot use a relocated
 * version of Adventure without there being problems. To get around this we map the relocated
 * Adventure objects to platform objects using reflection.</p>
 */
@Experimental
public final class AdventureMapper {

    public static final @NotNull String ORIGINAL_PACKAGE = "net.ky".concat("ori.adventure.");
    public static final @NotNull String ORIGINAL_AUDIENCE_CLASS_NAME = ORIGINAL_PACKAGE.concat("audience.Audience");
    public static final @NotNull String ORIGINAL_BOOK_CLASS_NAME = ORIGINAL_PACKAGE.concat("inventory.Book");
    public static final @NotNull String ORIGINAL_BOSSBAR_CLASS_NAME = ORIGINAL_PACKAGE.concat("bossbar.BossBar");
    public static final @NotNull String ORIGINAL_CHAT_TYPE_CLASS_NAME = ORIGINAL_PACKAGE.concat("chat.ChatType");
    public static final @NotNull String ORIGINAL_CHAT_TYPE_BOUND_CLASS_NAME = ORIGINAL_PACKAGE.concat("chat.ChatType$Bound");
    public static final @NotNull String ORIGINAL_COMPONENT_CLASS_NAME = ORIGINAL_PACKAGE.concat("text.Component");
    public static final @NotNull String ORIGINAL_COMPONENT_LIKE_CLASS_NAME = ORIGINAL_PACKAGE.concat("text.ComponentLike");
    public static final @NotNull String ORIGINAL_GSON_COMPONENT_SERIALIZER_CLASS_NAME = ORIGINAL_PACKAGE.concat("text.serializer.gson.GsonComponentSerializer");
    public static final @NotNull String ORIGINAL_IDENTITY_CLASS_NAME = ORIGINAL_PACKAGE.concat("identity.Identity");
    public static final @NotNull String ORIGINAL_KEY_CLASS_NAME = ORIGINAL_PACKAGE.concat("key.Key");
    public static final @NotNull String ORIGINAL_KEYED_CLASS_NAME = ORIGINAL_PACKAGE.concat("key.Keyed");
    public static final @NotNull String ORIGINAL_POINTER_CLASS_NAME = ORIGINAL_PACKAGE.concat("pointer.Pointer");
    public static final @NotNull String ORIGINAL_POINTERED_CLASS_NAME = ORIGINAL_PACKAGE.concat("pointer.Pointered");
    public static final @NotNull String ORIGINAL_SIGNED_MESSAGE_CLASS_NAME = ORIGINAL_PACKAGE.concat("chat.SignedMessage");
    public static final @NotNull String ORIGINAL_SIGNED_MESSAGE_SIGNATURE_CLASS_NAME = ORIGINAL_PACKAGE.concat("chat.SignedMessage$Signature");
    public static final @NotNull String ORIGINAL_SOUND_CLASS_NAME = ORIGINAL_PACKAGE.concat("sound.Sound");
    public static final @NotNull String ORIGINAL_SOUND_EMITTER_CLASS_NAME = ORIGINAL_PACKAGE.concat("sound.Sound$Emitter");
    public static final @NotNull String ORIGINAL_SOUND_STOP_CLASS_NAME = ORIGINAL_PACKAGE.concat("sound.SoundStop");
    public static final @NotNull String ORIGINAL_TITLE_TIMES_CLASS_NAME = ORIGINAL_PACKAGE.concat("title.Title$Times");
    public static final @NotNull String ORIGINAL_TITLE_PART_CLASS_NAME = ORIGINAL_PACKAGE.concat("title.TitlePart");

    private final @NotNull Chameleon chameleon;
    private final @NotNull AtomicBoolean loaded = new AtomicBoolean(false);
    private final @NotNull ComponentMapper componentMapper = new ComponentMapper();
    private final @NotNull BookMapper bookMapper = new BookMapper(this.componentMapper);
    private final @NotNull BossBarMapper bossBarMapper = new BossBarMapper(this.componentMapper);
    private final @NotNull KeyMapper keyMapper = new KeyMapper();
    private final @NotNull ChatTypeMapper chatTypeMapper = new ChatTypeMapper(this.keyMapper);
    private final @NotNull BoundMapper boundMapper = new BoundMapper(this.chatTypeMapper, this.componentMapper);
    private final @NotNull IdentityMapper identityMapper = new IdentityMapper();
    private final @NotNull PointerMapper pointerMapper = new PointerMapper(this.keyMapper);
    private final @NotNull SignatureMapper signatureMapper = new SignatureMapper();
    private final @NotNull SoundMapper soundMapper = new SoundMapper(this.keyMapper);
    private final @NotNull SoundStopMapper soundStopMapper = new SoundStopMapper(this.keyMapper);
    private final @NotNull TimesMapper timesMapper = new TimesMapper();
    private final @NotNull TitlePartMapper titlePartMapper = new TitlePartMapper();
    private final @NotNull AudienceReflection audienceReflection = new AudienceReflection(this);

    /**
     * Adventure mapper constructor.
     *
     * @param chameleon Chameleon implementation.
     */
    @Internal
    public AdventureMapper(@NotNull Chameleon chameleon) {
        Preconditions.checkNotNull("chameleon", chameleon);
        this.chameleon = chameleon;
    }

    /**
     * Load all mappers.
     *
     * @throws ChameleonReflectiveException when an exception is thrown by a mapper's load method.
     */
    public void load() throws ReflectiveOperationException {
        Preconditions.checkState(!this.loaded.get(), "mappers have already been loaded");
        this.componentMapper.load();
        this.bookMapper.load();
        this.bossBarMapper.load();
        this.keyMapper.load();
        this.chatTypeMapper.load();
        this.boundMapper.load();
        this.identityMapper.load();
        this.pointerMapper.load();
        this.signatureMapper.load();
        this.soundMapper.load();
        this.soundStopMapper.load();
        this.timesMapper.load();
        this.titlePartMapper.load();
        this.audienceReflection.load();
        this.loaded.set(true);
    }

    /**
     * Create a new reflected audience wrapping the given platform audience.
     *
     * @param audience Platform audience to be wrapped.
     *
     * @return new reflected audience.
     */
    public @NotNull ReflectedAudience createReflectedAudience(@NotNull Object audience) {
        Preconditions.checkState(isLoaded(), "mapper has not been loaded");
        Preconditions.checkNotNull("audience", audience);
        return new ReflectedAudience(audience, this.audienceReflection);
    }

    /**
     * Get whether this mapper has been loaded.
     *
     * @return loaded.
     */
    public boolean isLoaded() {
        return this.loaded.get();
    }

    /**
     * Get the stored Chameleon implementation.
     *
     * @return stored Chameleon implementation.
     */
    public @NotNull Chameleon getChameleon() {
        return this.chameleon;
    }

    /**
     * Get the component mapper.
     *
     * @return component mapper.
     */
    public @NotNull ComponentMapper getComponentMapper() {
        return this.componentMapper;
    }

    /**
     * Get the book mapper.
     *
     * @return book mapper.
     */
    public @NotNull BookMapper getBookMapper() {
        return this.bookMapper;
    }

    /**
     * Get the boss bar mapper.
     *
     * @return boss bar mapper.
     */
    public @NotNull BossBarMapper getBossBarMapper() {
        return this.bossBarMapper;
    }

    /**
     * Get the key mapper.
     *
     * @return key mapper.
     */
    public @NotNull KeyMapper getKeyMapper() {
        return this.keyMapper;
    }

    /**
     * Get the chat type mapper.
     *
     * @return chat type mapper.
     */
    public @NotNull ChatTypeMapper getChatTypeMapper() {
        return this.chatTypeMapper;
    }

    /**
     * Get the bound mapper.
     *
     * @return bound mapper.
     */
    public @NotNull BoundMapper getBoundMapper() {
        return this.boundMapper;
    }

    /**
     * Get the identity mapper.
     *
     * @return identity mapper.
     */
    public @NotNull IdentityMapper getIdentityMapper() {
        return this.identityMapper;
    }

    /**
     * Get the pointer mapper.
     *
     * @return pointer mapper.
     */
    public @NotNull PointerMapper getPointerMapper() {
        return this.pointerMapper;
    }

    /**
     * Get the signature mapper.
     *
     * @return signature mapper.
     */
    public @NotNull SignatureMapper getSignatureMapper() {
        return this.signatureMapper;
    }

    /**
     * Get the sound mapper.
     *
     * @return sound mapper.
     */
    public @NotNull SoundMapper getSoundMapper() {
        return this.soundMapper;
    }

    /**
     * Get the sound stop mapper.
     *
     * @return sound stop mapper.
     */
    public @NotNull SoundStopMapper getSoundStopMapper() {
        return this.soundStopMapper;
    }

    /**
     * Get the times mapper.
     *
     * @return times mapper.
     */
    public @NotNull TimesMapper getTimesMapper() {
        return this.timesMapper;
    }

    /**
     * Get the title part mapper.
     *
     * @return title part mapper.
     */
    public @NotNull TitlePartMapper getTitlePartMapper() {
        return this.titlePartMapper;
    }

}
