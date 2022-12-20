package com.example.clubben.remote.particpants

import com.example.clubben.remote.parties.PagedParties
import com.example.clubben.remote.profiles.PagedProfile
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
        }.body<PartyInvite>()

    suspend fun declinePartyInvite(pId: String, uId: String) =
        client.delete("$PARTICIPANTS_PATH/$pId/invite/$uId")

    suspend fun acceptPartyInvite(pId: String, uId: String) =
        client.put("$PARTICIPANTS_PATH/$pId/accept/$uId").body<PartyParticipant>()

    suspend fun joinParty(pId: String) =
        client.put("$PARTICIPANTS_PATH/$pId").body<PartyParticipant>()

    suspend fun leaveParty(pId: String) =
        client.delete("$PARTICIPANTS_PATH/$pId")

    suspend fun getUserInvites(
        uId: String, limit: Int, nextPage: String?
    ) = client.get("$PARTICIPANTS_PATH/invite/$uId") {
        url {
            parameters.append("limit", limit.toString())
            if (nextPage != null) {
                parameters.append("nextPaged", nextPage)
            }
        }
    }.body<PagedPartyInvites>()

    suspend fun getPartyParticipants(
        pId: String,
        requested: Boolean,
        limit: Int,
        nextPage: String?
    ) =
        client.get("$PARTICIPANTS_PATH/$pId") {
            url {
                parameters.append("limit", limit.toString())
                parameters.append("requested", requested.toString())
                if (nextPage != null) {
                    parameters.append("nextPage", nextPage)
                }
            }
        }.body<PagedProfile>()

    suspend fun getUsersPartyParticipation(
        uId: String, limit: Int, nextPage: String?
    ) =
        client.get("$PARTICIPANTS_PATH/user/$uId") {
            url {
                parameters.append("limit", limit.toString())
                if (nextPage != null) {
                    parameters.append("nextPaged", nextPage)
                }
            }
        }.body<PagedParties>()

    suspend fun getFriendsPartyParticipation(
        uId: String, limit: Int, nextPage: String?
    ) =
        client.get("$PARTICIPANTS_PATH/friends/$uId") {
            url {
                parameters.append("limit", limit.toString())
                if (nextPage != null) {
                    parameters.append("nextPaged", nextPage)
                }
            }
        }.body<PagedParties>()

}