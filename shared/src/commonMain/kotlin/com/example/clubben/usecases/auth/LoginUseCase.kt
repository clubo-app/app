package com.example.clubben.usecases.auth

import com.example.clubben.repository.auth.AuthRepository
import com.example.clubben.usecases.profile.GetProfileUseCase
import com.example.clubben.usecases.profile.toDomainProfile
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
            val profile =
                remote.profile?.toDomainProfile() ?: getProfileUseCase(remote.id).getOrThrow()
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