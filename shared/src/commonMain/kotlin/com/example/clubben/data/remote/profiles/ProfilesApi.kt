package com.example.clubben.data.remote.profiles

import com.example.clubben.data.remote.auth.Account
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent

const val PROFILES_PATH = "/profiles"

class ProfilesApi(
    private val client: HttpClient
) : KoinComponent {
    suspend fun me() = client.get("$PROFILES_PATH/me").body<Account>()

    suspend fun getProfile(id: String) = client.get("$PROFILES_PATH/$id").body<Profile>()

    suspend fun checkUsername(username: String) =
        client.get("$PROFILES_PATH/username-taken/$username").body<UsernameTakenResponse>()

    suspend fun updateProfile(req: UpdateProfileRequest) = client.patch(PROFILES_PATH) {
        contentType(ContentType.Application.Json)
        setBody(req)
    }.body<Profile>()
}