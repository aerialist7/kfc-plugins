package com.github.turansky.kfc.gradle.plugin

import java.io.File

internal fun defaultOutputConfiguration(): String =
    // language=JavaScript
    """
        config.output = config.output || {}
        config.output.libraryTarget = 'umd'
        delete config.output.library
    """.trimIndent()

internal fun entryConfiguration(
    output: Output? = null,
    entry: File
): String {
    val entryId = output?.id ?: "main"

    // language=JavaScript
    return """
        if ('$entryId' === 'main' && config.mode !== 'production') {
            return
        }

        config.entry['$entryId'] = ${entry.toPathString()}
    """.trimIndent()
}
