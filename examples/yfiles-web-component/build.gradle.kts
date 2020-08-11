plugins {
    id("com.github.turansky.kfc.webcomponent")
    id("com.github.turansky.kfc.yfiles")
}

webcomponent {
    id = "red-button"

    setter("content")
    property("color")
    property("chromeColor")
    getter("parentButton")

    method("export", "options")
    method("pragmaticExport", "format", "options")

    event("update-start")
    event("update-end")

    source = "com.test.example.app.RedButton"
}

dependencies {
    testImplementation(kotlin("test-js"))
}
