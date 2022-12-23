package com.example.clubben.remote.profiles

import com.example.clubben.db.LocalProfile
import com.example.clubben.remote.friends.FriendShipStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteProfile(
    val id: String,
    val username: String,
    val firstname: String,
    val lastname: String? = "",
    val avatar: String? = "",

    @SerialName("friend_count")
    val friendCount: Int,

    @SerialName("friendship_status")
    var friendshipStatus: FriendShipStatus? = null
)

fun RemoteProfile.toDBProfile() = LocalProfile(
    id = id,
    username = username,
    firstname = firstname,
    lastname = lastname,
    avatar = avatar,
    friend_count = friendCount
)

fun LocalProfile.toRemoteProfile() = RemoteProfile(
    id = id,
    username = username,
    firstname = firstname,
    lastname = lastname,
    avatar = avatar,
    friendCount = friend_count
)

@Serializable
data class PagedProfile(
    val profiles: List<RemoteProfile>,

    @SerialName("next_page")
    val nextPage: String? = ""
)

@Serializable
data class UpdateProfileRequest(
    val email: String? = "",
    val password: String? = "",
    val username: String? = "",
    val firstname: String? = "",
    val lastname: String? = "",
    val avatar: String? = ""
)

@Serializable
data class UsernameTakenResponse(
    val taken: Boolean
)