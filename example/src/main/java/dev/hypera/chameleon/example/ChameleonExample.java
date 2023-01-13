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
package dev.hypera.chameleon.example;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.annotations.Dependency;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.event.EventSubscriber;
import dev.hypera.chameleon.event.EventSubscriptionPriority;
import dev.hypera.chameleon.event.common.UserConnectEvent;
import dev.hypera.chameleon.event.common.UserDisconnectEvent;
import dev.hypera.chameleon.example.command.ExampleCommand;
import dev.hypera.chameleon.example.event.ExampleCustomEvent;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.scheduler.Schedule;
import dev.hypera.chameleon.scheduler.Task;
import java.time.Duration;
import java.time.Instant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

/**
 * Example Chameleon plugin.
 */
@Plugin(
        id = "chameleon-example",
        name = "ChameleonExample",
        version = "@version@", // @version@ is replaced by Gradle.
        description = "An example cross-platform plugin built with the Chameleon Framework!",
        url = "https://github.com/ChameleonFramework/Chameleon/",
        authors = {"Joshua Sing", "SLLCoding"},
        dependencies = {
            @Dependency(
                name = "LuckPerms",
                soft = true,
                platforms = { Platform.BUKKIT }
            )
        },
        platforms = {
            Platform.BUKKIT,
            Platform.BUNGEECORD,
            Platform.MINESTOM,
            Platform.NUKKIT,
            Platform.SPONGE,
            Platform.VELOCITY
        } // If this is empty, all platforms will be supported.
)
public final class ChameleonExample extends ChameleonPlugin {

    private final @NotNull ChameleonLogger logger;

    /**
     * Example Chameleon plugin constructor.
     *
     * @param chameleon Chameleon implementation.
     */
    public ChameleonExample(@NotNull Chameleon chameleon) {
        super(chameleon);
        this.logger = chameleon.getLogger();
    }

    /**
     * Called when the platform plugin is enabled.
     */
    @Override
    public void onEnable() {
        Instant start = Instant.now();
        this.logger.info("Starting...");

        /* Commands */
        chameleon.getCommandManager().register(new ExampleCommand());

        /* Events */
        // User connect event
        chameleon.getEventBus().subscribe(UserConnectEvent.class, event -> {
            event.getUser().sendMessage(Component.text(
                "Welcome to my server!", NamedTextColor.GREEN
            ));
        });

        // User connect event with an expiry after of 1 and HIGH priority.
        chameleon.getEventBus().subscribe(EventSubscriber.builder(UserConnectEvent.class)
            .expireAfter(1).handler(event -> {
                event.getUser().sendMessage(Component.text(
                    "Welcome, you're the first person to join since the last restart!",
                    NamedTextColor.GOLD
                ));
            }).priority(EventSubscriptionPriority.HIGH).build());

        // User disconnect event
        chameleon.getEventBus().subscribe(UserDisconnectEvent.class, event -> {
            this.logger.info("%s left the server", event.getUser().getName());
        });

        // Custom event
        chameleon.getEventBus().subscribe(ExampleCustomEvent.class, event -> {
            this.logger.info("Received example custom event! Hello, " + event.getName() + "!");
        });

        /* Scheduling */
        chameleon.getScheduler().schedule(Task.builder(() -> {
            this.logger.info("This plugin has been running for 10 seconds!");
        }).delay(Schedule.seconds(10)).build());

        chameleon.getScheduler().schedule(Task.builder(() -> {
            this.logger.info("This task will run twice!");
        }).delay(Schedule.seconds(2)).repeat(Schedule.seconds(5)).cancelAfter(2).build());

        this.logger.info("Successfully started ChameleonExample plugin, took %s ms.",
            Duration.between(start, Instant.now()).toMillis());
        this.logger.info("Running on %s (%s) v%s with Chameleon v%s!",
            chameleon.getPlatform().getName(), chameleon.getPlatform().getId(),
            chameleon.getPlatform().getVersion(), Chameleon.getVersion());
    }

    /**
     * Called when the platform plugin is disabled.
     */
    @Override
    public void onDisable() {
        this.logger.info("Goodbye");
    }

}
