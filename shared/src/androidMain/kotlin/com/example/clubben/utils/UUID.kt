package com.example.clubben.utils

actual class UUID {
    actual companion object {
        actual fun new(): String = java.util.UUID.randomUUID().toString()
    }
}