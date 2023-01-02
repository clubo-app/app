package com.example.clubben.ui.toast

import com.example.clubben.utils.ApiError
import com.rickclephas.kmm.viewmodel.KMMViewModel
import com.rickclephas.kmm.viewmodel.MutableStateFlow
import com.rickclephas.kmp.nativecoroutines.NativeCoroutinesState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

// used in ios to inject singleton
class ToastViewModelHelper : KoinComponent {
    private val vm: ToastViewModel by inject()
    fun vm(): ToastViewModel {
        println("Getting singleton ToastViewmodel instance.")
        return vm
    }
}

class ToastViewModel : KMMViewModel() {
    @NativeCoroutinesState
    var currentToast: MutableStateFlow<Toast?> = MutableStateFlow(viewModelScope, null)

    fun add(toast: Toast) {
        currentToast.update { null }
        currentToast.update { toast }
    }

    fun add(err: ApiError) {
        currentToast.update { null }
        currentToast.update {
            Toast.fromError(err)
        }
    }

    fun remove(id: String) {
        currentToast.update { null }
    }
}