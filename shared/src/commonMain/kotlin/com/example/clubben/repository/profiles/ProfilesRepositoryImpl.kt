package com.example.clubben.repository.profiles

import co.touchlab.kermit.Logger
import com.example.clubben.di.ClubbenDatabaseWrapper
import com.example.clubben.remote.auth.RemoteAccount
import com.example.clubben.remote.profiles.Profile
import com.example.clubben.remote.profiles.ProfilesApi
import com.example.clubben.remote.profiles.UpdateProfileRequest
import com.example.clubben.remote.profiles.toDBProfile
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.utils.catchApiError
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfilesRepositoryImpl : KoinComponent, ProfilesRepository {
    private val profilesApi: ProfilesApi by inject()

    private val logger = Logger.withTag("ProfilesRepositoryImpl")

    private val clubbenDatabase: ClubbenDatabaseWrapper by inject()
    private val profileQueries = clubbenDatabase.instance?.profileQueries
    private val accountQueries = clubbenDatabase.instance?.localAccountQueries
    private val friendShipStatusQueries = clubbenDatabase.instance?.friendShipStatusQueries

    override suspend fun getProfile(profileId: String): Profile {
        return profilesApi.getProfile(profileId)
    }

    override suspend fun updateAccount(req: UpdateProfileRequest): DataState<RemoteAccount, ApiError> {
        return catchApiError {
            val account = profilesApi.updateProfile(req)

            if (req.email != null) {
                accountQueries?.updateEmail(req.email, account.id)
            }
            if (req.username != null || req.firstname != null || req.lastname != null || req.avatar != null) {
                if (account.profile != null) {
                    profileQueries?.insert(account.profile.toDBProfile())
                }
            }

            account
        }
    }

    override suspend fun usernameExists(username: String): DataState<Boolean, ApiError> {
        return catchApiError {
            profilesApi.usernameExists(username).taken
        }
    }
}