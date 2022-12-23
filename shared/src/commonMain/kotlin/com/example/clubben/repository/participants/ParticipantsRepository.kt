package com.example.clubben.repository.participants

import com.example.clubben.remote.particpants.PagedPartyInvites
import com.example.clubben.remote.particpants.PartyInvite
import com.example.clubben.remote.particpants.PartyParticipant
import com.example.clubben.remote.parties.PagedParties
import com.example.clubben.remote.profiles.PagedProfile
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import kotlinx.datetime.DateTimePeriod

interface ParticipantsRepository {
    suspend fun inviteToParty(
        partyId: String,
        userId: String,
        validFor: DateTimePeriod
    ): DataState<PartyInvite, ApiError>

    suspend fun declinePartyInvite(partyId: String, userId: String): DataState<Unit, ApiError>
    suspend fun acceptPartyInvite(
        partyId: String,
        userId: String
    ): DataState<PartyParticipant, ApiError>

    suspend fun joinParty(partyId: String): DataState<PartyParticipant, ApiError>
    suspend fun leaveParty(partyId: String): DataState<Unit, ApiError>

    suspend fun getUserInvites(
        userId: String,
        nextPage: String?
    ): DataState<PagedPartyInvites, ApiError>

    suspend fun getPartyParticipants(
        partyId: String,
        requested: Boolean,
        nextPage: String?
    ): DataState<PagedProfile, ApiError>

    suspend fun getUsersPartyParticipation(
        userId: String,
        nextPage: String?
    ): DataState<PagedParties, ApiError>

    suspend fun getFriendsPartyParticipation(
        userId: String,
        nextPage: String?
    ): DataState<PagedParties, ApiError>
}