package io.github.turansky.kfc.gradle.plugin

import io.github.turansky.kfc.gradle.plugin.GradleProperty.VERSION
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.*
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile
import java.io.File

class PluginPublishPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.plugins.apply(RootPluginPublishPlugin::class)

        tasks {
            configureEach<KotlinJvmCompile> {
                kotlinOptions {
                    allWarningsAsErrors = true
                }
            }

            named<Jar>("jar") {
                into("META-INF") {
                    from("$projectDir/LICENSE.md")
                }
            }
        }

        // TODO: move to common
        val releaseMode = hasProperty("signing.keyId")

        plugins.withType<MavenPublishPlugin> {
            configure<PublishingExtension> {
                publications.withType<MavenPublication>().configureEach {
                    pom.configure(project, releaseMode)
                }
            }
        }
    }
}

private class RootPluginPublishPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        tasks {
            register("preparePublish") {
                doLast {
                    changeVersion(Version::toRelease, project.versionFiles())
                }
            }

            register("prepareDevelopment") {
                doLast {
                    changeVersion(Version::toNextSnapshot, project.versionFiles())
                }
            }
        }
    }
}

private fun Project.versionFiles(): Set<File> =
    fileTree(projectDir)
        .matching { include("**/src/main/kotlin/**/KotlinPluginArtifact.kt") }
        .files

private fun Project.changeVersion(
    change: (Version) -> Version,
    versionFiles: Set<File>,
) {
    val oldVersion = currentVersion.toString()
    val newVersion = change(currentVersion).toString()

    setGradleProperty(VERSION, newVersion)
    version = newVersion

    for (file in versionFiles) {
        val content = file.readText()
        val newContent = content.replace(oldVersion, newVersion)

        check(content != newContent) {
            "Unable to found plugin version in file: $file"
        }

        file.writeText(newContent)
    }
}
