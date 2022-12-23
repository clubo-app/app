package com.example.clubben.repository.parties

import com.example.clubben.remote.parties.CreatePartyRequest
import com.example.clubben.remote.parties.Party
import com.example.clubben.remote.parties.UpdatePartyRequest
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState

interface PartiesRepository {
    suspend fun createParty(req: CreatePartyRequest): DataState<Party, ApiError>
    suspend fun deleteParty(partyId: String): DataState<Unit, ApiError>
    suspend fun updateParty(partyId: String, req: UpdatePartyRequest): DataState<Party, ApiError>
    suspend fun getParty(partyId: String): DataState<Party, ApiError>

    suspend fun getPartiesByUser(
        userId: String,
        isPublic: Boolean,
        offset: Int?
    ): DataState<List<Party>, ApiError>

    suspend fun geoSearch(
        lat: Float,
        lon: Float,
        radius: Int,
        offset: Int?
    ): DataState<List<Party>, ApiError>
}