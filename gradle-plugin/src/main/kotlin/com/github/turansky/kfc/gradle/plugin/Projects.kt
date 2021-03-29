package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.internal.artifacts.dependencies.DefaultProjectDependency

private val MODULE_NAME = StringProperty("kfc.module.name")
private val MODULE_KEEP = StringProperty("kfc.module.keep")

private val OUTPUT_PATH = StringProperty("kfc.output.path")
private val OUTPUT_NAME = StringProperty("kfc.output.name")

internal val Project.jsModuleName: String
    get() {
        propertyOrNull(MODULE_NAME)
            ?.let { return it }

        return when (this) {
            rootProject -> rootProject.name
            else -> "${rootProject.name}-$name"
        }
    }

internal val Project.jsModuleKeep: String?
    get() = propertyOrNull(MODULE_KEEP)
        ?.let { "$jsModuleName.$it" }

private val Project.jsOutputPath: String?
    get() = propertyOrNull(OUTPUT_PATH)

internal fun Project.outputPath(
    path: String
): String =
    outputPath("", path)

internal fun Project.outputPath(
    prefix: String,
    suffix: String
): String {
    val basePath = jsOutputPath
        ?.let { "$it/" }
        ?: ""

    return prefix + basePath + suffix
}

internal val Project.jsOutputName: String
    get() = propertyOrNull(OUTPUT_NAME)
        ?: jsModuleName

internal val Project.jsOutputFileName: String
    get() = outputPath("$jsOutputName.js")

// TODO: optimize calculation
internal fun Project.relatedProjects(): Set<Project> {
    val configuration = configurations.findByName(JS_MAIN_IMPLEMENTATION)
        ?: configurations.findByName(IMPLEMENTATION)
        ?: return emptySet()

    return configuration
        .allDependencies
        .asSequence()
        .filterIsInstance<DefaultProjectDependency>()
        .map { it.dependencyProject }
        .flatMap { sequenceOf(it) + it.relatedProjects() }
        .plus(this)
        .toSet()
}

internal fun Project.ext(
    propertyName: String,
    value: Boolean
) {
    extensions.extraProperties[propertyName] = value.toString()
}

