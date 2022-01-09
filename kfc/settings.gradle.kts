rootProject.name = "kfc"

pluginManagement {
    plugins {
        val kotlinVersion = extra["kotlin.version"] as String
        kotlin("jvm") version kotlinVersion
        kotlin("js") version kotlinVersion

        val kfcVersion = extra["kfc.version"] as String
        id("com.github.turansky.kfc.library") version kfcVersion
        id("com.github.turansky.kfc.maven-central-publish") version kfcVersion
        id("com.github.turansky.kfc.plugin-publish") version kfcVersion

        id("com.gradle.plugin-publish") version "0.19.0"
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}

include(":kfc-react")

include(":kfc-gradle-plugin")
