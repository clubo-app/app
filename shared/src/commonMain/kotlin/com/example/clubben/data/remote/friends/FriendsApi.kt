package com.example.clubben.data.remote.friends

import com.example.clubben.data.remote.particpants.PARTICIPANTS_PATH
import com.example.clubben.data.remote.profiles.PagedProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent

const val FRIEND_PATH = "/friends"

class FriendsApi(
    private val client: HttpClient
) : KoinComponent {
    suspend fun createFriendRequest(friendId: String) =
        client.put("$FRIEND_PATH/request/$friendId")

    suspend fun declineFriendRequest(friendId: String) =
        client.delete("$PARTICIPANTS_PATH/requests/$friendId")

    suspend fun acceptFriendRequest(friendId: String) =
        client.put("$PARTICIPANTS_PATH/accept/$friendId")

    suspend fun removeFriend(friendId: String) =
        client.put("$PARTICIPANTS_PATH/$friendId")

    suspend fun getFriends(uId: String, accepted: Boolean, limit: Int, nextPage: String?) =
        client.get("$PARTICIPANTS_PATH/$uId").body<PagedProfile>()
}
