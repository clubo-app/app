package com.example.clubben.domain.toast

import com.example.clubben.utils.ApiError
import kotlinx.coroutines.flow.MutableStateFlow
import org.koin.core.component.KoinComponent

class ToastQueue : KoinComponent {
    val currentToast = MutableStateFlow<Toast?>(null)

    fun add(toast: Toast) {
        currentToast.value = toast
    }

    fun add(err: ApiError) {
        currentToast.value = Toast.fromError(err)
    }

    fun remove(id: String) {
        currentToast.value = null
    }

    fun remove() {
        currentToast.value = null
    }
}