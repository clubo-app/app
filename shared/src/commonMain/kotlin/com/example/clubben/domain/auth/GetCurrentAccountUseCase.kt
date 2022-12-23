package com.example.clubben.domain.auth

import com.example.clubben.domain.profile.GetProfileUseCase
import com.example.clubben.domain.profile.toDomainProfile
import com.example.clubben.repository.auth.AuthRepository

class GetCurrentAccountUseCase(
    private val authRepository: AuthRepository,
    private val getProfileUseCase: GetProfileUseCase
) {
    suspend operator fun invoke(): Account? {
        val remote = authRepository.getCurrentAccount()
        return if (remote != null) {
            val profile = remote.profile?.toDomainProfile() ?: getProfileUseCase(remote.id)
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
}