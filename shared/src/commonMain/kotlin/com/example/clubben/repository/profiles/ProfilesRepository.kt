package com.example.clubben.repository.profiles

import com.example.clubben.remote.auth.RemoteAccount
import com.example.clubben.remote.profiles.RemoteProfile
import com.example.clubben.remote.profiles.UpdateProfileRequest

interface ProfilesRepository {
    suspend fun getProfile(profileId: String): RemoteProfile
    suspend fun updateAccount(req: UpdateProfileRequest): RemoteAccount
    suspend fun usernameTaken(username: String): Boolean
}