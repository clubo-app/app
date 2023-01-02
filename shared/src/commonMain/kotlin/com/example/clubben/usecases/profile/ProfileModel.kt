package com.example.clubben.usecases.profile

import com.example.clubben.remote.friends.FriendShipStatus
import com.example.clubben.remote.profiles.RemoteProfile

data class Profile(
    val id: String,
    val username: String,
    val firstname: String,
    val lastname: String? = "",
    val avatar: String? = "",

    val friendCount: Int,
    var friendshipStatus: FriendShipStatus? = null
)

fun RemoteProfile.toDomainProfile() = Profile(
    id, username, firstname, lastname, avatar, friendCount, friendshipStatus
)