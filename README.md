<div align="center">
  <a href="https://github.com/ChameleonFramework/Chameleon">
    <img src="https://i.hypera.dev/assets/chameleon@750x150.png" />
  </a>
  <p><strong>Cross-platform Minecraft plugin framework</strong></p>
</div>

<div align="center">
  <img alt="Checks" src="https://img.shields.io/github/checks-status/ChameleonFramework/Chameleon/main?color=17aaaa&style=for-the-badge">
  <img alt="Stable version" src="https://img.shields.io/badge/Stable-N/A-%2317aaaa?style=for-the-badge">
  <img alt="Latest version" src="https://img.shields.io/badge/dynamic/json?color=17aaaa&label=Latest&prefix=v&query=%24.version&url=https%3A%2F%2Frepo.hypera.dev%2Fapi%2Fmaven%2Flatest%2Fversion%2Fsnapshots%2Fdev%2Fhypera%2Fchameleon-api&style=for-the-badge"><br/>
  <img alt="License" src="https://img.shields.io/badge/License-MIT-%2317aaaa?style=for-the-badge">
  <img alt="Code quality" src="https://img.shields.io/codefactor/grade/github/ChameleonFramework/Chameleon/main?style=for-the-badge&color=%2317aaaa">
  <img alt="Code size" src="https://img.shields.io/github/languages/code-size/ChameleonFramework/Chameleon?color=17aaaa&style=for-the-badge">
  <img alt="Code lines" src="https://img.shields.io/tokei/lines/github/ChameleonFramework/Chameleon?label=Lines%20of%20code&style=for-the-badge&color=17aaaa">
</div>

## What is Chameleon?
Chameleon is a framework created to allow developers to easily create Minecraft plugins that work across multiple platforms.

### Supported Platforms
Chameleon plans to eventually support every platform in this list.  
If there's another platform that you think we should support, please create a feature request using issues.
- [x] Bukkit
- [x] BungeeCord
- [ ] Fabric
- [ ] Forge
- [x] Minestom
- [x] Nukkit
- [x] Sponge
- [x] Velocity


## Getting started
**Proper documentation is coming soon**.  
**You can find an Example Chameleon project [here](example)**.  
If you have any questions, feel free to ask in our [official Discord server](https://discord.hypera.dev/).

### Project structure
 - `chameleon-api` - Chameleon's API and core, most things happen here.
 - `chameleon-annotations` - Chameleon annotation-based platform class generator.
 - `chamleleon-example` - An example project for Chameleon.
 - `chameleon-platform-<name>` - Chameleon implementation for the named platform, supported:
   - `bukkit`
   - `bungeecord`
   - `minestom`
   - `nukkit`
   - `sponge`
   - `velocity`

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
    implementation("dev.hypera:chameleon-api:$chameleonVersion")
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
    implementation 'dev.hypera:chameleon-api:${chameleonVersion}'
    // Repeat the line below and replace <module> with the module you wish to use.
    implementation 'dev.hypera:chameleon-<module>:${chameleonVersion}'

   // If you wish to use the automatic platform main class generation:
   compileOnly 'dev.hypera:chameleon-annotations:${chameleonVersion}'
   annotationProcessor 'dev.hypera:chameleon-annotations:${chameleonVersion}'
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
        <artifactId>chameleon-api</artifactId>
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

Please read [CONTRIBUTING](CONTRIBUTING.md).

### License
The contents of this repository is licensed under the [MIT License](LICENSE).
