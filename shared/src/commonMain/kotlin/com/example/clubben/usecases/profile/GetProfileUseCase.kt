package com.example.clubben.usecases.profile

import com.example.clubben.repository.profiles.ProfilesRepository
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.utils.catchApiError

class GetProfileUseCase(
    private val profilesRepository: ProfilesRepository
) {

    suspend operator fun invoke(id: String): DataState<Profile, ApiError> {
        return catchApiError {
            profilesRepository.getProfile(id).toDomainProfile()
        }
    }
}