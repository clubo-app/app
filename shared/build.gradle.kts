plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")
    kotlin("plugin.serialization") version Versions.kotlin
    id("com.squareup.sqldelight") version Versions.sqlDelight
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
        }
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

                //with(Deps.AndroidX) {
                //    api(viewmodelLifeCycle)
                //}

                with(Deps.SqlDelight) {
                    implementation(androidDriver)
                }
            }
        }
        val androidTest by getting
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
    compileSdk = 32
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}

sqldelight {
    database("ClubbenDatabase") {
        packageName = "com.example.clubben.db"
    }
}