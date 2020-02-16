package com.github.turansky.kfc.gradle.plugin

internal fun outputConfiguration(path: String): String {
    val libraryExport = path
        .split(".")
        .map { "'$it'" }
        .joinToString(", ")

    return """
            config.output.libraryTarget = 'umd'
            config.output.libraryExport = [$libraryExport]
    """.trimIndent()
}
