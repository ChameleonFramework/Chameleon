# Development Progress
| Platform                                | BungeeCord | Spigot | Velocity | Minestom | Sponge |
|:---------------------------------------:|:----------:|:------:|:--------:|:--------:|:------:|
| [Commands](#Commands)                   | ✓          | ✓      | ✓        | ✓        |        |
| [Events](#Events)                       |            |        |          |          |        |
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
import dev.hypera.chameleon.core.users.ChatUser;
import net.kyori.adventure.text.Component;

import java.util.Collections;
import java.util.List;

public class ExampleCommand extends Command {

    public ExampleCommand() {
        super("example", "e");
    }

    @Override
    public boolean execute(ChatUser chatUser, String[] strings) {
        chatUser.sendMessage(Component.text("You ran the example command!"));
        return true;
    }

    @Override
    public List<String> tabComplete(ChatUser chatUser, String[] strings) {
        return Collections.singletonList("tabcomplete");
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
* [ ] Event
* [ ] Listener
* [ ] Cross-platform events

#### ExampleListener.java
```java
// Work in progress
```

#### ChameleonProject.java
```java
@Override
public void onEnable() {
    // Work in progress
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