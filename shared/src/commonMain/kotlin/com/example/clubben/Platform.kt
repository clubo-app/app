package com.example.clubben

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform