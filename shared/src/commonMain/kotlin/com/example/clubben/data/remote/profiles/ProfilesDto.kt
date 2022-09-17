package com.example.clubben.data.remote.profiles

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Profile(
    val id: String,
    val username: String,
    val firstname: String,
    val lastname: String? = "",
    val avatar: String? = "",
    val friendCount: Int,

    @SerialName("friendship_status")
    val FriendshipStatus: FriendShipStatus? = null
)

@Serializable
data class PagedProfile(
    val profiles: List<Profile>,

    @SerialName("next_page")
    val nextPage: String? = ""
)

@Serializable
data class FriendShipStatus(
    @SerialName("is_friend")
    val isFriend: Boolean,

    @SerialName("incoming_request")
    val incomingRequest: Boolean,

    @SerialName("outgoing_request")
    val outgoingRequest: Boolean,

    @SerialName("requested_at")
    val requestedAt: LocalDateTime? = null,

    @SerialName("accepted_at")
    val acceptedAt: LocalDateTime? = null
)

@Serializable
data class UpdateProfileRequest(
    val id: String,
    val username: String,
    val firstname: String,
    val lastname: String? = "",
    val avatar: String? = ""
)

@Serializable
data class UsernameTakenResponse(
    val taken: Boolean
)