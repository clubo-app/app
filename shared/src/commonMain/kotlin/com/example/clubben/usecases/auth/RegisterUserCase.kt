package com.example.clubben.usecases.auth

import com.example.clubben.remote.auth.RegisterRequest
import com.example.clubben.repository.auth.AuthRepository
import com.example.clubben.usecases.profile.GetProfileUseCase
import com.example.clubben.usecases.profile.toDomainProfile
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.utils.catchApiError

class RegisterUserCase(
    private val authRepository: AuthRepository,
    private val getProfileUseCase: GetProfileUseCase
) {

    suspend operator fun invoke(
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
            val profile =
                remote.profile?.toDomainProfile() ?: getProfileUseCase(remote.id).getOrThrow()
            Account(
                remote.id,
                profile,
                remote.email,
                remote.emailVerified,
                remote.providerId
            )
        }
    }
}