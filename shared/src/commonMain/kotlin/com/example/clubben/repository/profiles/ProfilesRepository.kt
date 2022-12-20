package com.example.clubben.repository.profiles

import com.example.clubben.remote.auth.RemoteAccount
import com.example.clubben.remote.profiles.Profile
import com.example.clubben.remote.profiles.UpdateProfileRequest
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState

interface ProfilesRepository {
    suspend fun getProfile(profileId: String): Profile
    suspend fun updateAccount(req: UpdateProfileRequest): DataState<RemoteAccount, ApiError>
    suspend fun usernameExists(username: String): DataState<Boolean, ApiError>
}