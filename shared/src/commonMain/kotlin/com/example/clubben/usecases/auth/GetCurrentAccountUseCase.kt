package com.example.clubben.usecases.auth

import com.example.clubben.repository.auth.AuthRepository
import com.example.clubben.usecases.profile.GetProfileUseCase
import com.example.clubben.usecases.profile.Profile
import com.example.clubben.usecases.profile.toDomainProfile
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.utils.catchApiError

class GetCurrentAccountUseCase(
    private val authRepository: AuthRepository,
    private val getProfileUseCase: GetProfileUseCase
) {
    suspend operator fun invoke(): DataState<Account?, ApiError> {
        return catchApiError {
            val acc = authRepository.getCurrentAccount()
            if (acc != null) {
                val profile: Profile =
                    acc.profile?.toDomainProfile() ?: getProfileUseCase(acc.id).getOrThrow()
                acc.toDomainAccount(profile)
            } else {
                null
            }
        }
    }
}