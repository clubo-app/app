package com.example.clubben.repository.favorites

import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.remote.favorites.FavoriteParty
import com.example.clubben.remote.parties.PagedParties
import com.example.clubben.remote.profiles.PagedProfile

interface FavoritesRepository {
    suspend fun favorParty(partyId: String): DataState<FavoriteParty, ApiError>
    suspend fun defavorParty(partyId: String): DataState<Unit, ApiError>
    suspend fun getPartyFavoriteCount(partyId: String): DataState<Int, ApiError>

    suspend fun getFavorisingProfilesByParty(
        partyId: String,
        nextPage: String? = null
    ): DataState<PagedProfile, ApiError>

    suspend fun getFavoritePartiesByUser(
        userId: String,
        nextPage: String? = null
    ): DataState<PagedParties, ApiError>
}