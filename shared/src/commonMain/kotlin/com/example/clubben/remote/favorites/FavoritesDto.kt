package com.example.clubben.remote.favorites

import com.example.clubben.remote.parties.Party
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteParty(
    @SerialName("user_id")
    val userId: String,

    val party: Party,

    @SerialName("favorited_at")
    val favoritedAt: LocalDateTime
)

fun FavoriteParty.toDBFavoriteParty() = com.example.clubben.db.LocalFavoriteParty(
    party.id,
    favoritedAt.toString()
)

@Serializable
data class PagedFavoriteParties(
    @SerialName("favorite_parties")
    val favoriteParties: List<FavoriteParty>,

    @SerialName("next_page")
    val nextPage: String? = ""
)

@Serializable
data class FavoriteCount(
    @SerialName("favorite_count")
    val count: Int
)