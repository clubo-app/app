package com.example.clubben.usecases.auth

import com.example.clubben.remote.auth.RemoteAccount
import com.example.clubben.usecases.profile.Profile

data class Account(
    val id: String,
    val profile: Profile,
    val email: String?,
    val emailVerified: Boolean,
    val providerId: String,
)

fun RemoteAccount.toDomainAccount(profile: Profile) = Account(
    id, profile, email, emailVerified, providerId
)