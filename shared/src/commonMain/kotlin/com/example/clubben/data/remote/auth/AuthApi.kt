package com.example.clubben.data.remote.auth

import io.ktor.client.*
import org.koin.core.component.KoinComponent

class AuthApi(
    private val client: HttpClient,
): KoinComponent {

}