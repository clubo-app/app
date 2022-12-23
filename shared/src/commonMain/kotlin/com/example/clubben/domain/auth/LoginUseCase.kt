package com.example.clubben.domain.auth

import com.example.clubben.domain.profile.GetProfileUseCase
import com.example.clubben.domain.profile.toDomainProfile
import com.example.clubben.repository.auth.AuthRepository
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.utils.catchApiError

class LoginUseCase(
    private val authRepository: AuthRepository,
    private val getProfileUseCase: GetProfileUseCase
) {
    suspend operator fun invoke(email: String, password: String): DataState<Account, ApiError> {
        return catchApiError {
            val remote = authRepository.login(email, password)

            if (remote != null) {
                val profile = remote.profile?.toDomainProfile() ?: getProfileUseCase(remote.id)
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
}