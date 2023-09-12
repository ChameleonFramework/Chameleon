<div align="center">
  <a href="https://github.com/ChameleonFramework/Chameleon">
    <img alt="Chameleon Logo" src="https://assets.hypera.dev/chameleon@750x150.png" />
  </a>
  <p><strong>Cross-platform Minecraft plugin framework</strong></p>
</div>

<div align="center">
  <img alt="License" src="https://img.shields.io/badge/License-MIT-%2317aaaa?style=for-the-badge">
  <img alt="Code quality" src="https://img.shields.io/codefactor/grade/github/ChameleonFramework/Chameleon/main?style=for-the-badge&color=%2317aaaa">
  <img alt="Test coverage" src="https://img.shields.io/codecov/c/github/ChameleonFramework/Chameleon?style=for-the-badge&color=%2317aaaa" /><br/>
  <img alt="Latest release" src="https://img.shields.io/maven-central/v/dev.hypera/chameleon-api?color=%2317aaaa&label=Latest%20Release&style=for-the-badge">
  <img alt="Latest version" src="https://img.shields.io/maven-metadata/v?color=%2317aaaa&label=Latest%20Snapshot&metadataUrl=https%3A%2F%2Fs01.oss.sonatype.org%2Fcontent%2Frepositories%2Fsnapshots%2Fdev%2Fhypera%2Fchameleon-api%2Fmaven-metadata.xml&style=for-the-badge">
</div>
<br/>
<details>
  <summary>Table of Contents</summary>

  * [What is Chameleon?](#what-is-chameleon)
    * [Stability](#stability)
    * [Supported Platforms](#supported-platforms)
  * [Getting started](#getting-started)
    * [Project structure](#project-structure)
    * [Annotation-based platform class generator](#annotation-based-platform-class-generator)
    * [Dependencies](#dependencies)
      * [Java 11](#java-11)
      * [Gradle](#gradle)
      * [Maven](#maven)
  * [Contributing](#contributing)
    * [Building](#building)
    * [Contact](#contact)
    * [License](#license)
  * [Acknowledgements](#acknowledgements)
    * [Supporters](#supporters)
    * [Plugins using Chameleon](#plugins-using-chameleon)
</details>

## What is Chameleon?

Chameleon is a framework created to allow developers to easily create Minecraft plugins that work
across multiple platforms.

### Stability

While still in its development phrase, Chameleon is being actively used by the authors in production.
However, until a stable release is made, the codebase is subject to major and potentially breaking
changes without warning.

When we make a breaking change, we will try our best to change the version so that existing projects
will not be affected until they update to a newer version.

Use of the Chameleon Framework in production environments, or for any other purpose, is entirely at
your own risk.

### Supported Platforms

Chameleon supports a variety of platforms.
Do you know of another platform that we could support? Let us know by creating a feature request
issue or sending a message in our [official Discord server](https://discord.hypera.dev/)!

Please note that platforms marked as *Unstable* often experience breaking API changes that may
impact the functionality of Chameleon. If you experience any issues with any of the platforms,
please create a bug report, so we can address the issue.

In the event that maintaining compatibility with a platform's API becomes unfeasible or for any
other reason a platform is deemed no longer worth supporting, we reserve the right to discontinue
support for the platform without prior notice.

| Platform Name  | Dependency                                 | Status       |
|----------------|--------------------------------------------|--------------|
| **Bukkit**     | `dev.hypera:chameleon-platform-bukkit`     |              |
| **BungeeCord** | `dev.hypera:chameleon-platform-bungeecord` |              |
| Fabric         | ~~`dev.hypera:chameleon-platform-fabric`~~ | Coming soon  |
| **Folia**      | `dev.hypera:chameleon-platform-folia`      | Experimental |
| Forge          | ~~`dev.hypera:chameleon-platform-forge`~~  | Coming soon  |
| **Nukkit**     | `dev.hypera:chameleon-platform-nukkit`     |              |
| **Sponge**     | `dev.hypera:chameleon-platform-sponge`     | Unstable     |
| **Velocity**   | `dev.hypera:chameleon-platform-velocity`   |              |

#### Discontinued Platform Support

Chameleon attempts to maintain support for a wide range of platforms. However, there are times when
we must discontinue support for certain platforms. The decision to drop support for a platform is
typically made when maintaining support the platform becomes too challenging, or when the platform
becomes obsolete (or has been surpassed).

Below is a list of platforms that Chameleon no longer supports:

| Platform Name | Dependency                               | Last version      | Related Issues                                                     |
|---------------|------------------------------------------|-------------------|--------------------------------------------------------------------|
| **Minestom**  | `dev.hypera:chameleon-platform-minestom` | `0.16.0-SNAPSHOT` | [#268](https://github.com/ChameleonFramework/Chameleon/issues/268) |

## Getting started

We're currently working on Chameleon's documentation, and it will be available soon.
In the meantime, you can check out our example project [here](example). If you have any questions,
don't hesitate to ask us in our [official Discord server](https://discord.hypera.dev/)!

### Project structure

Chameleon is a modular system that allows you to choose which parts you want to use.
Here are the key modules:

- `chameleon-api` contains Chameleon's core API, most things happen here.
- `chameleon-annotations` contains our annotation-based platform class generator, designed to make
  using Chameleon as easy as possible.
- `chameleon-platform-(name)` contains the implementation of Chameleon's API on the named platform.
  You can see a full list of supported platforms [here](#supported-platforms).

If you have any questions or concerns, please do not hesitate to reach out to us in our
[official Discord server](https://discord.hypera.dev/).

### Annotation-based platform class generator

`chameleon-annotations` simplifies the process of generating the required classes for each platform
to load and start Chameleon automatically.

To use this feature, add `dev.hypera:chameleon-annotations` as a compileOnly dependency and an
annotationProcessor (Gradle) or as a provided dependency (Maven). Then, annotate your
`ChameleonPlugin` class with `@Plugin` and provide some information about your plugin, that's it!

### Dependencies

![Latest Release](https://img.shields.io/maven-central/v/dev.hypera/chameleon-api?color=%2317aaaa&label=Latest%20Release&style=for-the-badge)
![Latest Snapshot](https://img.shields.io/maven-metadata/v?color=%2317aaaa&label=Latest%20Snapshot&metadataUrl=https%3A%2F%2Fs01.oss.sonatype.org%2Fcontent%2Frepositories%2Fsnapshots%2Fdev%2Fhypera%2Fchameleon-api%2Fmaven-metadata.xml&style=for-the-badge)

#### Java 11

Chameleon requires Java 11 as a minimum version. This is because Java 8 reached its end-of-life (EOL)
in March 2022, which means that it will no longer receive public updates, leaving it vulnerable to
security risks. Java 11 is the next long-term support (LTS) version, which means it will continue
receiving public updates until at least September 2026 (Azul).

In addition to security reasons, Java 11 brings significant improvements over Java 8, such as better
performance, improved memory management, and new API features. Using Java 11 will help ensure that
Chameleon runs smoothly and efficiently, providing a better use experience.

#### Gradle

<details>
<summary>Kotlin DSL</summary>

```kotlin
repositories {
    // If using a release:
    mavenCentral()

    // If using a snapshot:
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    // Import Chameleon's Bill of Materials to set the version for every other
    // Chameleon dependency.
    implementation(platform("dev.hypera:chameleon-bom:(VERSION)"))

    // Include Chameleon's API
    implementation("dev.hypera:chameleon-api")

    // Include the Chameleon implementation for each platform you wish to support.
    // Replace (name) with the platform name, and repeat the next line for as many
    // platforms as you wish.
    implementation("dev.hypera:chameleon-platform-(name)")

    // If you wish to use the automatic platform main class generation:
    compileOnly("dev.hypera:chameleon-annotations")
    annotationProcessor("dev.hypera:chameleon-annotations")
}
```
</details>

<details>
<summary>Groovy DSL</summary>

```groovy
repositories {
    // If using a release:
    mavenCentral()

    // If using a snapshot:
    maven { url 'https://s01.oss.sonatype.org/content/repositories/snapshots/' }
}

dependencies {
    // Import Chameleon's Bill of Materials to set the version for every other
    // Chameleon dependency.
    implementation platform('dev.hypera:chameleon-bom:(VERSION)')

    // Include Chameleon's API
    implementation 'dev.hypera:chameleon-api'

    // Include the Chameleon implementation for each platform you wish to support.
    // Replace (name) with the platform name, and repeat the next line for as many
    // platforms as you wish.
    implementation 'dev.hypera:chameleon-platform-(name)'

    // If you wish to use the automatic platform main class generation:
    compileOnly 'dev.hypera:chameleon-annotations'
    annotationProcessor 'dev.hypera:chameleon-annotations'
}
```
</details>

#### Maven

<details>
<summary>Maven</summary>

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <!-- ... -->

    <repositories>
      <!-- If using a snapshot: -->
      <repository>
        <id>sonatype-oss-s01</id>
        <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
      </repository>
    </repositories>
    
    <dependencyManagement>
      <dependencies>
        <!-- Import Chameleon's Bill of Materials to set the version for every other -->
        <!-- Chameleon dependency. -->
        <dependency>
          <groupId>dev.hypera</groupId>
          <artifactId>chameleon-bom</artifactId>
          <version>(VERSION)</version>
          <type>pom</type>
          <scope>import</scope>
        </dependency>
      </dependencies>
    </dependencyManagement>
    
    <dependencies>
      <!-- Include Chameleon's API -->
      <dependency>
        <groupId>dev.hypera</groupId>
        <artifactId>chameleon-api</artifactId>
      </dependency>
    
      <!-- Include the Chameleon implementation for each platform you wish to support. -->
      <!-- Replace (name) with the platform name, and repeat the next line for as many -->
      <!-- platforms as you wish. -->
      <dependency>
        <groupId>dev.hypera</groupId>
        <artifactId>chameleon-platform-(name)</artifactId>
      </dependency>
    
      <!-- If you wish to use the automatic platform main class generation: -->
      <dependency>
        <groupId>dev.hypera</groupId>
        <artifactId>chameleon-annotations</artifactId>
        <scope>provided</scope>
      </dependency>
    </dependencies>
</project>
```
</details>

## Contributing

We welcome all contributions! If you have found something that you think you can improve, please
feel free to contribute!  
Please read our [Contributing guide](CONTRIBUTING.md) before creating a Pull Request or Issue :D

### Building

To build this project, execute <kbd>./gradlew build</kbd> in the project root directory.
If you have Gradle 8.0+ installed, you can alternatively execute <kbd>gradle build</kbd> in the
project root directory.

To publish the built artifacts to your local maven repository, add `publishToMavenLocal` to the end
of the build command, e.g. <kbd>./gradlew build publishToMavenLocal</kbd>

It is important to note that while the projects created with Chameleon can run on Java 11 or above,
you will need Java 17 or newer to build Chameleon. This is due to several supported platforms
requiring modern versions of Java.

### Contact

You can contact us in our [official Discord server](https://discord.hypera.dev/) (faster) or by
emailing us at [contact@hypera.dev](mailto:contact@hypera.dev). 

You can report a security vulnerability in Chameleon by:
- [Create a vulnerability report on our GitHub repository](https://github.com/ChameleonFramework/Chameleon/security/advisories/new).
- Send an email to [security@hypera.dev](mailto:security@hypera.dev).

### License

Chameleon Framework is distributed under the terms of the [MIT License](LICENSE).  
For further details, please refer to the [LICENSE](LICENSE) file.

## Acknowledgements

We are extremely grateful to the
[amazing individuals who have contributed to this project](https://github.com/ChameleonFramework/Chameleon/graphs/contributors),
as well as those who have supported us by providing valuable feedback and donations.

We would also like to thank all the individuals and companies who have supported us in sustaining
this project. We are grateful for their valuable contributions that have enabled us to continue to
improve Chameleon.

Please note that the individuals and companies listed under the "Supporters" section are
independent of this project, and their inclusion should not be interpreted as an endorsement or
affiliation.

### Supporters

<a href="https://jb.gg/OpenSourceSupport">
    <img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png" alt="JetBrains" height="128"/>
</a>

### Plugins using Chameleon

We are thrilled to showcase some of the amazing plugins and resources that have been created using
Chameleon! If you have created something using Chameleon that you think deserves to be on this list,
don't hesitate to create a pull request to add it here! We would love to see what you have created.

 - [**UltraStaffChatPro**](https://www.spigotmc.org/resources/80461/) v2.0.0+
