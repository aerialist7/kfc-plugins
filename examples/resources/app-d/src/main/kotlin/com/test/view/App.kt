package com.test.view

import AAA
import BBB
import CCC

fun main() {
    Api
}

object Api {
    fun App(): String = AAA() + BBB() + CCC()

    fun json(): String = require("app.json")
}

external fun require(path: String): String
