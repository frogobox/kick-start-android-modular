import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies{
    // library open-build-src
    implementation("com.github.frogobox:open-build-src:1.0.2")

    // library frogo-build-src
    implementation("com.github.frogobox:frogo-build-src:1.0.4")

}