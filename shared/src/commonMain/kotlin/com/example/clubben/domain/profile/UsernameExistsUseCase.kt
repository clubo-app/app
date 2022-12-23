package com.example.clubben.domain.profile

import com.example.clubben.repository.profiles.ProfilesRepository

class UsernameExistsUseCase(
    private val profilesRepository: ProfilesRepository
) {
    suspend operator fun invoke(username: String): Boolean {
        return profilesRepository.usernameExists(username)
    }
}