<div align="center">
  <a href="https://github.com/ChameleonFramework/Chameleon">
    <img src="https://i.hypera.dev/assets/chameleon@750x150.png" />
  </a>
  <p><strong>Cross-platform Minecraft plugin framework</strong></p>
</div>

<div align="center">
  <img alt="Stable version" src="https://img.shields.io/badge/Stable-N/A-%2317aaaa?style=for-the-badge">
  <img alt="Latest version" src="https://img.shields.io/badge/dynamic/xml?color=%2317aaaa&label=Latest&query=%2F%2Fmetadata%2Fversioning%2Flatest&style=for-the-badge&url=https%3A%2F%2Frepo.hypera.dev%2Fsnapshots%2Fdev%2Fhypera%2Fchameleon-core%2Fmaven-metadata.xml"><br/>
  <img alt="License" src="https://img.shields.io/badge/License-MIT-%2317aaaa?style=for-the-badge">
  <img alt="Checks" src="https://img.shields.io/github/checks-status/ChameleonFramework/Chameleon/main?color=17aaaa&style=for-the-badge">
  <img alt="Code quality" src="https://img.shields.io/codefactor/grade/github/ChameleonFramework/Chameleon/main?style=for-the-badge&color=%2317aaaa">
  <img alt="Code size" src="https://img.shields.io/github/languages/code-size/ChameleonFramework/Chameleon?color=17aaaa&style=for-the-badge">
</div>

## What is Chameleon?
Chameleon is a framework created to allow developers to easily create Minecraft plugins that work across multiple platforms.

### Supported Platforms
Chameleon plans to support every platform in this list. If you have ideas for other platforms that we could support, please suggest them in our [official Discord server][discord].
- [x] Bukkit
- [x] BungeeCord
- [x] Velocity
- [x] Minestom
- [ ] Fabric
- [ ] Sponge
- [ ] Nukkit


## Getting started
**Proper documentation is coming soon**.  
**You can find an Example Chameleon project [here][example]**.  
If you have any questions, feel free to ask in our [official Discord server][discord].

### Project structure
 - `chameleon-core` - Chameleon's core, most things happen here.
 - `chameleon-annotations` - Chameleon annotation-based platform class generator.
 - `chameleon-feature-configuration` - An easy-to-use configuration library, can be used without Chameleon.
 - `chameleon-platform-<name>` - Chameleon implementation for the named platform, supported:
   - `bukkit`
   - `bungeecord`
   - `velocity`
   - `minestom`

### Annotation-based platform class generator
`chameleon-annotations` can be used to automatically generate the main classes that each platform requires in-order to load the plugin then start Chameleon.  
To use this, all you have to do is add the `chameleon-annotations` module as a compileOnly dependency and annotationProcessor (gradle) or provided dependency (maven) and add the `@Plugin` annotation to your ChameleonPlugin class.  
```java
@Plugin(
    id = "myplugin",
    name = "MyPlugin"
)
```

### Dependencies
You can find the latest versions at the top of this file.

#### Gradle (Kotlin)
```kotlin
repositories {
    // Replace 'snapshots' with 'releases' to use a non-snapshot version.
    maven("https://repo.hypera.dev/snapshots/")
}

dependencies {
    val chameleonVersion = "<version>"
    implementation("dev.hypera:chameleon-core:$chameleonVersion")
    // Repeat the line below and replace <module> with the module you wish to use.
    implementation("dev.hypera:chameleon-<module>:$chameleonVersion")
   
   // If you wish to use the automatic platform main class generation:
   compileOnly("dev.hypera:chameleon-annotations:$chameleonVersion")
   annotationProcessor("dev.hypera:chameleon-annotations:$chameleonVersion")
}
```

#### Gradle (Groovy)
```groovy
repositories {
    // Replace 'snapshots' with 'releases' to use a non-snapshot version.
    maven { url 'https://repo.hypera.dev/snapshots/' }
}

dependencies {
    def chameleonVersion = '<version>'
    implementation 'dev.hypera:chameleon-core:$chameleonVersion'
    // Repeat the line below and replace <module> with the module you wish to use.
    implementation 'dev.hypera:chameleon-<module>:$chameleonVersion'

   // If you wish to use the automatic platform main class generation:
   compileOnly 'dev.hypera:chameleon-annotations:$chameleonVersion'
   annotationProcessor 'dev.hypera:chameleon-annotations:$chameleonVersion'
}
```

#### Maven
```xml
<properties>
   <chameleon.version>VERSION</chameleon.version>
</properties>

<repositories>
    <!-- Replace 'snapshots' with 'releases' to use a non-snapshot version. -->
    <repository>
        <id>hypera-snapshots</id>
        <url>https://repo.hypera.dev/snapshots/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>dev.hypera</groupId>
        <artifactId>chameleon-core</artifactId>
        <version>${chameleon.version}</version>
    </dependency>

    <!-- Repeat the block below and replace 'MODULE' with the module you wish to use. -->
    <dependency>
       <groupId>dev.hypera</groupId>
       <artifactId>chameleon-MODULE</artifactId>
       <version>${chameleon.version}</version>
    </dependency>

    <!-- If you wish to use the automatic platform main class generation: -->
    <dependency>
       <groupId>dev.hypera</groupId>
       <artifactId>chameleon-annotations</artifactId>
       <version>${chameleon.version}</version>
       <scope>provided</scope>
    </dependency>
</dependencies>
```

## Contributing

Please read [CONTRIBUTING][contributing].

### License
The contents of this repository is licensed under the [MIT License](LICENSE).

[example]: https://github.com/ChameleonFramework/Example
[discord]: https://discord.hypera.dev/
[contributing]: CONTRIBUTING.md