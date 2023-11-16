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
 * Chameleon requires Java 11. Additionally, Folia and Sponge support requires Java 17.
 * If you wish to support Folia and/or Sponge, you must target Java 17.
 *
 * If you want your plugin to work with Java 11+, you must keep your source code
 * compatible with Java 11.
 */
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
        targetCompatibility = JavaVersion.VERSION_17
    }
}

repositories {
    // mavenCentral() // If you're using a release version of Chameleon
    // maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") // If you're using a snapshot version of Chameleon

    maven("https://papermc.io/repo/repository/maven-public/") // Required for Bukkit/Paper/Folia support
    maven("https://oss.sonatype.org/content/repositories/snapshots/") // Required for BungeeCord support
    maven("https://repo.spongepowered.org/maven/") // Required for Sponge support
    maven("https://repo.opencollab.dev/main/") // Required for Nukkit support
    maven("https://repo.papermc.io/repository/maven-public/") // Required for Velocity support
}

dependencies {
    // Chameleon API
    implementation(projects.chameleonApi) // dev.hypera:chameleon-api

    // Bukkit (and Paper, Folia) support
    implementation(projects.chameleonPlatformBukkit) // dev.hypera:chameleon-platform-bukkit
    compileOnly(libs.platform.bukkit) // io.papermc.paper:paper-api

    // BungeeCord support
    implementation(projects.chameleonPlatformBungeecord) // dev.hypera:chameleon-platform-bungeecord
    compileOnly(libs.platform.bungeecord) // net.md-5:bungeecord-api

    // Nukkit support
    implementation(projects.chameleonPlatformNukkit) // dev.hypera:chameleon-platform-nukkit
    compileOnly(libs.platform.nukkit) // cn.nukkit:nukkit

    // Sponge support
    implementation(projects.chameleonPlatformSponge) // dev.hypera:chameleon-platform-sponge
    compileOnly(libs.platform.sponge) // org.spongepowered:spongeapi

    // Velocity support
    implementation(projects.chameleonPlatformVelocity) // dev.hypera:chameleon-platform-velocity
    compileOnly(libs.platform.velocity) // com.velocitypowered:velocity-api

    // Annotation-based platform "main class" and manifest generation
    compileOnly(projects.chameleonAnnotations) // dev.hypera:chameleon-annotations
    annotationProcessor(projects.chameleonAnnotations) // dev.hypera:chameleon-annotations
}

tasks {
    build {
        dependsOn("shadowJar")
    }

    shadowJar {
        archiveFileName.set(String.format("%s-%s.jar", project.name, rootProject.version))
        mergeServiceFiles()

        /*
         * IMPORTANT: Relocate all dependencies to avoid conflicts
         * Relocated Adventure will work on all platforms due to special handling inside Chameleon.
         */
        relocate("dev.hypera.chameleon", "dev.hypera.example.lib.chameleon")
        relocate("net.kyori", "dev.hypera.example.lib.kyori")
        relocate("com.google.gson", "dev.hypera.example.lib.gson")
    }

    jar {
        enabled = false
    }
}
