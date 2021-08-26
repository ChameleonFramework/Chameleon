<div align="center">
    <img src="https://i.hypera.dev/assets/chameleon@750x150.png" />
    <p><strong>Cross-platform Minecraft plugin framework</strong></p>
</div>

-----------



# Development Progress
| Platform                                | BungeeCord | Spigot | Velocity | Minestom | Sponge |
|:---------------------------------------:|:----------:|:------:|:--------:|:--------:|:------:|
| [Commands](#Commands)                   | ✓          | ✓      | ✓        | ✓        |        |
| [Events](#Events)                       | ✓          | ✓      | ✓        | ✓        |        |
| [Users](#Users)                         |            |        |          |          |        |
| [Configuration](#Configuration)         | ✓          | ✓      | ✓        | ✓        |        |

### Extra Information
All examples below are taken from [the example Chameleon project](https://github.com/HyperaOfficial/ChameleonProject).

## Commands
* [x] Name
* [x] Aliases
* [x] Executing
* [x] Tab Complete

#### ExampleCommand.java
```java
package org.example.chameleonproject.commands;

import dev.hypera.chameleon.core.commands.Command;
import dev.hypera.chameleon.core.objects.Platform;
import dev.hypera.chameleon.core.users.ChatUser;
import java.util.Collections;
import java.util.List;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public class ExampleCommand extends Command {

	@Override
	public void execute(ChatUser chatUser, String[] args) {
		chatUser.sendMessage(Component.text("You ran the example command!"));
	}

	@Override
	public List<String> tabComplete(ChatUser chatUser, String[] args) {
		return Collections.singletonList("tabcomplete");
	}

	@Override
	public @NotNull String getName() {
		return "example";
	}

	@Override
	public @NotNull List<String> getAliases() {
		return Collections.singletonList("ex");
	}

	@Override
	public @NotNull Platform getPlatform() {
		return Platform.ALL;
	}

}
```

#### ChameleonProject.java
```java
@Override
public void onEnable() {
    // ...
    chameleon.registerCommand(new ExampleCommand());
    // ...
}
```

## Events
* [x] Event
* [x] Listener
* [x] Cross-platform events

#### ExampleListener.java
```java
package org.example.chameleonproject.events;

import dev.hypera.chameleon.core.events.impl.common.UserJoinEvent;
import dev.hypera.chameleon.core.events.listener.ChameleonListener;
import dev.hypera.chameleon.core.events.listener.EventHandler;
import net.kyori.adventure.text.Component;

public class ExampleListener implements ChameleonListener {

	@EventHandler
	public void onJoin(UserJoinEvent event) {
		event.getPlayer().sendMessage(Component.text("Welcome to my server!"));
	}

}
```

#### ChameleonProject.java
```java
@Override
public void onEnable() {
    // ...
    chameleon.getEventDispatcher().registerListener(new ExampleListener(this));
    // ...
}
```

## Users
* [x] ChatUser
* [ ] User
* [ ] ProxyUser
* [ ] ServerUser
* [x] AudienceWrapper

## Configuration
* [x] Data folders
* [x] Config
* [x] Yaml support
* [x] Json support
* [x] Copy default from resources
* [ ] Setters
* [ ] Saving

#### ChameleonProject.java
```java
private static Configuration yamlConfig;
private static Configuration jsonTest;

@Override
public void onEnable() {
    // ...
    yamlConfig = new YamlConfiguration(chameleon, "config.yml", true);
    jsonTest = new JsonConfiguration(chameleon, "test.json", true);
    // ...
}
```