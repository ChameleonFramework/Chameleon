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
plugins {
    id("java")
    id("chameleon.base") // Checkstyle and version injection, not required.
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

/*
 * Minestom requires Java 17. If you want your plugin to work on Java 8+ you'll
 * need to keep your source code compatible with Java 8, and it should work fine.
 */
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
        targetCompatibility = JavaVersion.VERSION_17
    }
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots/") // Required for BungeeCord support
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") // Required for Bukkit support
    // maven("https://jitpack.io/") // Required for Minestom support
    maven("https://repo.spongepowered.org/maven/") // Required for Minestom/Sponge support
    maven("https://repo.opencollab.dev/main/") // Required for Nukkit support
    maven("https://repo.papermc.io/repository/maven-public/") // Required for Velocity support

    // Due to JitPack deleting old versions of Minestom, which breaks Chameleon
    // builds, we have decided to start mirroring certain JitPack artifacts to
    // our Maven repository and caching them so that if JitPack does delete an
    // artifact, we have a copy of it to keep builds running until we get the
    // chance to update the dependency.
    //
    // If you wish to use JitPack, uncomment the JitPack repository at line 42.
    maven("https://repo.hypera.dev/mirror/")
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
    annotationProcessor(project(":chameleon-annotations")) // dev.hypera:chameleon-annotations
}

tasks {
    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        archiveFileName.set(String.format("%s-%s.jar", project.name, rootProject.version))
        mergeServiceFiles()

        /* IMPORTANT: Relocate all dependencies to avoid conflicts */
        relocate("dev.hypera.chameleon", "dev.hypera.chameleon.example.lib.chameleon")
        relocate("net.kyori", "dev.hypera.chameleon.example.lib.kyori")
        relocate("com.google.gson", "dev.hypera.chameleon.example.lib.gson")
    }

    jar {
        enabled = false
    }
}