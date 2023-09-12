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
    id("net.kyori.indra.publishing")
}

/* Set up publishing */
indra {
    mitLicense()
    github("ChameleonFramework", "Chameleon") {
        ci(true)
    }

    signWithKeyFromPrefixedProperties("hypera")
    publishReleasesTo("hyperaReleases", "https://repo.hypera.dev/releases")
    publishSnapshotsTo("hyperaSnapshots", "https://repo.hypera.dev/snapshots")

    configurePublications {
        pom {
            inceptionYear.set("2021")

            organization {
                name.set("Hypera Development")
                url.set("https://hypera.dev/")
            }

            developers {
                developer {
                    id.set("joshuasing")
                    name.set("Joshua Sing")
                    email.set("joshua@hypera.dev")
                    timezone.set("Australia/Melbourne")
                }

                developer {
                    id.set("LooFifteen")
                    name.set("Luis")
                    email.set("luis@lu15.dev")
                    timezone.set("Europe/London")
                }
            }
        }
    }
}

