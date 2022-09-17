package com.example.clubben.di

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSerializationApi::class)
fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true; explicitNulls = false }

fun createHttpClient(
    httpClientEngine: HttpClientEngine,
    baseUrl: String,
    enableNetworkLogs: Boolean
) = HttpClient(httpClientEngine) {
    install(ContentNegotiation) {
        json(createJson())
    }
    install(DefaultRequest) {
        url(baseUrl)
    }

    if (enableNetworkLogs) {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.INFO
        }
    }
}