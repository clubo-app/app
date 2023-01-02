buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:7.3.1")
    }
}
plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("7.2.2").apply(false)
    id("com.android.library").version("7.2.2").apply(false)
    kotlin("android").version(Versions.kotlin).apply(false)
    kotlin("multiplatform").version(Versions.kotlin).apply(false)
    id("com.google.gms.google-services").version("4.3.14").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
