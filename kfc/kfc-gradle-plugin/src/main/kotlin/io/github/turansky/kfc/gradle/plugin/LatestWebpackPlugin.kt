package io.github.turansky.kfc.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

class LatestWebpackPlugin : Plugin<Project> {
    override fun apply(target: Project): Unit = with(target) {
        rootProject.configureLatestWebpack()
    }
}

private fun Project.configureLatestWebpack() {
    plugins.withType<NodeJsRootPlugin> {
        the<NodeJsRootExtension>().versions.apply {
            webpack.version = "5.75.0"
            webpackCli.version = "4.10.0"
            webpackDevServer.version = "4.11.1"

            karma.version = "6.4.1"
        }
    }

    plugins.withType<YarnPlugin> {
        the<YarnRootExtension>().apply {
            resolution("nanoid", "^3.3.4")
        }
    }
}
