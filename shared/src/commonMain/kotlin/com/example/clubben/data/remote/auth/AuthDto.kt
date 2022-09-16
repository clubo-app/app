package com.example.clubben.data.remote.auth

import com.example.clubben.data.remote.profiles.Profile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Tokens(
    @SerialName("access_token")
    val accessToken: String,

    @SerialName("refresh_token")
    val refreshToken: String,
)

@Serializable
data class Account(
    val id: String,

    // TODO: get profile
    val profile: Profile,
    val email: String,
)

@Serializable
data class LoginResponse(
    val tokens: Tokens,
    val account: Account,
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String,
    val firstname: String,
    val lastname: String?,
    val avatar: String?, // base64 encoded image
)