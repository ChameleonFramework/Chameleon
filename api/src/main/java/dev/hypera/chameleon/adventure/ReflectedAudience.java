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

import dev.hypera.chameleon.util.Preconditions;
import java.util.Optional;
import java.util.function.Supplier;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.audience.ForwardingAudience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.chat.SignedMessage;
import net.kyori.adventure.identity.Identified;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.inventory.Book;
import net.kyori.adventure.pointer.Pointer;
import net.kyori.adventure.pointer.Pointers;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.SoundStop;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.TitlePart;
import org.jetbrains.annotations.ApiStatus.Experimental;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

/**
 * Reflected audience wrapper.
 */
@Internal
@Experimental
public final class ReflectedAudience implements ForwardingAudience.Single {

    private final @NotNull Object audience;
    private final @NotNull AudienceReflection audienceReflection;

    /**
     * Reflected audience constructor.
     *
     * @param audience           Audience to be wrapped.
     * @param audienceReflection Audience reflection helper.
     */
    public ReflectedAudience(@NotNull Object audience, @NotNull AudienceReflection audienceReflection) {
        this.audience = audience;
        this.audienceReflection = audienceReflection;
    }

    /**
     * Sends a system chat message to this Audience.
     *
     * @param message a message.
     */
    @Override
    public void sendMessage(@NotNull Component message) {
        Preconditions.checkNotNull("message", message);
        this.audienceReflection.sendMessage(this.audience, message);
    }

    /**
     * Sends a message to this Audience with the provided bound chat type.
     *
     * @param message       the component content.
     * @param boundChatType the bound chat type.
     */
    @Override
    public void sendMessage(@NotNull Component message, @NotNull ChatType.Bound boundChatType) {
        Preconditions.checkNotNull("message", message);
        Preconditions.checkNotNull("boundChatType", boundChatType);
        this.audienceReflection.sendMessage(this.audience, message, boundChatType);
    }

    /**
     * Sends an unsigned player chat message from the given Identified to this Audience with the
     * ChatType corresponding to the provided MessageType.
     *
     * @param source  The source of the message.
     * @param message A message.
     * @param type    The type.
     *
     * @deprecated for removal since Adventure 4.12.0.
     */
    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    public void sendMessage(@NotNull Identified source, @NotNull Component message, @NotNull net.kyori.adventure.audience.MessageType type) {
        sendMessage(source.identity(), message, type);
    }

    /**
     * Sends an unsigned player chat message from the given Identity to this Audience with the
     * ChatType corresponding to the provided MessageType.
     *
     * @param source  The source of the message.
     * @param message A message.
     * @param type    The type.
     *
     * @deprecated for removal since Adventure 4.12.0.
     */
    @Override
    @Deprecated
    @SuppressWarnings("deprecation")
    public void sendMessage(@NotNull Identity source, @NotNull Component message, @NotNull net.kyori.adventure.audience.MessageType type) {
        Preconditions.checkNotNull("source", source);
        Preconditions.checkNotNull("message", message);
        Preconditions.checkNotNull("type", type);
        this.audienceReflection.sendMessage(this.audience, source, message, type);
    }

    /**
     * Requests deletion of a message with the provided signature.
     *
     * @param signature The signature.
     */
    @Override
    public void deleteMessage(@NotNull SignedMessage.Signature signature) {
        Preconditions.checkNotNull("signature", signature);
        this.audienceReflection.deleteMessage(this.audience, signature);
    }

    /**
     * Sends a message on the action bar.
     *
     * @param message A message.
     */
    @Override
    public void sendActionBar(@NotNull Component message) {
        Preconditions.checkNotNull("message", message);
        this.audienceReflection.sendActionBar(this.audience, message);
    }

    /**
     * Sends the player list header.
     *
     * @param header The header.
     */
    @Override
    public void sendPlayerListHeader(@NotNull Component header) {
        Preconditions.checkNotNull("header", header);
        this.audienceReflection.sendPlayerListHeader(this.audience, header);
    }

    /**
     * Sends the player list footer.
     *
     * @param footer The footer.
     */
    @Override
    public void sendPlayerListFooter(@NotNull Component footer) {
        Preconditions.checkNotNull("footer", footer);
        this.audienceReflection.sendPlayerListFooter(this.audience, footer);
    }

    /**
     * Sends the player list header and footer.
     *
     * @param header The header.
     * @param footer The footer.
     */
    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
        Preconditions.checkNotNull("header", header);
        Preconditions.checkNotNull("footer", footer);
        this.audienceReflection.sendPlayerListHeaderAndFooter(this.audience, header, footer);
    }

    /**
     * Shows a part of a title.
     *
     * @param part  The part.
     * @param value The value.
     * @param <T>   The type of the value of the part.
     *
     * @throws IllegalArgumentException if a title part that is not in TitlePart is used.
     */
    @Override
    public <T> void sendTitlePart(@NotNull TitlePart<T> part, @NotNull T value) {
        Preconditions.checkNotNull("part", part);
        Preconditions.checkNotNull("value", value);
        this.audienceReflection.sendTitlePart(this.audience, part, value);
    }

    /**
     * Clears the title, if one is being displayed.
     */
    @Override
    public void clearTitle() {
        this.audienceReflection.clearTitle(this.audience);
    }

    /**
     * Resets the title and timings back to their default.
     */
    @Override
    public void resetTitle() {
        this.audienceReflection.resetTitle(this.audience);
    }

    /**
     * Shows a boss bar.
     *
     * @param bar A boss bar.
     */
    @Override
    public void showBossBar(@NotNull BossBar bar) {
        Preconditions.checkNotNull("bar", bar);
        this.audienceReflection.showBossBar(this.audience, bar);
    }

    /**
     * Hides a boss bar.
     *
     * @param bar A boss bar.
     */
    @Override
    public void hideBossBar(@NotNull BossBar bar) {
        Preconditions.checkNotNull("bar", bar);
        this.audienceReflection.hideBossBar(this.audience, bar);
    }

    /**
     * Plays a sound at the location of the recipient of the sound.
     *
     * @param sound A sound.
     */
    @Override
    public void playSound(@NotNull Sound sound) {
        Preconditions.checkNotNull("sound", sound);
        this.audienceReflection.playSound(this.audience, sound);
    }

    /**
     * Plays a sound at a location.
     *
     * @param sound A sound.
     * @param x     Position X coordinate.
     * @param y     Position Y coordinate.
     * @param z     Position Z coordinate.
     */
    @Override
    public void playSound(@NotNull Sound sound, double x, double y, double z) {
        Preconditions.checkNotNull("sound", sound);
        this.audienceReflection.playSound(this.audience, sound, x, y, z);
    }

    /**
     * Plays a sound from an emitter, usually an entity.
     *
     * @param sound   A sound.
     * @param emitter An emitter.
     */
    @Override
    public void playSound(@NotNull Sound sound, @NotNull Sound.Emitter emitter) {
        Preconditions.checkNotNull("sound", sound);
        Preconditions.checkNotNull("emitter", emitter);
        this.audienceReflection.playSound(this.audience, sound, emitter);
    }

    /**
     * Stops a sound, or many sounds.
     *
     * @param stop A sound stop.
     */
    @Override
    public void stopSound(@NotNull SoundStop stop) {
        Preconditions.checkNotNull("stop", stop);
        this.audienceReflection.stopSound(this.audience, stop);
    }

    /**
     * Opens a book.
     *
     * @param book A book.
     */
    @Override
    public void openBook(@NotNull Book book) {
        Preconditions.checkNotNull("book", book);
        this.audienceReflection.openBook(this.audience, book);
    }

    /**
     * Gets the audience.
     *
     * @return the audience.
     */
    @Override
    public @NotNull Audience audience() {
        return this;
    }

    /**
     * Gets the value of {@code pointer}.
     *
     * @param pointer The pointer.
     * @param <T>     The type.
     *
     * @return the value.
     */
    @Override
    public <T> @NotNull Optional<T> get(@NotNull Pointer<T> pointer) {
        return this.audienceReflection.getPointer(this.audience, pointer);
    }

    /**
     * Gets the value of {@code pointer}.
     *
     * @param pointer      The pointer.
     * @param defaultValue The default value.
     * @param <T>          The type.
     *
     * @return the value.
     */
    @Contract("_, null -> _; _, !null -> !null")
    @Override
    public <T> @Nullable T getOrDefault(@NotNull Pointer<T> pointer, @Nullable T defaultValue) {
        return get(pointer).orElse(defaultValue);
    }

    /**
     * Gets the value of {@code pointer}.
     *
     * @param pointer      The pointer.
     * @param defaultValue The default value supplier.
     * @param <T>          The type.
     *
     * @return the value.
     */
    @Override
    public <T> @UnknownNullability T getOrDefaultFrom(@NotNull Pointer<T> pointer, @NotNull Supplier<? extends T> defaultValue) {
        Preconditions.checkNotNull("defaultValue", defaultValue);
        return get(pointer).orElseGet(defaultValue);
    }

    /**
     * Gets the pointers for this object.
     *
     * @return the pointers.
     */
    @Override
    public @NotNull Pointers pointers() {
        return Pointers.empty(); // unsupported
    }

}
