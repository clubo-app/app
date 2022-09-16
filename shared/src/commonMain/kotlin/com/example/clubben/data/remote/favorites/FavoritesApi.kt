package com.example.clubben.data.remote.favorites

import com.example.clubben.data.remote.profiles.PagedProfile
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.koin.core.component.KoinComponent

const val FAVORITE_PATH = "/favorites"

class FavoritesApi(
    private val client: HttpClient
) : KoinComponent {
    suspend fun favorParty(pId: String) = client.put("$FAVORITE_PATH/$pId").body<FavoriteParty>()

    suspend fun defavorParty(pId: String) =
        client.delete("$FAVORITE_PATH/$pId").body<FavoriteParty>()

    suspend fun getFavoritePartiesByUser(uId: String, limit: Int, nextPage: String? = null) =
        client.get("$FAVORITE_PATH/user/$uId") {
            url {
                parameters.append("limit", limit.toString())
                if (nextPage != null) {
                    parameters.append("nextPage", nextPage)
                }
            }
        }.body<PagedFavoriteParties>()

    suspend fun getFavorisingProfilesByParty(pId: String, limit: Int, nextPage: String? = null) {
        client.get("$FAVORITE_PATH/$pId") {
            url {
                parameters.append("limit", limit.toString())
                if (nextPage != null) {
                    parameters.append("nextPage", nextPage)
                }
            }
        }.body<PagedProfile>()
    }
}