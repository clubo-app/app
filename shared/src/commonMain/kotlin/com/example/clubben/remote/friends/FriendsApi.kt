package com.example.clubben.remote.friends

import com.example.clubben.remote.particpants.PARTICIPANTS_PATH
import com.example.clubben.remote.profiles.PagedProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent

const val FRIEND_PATH = "/friends"

class FriendsApi(
    private val client: HttpClient
) : KoinComponent {
    suspend fun createFriendRequest(friendId: String) =
        client.put("$FRIEND_PATH/request/$friendId").body<FriendShipStatus>()

    suspend fun declineFriendRequest(friendId: String) =
        client.delete("$PARTICIPANTS_PATH/requests/$friendId")

    suspend fun acceptFriendRequest(friendId: String) =
        client.put("$PARTICIPANTS_PATH/accept/$friendId")

    suspend fun removeFriend(friendId: String) =
        client.put("$PARTICIPANTS_PATH/$friendId")

    suspend fun getFriends(userId: String, accepted: Boolean, limit: Int, nextPage: String?) =
        client.get("$PARTICIPANTS_PATH/$userId") {
            url {
                parameters.append("accepted", accepted.toString())
                parameters.append("limit", limit.toString())
                if (nextPage != null) {
                    parameters.append("nextPage", nextPage.toString())
                }
            }
        }.body<PagedProfile>()

}
