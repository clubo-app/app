package com.example.clubben.repository.favorites

import co.touchlab.kermit.Logger
import com.example.clubben.Constants
import com.example.clubben.di.ClubbenDatabaseWrapper
import com.example.clubben.remote.favorites.FavoriteParty
import com.example.clubben.remote.favorites.FavoritesApi
import com.example.clubben.remote.favorites.toDBFavoriteParty
import com.example.clubben.remote.parties.PagedParties
import com.example.clubben.remote.profiles.PagedProfile
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.utils.TokenManager
import com.example.clubben.utils.catchApiError
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavoritesRepositoryImpl(
    val tokenManager: TokenManager
) : KoinComponent, FavoritesRepository {
    private val favoritesApi: FavoritesApi by inject()

    private val logger = Logger.withTag("FavoritesRepositoryImpl")

    private val clubbenDatabase: ClubbenDatabaseWrapper by inject()
    private val favoritePartyQueries = clubbenDatabase.instance?.favoritePartyQueries


    override suspend fun favorParty(partyId: String): DataState<FavoriteParty, ApiError> {
        return catchApiError {
            val res = favoritesApi.favorParty(partyId)
            favoritePartyQueries?.insert(res.toDBFavoriteParty())
            res
        }
    }

    override suspend fun defavorParty(partyId: String): DataState<Unit, ApiError> {
        return catchApiError {
            favoritesApi.defavorParty(partyId)
            favoritePartyQueries?.delete(partyId)
        }
    }

    override suspend fun getPartyFavoriteCount(partyId: String): DataState<Int, ApiError> {
        return catchApiError {
            favoritesApi.getPartyFavoriteCount(partyId).count
        }
    }

    override suspend fun getFavorisingProfilesByParty(
        partyId: String,
        nextPage: String?
    ): DataState<PagedProfile, ApiError> {
        return catchApiError {
            favoritesApi.getFavorisingProfilesByParty(partyId, Constants.limit, nextPage)
        }
    }

    override suspend fun getFavoritePartiesByUser(
        userId: String,
        nextPage: String?
    ): DataState<PagedParties, ApiError> {
        return catchApiError {
            favoritesApi.getFavoritePartiesByUser(userId, Constants.limit, nextPage)
        }
    }
}