package com.example.clubben.remote.friends

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


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

fun FriendShipStatus.toDBFriendShipStatus(friendId: String) =
    com.example.clubben.db.LocalFriendShipStatus(
        friendId,
        isFriend,
        incomingRequest,
        outgoingRequest,
        requestedAt.toString(),
        acceptedAt.toString()
    )

fun com.example.clubben.db.LocalFriendShipStatus.toFriendShipStatus() = FriendShipStatus(
    is_friend,
    incoming_request,
    outgoing_request,
    if (requested_at != null) LocalDateTime.parse(requested_at) else null,
    if (accepted_at != null) LocalDateTime.parse(accepted_at) else null
)