package com.example.clubben.utils

import platform.Foundation.NSUUID

actual class UUID() {

    actual companion object {
        actual fun new(): String = NSUUID().UUIDString()
    }
}