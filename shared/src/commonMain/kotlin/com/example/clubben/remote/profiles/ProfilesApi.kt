package com.example.clubben.remote.profiles

import com.example.clubben.remote.auth.RemoteAccount
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent

const val PROFILES_PATH = "/profiles"

class ProfilesApi(
    private val client: HttpClient
) : KoinComponent {
    suspend fun getProfile(id: String): RemoteProfile =
        client.get("$PROFILES_PATH/$id").body<RemoteProfile>()

    suspend fun usernameExists(username: String) =
        client.get("$PROFILES_PATH/username-taken/$username").body<UsernameTakenResponse>()

    suspend fun updateProfile(req: UpdateProfileRequest) = client.patch(PROFILES_PATH) {
        contentType(ContentType.Application.Json)
        setBody(req)
    }.body<RemoteAccount>()
}