package com.example.clubben.data.repository.favorites

import com.example.clubben.data.remote.favorites.FavoriteParty
import com.example.clubben.data.remote.favorites.PagedFavoriteParties
import com.example.clubben.data.remote.profiles.PagedProfile
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun favorParty(partyId: String): FavoriteParty
    suspend fun devaforParty(partyId: String): FavoriteParty

    fun getFavoritePartiesByUser(
        Id: String,
        limit: Int,
        nextPage: String? = null
    ): Flow<PagedFavoriteParties>

    fun getFavorisingProfilesByParty(
        pId: String,
        limit: Int,
        nextPage: String? = null
    ): Flow<PagedProfile>

    fun getPartyFavoriteCount(pId: String): Flow<Int>
}