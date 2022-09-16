package com.example.clubben.data.remote.auth

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.koin.core.component.KoinComponent

const val AUTH_PATH = "/auth"

class AuthApi(
    private val client: HttpClient,
) : KoinComponent {
    suspend fun login(req: LoginRequest) = client.post("$AUTH_PATH/login") {
        contentType(ContentType.Application.Json)
        setBody(req)
    }.body<LoginResponse>()

    suspend fun register(req: RegisterRequest) = client.post("$AUTH_PATH/register") {
        contentType(ContentType.Application.Json)
        setBody(req)
    }.body<LoginResponse>()
}