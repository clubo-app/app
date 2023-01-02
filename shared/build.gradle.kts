plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version Versions.kotlin
    id("app.cash.sqldelight") version Versions.sqlDelight

    id("com.google.devtools.ksp") version "1.8.0-RC2-1.0.8"
    id("com.rickclephas.kmp.nativecoroutines") version Versions.nativeCoroutines
}

// CocoaPods requires the podspec to have a version.
version = 1.0

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets.all {
        languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                with(Deps.Ktor) {
                    implementation(clientCore)
                    implementation(clientJson)
                    implementation(clientLogging)
                    implementation(clientSerialization)
                    implementation(contentNegotiation)
                    implementation(json)
                    implementation(clientAuth)
                }

                with(Deps.Kotlinx) {
                    implementation(coroutinesCore)
                    implementation(serializationCore)
                    implementation(dateTime)
                }

                with(Deps.Koin) {
                    api(core)
                }

                with(Deps.Log) {
                    api(kermit)
                }

                with(Deps.SqlDelight) {
                    implementation(coroutineExtensions)
                    implementation(adapters)
                }

                with(Deps.Firebase) {
                    implementation(auth)
                }

                with(Deps.KMMViewmodel) {
                    implementation(core)
                }
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                with(Deps.Ktor) {
                    implementation(clientAndroid)
                }

                with(Deps.SqlDelight) {
                    implementation(androidDriver)
                }
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                with(Deps.Ktor) {
                    implementation(clientDarwin)
                }

                with(Deps.SqlDelight) {
                    implementation(nativeDriver)
                }
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}


android {
    namespace = "com.example.clubben"
    compileSdk = AndroidSdk.compile
    defaultConfig {
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

sqldelight {
    database("ClubbenDatabase") {
        packageName = "com.example.clubben.db"
    }
}