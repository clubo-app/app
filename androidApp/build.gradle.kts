plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.clubben.android"
    compileSdk = AndroidSdk.compile
    defaultConfig {
        applicationId = "com.example.clubben.android"
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    with (Deps.AndroidX) {
        implementation(activityCompose)
    }

    with (Deps.Compose) {
        implementation(compiler)
        implementation(ui)
        implementation(uiGraphics)
        implementation(uiTooling)
        implementation(foundationLayout)
        implementation(material)
        implementation(navigation)
    }

    with(Deps.Koin) {
        implementation(core)
        implementation(android)
    }


    implementation(project(":shared"))
}