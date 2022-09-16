package com.example.clubben.data.remote.favorites

import com.example.clubben.data.remote.parties.Party
import com.example.clubben.data.remote.profiles.Profile
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteParty(
    @SerialName("user_id")
    val userId: String,

    val party: Party,

    @SerialName("favorited_at")
    val favoritedAt: LocalDateTime,
)

@Serializable
data class FavorisingProfiles(
    val profile: Profile,

    @SerialName("party_id")
    val partyId: String,

    @SerialName("favorited_at")
    val favoritedAt: LocalDateTime,
)

@Serializable
data class PagedFavoriteParties(
    @SerialName("favorite_parties")
    val favoriteParties: List<FavoriteParty>,

    @SerialName("next_page")
    val nextPage: String?
)

@Serializable
data class PagedFavorisingProfiles(
    @SerialName("favorising_profiles")
    val favorisingProfiles: List<FavorisingProfiles>,

    @SerialName("next_page")
    val nextPage: String?
)