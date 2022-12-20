package com.example.clubben.remote.auth

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent

const val AUTH_PATH = "/auth"

class AuthApi(
    private val client: HttpClient,
) : KoinComponent {
    suspend fun me() = client.get("$AUTH_PATH/me").body<RemoteAccount>()

    suspend fun register(req: RegisterRequest) = client.post("$AUTH_PATH/register") {
        contentType(ContentType.Application.Json)
        setBody(req)
    }.body<RegisterResponse>()

    suspend fun registerAnon() = client.get("$AUTH_PATH/register-anon").body<RegisterAnonResponse>()
}