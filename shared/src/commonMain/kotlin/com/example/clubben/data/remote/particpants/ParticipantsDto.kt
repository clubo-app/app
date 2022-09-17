package com.example.clubben.data.remote.particpants

import com.example.clubben.data.remote.parties.Party
import com.example.clubben.data.remote.profiles.Profile
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PartyInvite(
    val profile: Profile? = null,
    val inviter: Profile,
    val party: Party? = null,

    @SerialName("valid_until")
    val validUntil: LocalDateTime
)

@Serializable
data class PagedPartyInvites(
    val invites: List<PartyInvite>,

    @SerialName("next_page")
    val nextPage: String? = ""
)

@Serializable
data class PartyParticipant(
    val profile: Profile,
    val party: Party,
    val requested: Boolean,

    @SerialName("joined_at")
    val joinedAt: LocalDateTime,

    @SerialName("requested_at")
    val requestedAt: LocalDateTime
)

@Serializable
data class PagedPartyParticipant(
    val participants: List<PartyParticipant>,

    @SerialName("next_page")
    val nextPage: String? = ""
)