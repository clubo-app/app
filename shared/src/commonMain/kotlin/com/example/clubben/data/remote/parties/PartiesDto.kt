package com.example.clubben.data.remote.parties

import com.example.clubben.data.remote.profiles.Profile
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Party(
    val id: String,
    val creator: Profile?,
    val title: String,
    val description: String?,
    val lat: Float,
    val lon: Float,

    //TODO: stories

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
    val entryDate: LocalDateTime,

    @SerialName("created_at")
    val createdAt: LocalDateTime,

    @SerialName("favorite_count")
    val favorite_count: Int
)

@Serializable
data class CreatePartyRequest(
    val title: String,
    val description: String?,
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
    val parties: List<Party>
)

@Serializable
data class UpdatePartyRequest(
    val title: String,
    val description: String?,
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
    val entryDate: LocalDateTime,
)