package com.example.clubben.domain.auth

import com.example.clubben.repository.auth.AuthRepository

class SignOutUseCase(
    private val authRepository: AuthRepository,
) {
    suspend operator fun invoke() {
        authRepository.signOut()
    }
}