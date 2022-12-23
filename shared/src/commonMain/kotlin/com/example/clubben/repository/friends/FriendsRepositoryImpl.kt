package com.example.clubben.repository.friends

import co.touchlab.kermit.Logger
import com.example.clubben.Constants
import com.example.clubben.di.ClubbenDatabaseWrapper
import com.example.clubben.remote.friends.FriendShipStatus
import com.example.clubben.remote.friends.FriendsApi
import com.example.clubben.remote.friends.toDBFriendShipStatus
import com.example.clubben.remote.profiles.PagedProfile
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.utils.catchApiError
import com.example.clubben.utils.currentTimeWithOffset
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FriendsRepositoryImpl() : KoinComponent, FriendsRepository {
    private val friendsApi: FriendsApi by inject()

    private val logger = Logger.withTag("FriendsRepositoryImpl")

    private val clubbenDatabase: ClubbenDatabaseWrapper by inject()
    private val profileQueries = clubbenDatabase.instance?.profileQueries
    private val friendShipQueries = clubbenDatabase.instance?.friendShipStatusQueries

    override suspend fun createFriendRequest(friendId: String): DataState<FriendShipStatus, ApiError> {
        return catchApiError {
            val friendShipStatus = friendsApi.createFriendRequest(friendId)

            friendShipQueries?.insert(
                friendShipStatus.toDBFriendShipStatus(friendId)
            )
            friendShipStatus
        }
    }

    override suspend fun acceptFriendRequest(friendId: String): DataState<Unit, ApiError> {
        return catchApiError {
            friendsApi.acceptFriendRequest(friendId)
            friendShipQueries?.acceptFriend(friendId, currentTimeWithOffset().toString())
        }
    }

    override suspend fun declineFriendRequest(friendId: String): DataState<Unit, ApiError> {
        return catchApiError {
            friendsApi.declineFriendRequest(friendId)
            friendShipQueries?.delete(friendId)
        }
    }

    override suspend fun removeFriend(friendId: String): DataState<Unit, ApiError> {
        return catchApiError {
            friendsApi.removeFriend(friendId)
            friendShipQueries?.delete(friendId)
        }
    }

    override suspend fun getFriends(
        userId: String,
        accepted: Boolean,
        nextPage: String?
    ): DataState<PagedProfile, ApiError> {
        return catchApiError {
            friendsApi.getFriends(userId, accepted, Constants.limit, nextPage)
        }
    }
}