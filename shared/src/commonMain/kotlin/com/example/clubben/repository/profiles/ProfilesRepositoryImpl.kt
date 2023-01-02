package com.example.clubben.repository.profiles

import co.touchlab.kermit.Logger
import com.example.clubben.db.ClubbenDatabase
import com.example.clubben.remote.auth.RemoteAccount
import com.example.clubben.remote.profiles.ProfilesApi
import com.example.clubben.remote.profiles.RemoteProfile
import com.example.clubben.remote.profiles.UpdateProfileRequest
import com.example.clubben.remote.profiles.toDBProfile
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ProfilesRepositoryImpl : KoinComponent, ProfilesRepository {
    private val profilesApi: ProfilesApi by inject()

    private val logger = Logger.withTag("ProfilesRepositoryImpl")

    private val clubbenDatabase: ClubbenDatabase by inject()
    private val profileQueries = clubbenDatabase.profileQueries
    private val accountQueries = clubbenDatabase.localAccountQueries
    private val friendShipStatusQueries = clubbenDatabase.friendShipStatusQueries

    override suspend fun getProfile(profileId: String): RemoteProfile {
        return profilesApi.getProfile(profileId)
    }

    override suspend fun updateAccount(req: UpdateProfileRequest): RemoteAccount {
        val account = profilesApi.updateProfile(req)

        if (req.email != null) {
            accountQueries.updateEmail(req.email, account.id)
        }
        if (req.username != null || req.firstname != null || req.lastname != null || req.avatar != null) {
            if (account.profile != null) {
                profileQueries.insert(account.profile.toDBProfile())
            }
        }

        return account
    }

    override suspend fun usernameTaken(username: String): Boolean {
        return profilesApi.usernameExists(username).taken
    }
}