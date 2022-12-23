package com.example.clubben.remote.auth

import com.example.clubben.remote.profiles.RemoteProfile
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteAccount(
    val id: String,
    val profile: RemoteProfile? = null,
    val email: String?,

    @SerialName("email_verified")
    val emailVerified: Boolean,

    @SerialName("provider_id")
    val providerId: String,
)

@Serializable
data class RegisterAnonResponse(
    @SerialName("custom_token")
    val customToken: String
)

@Serializable
data class RegisterResponse(
    val account: RemoteAccount,

    @SerialName("custom_token")
    val customToken: String
)

@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String,
    val firstname: String,
    val lastname: String? = null,
    val avatar: String? = null, // base64 encoded image
)