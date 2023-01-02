package com.example.clubben.usecases.profile

import com.example.clubben.repository.profiles.ProfilesRepository
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.utils.catchApiError

class UsernameTakenUseCase(
    private val profilesRepository: ProfilesRepository
) {
    suspend operator fun invoke(username: String): DataState<Boolean, ApiError> {
        return catchApiError {
            profilesRepository.usernameTaken(username)
        }
    }
}