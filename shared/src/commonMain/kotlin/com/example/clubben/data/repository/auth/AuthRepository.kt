package com.example.clubben.data.repository.auth

import com.example.clubben.data.remote.auth.LoginRequest
import com.example.clubben.data.remote.auth.LoginResponse
import com.example.clubben.data.remote.auth.RegisterRequest

interface AuthRepository {
    suspend fun login(req: LoginRequest): LoginResponse
    suspend fun register(req: RegisterRequest): RegisterRequest
}