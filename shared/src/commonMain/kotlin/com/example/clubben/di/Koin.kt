package com.example.clubben.di

import com.example.clubben.data.remote.auth.AuthApi
import com.example.clubben.data.remote.favorites.FavoritesApi
import com.example.clubben.data.remote.friends.FriendsApi
import com.example.clubben.data.remote.particpants.ParticipantsApi
import com.example.clubben.data.remote.parties.PartiesApi
import com.example.clubben.data.remote.profiles.ProfilesApi
import com.example.clubben.data.repository.platformModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin() {
        appDeclaration()
        modules(commonModule(enableNetworkLogs = enableNetworkLogs), platformModule())
    }

// called by iOS etc
fun initKoin() = initKoin(enableNetworkLogs = false) {}

const val baseUrl = "https://aggregator-service-jonashiltl.cloud.okteto.net"

fun commonModule(enableNetworkLogs: Boolean) = getDataModule(enableNetworkLogs)

fun getDataModule(enableNetworkLogs: Boolean) = module {
    single { createHttpClient(get(), baseUrl, enableNetworkLogs) }

    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }

    single { AuthApi(get()) }
    single { FavoritesApi(get()) }
    single { FriendsApi(get()) }
    single { ParticipantsApi(get()) }
    single { PartiesApi(get()) }
    single { ProfilesApi(get()) }
}