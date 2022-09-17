package com.example.clubben.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin() {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs))
    }

// called by iOS etc
fun initKoin() = initKoin(enableNetworkLogs = false) {}

const val baseUrl = "https://aggregator-service-jonashiltl.cloud.okteto.net"

fun commonModule(enableNetworkLogs: Boolean) = module {
    single { createHttpClient(get(), baseUrl, enableNetworkLogs) }

    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
}
