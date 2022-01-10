plugins {
    id("com.github.turansky.kfc.application")
    id("com.github.turansky.kfc.react")
}

val kotlinWrappersVersion = property("kotlin-wrappers.version") as String

dependencies {
    implementation(enforcedPlatform("org.jetbrains.kotlin-wrappers:kotlin-wrappers-bom:$kotlinWrappersVersion"))
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react")
    implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom")
    implementation("com.github.turansky.kfc:kfc-react")
}