package com.example.clubben.remote.parties

import com.example.clubben.remote.profiles.Profile
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Party(
    val id: String,

    @SerialName("creator_id")
    val creatorId: String,

    val creator: Profile?,
    val title: String,
    val description: String? = "",
    val lat: Float,
    val lon: Float,

    //TODO: stories
    // needs to have a defualt value of emptyList()

    @SerialName("is_public")
    val isPublic: Boolean,

    @SerialName("music_genre")
    val musicGenre: String,

    @SerialName("max_participants")
    val maxParticipants: Int,

    @SerialName("participation_status")
    val participationStatus: ParticipationStatus? = null,

    @SerialName("street_address")
    val streetAddress: String?,

    @SerialName("postal_code")
    val postalCode: String?,

    val state: String?,
    val country: String?,

    @SerialName("entry_date")
    val entryDate: LocalDateTime,

    @SerialName("created_at")
    val createdAt: LocalDateTime,

    @SerialName("favorite_count")
    val favoriteCount: Int,

    @SerialName("participation_count")
    val participationCount: Int
)

fun Party.toDBParty() = com.example.clubben.db.LocalParty(
    id,
    creatorId,
    title,
    description,
    isPublic,
    musicGenre,
    maxParticipants,
    lat,
    lon,
    streetAddress,
    postalCode,
    state,
    country,
    entryDate.toString(),
    createdAt.toString(),
    favoriteCount,
    participationCount
)

fun com.example.clubben.db.LocalParty.toParty() = Party(
    party_id,
    creator_id,
    null,
    title,
    description,
    lat,
    lon,
    is_public,
    music_genre,
    max_participants,
    null,
    street_address,
    postal_code,
    state,
    country,
    LocalDateTime.parse(entry_date),
    LocalDateTime.parse(created_at),
    favorite_count,
    participation_count
)

@Serializable
data class CreatePartyRequest(
    val title: String,
    val description: String? = "",
    val lat: Float,
    val lon: Float,

    @SerialName("is_public")
    val isPublic: Boolean,

    @SerialName("music_genre")
    val musicGenre: String,

    @SerialName("max_participants")
    val maxParticipants: Int,

    @SerialName("street_address")
    val streetAddress: String,

    @SerialName("postal_code")
    val postalCode: String,

    val state: String,
    val country: String,

    @SerialName("entry_date")
    // https://github.com/Kotlin/kotlinx-datetime/issues/223
    val entryDate: LocalDateTime
)

@Serializable
data class PagedParties(
    val parties: List<Party>,

    @SerialName("next_page")
    val nextPage: String? = ""
)

@Serializable
data class UpdatePartyRequest(
    val title: String,
    val description: String? = "",
    val lat: Float,
    val lon: Float,

    @SerialName("music_genre")
    val musicGenre: String,

    @SerialName("max_participants")
    val maxParticipants: Int,

    @SerialName("street_address")
    val streetAddress: String,

    @SerialName("postal_code")
    val postalCode: String,

    val state: String,
    val country: String,

    @SerialName("entry_date")
    val entryDate: LocalDateTime
)

@Serializable
data class ParticipationStatus(
    val requested: Boolean,
    val participating: Boolean,

    @SerialName("requested_at")
    val requestedAt: LocalDateTime? = null,

    @SerialName("joined_at")
    val joinedAt: LocalDateTime? = null
)