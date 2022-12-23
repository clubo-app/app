package com.example.clubben.repository.friends

import com.example.clubben.remote.friends.FriendShipStatus
import com.example.clubben.remote.profiles.PagedProfile
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState

interface FriendsRepository {
    suspend fun createFriendRequest(friendId: String): DataState<FriendShipStatus, ApiError>
    suspend fun declineFriendRequest(friendId: String): DataState<Unit, ApiError>
    suspend fun acceptFriendRequest(friendId: String): DataState<Unit, ApiError>
    suspend fun removeFriend(friendId: String): DataState<Unit, ApiError>
    suspend fun getFriends(
        userId: String,
        accepted: Boolean,
        nextPage: String?
    ): DataState<PagedProfile, ApiError>
}