package com.example.clubben.domain.auth

import com.example.clubben.domain.profile.Profile

data class Account(
    val id: String,
    val profile: Profile,
    val email: String?,
    val emailVerified: Boolean,
    val providerId: String,
)