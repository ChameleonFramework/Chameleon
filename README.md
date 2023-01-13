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

> **Warning** - This framework is currently considered unstable, however the authors use it in
> production.  
> Until a stable release is made, the codebase is subject to major/breaking changes without warning.

Chameleon is a framework created to allow developers to easily create Minecraft plugins that work
across multiple platforms.

### Supported Platforms

> **Know another platform we could support?**  
> Let us know by creating a *feature request issue* or
> sending a message in our [official Discord server](https://discord.hypera.dev/)!

> **Warning** - Platforms marked with *Unstable* often have breaking API changes and Chameleon may
> stop working on newer versions. If you experience an issue using any platform, please create a
> bug report, so we can resolve the issue.

 - **Bukkit** - `dev.hypera:chameleon-platform-bukkit`
 - **BungeeCord** - `dev.hypera:chameleon-platform-bungeecord`
 - *Fabric - `dev.hypera:chameleon-platform-fabric` *Coming soon**
 - *Forge - `dev.hypera:chameleon-platform-forge` *Coming soon**
 - **Minestom** - `dev.hypera:chameleon-platform-minestom` *Unstable*
 - **Nukkit** - `dev.hypera:chameleon-platform-nukkit`
 - **Sponge** - `dev.hypera:chameleon-platform-sponge` *Unstable*
 - **Velocity** - `dev.hypera:chameleon-platform-velocity`

## Getting started

**Proper documentation is coming soon**.  
**You can find an Example Chameleon project [here](example)**.  
If you have any questions, feel free to ask in
our [official Discord server](https://discord.hypera.dev/).

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

`chameleon-annotations` can be used to automatically generate the main classes that each platform
requires in-order to load the plugin then start Chameleon.  
To use this, all you have to do is add the `chameleon-annotations` module as a compileOnly
dependency and annotationProcessor (gradle) or provided dependency (maven) and add the `@Plugin`
annotation to your ChameleonPlugin class.

```java
@Plugin(id = "myplugin", name = "MyPlugin")
```

### Dependencies

You can find the latest versions at the top of this file.

#### Gradle (Kotlin)

```kotlin
repositories {
    // If using a release:
    mavenCentral()

    // If using a snapshot:
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
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
    // If using a release:
    mavenCentral()

    // If using a snapshot:
    maven { url 'https://s01.oss.sonatype.org/content/repositories/snapshots/' }
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
  <!-- If using a snapshot: -->
  <repository>
      <id>sonatype-oss-s01</id>
      <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
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

Please read [the Contributing guide](CONTRIBUTING.md).

### Building

You can build this project by running `./gradlew build` in the project root directory, or if you use
Microsoft Windows, you can run `./gradlew.bat build`.
If you have Gradle 7.0+ installed, you can simply run the `gradle build` command in the project root
directory.

To publish the built artifacts to your local maven repository, add `publishToMavenLocal` after
the `build` command.

While the project can run on Java 8 or above, to build the project you will need Java 17 or above
due to several platforms we support requiring modern versions of Java.

### License

The contents of this repository is licensed under the [MIT License](LICENSE).

## Thank you

This project was made possible by
the [amazing people who have contributed](https://github.com/ChameleonFramework/Chameleon/graphs/contributors)
and the people who have supported us in other ways whether it be providing feedback or donating to
the developers.

### Supporters

People and companies who have donated or provided us with something that aids us in continuing this
project.  
<a href="https://jb.gg/OpenSourceSupport"><img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_square.svg" alt="JetBrains" height="82"/></a>
<a href="https://www.macstadium.com/"><img src="https://uploads-ssl.webflow.com/5ac3c046c82724970fc60918/5c019d917bba312af7553b49_MacStadium-developerlogo.png" alt="MacStadium" height="82"/></a>

### Plugins

Plugins or resources that have been created using Chameleon.

- [UltraStaffChatPro](https://www.spigotmc.org/resources/80461/) v2.0.0+
