package com.example.clubben.domain.auth

import com.example.clubben.domain.profile.GetProfileUseCase
import com.example.clubben.domain.profile.toDomainProfile
import com.example.clubben.remote.auth.RegisterRequest
import com.example.clubben.repository.auth.AuthRepository
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

            val profile = remote.profile?.toDomainProfile() ?: getProfileUseCase(remote.id)
            Account(
                id = remote.id,
                profile = profile,
                email = remote.email,
                emailVerified = remote.emailVerified,
                providerId = remote.providerId,
            )
        }
    }
}