<div align="center">
    <img src="https://i.hypera.dev/assets/chameleon@750x150.png" />
    <p><strong>Cross-platform Minecraft plugin framework</strong></p>
</div>

-----------

# Development Progress

|            Platform             | BungeeCord | Spigot | Velocity | Minestom | Sponge | Nukkit |
|:-------------------------------:|:----------:|:------:|:--------:|:--------:|:------:|:------:|
|       [Logging](#Logging)       |     ✓      |   ✓    |    ✓     |    ✓     |   ✓    |   ✓    |
|      [Commands](#Commands)      |     ✓      |   ✓    |    ✓     |    ✓     |        |        |
|        [Events](#Events)        |     ✓      |   ✓    |    ✓     |    ✓     |        |        |
|         [Users](#Users)         |     ✓      |  WIP   |    ✓     |   WIP    |        |        |
| [Configuration](#Configuration) |     ✓      |   ✓    |    ✓     |    ✓     |   ✓    |   ✓    |

### Extra Information

All examples below are taken
from [the example Chameleon project](https://github.com/ChameleonFramework/Example).

## Platforms

- [x] BungeeCord
- [x] Spigot
- [x] Velocity
- [x] Minestom
- [ ] Sponge
- [ ] Nukkit

## Logging

- [x] Info
- [x] Debug
- [x] Warning
- [x] Error

**ChameleonProject.java**

```java
@Override
public void onEnable() {
        // ...
        chameleon.getLogger().info("Hello %s!", "world");
        // ...
}
```

## Commands

- [x] Name
- [x] Aliases
- [x] Executing
- [x] Tab Complete

**ExampleCommand.java**

```java
@CommandHandler("example|ex")
public class ExampleCommand extends Command {

	public ExampleCommand() {
		setPermission(Permission.of("example.command", Component.text("No permission.", NamedTextColor.RED)));
		setConditions(Condition.of(c -> c.getSender() instanceof User, Component.text("This command can only be used in-game.", NamedTextColor.RED)));
		setPlatform(Platform.ALL);
	}

	@Override
	public void execute(@NotNull Context context) {
		context.getSender().sendMessage(Component.text("Hello, world!"));
	}

	@SubCommandHandler("sub|test")
	public void sub(@NotNull Context context) {
		if (context.getSender().hasPermission("example.test")) {
			context.getSender().sendMessage(Component.text("Hello, " + (context.getArgs().length > 0 ? context.getArgs()[0] : context.getSender().getName()) + "!"));
		} else {
			context.getSender().sendMessage(Component.text("No permission.", NamedTextColor.RED));
		}
	}

	@Override
	public @NotNull List<String> tabComplete(@NotNull Context context) {
		return Collections.singletonList("tabcomplete");
	}

}
```

**ChameleonProject.java**

```java
@Override
public void onEnable() {
	// ...
    chameleon.getCommandManager().register(new ExampleCommand());
    // ...
}
```

## Events

* [x] Event
* [x] Listener
* [x] Cross-platform events

**ExampleListener.java**

```java
public class ExampleListener implements ChameleonListener {

	@EventHandler
	public void onConnectEvent(@NotNull UserConnectEvent event) {
		event.getUser().sendMessage(Component.text("Welcome to my server!", NamedTextColor.GREEN));
	}

}
```

**ChameleonProject.java**

```java
@Override
public void onEnable() {
	// ...
	chameleon.getCommandManager().register(new ExampleCommand());
	chameleon.getEventManager().registerListener(UserDisconnectEvent.class, event -> chameleon.getLogger().info("%s left the server!", event.getUser().getName()));
	// ...
}
```

## Users

- [x] ChatUser
- [x] User
- [x] ProxyUser
- [ ] ServerUser (WIP)

## Configuration

- [x] Data folders
- [x] Config
- [x] YAML support
- [x] JSON support
- [x] Copy default from resources
- [ ] Setters
- [ ] Saving

```xml
<dependency>
  <groupId>dev.hypera.chameleon</groupId>
  <artifactId>feature-configuration</artifactId>
  <version>VERSION</version>
</dependency>
```

**ChameleonProject.java**

```java
private static Configuration yamlConfig;
private static Configuration jsonTest;

@Override
public void onEnable() {
        // ...
        yamlConfig = new YamlConfiguration(chameleon.getDataFolder(), "config.yml", true);
        jsonTest = new JsonConfiguration(chameleon.getDataFolder(), "test.json", true);
        // ...
}
```