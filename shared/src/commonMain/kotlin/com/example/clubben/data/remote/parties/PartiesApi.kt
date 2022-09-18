package com.example.clubben.data.remote.parties

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent

const val PARTIES_PATH = "/parties"

class PartiesApi(
    private val client: HttpClient
) : KoinComponent {
    suspend fun createParty(req: CreatePartyRequest) = client.post(PARTIES_PATH) {
        contentType(ContentType.Application.Json)
        setBody(req)
    }.body<Party>()

    suspend fun deleteParty(id: String) = client.delete("$PARTIES_PATH/$id")

    suspend fun getParty(id: String) = client.get("$PARTIES_PATH/$id").body<Party>()

    suspend fun getPartiesByUser(uId: String, limit: Int, offset: Int?, isPublic: Boolean) =
        client.get("$PARTIES_PATH/user/${uId}") {
            url {
                parameters.append("limit", limit.toString())
                parameters.append("is_public", isPublic.toString())
                if (offset != null) {
                    parameters.append("offset", offset.toString())
                }
            }
        }.body<List<Party>>()

    suspend fun updateParty(pId: String, req: UpdatePartyRequest) =
        client.patch("$PARTIES_PATH/$pId") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }

    suspend fun geoSearch(
        lat: Float,
        lon: Float,
        radius: Int,
        limit: Int,
        isPublic: Boolean,
        offset: Int?
    ) =
        client.get("$PARTIES_PATH/geo-search/$lat/$lon") {
            url {
                parameters.append("limit", limit.toString())
                parameters.append("radius", radius.toString())
                parameters.append("is_public", isPublic.toString())
                if (offset != null) {
                    parameters.append("offset", offset.toString())
                }
            }
        }.body<List<Party>>()
}