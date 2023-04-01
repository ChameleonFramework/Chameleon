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
import com.adarshr.gradle.testlogger.theme.ThemeType
import net.ltgt.gradle.errorprone.errorprone
import net.ltgt.gradle.nullaway.nullaway

plugins {
    id("net.kyori.indra")
    id("net.kyori.indra.git")
    id("net.kyori.indra.checkstyle")
    id("net.kyori.indra.licenser.spotless")
    id("net.kyori.blossom")

    id("com.adarshr.test-logger")
    id("net.ltgt.errorprone")
    id("net.ltgt.nullaway")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

indra {
    javaVersions {
        if (project.name.contains("minestom") || project.name.contains("sponge") || project.name.contains("example")) {
            target(17)
            testWith(17)
        } else {
            target(11)
            testWith(11, 17)
        }
    }
}

indraSpotlessLicenser {
    property("year", "2021-2023")
}

testlogger {
    theme = ThemeType.MOCHA_PARALLEL
}

blossom {
    replaceToken("@version@", rootProject.version)
    replaceToken("@commit@", indraGit.commit()?.name?.substring(0, 7) ?: "unknown")
}

dependencies {
    errorprone(libs.findLibrary("build-errorprone-core").get())
    errorprone(libs.findLibrary("build-nullaway-core").get())
    compileOnly(libs.findLibrary("build-errorprone-annotations").get())
}

tasks.withType<JavaCompile>().configureEach {
    options.errorprone {
        disable("AnnotateFormatMethod")
        disable("CanIgnoreReturnValueSuggester")

        nullaway {
            error()
            annotatedPackages.add("dev.hypera.chameleon")
            unannotatedSubPackages.add("dev.hypera.chameleon.example")
            acknowledgeRestrictiveAnnotations.set(true)
            checkOptionalEmptiness.set(true)
            checkContracts.set(true)
        }
    }
}
