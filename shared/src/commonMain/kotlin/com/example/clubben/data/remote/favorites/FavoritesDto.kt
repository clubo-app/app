package com.example.clubben.data.remote.favorites

import com.example.clubben.data.remote.parties.Party
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
data class PagedFavoriteParties(
    @SerialName("favorite_parties")
    val favoriteParties: List<FavoriteParty>,

    @SerialName("next_page")
    val nextPage: String?
)