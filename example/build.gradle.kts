/*
 * Chameleon Framework - Cross-platform Minecraft plugin framework
 *  Copyright (c) 2021-present The Chameleon Framework Authors.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */
plugins {
    id("chameleon.common") // Codestyle checking, not required.
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

val tokens = mapOf(
    "version" to version
)

java {
    targetCompatibility = JavaVersion.VERSION_17 // Required for Minestom support
    sourceCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    /* Platforms */
    maven("https://oss.sonatype.org/content/repositories/snapshots/") // Required for BungeeCord support
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") // Required for Bukkit support
    maven("https://jitpack.io/") // Required for Minestom support
    maven("https://repo.spongepowered.org/maven/") // Required for Minestom support
    maven("https://repo.opencollab.dev/main/") // Required for Nukkit support
    maven("https://nexus.velocitypowered.com/repository/maven-public/") // Required for Velocity support
}

dependencies {
    implementation(project(":chameleon-api")) // dev.hypera:chameleon-api
    implementation(project(":chameleon-platform-bukkit")) // dev.hypera:chameleon-platform-bukkit
    implementation(project(":chameleon-platform-bungeecord")) // dev.hypera:chameleon-platform-bungeecord
    implementation(project(":chameleon-platform-nukkit")) // dev.hypera:chameleon-platform-nukkit
    implementation(project(":chameleon-platform-minestom")) // dev.hypera:chameleon-platform-minestom
    implementation(project(":chameleon-platform-velocity")) // dev.hypera:chameleon-platform-velocity
    implementation(project(":chameleon-platform-sponge")) // dev.hypera:chameleon-platform-sponge

    compileOnly(project(":chameleon-annotations")) // dev.hypera:chameleon-annotations
    annotationProcessor(project(":chameleon-annotations"))
}

tasks {
    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        archiveFileName.set(String.format("%s-%s.jar", project.name, project.version))
        mergeServiceFiles()

        relocate("dev.hypera.chameleon", "dev.hypera.example.lib.chameleon")
        relocate("net.kyori", "dev.hypera.example.lib.kyori")
        relocate("com.google.gson", "dev.hypera.example.lib.gson")
        relocate("org.yaml.snakeyaml", "dev.hypera.example.lib.snakeyaml")
        relocate("org.json.simple", "dev.hypera.example.lib.jsonsimple")
    }

    val sourcesForRelease = task<Copy>("sourcesForRelease") {
        from("src/main/java")
        into("build/src/java")
        filter<org.apache.tools.ant.filters.ReplaceTokens>(mapOf("tokens" to tokens))
    }

    compileJava {
        dependsOn(sourcesForRelease)
        source = fileTree(sourcesForRelease.destinationDir)
    }

    jar {
        enabled = false
    }
}