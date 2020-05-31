package com.github.turansky.kfc.gradle.plugin

import org.gradle.api.Project
import org.gradle.kotlin.dsl.invoke
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack
import org.jetbrains.kotlin.gradle.tasks.KotlinJsDce

internal fun Project.disableBrowserTasks() {
    tasks {
        configureEach<KotlinJsDce> {
            enabled = false
        }

        configureEach<KotlinWebpack> {
            enabled = false
        }
    }
}