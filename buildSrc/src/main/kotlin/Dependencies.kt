object Versions {
    const val kotlin = "1.7.20"
    const val kotlinCoroutines = "1.6.4"
    const val ktor = "2.1.2"
    const val koin = "3.2.2"

    const val kotlinxSerialization = "1.3.3"
    const val kotlinxDateTime = "0.4.0"

    const val sqlDelight = "1.5.3"

    const val nativeCoroutines = "0.13.1"

    const val kSwift = "0.6.1"

    const val compose = "1.2.1"
    const val composeCompiler = "1.3.2"
    const val navCompose = "2.5.2"
    const val activityCompose = "1.5.1"

    const val kermit = "1.1.3"

    const val firebase = "1.6.2"
}

object AndroidSdk {
    const val min = 21
    const val compile = 32
    const val target = compile
}

object Deps {
    object AndroidX {
        const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    }

    object Compose {
        const val compiler = "androidx.compose.compiler:compiler:${Versions.composeCompiler}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val foundationLayout =
            "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.navCompose}"
    }

    object Kotlinx {
        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
        const val serializationCore =
            "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}"
        const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDateTime}"
    }

    object Ktor {
        const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val clientJson = "io.ktor:ktor-client-json:${Versions.ktor}"
        const val clientAuth = "io.ktor:ktor-client-auth:${Versions.ktor}"
        const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val clientSerialization = "io.ktor:ktor-client-serialization:${Versions.ktor}"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val json = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"

        const val clientAndroid = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
        const val clientDarwin = "io.ktor:ktor-client-darwin:${Versions.ktor}"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    }

    object Log {
        const val kermit = "co.touchlab:kermit:${Versions.kermit}"
    }

    object SqlDelight {
        const val coroutineExtensions =
            "com.squareup.sqldelight:coroutines-extensions:${Versions.sqlDelight}"
        const val androidDriver = "com.squareup.sqldelight:android-driver:${Versions.sqlDelight}"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:${Versions.sqlDelight}"
    }

    object Firebase {
        const val auth = "dev.gitlive:firebase-auth:${Versions.firebase}"
    }
}