package com.example.clubben.repository.participants

import co.touchlab.kermit.Logger
import com.example.clubben.Constants
import com.example.clubben.remote.particpants.PagedPartyInvites
import com.example.clubben.remote.particpants.ParticipantsApi
import com.example.clubben.remote.particpants.PartyInvite
import com.example.clubben.remote.particpants.PartyParticipant
import com.example.clubben.remote.parties.PagedParties
import com.example.clubben.remote.profiles.PagedProfile
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import com.example.clubben.utils.catchApiError
import kotlinx.datetime.DateTimePeriod
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ParticipantsRepositoryImpl : KoinComponent, ParticipantsRepository {
    private val participantsApi: ParticipantsApi by inject()

    private val logger = Logger.withTag("ParticipantsRepositoryImpl")

    override suspend fun joinParty(partyId: String): DataState<PartyParticipant, ApiError> {
        return catchApiError {
            participantsApi.joinParty(partyId)
        }
    }

    override suspend fun acceptPartyInvite(
        partyId: String,
        userId: String
    ): DataState<PartyParticipant, ApiError> {
        return catchApiError {
            participantsApi.acceptPartyInvite(partyId, userId)
        }
    }

    override suspend fun declinePartyInvite(
        partyId: String,
        userId: String
    ): DataState<Unit, ApiError> {
        return catchApiError {
            participantsApi.declinePartyInvite(partyId, userId)
        }
    }

    override suspend fun leaveParty(partyId: String): DataState<Unit, ApiError> {
        return catchApiError {
            participantsApi.leaveParty(partyId)
        }
    }

    override suspend fun inviteToParty(
        partyId: String,
        userId: String,
        validFor: DateTimePeriod
    ): DataState<PartyInvite, ApiError> {
        return catchApiError {
            participantsApi.inviteToParty(partyId, userId, validFor)
        }
    }

    override suspend fun getUserInvites(
        userId: String,
        nextPage: String?
    ): DataState<PagedPartyInvites, ApiError> {
        return catchApiError {
            participantsApi.getUserInvites(userId, Constants.limit, nextPage)
        }
    }

    override suspend fun getFriendsPartyParticipation(
        userId: String,
        nextPage: String?
    ): DataState<PagedParties, ApiError> {
        return catchApiError {
            participantsApi.getFriendsPartyParticipation(userId, Constants.limit, nextPage)
        }
    }

    override suspend fun getUsersPartyParticipation(
        userId: String,
        nextPage: String?
    ): DataState<PagedParties, ApiError> {
        return catchApiError {
            participantsApi.getUsersPartyParticipation(userId, Constants.limit, nextPage)
        }
    }

    override suspend fun getPartyParticipants(
        partyId: String,
        requested: Boolean,
        nextPage: String?
    ): DataState<PagedProfile, ApiError> {
        return catchApiError {
            participantsApi.getPartyParticipants(partyId, requested, Constants.limit, nextPage)
        }
    }
}