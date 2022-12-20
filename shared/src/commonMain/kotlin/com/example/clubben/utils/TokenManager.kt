package com.example.clubben.utils

import co.touchlab.kermit.Logger
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import io.ktor.client.plugins.auth.providers.*
import org.koin.core.component.KoinComponent

class TokenManager : KoinComponent {
    private val logger = Logger.withTag("AuthRepositoryImpl")

    suspend fun loadTokens(): BearerTokens {
        logger.d { "Loading Tokens" }
        return try {
            val user = Firebase.auth
            val token = user.currentUser?.getIdToken(false)

            logger.d { "Got token: $token" }
            return BearerTokens(
                accessToken = token ?: "",
                refreshToken = "",
            )
        } catch (e: Exception) {
            logger.w(e) { "Exception during loadTokens: $e" }
            BearerTokens(accessToken = "", refreshToken = "")
        }
    }
}