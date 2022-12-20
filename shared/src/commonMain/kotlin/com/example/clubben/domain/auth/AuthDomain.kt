package com.example.clubben.domain.auth

import com.example.clubben.remote.auth.RegisterRequest
import com.example.clubben.repository.auth.AuthRepository
import com.example.clubben.repository.profiles.ProfilesRepository
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.utils.catchApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthDomain : KoinComponent {
    val authRepository: AuthRepository by inject()
    val profilesRepository: ProfilesRepository by inject()

    suspend fun login(email: String, password: String): DataState<Account, ApiError> {
        return catchApiError {
            val remote = authRepository.login(email, password)

            if (remote != null) {
                val profile = remote.profile ?: profilesRepository.getProfile(remote.id)
                Account(
                    id = remote.id,
                    profile = profile,
                    email = remote.email,
                    emailVerified = remote.emailVerified,
                    providerId = remote.providerId,
                )
            } else {
                throw ApiError(message = "Failed to create your Account")
            }
        }
    }

    suspend fun register(
        email: String,
        password: String,
        username: String,
        firstname: String,
        lastname: String? = null,
        avatar: String? = null, // base64 encoded image
    ): DataState<Account, ApiError> {
        return catchApiError {
            val remote = authRepository.register(
                RegisterRequest(
                    email,
                    password,
                    username,
                    firstname,
                    lastname,
                    avatar
                )
            )

            val profile = remote.profile ?: profilesRepository.getProfile(remote.id)
            Account(
                id = remote.id,
                profile = profile,
                email = remote.email,
                emailVerified = remote.emailVerified,
                providerId = remote.providerId,
            )
        }
    }

    suspend fun signOut() = authRepository.signOut()

    suspend fun getCurrentAccount(): Account? {
        val remote = authRepository.getCurrentAccount()
        return if (remote != null) {
            val profile = remote.profile ?: profilesRepository.getProfile(remote.id)
            println("Got Profile $profile")
            Account(
                id = remote.id,
                profile = profile,
                email = remote.email,
                emailVerified = remote.emailVerified,
                providerId = remote.providerId,
            )
        } else {
            null
        }
    }

    fun consumeAuthState(): Flow<Account?> = flow {
        authRepository.authStateChanged.collect { remote ->
            if (remote != null) {
                val profile = remote.profile ?: profilesRepository.getProfile(remote.id)
                emit(
                    Account(
                        id = remote.id,
                        profile = profile,
                        email = remote.email,
                        emailVerified = remote.emailVerified,
                        providerId = remote.providerId,
                    )
                )
            } else {
                emit(null)
            }
        }
    }
}