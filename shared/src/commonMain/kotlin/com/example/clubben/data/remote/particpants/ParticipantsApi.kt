package com.example.clubben.data.remote.particpants

import com.example.clubben.data.remote.parties.PagedParties
import com.example.clubben.data.remote.profiles.PagedProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.datetime.DateTimePeriod
import org.koin.core.component.KoinComponent

const val PARTICIPANTS_PATH = "/participants"

class ParticipantsApi(
    private val client: HttpClient
) : KoinComponent {
    suspend fun inviteToParty(pId: String, uId: String, validFor: DateTimePeriod) =
        client.put("$PARTICIPANTS_PATH/$pId/invite/$uId") {
            url {
                parameters.append("validFor", validFor.toString())
            }
        }

    suspend fun declinePartyInvite(pId: String, uId: String) =
        client.delete("$PARTICIPANTS_PATH/$pId/invite/$uId")

    suspend fun acceptPartyInvite(pId: String, uId: String) =
        client.put("$PARTICIPANTS_PATH/$pId/accept/$uId")

    suspend fun getPartyParticipants(pId: String) =
        client.get("$PARTICIPANTS_PATH/$pId").body<PagedProfile>()

    suspend fun getUserPartyParticipation(uId: String) =
        client.get("$PARTICIPANTS_PATH/user/$uId").body<PagedParties>()

    suspend fun joinParty(pId: String) =
        client.put("$PARTICIPANTS_PATH/$pId").body<PartyParticipant>()

    suspend fun leaveParty(pId: String) =
        client.delete("$PARTICIPANTS_PATH/$pId")
}