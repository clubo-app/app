package com.example.clubben.repository.parties

import co.touchlab.kermit.Logger
import com.example.clubben.Constants
import com.example.clubben.di.ClubbenDatabaseWrapper
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.utils.catchApiError
import com.example.clubben.remote.parties.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class PartiesRepositoryImpl : KoinComponent, PartiesRepository {
    private val partiesApi: PartiesApi by inject()

    private val logger = Logger.withTag("PartiesRepositoryImpl")

    private val clubbenDatabase: ClubbenDatabaseWrapper by inject()
    private val partyQueries = clubbenDatabase.instance?.partyQueries

    override suspend fun createParty(req: CreatePartyRequest): DataState<Party, ApiError> {
        return catchApiError {
            val party = partiesApi.createParty(req)
            partyQueries?.insert(party.toDBParty())
            party
        }
    }

    override suspend fun deleteParty(partyId: String): DataState<Unit, ApiError> {
        return catchApiError {
            partiesApi.deleteParty(partyId)
        }
    }

    override suspend fun updateParty(
        partyId: String,
        req: UpdatePartyRequest
    ): DataState<Party, ApiError> {
        return catchApiError {
            val party = partiesApi.updateParty(partyId, req)
            partyQueries?.insert(party.toDBParty())
            party
        }
    }

    override suspend fun getParty(partyId: String): DataState<Party, ApiError> {
        return catchApiError {
            partiesApi.getParty(partyId)
        }
    }

    override suspend fun geoSearch(
        lat: Float,
        lon: Float,
        radius: Int,
        offset: Int?
    ): DataState<List<Party>, ApiError> {
        return catchApiError {
            partiesApi.geoSearch(lat, lon, radius, Constants.limit, true, offset)
        }
    }

    override suspend fun getPartiesByUser(
        userId: String,
        isPublic: Boolean,
        offset: Int?
    ): DataState<List<Party>, ApiError> {
        return catchApiError {
            partiesApi.getPartiesByUser(
                userId,
                Constants.limit,
                offset,
                isPublic
            )
        }
    }
}