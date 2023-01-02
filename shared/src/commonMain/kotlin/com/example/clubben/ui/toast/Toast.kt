package com.example.clubben.ui.toast

import com.example.clubben.utils.ApiError
import com.example.clubben.utils.UUID

enum class ToastVariant {
    SUCCESS, WARNING, ERROR
}

data class Toast(
    val id: String = UUID.new(),
    val message: String = "",
    val variant: ToastVariant = ToastVariant.ERROR
) {
    companion object {
        fun fromError(err: ApiError): Toast {
            return Toast(variant = ToastVariant.ERROR, message = err.message)
        }
    }
}
