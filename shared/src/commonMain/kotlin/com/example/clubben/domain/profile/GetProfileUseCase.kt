package com.example.clubben.domain.profile

import com.example.clubben.repository.profiles.ProfilesRepository

class GetProfileUseCase(
    private val profilesRepository: ProfilesRepository
) {

    suspend operator fun invoke(id: String): Profile {
        return profilesRepository.getProfile(id).toDomainProfile()
    }
}