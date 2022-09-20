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
package dev.hypera.example;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.annotations.PlatformDependency;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.events.EventSubscriber;
import dev.hypera.chameleon.events.EventSubscriptionPriority;
import dev.hypera.chameleon.events.common.UserConnectEvent;
import dev.hypera.chameleon.events.common.UserDisconnectEvent;
import dev.hypera.chameleon.logging.ChameleonLogger;
import dev.hypera.example.commands.ExampleCommand;
import java.time.Duration;
import java.time.Instant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Example {@link Chameleon} plugin.
 */
@Plugin(
        id = "chameleon-example",
        name = "ChameleonExample",
        version = "@version@", // @version@ is replaced by Gradle.
        description = "An example Chameleon plugin",
        url = "https://github.com/ChameleonFramework/Chameleon/example",
        authors = {"Joshua Sing", "SLLCoding"},
        dependencies = {
            @PlatformDependency(
                name = "LuckPerms",
                soft = true,
                platforms = { Plugin.Platform.BUKKIT }
            )
        },
        platforms = {} // If this is empty, all platforms will be supported.
)
public class ChameleonExample extends ChameleonPlugin {

    private static @Nullable ChameleonExample instance;

    private final @NotNull ChameleonLogger logger;

    /**
     * Example {@link Chameleon} plugin.
     *
     * @param chameleon chameleon.
     */
    public ChameleonExample(@NotNull Chameleon chameleon) {
        super(chameleon);
        this.logger = chameleon.getLogger();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEnable() {
        Instant start = Instant.now();
        this.logger.info("Starting...");

        instance = this;

        chameleon.getCommandManager().register(new ExampleCommand());

        chameleon.getEventBus().subscribe(UserConnectEvent.class, event -> {
            event.getUser().sendMessage(Component.text("Welcome to my server!", NamedTextColor.GREEN));
        });

        chameleon.getEventBus().subscribe(UserConnectEvent.class, EventSubscriber.builder(UserConnectEvent.class).expireAfter(1).handler(event -> {
            event.getUser().sendMessage(Component.text("Welcome, you're the first person to join since the last restart!", NamedTextColor.GOLD));
        }).priority(EventSubscriptionPriority.HIGH).build());

        chameleon.getEventBus().subscribe(UserDisconnectEvent.class, event -> {
            chameleon.getLogger().info("%s left the server", event.getUser().getName());
        });

        this.logger.info("Successfully started, took %s ms.", Duration.between(start, Instant.now()).toMillis());
        this.logger.info("Using Chameleon v%s!", Chameleon.getVersion());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisable() {

    }

    /**
     * Get the instance of this plugin.
     *
     * @return instance.
     */
    public static @NotNull ChameleonExample getInstance() {
        if (instance == null) throw new IllegalStateException("ChameleonExample has not started.");
        return instance;
    }

}
