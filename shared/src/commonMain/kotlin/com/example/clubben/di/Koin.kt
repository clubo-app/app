package com.example.clubben.di

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
import com.example.clubben.repository.profiles.ProfilesRepository
import com.example.clubben.repository.profiles.ProfilesRepositoryImpl
import com.example.clubben.ui.app.AppViewModel
import com.example.clubben.ui.login.LoginViewModel
import com.example.clubben.ui.signup.SignUpViewModel
import com.example.clubben.ui.toast.ToastViewModel
import com.example.clubben.usecases.auth.GetCurrentAccountUseCase
import com.example.clubben.usecases.auth.LoginUseCase
import com.example.clubben.usecases.auth.RegisterUserCase
import com.example.clubben.usecases.auth.SignOutUseCase
import com.example.clubben.usecases.profile.GetProfileUseCase
import com.example.clubben.usecases.profile.UsernameTakenUseCase
import com.example.clubben.utils.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

fun initKoin(enableNetworkLogs: Boolean = false, appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            getBaseModule(),
            platformModule(),
            getPlatformModule(),
            getRemoteModule(enableNetworkLogs = enableNetworkLogs),
            getRepositoryModule(),
            getUseCaseModule(),
            getViewModelModule()
        )
    }

// called by iOS
fun initKoin() = initKoin(enableNetworkLogs = false) {}

const val baseUrl = "https://aggregator-service-jonashiltl.cloud.okteto.net"

@OptIn(ExperimentalSerializationApi::class)
fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true; explicitNulls = false }

fun getBaseModule() = module {
    single { createJson() }
    single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
    single { TokenManager() }
}


fun getPlatformModule() = module {
    single { createDatabase(get()) }
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
    single<AuthRepository> { AuthRepositoryImpl() }
    single<FavoritesRepository> { FavoritesRepositoryImpl(get()) }
    single<FriendsRepository> { FriendsRepositoryImpl() }
    single<ParticipantsRepository> { ParticipantsRepositoryImpl() }
    single<PartiesRepository> { PartiesRepositoryImpl() }
    single<ProfilesRepository> { ProfilesRepositoryImpl() }
}

fun getUseCaseModule() = module {
    // Profile domain
    factoryOf(::GetProfileUseCase)
    factoryOf(::UsernameTakenUseCase)

    // Auth domain
    factoryOf(::GetCurrentAccountUseCase)
    factoryOf(::LoginUseCase)
    factoryOf(::RegisterUserCase)
    factoryOf(::SignOutUseCase)
}

fun getViewModelModule() = module {
    // shared VM
    single { ToastViewModel() }
    single { AppViewModel() }


    factory { LoginViewModel() }
    factory { SignUpViewModel() }
}