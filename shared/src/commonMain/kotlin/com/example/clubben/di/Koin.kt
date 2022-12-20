package com.example.clubben.di

import com.example.clubben.domain.auth.AuthDomain
import com.example.clubben.domain.toast.ToastQueue
import com.example.clubben.remote.auth.AuthApi
import com.example.clubben.remote.favorites.FavoritesApi
import com.example.clubben.remote.friends.FriendsApi
import com.example.clubben.remote.particpants.ParticipantsApi
import com.example.clubben.remote.parties.PartiesApi
import com.example.clubben.remote.profiles.ProfilesApi
import com.example.clubben.repository.auth.AuthRepository
import com.example.clubben.repository.auth.AuthRepositoryImpl
import com.example.clubben.repository.favorites.FavoritesRepository
import com.example.clubben.repository.favorites.FavoritesRepositoryImpl
import com.example.clubben.repository.friends.FriendsRepository
import com.example.clubben.repository.friends.FriendsRepositoryImpl
import com.example.clubben.repository.participants.ParticipantsRepository
import com.example.clubben.repository.participants.ParticipantsRepositoryImpl
import com.example.clubben.repository.parties.PartiesRepository
import com.example.clubben.repository.parties.PartiesRepositoryImpl
import com.example.clubben.repository.platformModule
import com.example.clubben.repository.profiles.ProfilesRepository
import com.example.clubben.repository.profiles.ProfilesRepositoryImpl
import com.example.clubben.utils.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            platformModule(),
            getBaseModule(),
            getRemoteModule(enableNetworkLogs = enableNetworkLogs),
            getRepositoryModule(),
            getDomainModule(),
        )
    }

// called by iOS
fun KoinApplication.Companion.start(): KoinApplication = initKoin()
val Koin.toastQueue: ToastQueue
    get() = get()

const val baseUrl = "https://aggregator-service-jonashiltl.cloud.okteto.net"

@OptIn(ExperimentalSerializationApi::class)
fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true; explicitNulls = false }

fun getBaseModule() = module {
    single { createJson() }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
    single { TokenManager() }
}

fun getRemoteModule(enableNetworkLogs: Boolean) = module {
    single { createHttpClient(baseUrl, enableNetworkLogs, get(), get()) }

    factoryOf(::AuthApi)
    factoryOf(::FavoritesApi)
    factoryOf(::FriendsApi)
    factoryOf(::ParticipantsApi)
    factoryOf(::PartiesApi)
    factoryOf(::ProfilesApi)
}

fun getRepositoryModule() = module {
    factory<AuthRepository> { AuthRepositoryImpl() }
    factory<FavoritesRepository> { FavoritesRepositoryImpl(get()) }
    factory<FriendsRepository> { FriendsRepositoryImpl() }
    factory<ParticipantsRepository> { ParticipantsRepositoryImpl() }
    factory<PartiesRepository> { PartiesRepositoryImpl() }
    factory<ProfilesRepository> { ProfilesRepositoryImpl() }
}

fun getDomainModule() = module {
    single { ToastQueue() }
    single { AuthDomain() }
}