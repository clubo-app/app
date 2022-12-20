package com.example.clubben.repository.auth

import com.example.clubben.remote.auth.RegisterRequest
import com.example.clubben.remote.auth.RemoteAccount
import com.example.clubben.utils.ApiError
import com.example.clubben.utils.DataState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun getMe(): RemoteAccount
    suspend fun login(email: String, password: String): RemoteAccount?
    suspend fun register(req: RegisterRequest): RemoteAccount
    suspend fun googleLogin(
        accessToken: String,
        idToken: String?
    ): DataState<RemoteAccount, ApiError>

    suspend fun signOut()

    fun getCurrentAccount(): RemoteAccount?
    val authStateChanged: Flow<RemoteAccount?>
}