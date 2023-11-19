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
package dev.hypera.example;

import dev.hypera.chameleon.Chameleon;
import dev.hypera.chameleon.ChameleonPlugin;
import dev.hypera.chameleon.annotations.Dependency;
import dev.hypera.chameleon.annotations.Plugin;
import dev.hypera.chameleon.event.EventSubscriber;
import dev.hypera.chameleon.event.EventSubscriptionPriority;
import dev.hypera.chameleon.event.common.UserConnectEvent;
import dev.hypera.chameleon.event.common.UserDisconnectEvent;
import dev.hypera.chameleon.logger.ChameleonLogger;
import dev.hypera.chameleon.meta.MetadataKey;
import dev.hypera.chameleon.platform.Platform;
import dev.hypera.chameleon.platform.PlatformPlugin;
import dev.hypera.chameleon.scheduler.Schedule;
import dev.hypera.chameleon.scheduler.Task;
import dev.hypera.example.command.ExampleCommand;
import dev.hypera.example.event.ExampleCustomEvent;
import java.time.Duration;
import java.time.Instant;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.jetbrains.annotations.NotNull;

/**
 * Example Chameleon plugin.
 *
 * <p>This example uses the annotation-based platform class generator. This is not required, and
 * you can manually create each platform class, which gives you a lot more control over
 * platform-specific features.</p>
 */
@Plugin(
    id = "chameleon-example",
    name = "ChameleonExample",
    version = "@version@", // @version@ is replaced by Gradle at build time.
    description = "An example cross-platform plugin built with the Chameleon Framework!",
    url = "https://github.com/ChameleonFramework/Chameleon/",
    authors = { "Joshua Sing", "LooFifteen" },
    dependencies = {
        @Dependency(
            name = "LuckPerms",
            soft = true,
            platforms = { Platform.BUKKIT, Platform.BUNGEECORD }
        )
    },
    platforms = { // If this is empty, all platforms will be supported.
        Platform.BUKKIT,
        Platform.BUNGEECORD,
        Platform.NUKKIT,
        Platform.SPONGE,
        Platform.VELOCITY
    },
    // You can use custom bootstraps to initialise your plugin.
    // If you do not provide one here, the default one will be used, and a public constructor with
    // a single Chameleon parameter will be required.
    bootstrap = ChameleonExampleBootstrap.class
)
public final class ChameleonExample implements ChameleonPlugin {

    public static final @NotNull MetadataKey<Boolean> FIRST_PLAYER = MetadataKey.bool("example:first_player");

    private final @NotNull Chameleon chameleon;
    private final @NotNull ChameleonLogger logger;

    /**
     * Example Chameleon plugin constructor.
     *
     * @param chameleon Chameleon implementation.
     */
    public ChameleonExample(@NotNull Chameleon chameleon) {
        this.chameleon = chameleon;
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
        this.chameleon.getCommandManager().register(new ExampleCommand());

        /* Events */
        // User connect event
        this.chameleon.getEventBus().subscribe(UserConnectEvent.class, event -> {
            event.getUser().sendMessage(Component.text("Welcome to my server!", NamedTextColor.GREEN));

            // Dispatch a custom event
            this.chameleon.getEventBus().dispatch(new ExampleCustomEvent(event.getUser().getName()));
        });

        // User connect event with an expiry after of 1 and HIGH priority.
        this.chameleon.getEventBus().subscribe(EventSubscriber.builder(UserConnectEvent.class)
            .expireAfter(1).handler(event -> {
                event.getUser().sendMessage(Component.text(
                    "Welcome, you're the first person to join since the last restart!",
                    NamedTextColor.GOLD
                ));

                // Set example metadata
                event.getUser().setMetadata(FIRST_PLAYER, true);
            }).priority(EventSubscriptionPriority.HIGH).build());

        // User disconnect event
        this.chameleon.getEventBus().subscribe(UserDisconnectEvent.class, event ->
            this.logger.info("{} left the server", event.getUser().getName())
        );

        // Custom event
        this.chameleon.getEventBus().subscribe(ExampleCustomEvent.class, event ->
            this.logger.info("Received example custom event! Hello, {}!", event.getName())
        );

        /* Scheduling */
        this.chameleon.getScheduler().schedule(Task.builder(() ->
            this.logger.info("This plugin has been running for 10 seconds!")
        ).delay(Schedule.seconds(10)).build());

        this.chameleon.getScheduler().schedule(Task.builder(() ->
            this.logger.info("This task will run twice!")
        ).delay(Schedule.seconds(2)).repeat(Schedule.seconds(5)).cancelAfter(2).build());

        /* Plugin Management */
        for (PlatformPlugin plugin : this.chameleon.getPluginManager().getPlugins()) {
            this.logger.info("Found plugin {} v{}", plugin.getName(), plugin.getVersion());
        }

        this.logger.info(
            "Successfully started ChameleonExample plugin, took {} ms.",
            Duration.between(start, Instant.now()).toMillis()
        );
        this.logger.info(
            "Running on {} ({}) v{} with Chameleon v{} (git: {}, {})",
            this.chameleon.getPlatform().getName(), this.chameleon.getPlatform().getId(),
            this.chameleon.getPlatform().getVersion(), Chameleon.getVersion(),
            Chameleon.getBranch(), Chameleon.getShortCommitHash()
        );
    }

    /**
     * Called when the platform plugin is disabled.
     */
    @Override
    public void onDisable() {
        this.logger.info("Goodbye");
    }

}
