/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(com.orange.omnis.gradle.Dependencies.androidGradlePlugin)
        classpath(com.orange.omnis.gradle.Dependencies.kotlinGradlePlugin)
        classpath(com.orange.omnis.gradle.Dependencies.firebaseAppDistributionGradlePlugin)
        classpath(com.orange.omnis.gradle.Dependencies.googleServicesGradlePlugin)
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register<Delete>("clean") {
    group = "cleanup"
    delete(rootProject.buildDir)
}
