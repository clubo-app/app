package com.example.clubben.di

import com.example.clubben.utils.ApiError
import com.example.clubben.utils.TokenManager
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.auth.*
import io.ktor.client.plugins.auth.providers.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun createHttpClient(
    baseUrl: String,
    enableNetworkLogs: Boolean,
    tokenManager: TokenManager,
    config: Json
) = HttpClient {
    install(ContentNegotiation) {
        json(config)
    }
    install(Auth) {
        bearer {
            loadTokens {
                tokenManager.loadTokens()
            }
        }
    }
    install(DefaultRequest) {
        url(baseUrl)
    }

    expectSuccess = true

    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            val clientException =
                exception as? ClientRequestException ?: return@handleResponseExceptionWithRequest
            val exceptionResponse = clientException.response
            if (exceptionResponse.status.value > 200) {
                val error: ApiError = exceptionResponse.body()
                throw error
            }
        }
    }
    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }
}