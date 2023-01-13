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
package dev.hypera.chameleon.platform.nukkit.user;

import cn.nukkit.Player;
import cn.nukkit.network.protocol.SetTitlePacket;
import dev.hypera.chameleon.platform.server.GameMode;
import dev.hypera.chameleon.user.ServerUser;
import dev.hypera.chameleon.util.Preconditions;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.kyori.adventure.audience.Audience;
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
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.kyori.adventure.title.Title.Times;
import net.kyori.adventure.title.TitlePart;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

/**
 * Nukkit server user implementation.
 */
@Internal
public final class NukkitUser implements ServerUser {

    private final @NotNull Player player;
    private final @NotNull Pointers pointers;

    /**
     * Nukkit user constructor.
     *
     * @param player Player to be wrapped.
     */
    public NukkitUser(@NotNull Player player) {
        this.player = player;
        this.pointers = Pointers.builder()
            .withDynamic(Identity.NAME, player::getName)
            .withDynamic(Identity.UUID, player::getUniqueId)
            .withDynamic(Identity.DISPLAY_NAME, () ->
                LegacyComponentSerializer.legacySection().deserialize(player.getDisplayName()))
            .withDynamic(Identity.LOCALE, player::getLocale)
            .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull String getName() {
        return this.player.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasInteractiveChat() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull UUID getId() {
        return this.player.getUniqueId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull Optional<SocketAddress> getAddress() {
        return Optional.ofNullable(this.player.getSocketAddress());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getLatency() {
        return this.player.getPing();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendData(@NotNull String channel, byte[] data) {
        // Minecraft Bedrock does not support plugin messages.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disconnect(@NotNull Component reason) {
        Preconditions.checkNotNull("reason", reason);
        this.player.kick(LegacyComponentSerializer.legacySection().serialize(reason));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasPermission(@NotNull String permission) {
        Preconditions.checkNotNull("permission", permission);
        return this.player.hasPermission(permission);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public @NotNull GameMode getGameMode() {
        return convertGameModeToChameleon(this.player.getGamemode());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGameMode(@NotNull GameMode gameMode) {
        Preconditions.checkNotNull("gameMode", gameMode);
        this.player.setGamemode(convertGameModeToNukkit(gameMode));
    }

    /**
     * Filters this audience.
     *
     * @param filter a filter that determines if an audience should be included.
     *
     * @return an audience.
     */
    @Override
    public @NotNull Audience filterAudience(@NotNull Predicate<? super Audience> filter) {
        Preconditions.checkNotNull("filter", filter);
        return filter.test(this) ? this : Audience.empty();
    }

    /**
     * Executes an action against all audiences.
     *
     * @param action the action.
     */
    @Override
    public void forEachAudience(@NotNull Consumer<? super Audience> action) {
        Preconditions.checkNotNull("action", action);
        action.accept(this);
    }

    /**
     * Sends a system chat message to this Audience.
     *
     * @param message a message.
     */
    @Override
    public void sendMessage(@NotNull Component message) {
        Preconditions.checkNotNull("message", message);
        this.player.sendMessage(LegacyComponentSerializer.legacySection().serialize(message));
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
        this.player.sendMessage(LegacyComponentSerializer.legacySection().serialize(message));
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
        this.player.sendMessage(LegacyComponentSerializer.legacySection().serialize(message));
    }

    /**
     * Requests deletion of a message with the provided signature.
     *
     * @param signature The signature.
     */
    @Override
    public void deleteMessage(@NotNull SignedMessage.Signature signature) {
        // unsupported
    }

    /**
     * Sends a message on the action bar.
     *
     * @param message A message.
     */
    @Override
    public void sendActionBar(@NotNull Component message) {
        Preconditions.checkNotNull("message", message);
        this.player.sendActionBar(LegacyComponentSerializer.legacySection().serialize(message));
    }

    /**
     * Sends the player list header.
     *
     * @param header The header.
     */
    @Override
    public void sendPlayerListHeader(@NotNull Component header) {
        // unsupported
    }

    /**
     * Sends the player list footer.
     *
     * @param footer The footer.
     */
    @Override
    public void sendPlayerListFooter(@NotNull Component footer) {
        // unsupported
    }

    /**
     * Sends the player list header and footer.
     *
     * @param header The header.
     * @param footer The footer.
     */
    @Override
    public void sendPlayerListHeaderAndFooter(@NotNull Component header, @NotNull Component footer) {
        // unsupported
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

        if (value instanceof Component) {
            if (part.equals(TitlePart.TITLE)) {
                SetTitlePacket packet = new SetTitlePacket();
                packet.type = SetTitlePacket.TYPE_TITLE;
                packet.text = LegacyComponentSerializer.legacySection().serialize((Component) value);
                this.player.dataPacket(packet);
                return;
            }

            if (part.equals(TitlePart.SUBTITLE)) {
                SetTitlePacket packet = new SetTitlePacket();
                packet.type = SetTitlePacket.TYPE_SUBTITLE;
                packet.text = LegacyComponentSerializer.legacySection().serialize((Component) value);
                this.player.dataPacket(packet);
            }

            return;
        }

        if (value instanceof Times && part.equals(TitlePart.TIMES)) {
            Times times = (Times) value;
            SetTitlePacket packet = new SetTitlePacket();
            packet.type = SetTitlePacket.TYPE_ANIMATION_TIMES;
            packet.fadeInTime = (int) times.fadeIn().getSeconds();
            packet.stayTime = (int) times.stay().getSeconds();
            packet.fadeOutTime = (int) times.fadeOut().getSeconds();
            this.player.dataPacket(packet);
        }

        throw new IllegalArgumentException("Invalid title part");
    }

    /**
     * Clears the title, if one is being displayed.
     */
    @Override
    public void clearTitle() {
        this.player.clearTitle();
    }

    /**
     * Resets the title and timings back to their default.
     */
    @Override
    public void resetTitle() {
        this.player.resetTitleSettings();
    }

    /**
     * Shows a boss bar.
     *
     * @param bar A boss bar.
     */
    @Override
    public void showBossBar(@NotNull BossBar bar) {
        // unsupported, might be possible in the future?
    }

    /**
     * Hides a boss bar.
     *
     * @param bar A boss bar.
     */
    @Override
    public void hideBossBar(@NotNull BossBar bar) {
        // unsupported, might be possible in the future?
    }

    /**
     * Plays a sound at the location of the recipient of the sound.
     *
     * @param sound A sound.
     */
    @Override
    public void playSound(@NotNull Sound sound) {
        // unsupported, might be possible in the future?
        // requires java <-> bedrock sound mappings
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
        // unsupported, might be possible in the future?
        // requires java <-> bedrock sound mappings
    }

    /**
     * Plays a sound from an emitter, usually an entity.
     *
     * @param sound   A sound.
     * @param emitter An emitter.
     */
    @Override
    public void playSound(@NotNull Sound sound, @NotNull Sound.Emitter emitter) {
        // unsupported, might be possible in the future?
        // requires java <-> bedrock sound mappings
    }

    /**
     * Stops a sound, or many sounds.
     *
     * @param stop A sound stop.
     */
    @Override
    public void stopSound(@NotNull SoundStop stop) {
        // unsupported, might be possible in the future?
        // requires java <-> bedrock sound mappings
    }

    /**
     * Opens a book.
     *
     * @param book A book.
     */
    @Override
    public void openBook(@NotNull Book book) {
        // unsupported, might be possible in the future?
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
        return pointers().get(pointer);
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
        return pointers().getOrDefault(pointer, defaultValue);
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
        return pointers().getOrDefaultFrom(pointer, defaultValue);
    }

    /**
     * Gets the pointers for this object.
     *
     * @return the pointers.
     */
    @Override
    public @NotNull Pointers pointers() {
        return this.pointers;
    }

    private int convertGameModeToNukkit(@NotNull GameMode gameMode) {
        switch (gameMode) {
            case CREATIVE:
                return Player.CREATIVE;
            case ADVENTURE:
                return Player.ADVENTURE;
            case SPECTATOR:
                return Player.SPECTATOR;
            default:
                return Player.SURVIVAL;
        }
    }

    private @NotNull GameMode convertGameModeToChameleon(int gameMode) {
        switch (gameMode) {
            case Player.CREATIVE:
                return GameMode.CREATIVE;
            case Player.ADVENTURE:
                return GameMode.ADVENTURE;
            case Player.SPECTATOR:
                return GameMode.SPECTATOR;
            default:
                return GameMode.SURVIVAL;
        }
    }

}
