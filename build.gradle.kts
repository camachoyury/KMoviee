plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.2.0-alpha07").apply(false)
    id("com.android.library").version("8.2.0-alpha07").apply(false)
    kotlin("android").version("1.8.21").apply(false)
    kotlin("multiplatform").version("1.8.21").apply(false)
    kotlin("plugin.serialization") version "1.8.10"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
